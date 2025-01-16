/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.mulch

import net.minecraft.util.StringIdentifiable

/**
 * Represents the different types of Mulch implemented in the mod.
 *
 */
enum class MulchVariant(val duration: Int = -1) : StringIdentifiable {
    COARSE,
    GROWTH(3),
    HUMID,
    LOAMY,
    PEAT,
    RICH(3),
    SANDY,
    SURPRISE(3),
    NONE;

    override fun asString(): String {
        return name.lowercase()
    }
}
