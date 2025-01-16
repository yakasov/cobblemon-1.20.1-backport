/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.events.pokeball

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity

/**
 * Event fired when a thrown Pokeball hits a Pokémon. Cancelling this event prevents the capture being started.
 */
class ThrownPokeballHitEvent(
    val pokeBall : EmptyPokeBallEntity,
    val pokemon : PokemonEntity
) : Cancelable()