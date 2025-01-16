/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.pokemon.evolution.requirements

import com.cobblemon.mod.common.api.pokemon.evolution.requirement.EvolutionRequirement
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.evolution.progress.RecoilEvolutionProgress

/**
 * An [EvolutionRequirement] which requires a specific [amount] of recoil without fainting in order to pass.
 * It keeps track of progress through [RecoilEvolutionProgress].
 *
 * @param amount The requirement amount of recoil
 *
 * @author Licious
 * @since January 27th, 2022
 */
class RecoilRequirement(amount: Int) : EvolutionRequirement {

    constructor() : this(0)

    /**
     * The requirement amount of recoil
     */
    val amount: Int = amount

    override fun check(pokemon: Pokemon): Boolean = pokemon.evolutionProxy.current()
        .progress()
        .filterIsInstance<RecoilEvolutionProgress>()
        .any { progress -> progress.currentProgress().recoil >= this.amount }

    companion object {
        const val ADAPTER_VARIANT = "recoil"
    }

}