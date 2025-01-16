/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.battle

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.PacketByteBuf

/**
 * Packet fired to tell the client that a battle challenge expired.
 *
 * @author Hiroku
 * @since March 11th, 2023
 */
class BattleChallengeExpiredPacket(val battleChallengeId: UUID) : NetworkPacket<BattleChallengeExpiredPacket> {
    companion object {
        val ID = cobblemonResource("battle_challenge_expired")
        fun decode(buffer: PacketByteBuf) = BattleChallengeExpiredPacket(buffer.readUuid())
    }

    override val id = ID
    override fun encode(buffer: PacketByteBuf) {
        buffer.writeUuid(battleChallengeId)
    }
}