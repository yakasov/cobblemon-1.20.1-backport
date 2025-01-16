/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.events.starter

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.server.network.ServerPlayerEntity

/**
 * Event fired when a starter Pokémon is chosen.
 *
 * @author Hiroku
 * @since August 1st, 2022
 */
data class StarterChosenEvent(val player: ServerPlayerEntity, val properties: PokemonProperties, var pokemon: Pokemon) : Cancelable()