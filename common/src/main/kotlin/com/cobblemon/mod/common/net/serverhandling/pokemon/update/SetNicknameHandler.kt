/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.serverhandling.pokemon.update

import com.cobblemon.mod.common.CobblemonNetwork.sendPacket
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.PokemonNicknamedEvent
import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.cobblemon.mod.common.api.storage.PokemonStore
import com.cobblemon.mod.common.api.storage.pc.link.PCLinkManager
import com.cobblemon.mod.common.net.messages.client.pokemon.update.NicknameUpdatePacket
import com.cobblemon.mod.common.net.messages.client.storage.pc.ClosePCPacket
import com.cobblemon.mod.common.net.messages.server.pokemon.update.SetNicknamePacket
import com.cobblemon.mod.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

object SetNicknameHandler : ServerNetworkPacketHandler<SetNicknamePacket> {
    override fun handle(packet: SetNicknamePacket, server: MinecraftServer, player: ServerPlayerEntity) {
        val pokemonStore: PokemonStore<*> = if (packet.isParty) {
            player.party()
        } else {
            PCLinkManager.getPC(player) ?: return run { ClosePCPacket(null).sendToPlayer(player) }
        }

        val pokemon = pokemonStore[packet.pokemonUUID] ?: return

        CobblemonEvents.POKEMON_NICKNAMED.postThen(
            event = PokemonNicknamedEvent(
                player = player,
                pokemon = pokemon,
                nickname = packet.nickname?.let { Text.literal(it) }
            ),
            ifSucceeded = {
                pokemon.nickname = it.nickname
            },
            ifCanceled = {
                player.sendPacket(NicknameUpdatePacket({ pokemon }, pokemon.nickname))
            }
        )
    }
}