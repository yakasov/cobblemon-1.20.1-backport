/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.block.entity

import com.cobblemon.mod.common.CobblemonBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.HangingSignBlockEntity
import net.minecraft.util.math.BlockPos

class CobblemonHangingSignBlockEntity(pos: BlockPos, state: BlockState) : HangingSignBlockEntity(pos, state) {

    override fun getType(): BlockEntityType<*> = CobblemonBlockEntities.HANGING_SIGN

}