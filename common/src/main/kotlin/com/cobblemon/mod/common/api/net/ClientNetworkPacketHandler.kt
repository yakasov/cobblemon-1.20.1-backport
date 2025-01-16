/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.net

import net.minecraft.client.MinecraftClient

interface ClientNetworkPacketHandler<T: NetworkPacket<T>> {

    fun handle(packet: T, client: MinecraftClient)

    fun handleOnNettyThread(packet: T) {
        val client = MinecraftClient.getInstance()
        client.execute { handle(packet, client) }
    }
}