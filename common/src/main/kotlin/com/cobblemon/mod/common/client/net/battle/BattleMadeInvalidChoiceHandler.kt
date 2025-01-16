/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.net.battle

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.gui.battle.BattleGUI
import com.cobblemon.mod.common.net.messages.client.battle.BattleMadeInvalidChoicePacket
import net.minecraft.client.MinecraftClient

object BattleMadeInvalidChoiceHandler : ClientNetworkPacketHandler<BattleMadeInvalidChoicePacket> {
    override fun handle(packet: BattleMadeInvalidChoicePacket, client: MinecraftClient) {
        //Remove previous selected action, so user can select a new action
        val battle = CobblemonClient.battle ?: return
        battle.mustChoose = true
        val gui = MinecraftClient.getInstance().currentScreen
        if (gui is BattleGUI) {
            gui.removeInvalidBattleActionSelection()
        }
    }
}