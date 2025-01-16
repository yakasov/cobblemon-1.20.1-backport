/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.spawning

import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition
import com.cobblemon.mod.common.api.drop.DropEntry
import com.cobblemon.mod.common.api.drop.ItemDropMethod
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.context.RegisteredSpawningContext
import com.cobblemon.mod.common.api.spawning.detail.PossibleHeldItem
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.cobblemon.mod.common.util.adapters.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mojang.datafixers.util.Either
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.structure.Structure

/**
 * Object responsible for actually deserializing spawns. You should probably
 * rely on this object for it as it would make your code better future proofed.
 *
 * @author Hiroku
 * @since January 31st, 2022
 */
object SpawnLoader {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .setLenient()
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition::class.java, Biome::class.java).type, BiomeLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition::class.java, Block::class.java).type, BlockLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition::class.java, Fluid::class.java).type, FluidLikeConditionAdapter)
        .registerTypeAdapter(
            TypeToken.getParameterized(
                Either::class.java,
                Identifier::class.java,
                TypeToken.getParameterized(
                    TagKey::class.java,
                    Structure::class.java
                ).type
            ).type,
            EitherIdentifierOrTagAdapter(RegistryKeys.STRUCTURE)
        )
        .registerTypeAdapter(RegisteredSpawningContext::class.java, RegisteredSpawningContextAdapter)
        .registerTypeAdapter(Identifier::class.java, IdentifierAdapter)
        .registerTypeAdapter(SpawnDetail::class.java, SpawnDetailAdapter)
        .registerTypeAdapter(DropEntry::class.java, DropEntryAdapter)
        .registerTypeAdapter(SpawningCondition::class.java, SpawningConditionAdapter)
        .registerTypeAdapter(TimeRange::class.java, IntRangesAdapter(TimeRange.timeRanges) { TimeRange(*it) })
        .registerTypeAdapter(MoonPhaseRange::class.java, IntRangesAdapter(MoonPhaseRange.moonPhaseRanges) { MoonPhaseRange(*it) })
        .registerTypeAdapter(ItemDropMethod::class.java, ItemDropMethod.adapter)
        .registerTypeAdapter(PokemonProperties::class.java, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(SpawnBucket::class.java, SpawnBucketAdapter)
        .registerTypeAdapter(NbtCompound::class.java, NbtCompoundAdapter)
        .registerTypeAdapter(IntRange::class.java, IntRangeAdapter)
        .registerTypeAdapter(PossibleHeldItem::class.java, PossibleHeldItemAdapter)
        .create()

    var deserializingConditionClass: Class<out SpawningCondition<*>>? = null
}
