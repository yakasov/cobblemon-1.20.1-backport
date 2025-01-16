/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.fossil

import com.cobblemon.mod.common.api.fossil.Fossil
import com.cobblemon.mod.common.api.fossil.Fossils
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.net.messages.client.data.DataRegistrySyncPacket
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.network.PacketByteBuf



class FossilRegistrySyncPacket(fossils: List<Fossil>) : DataRegistrySyncPacket<Fossil, FossilRegistrySyncPacket>(fossils) {
    companion object {
        val ID = cobblemonResource("fossils")
        fun decode(buffer: PacketByteBuf) = FossilRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }


    override val id = ID
    override fun encodeEntry(buffer: PacketByteBuf, entry: Fossil) {
        buffer.writeIdentifier(entry.identifier)
        buffer.writeString(Fossils.gson.toJson(entry.result, PokemonProperties::class.java))
    }

    override fun decodeEntry(buffer: PacketByteBuf): Fossil {
        return Fossil (
                identifier = buffer.readIdentifier(),
                result = Fossils.gson.fromJson(buffer.readString(), PokemonProperties::class.java),
                fossils = emptyList()
        )
    }

    override fun synchronizeDecoded(entries: Collection<Fossil>) {
        Fossils.reload(entries.associateBy { it.identifier })
    }
}