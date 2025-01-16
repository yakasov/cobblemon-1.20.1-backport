/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.trade

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.PacketByteBuf

/**
 * Packet sent to the client when the open trade has been completed.
 *
 * Handled by [com.cobblemon.mod.common.client.net.trade.TradeCompletedHandler]
 *
 * @author Hiroku
 * @since March 5th, 2023
 */
class TradeCompletedPacket(val pokemonId1: UUID, val pokemonId2: UUID) : NetworkPacket<TradeCompletedPacket> {
    companion object {
        val ID = cobblemonResource("trade_completed")
        fun decode(buffer: PacketByteBuf) = TradeCompletedPacket(buffer.readUuid(), buffer.readUuid())
    }

    override val id = ID

    override fun encode(buffer: PacketByteBuf) {
        buffer.writeUuid(pokemonId1)
        buffer.writeUuid(pokemonId2)
    }
}