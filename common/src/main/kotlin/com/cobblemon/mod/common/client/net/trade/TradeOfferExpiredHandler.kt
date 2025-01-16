/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.net.trade

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.net.messages.client.trade.TradeOfferExpiredPacket
import net.minecraft.client.MinecraftClient

object TradeOfferExpiredHandler : ClientNetworkPacketHandler<TradeOfferExpiredPacket> {
    override fun handle(packet: TradeOfferExpiredPacket, client: MinecraftClient) {
        CobblemonClient.requests.tradeOffers.removeIf { it.tradeOfferId == packet.tradeOfferId }
    }
}