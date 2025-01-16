/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon

import com.cobblemon.mod.common.api.scheduling.ClientTaskTracker
import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity

/**
 * A [PoseableEntityState] that exists even without an entity or age-based state. This is
 * for when a Pokémon needs to be continuously animated from GUIs and there isn't an entity
 * to attach state to. It has no age, and updating the partial ticks will increment rather
 * than replace.
 *
 * What to look out for: Don't run updatePartialTicks multiple times in a single frame. For
 * other [PoseableEntityState] this is fine as it just replaces what was there before, but
 * since this is incremental it would actually mess up the animation speeds.
 *
 * @author Hiroku
 * @since May 1st, 2022
 */
class PokemonFloatingState : PoseableEntityState<PokemonEntity>() {
    override fun getEntity() = null
    override val schedulingTracker = ClientTaskTracker
    override fun updatePartialTicks(partialTicks: Float) {
        this.currentPartialTicks += partialTicks
    }
}