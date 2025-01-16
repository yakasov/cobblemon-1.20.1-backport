/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.serverhandling.trade

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.cobblemon.mod.common.net.messages.server.trade.AcceptTradeRequestPacket
import com.cobblemon.mod.common.trade.TradeManager
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity

object AcceptTradeRequestHandler : ServerNetworkPacketHandler<AcceptTradeRequestPacket> {
    override fun handle(packet: AcceptTradeRequestPacket, server: MinecraftServer, player: ServerPlayerEntity) {
        if(player.isSpectator) return
        TradeManager.acceptTradeRequest(player, packet.tradeOfferId)
    }
}