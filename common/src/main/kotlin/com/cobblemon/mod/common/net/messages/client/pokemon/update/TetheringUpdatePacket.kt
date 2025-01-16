/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.pokemon.update

import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.PacketByteBuf

/**
 * Packet sent to update the client's tetheringId for a Pokémon. Really only used to show in the PC appropriately.
 *
 * @author Hiroku
 * @since April 4th, 2023
 */
class TetheringUpdatePacket(pokemon: () -> Pokemon, tetheringId: UUID?) : SingleUpdatePacket<UUID?, TetheringUpdatePacket>(pokemon, tetheringId) {

    override val id = ID

    override fun encodeValue(buffer: PacketByteBuf) {
        buffer.writeNullable(this.value) { _, v -> buffer.writeUuid(v) }
    }

    override fun set(pokemon: Pokemon, value: UUID?) {
        pokemon.tetheringId = value
    }

    companion object {
        val ID = cobblemonResource("tethering_update")
        fun decode(buffer: PacketByteBuf): TetheringUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val tetheringId = buffer.readNullable { buffer.readUuid() }
            return TetheringUpdatePacket(pokemon, tetheringId)
        }
    }
}