/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.events.pokemon.evolution

import com.cobblemon.mod.common.api.pokemon.evolution.Evolution
import com.cobblemon.mod.common.pokemon.Pokemon

/**
 * An event fired when [Evolution.test] has been performed.
 * The final state [result] will be the return value of the test function.
 *
 * @property pokemon The [Pokemon] being tested against.
 * @property evolution The [Evolution] instance performing the test.
 * @property originalResult The base result from the Cobblemon implementation.
 * @property result The final value returned by [Evolution.test].
 */
data class EvolutionTestedEvent(
    override val pokemon: Pokemon,
    override val evolution: Evolution,
    val originalResult: Boolean,
    var result: Boolean
) : EvolutionEvent
