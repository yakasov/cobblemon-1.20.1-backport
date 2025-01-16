/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.moves.categories

import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.text.Text
import net.minecraft.util.Identifier

/**
 * Representing a DamageCategory from the Pokemon Game
 *
 * @param name: The English name used to load / find it (spaces -> _)
 * @param displayName: A Component used to display the name, normally a TranslatableText
 * @param resourceLocation: The location of the resource used in the GUI
 */
class DamageCategory(
    val name: String,
    val displayName: Text,
    val textureXMultiplier: Int,
    val resourceLocation: Identifier = cobblemonResource("textures/gui/categories.png")
) {
}