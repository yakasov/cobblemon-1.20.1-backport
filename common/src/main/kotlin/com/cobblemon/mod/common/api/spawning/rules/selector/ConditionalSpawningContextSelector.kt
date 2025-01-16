/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.spawning.rules.selector

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.context.SpawningContext

class ConditionalSpawningContextSelector : SpawningContextSelector {
    var conditions = mutableListOf<SpawningCondition<*>>()
    var anticonditions = mutableListOf<SpawningCondition<*>>()

    override fun selects(ctx: SpawningContext): Boolean {
        return if (conditions.isNotEmpty() && conditions.none { it.isSatisfiedBy(ctx) }) {
            false
        } else {
            !(anticonditions.isNotEmpty() && anticonditions.any { it.isSatisfiedBy(ctx) })
        }
    }
}