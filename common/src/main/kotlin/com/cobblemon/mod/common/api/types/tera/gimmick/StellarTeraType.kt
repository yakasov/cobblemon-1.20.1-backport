/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.types.tera.gimmick

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.types.tera.TeraType
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class StellarTeraType : TeraType {
    override val id: Identifier = ID

    override val legalAsStatic: Boolean = false

    override val displayName: Text = LANG

    override fun showdownId(): String = ID.path

    companion object {
        val ID = cobblemonResource("stellar")
        private val LANG = Text.translatable("${Cobblemon.MODID}.terra_type.stellar")
    }
}