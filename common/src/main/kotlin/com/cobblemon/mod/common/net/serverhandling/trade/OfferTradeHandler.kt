/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.serverhandling.trade

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.cobblemon.mod.common.net.messages.server.trade.OfferTradePacket
import com.cobblemon.mod.common.trade.TradeManager
import com.cobblemon.mod.common.util.getPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity

object OfferTradeHandler : ServerNetworkPacketHandler<OfferTradePacket> {
    override fun handle(packet: OfferTradePacket, server: MinecraftServer, player: ServerPlayerEntity) {
        if(player.isSpectator) return

        TradeManager.offerTrade(player, packet.offeredPlayerId.getPlayer() ?: return)
    }
}