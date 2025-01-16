/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.effect

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

/**
 * Packet that spawns a snowstorm particle effect on a specified entity and specified locator.
 *
 * @author Hiroku
 * @since January 21st, 2024
 */
class SpawnSnowstormEntityParticlePacket(val effectId: Identifier, val entityId: Int, val locator: String = "root") : NetworkPacket<SpawnSnowstormEntityParticlePacket> {
    companion object {
        val ID = cobblemonResource("spawn_snowstorm_entity_particle")

        fun decode(buffer: PacketByteBuf) = SpawnSnowstormEntityParticlePacket(buffer.readIdentifier(), buffer.readInt(), buffer.readString())
    }

    override val id = ID

    override fun encode(buffer: PacketByteBuf) {
        buffer.writeIdentifier(effectId)
        buffer.writeInt(entityId)
        buffer.writeString(locator)
    }
}