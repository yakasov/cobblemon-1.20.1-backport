/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.battle

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.net.messages.PokemonDTO
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.network.PacketByteBuf

/**
 * Packet sent to the client to give a player a total update of one of their battle
 * Pokémon's data. Unlike other update packets this gives complete and private data,
 * unaffected by the 'fog of war' in battles (knowing all the moves, for example).
 *
 * Handled by [com.cobblemon.mod.common.client.net.battle.BattleUpdateTeamPokemonHandler].
 *
 * @author Hiroku
 * @since August 27th, 2022
 */
class BattleUpdateTeamPokemonPacket(val pokemon: PokemonDTO) : NetworkPacket<BattleUpdateTeamPokemonPacket> {
    override val id = ID
    constructor(pokemon: Pokemon) : this(PokemonDTO(pokemon, true))
    override fun encode(buffer: PacketByteBuf) {
        pokemon.encode(buffer)
    }
    companion object {
        val ID = cobblemonResource("battle_update_team")
        fun decode(buffer: PacketByteBuf) = BattleUpdateTeamPokemonPacket(PokemonDTO().also { it.decode(buffer) })
    }
}