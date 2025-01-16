/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.server.storage.pc

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.api.storage.pc.PCPosition
import com.cobblemon.mod.common.api.storage.pc.PCPosition.Companion.readPCPosition
import com.cobblemon.mod.common.api.storage.pc.PCPosition.Companion.writePCPosition
import com.cobblemon.mod.common.net.serverhandling.storage.pc.SwapPCPokemonHandler
import com.cobblemon.mod.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.PacketByteBuf

/**
 * Tells the server to swap two Pokémon in the PC linked to the player.
 *
 * Handled by [SwapPCPokemonHandler].
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
class SwapPCPokemonPacket(val pokemon1ID: UUID, val position1: PCPosition, val pokemon2ID: UUID, val position2: PCPosition) : NetworkPacket<SwapPCPokemonPacket> {
    override val id = ID
    override fun encode(buffer: PacketByteBuf) {
        buffer.writeUuid(pokemon1ID)
        buffer.writePCPosition(position1)
        buffer.writeUuid(pokemon2ID)
        buffer.writePCPosition(position2)
    }

    companion object {
        val ID = cobblemonResource("swap_pc_pokemon")
        fun decode(buffer: PacketByteBuf) = SwapPCPokemonPacket(buffer.readUuid(), buffer.readPCPosition(), buffer.readUuid(), buffer.readPCPosition())
    }
}