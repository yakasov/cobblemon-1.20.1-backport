/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.keybind.keybinds

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.gui.summary.Summary
import com.cobblemon.mod.common.client.keybind.CobblemonPartyLockedKeyBinding
import com.cobblemon.mod.common.client.keybind.KeybindCategories
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.InputUtil

// ToDo PartyOverlay#render needs to be replaced back to this keybind
object PokeNavigatorBinding : CobblemonPartyLockedKeyBinding(
    "key.cobblemon.pokenavigator",
    InputUtil.Type.KEYSYM,
    InputUtil.GLFW_KEY_N,
    KeybindCategories.COBBLEMON_CATEGORY
) {
    override fun onPress() {
        // MinecraftClient.getInstance().setScreen(PokeNav())
        try {
            Summary.open(CobblemonClient.storage.myParty.slots, true, CobblemonClient.storage.selectedSlot)
        } catch (e: Exception) {
            Cobblemon.LOGGER.debug("Failed to open the summary from the PokeNav keybind", e)
        }
    }
}