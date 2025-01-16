/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.pokemon.evolution

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionTestedEvent
import com.cobblemon.mod.common.api.moves.BenchedMove
import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.evolution.requirement.EvolutionRequirement
import com.cobblemon.mod.common.api.scheduling.afterOnServer
import com.cobblemon.mod.common.entity.generic.GenericBedrockEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.activestate.ShoulderedState
import com.cobblemon.mod.common.pokemon.evolution.variants.ItemInteractionEvolution
import com.cobblemon.mod.common.pokemon.evolution.variants.LevelUpEvolution
import com.cobblemon.mod.common.pokemon.evolution.variants.TradeEvolution
import com.cobblemon.mod.common.util.cobblemonResource
import com.cobblemon.mod.common.util.lang
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory

/**
 * Represents an evolution of a [Pokemon], this is the server side counterpart of [EvolutionDisplay].
 * Following Pokémon these can be triggered by 3 possible events, level ups, trades or using an item.
 * For the default implementations see [LevelUpEvolution], [TradeEvolution] & [ItemInteractionEvolution].
 * Also see [PassiveEvolution] & [ContextEvolution].
 *
 * @author Licious
 * @since March 19th, 2022
 */
interface Evolution : EvolutionLike {

    /**
     * The result of this evolution.
     */
    val result: PokemonProperties

    /**
     * If this evolution allows the user to choose when to start it or not.
     */
    var optional: Boolean

    /**
     * If this [Evolution] will consume the [Pokemon.heldItem]
     */
    var consumeHeldItem: Boolean

    /**
     * The [EvolutionRequirement]s behind this evolution.
     */
    val requirements: MutableSet<EvolutionRequirement>

    /**
     * The [MoveTemplate]s that will be offered to be learnt upon evolving.
     */
    val learnableMoves: MutableSet<MoveTemplate>

    /**
     * Checks if the given [Pokemon] passes all the conditions and is ready to evolve.
     *
     * @param pokemon The [Pokemon] being queried.
     * @return If the [Evolution] can start.
     */
    fun test(pokemon: Pokemon): Boolean {
        val result = this.requirements.all { requirement -> requirement.check(pokemon) }
        val event = EvolutionTestedEvent(pokemon, this, result, result)
        CobblemonEvents.EVOLUTION_TESTED.post(event)
        return event.result
    }

    /**
     * Starts this evolution or queues it if [optional] is true.
     * Side effects may occur based on [consumeHeldItem].
     *
     * @param pokemon The [Pokemon] being evolved.
     */
    fun evolve(pokemon: Pokemon): Boolean {
        if (this.consumeHeldItem) {
            pokemon.swapHeldItem(ItemStack.EMPTY)
        }
        if (this.optional) {
            // All the networking is handled under the hood, see EvolutionController.
            return pokemon.evolutionProxy.server().add(this)
        }
        this.forceEvolve(pokemon)
        return true
    }


    /**
     * Starts this evolution as soon as possible.
     * This will not present a choice to the client regardless of [optional].
     *
     * @param pokemon The [Pokemon] being evolved.
     */
    fun forceEvolve(pokemon: Pokemon) {
        // This is a switch to enable/disable the evolution effect while we get particles improved
        val useEvolutionEffect = false

        if (pokemon.state is ShoulderedState) {
            pokemon.tryRecallWithAnimation()
        }

        val pokemonEntity = pokemon.entity
        if (pokemonEntity == null || !useEvolutionEffect) {
            evolutionMethod(pokemon)
        } else {
            pokemonEntity.evolutionEntity = pokemon.getOwnerPlayer()?.let { GenericBedrockEntity(world = it.world) }
            val evolutionEntity = pokemon.entity!!.evolutionEntity
            evolutionEntity?.apply {
                category = cobblemonResource("evolution")
                colliderHeight = pokemonEntity.height
                colliderWidth = pokemonEntity.width
                scale = pokemonEntity.scaleFactor
                syncAge = true // Otherwise particle animation will be starting from zero even if you come along partway through
                setPosition(pokemonEntity.x, pokemonEntity.y, pokemonEntity.z)
            }
            pokemon.getOwnerPlayer()?.world?.spawnEntity(evolutionEntity)
            afterOnServer(seconds = 9F) {
                if (!pokemonEntity.isRemoved) {
                    evolutionMethod(pokemon)
                    afterOnServer(seconds = 1.5F) { pokemonEntity.cry() }
                    afterOnServer(seconds = 3F) {
                        if (evolutionEntity != null) {
                            evolutionEntity.kill()
                            if (!pokemonEntity.isRemoved) {
                                pokemonEntity.evolutionEntity = null
                            }
                        }
                    }
                }
            }
        }
    }

    fun evolutionMethod(pokemon: Pokemon) {
        this.result.apply(pokemon)
        this.learnableMoves.forEach { move ->
            if (pokemon.moveSet.hasSpace()) {
                pokemon.moveSet.add(move.create())
            } else {
                pokemon.benchedMoves.add(BenchedMove(move, 0))
            }
            pokemon.getOwnerPlayer()?.sendMessage(lang("experience.learned_move", pokemon.getDisplayName(), move.displayName))
        }
        // we want to instantly tick for example you might only evolve your Bulbasaur at level 34 so Venusaur should be immediately available
        pokemon.lockedEvolutions.filterIsInstance<PassiveEvolution>().forEach { evolution -> evolution.attemptEvolution(pokemon) }
        pokemon.getOwnerPlayer()?.playSound(CobblemonSounds.EVOLVING, SoundCategory.NEUTRAL, 1F, 1F)
        CobblemonEvents.EVOLUTION_COMPLETE.post(EvolutionCompleteEvent(pokemon, this))
    }

    fun applyTo(pokemon: Pokemon) {
        result.apply(pokemon)
    }
}