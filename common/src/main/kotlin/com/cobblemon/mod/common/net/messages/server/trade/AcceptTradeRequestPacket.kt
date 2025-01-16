/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.server.trade

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.PacketByteBuf

class AcceptTradeRequestPacket(val tradeOfferId: UUID) : NetworkPacket<AcceptTradeRequestPacket> {
    companion object {
        val ID = cobblemonResource("accept_trade_request")
        fun decode(buffer: PacketByteBuf) = AcceptTradeRequestPacket(buffer.readUuid())
    }

    override val id = ID
    override fun encode(buffer: PacketByteBuf) {
        buffer.writeUuid(tradeOfferId)
    }
}