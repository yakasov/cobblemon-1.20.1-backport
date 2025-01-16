/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.events.berry

import com.cobblemon.mod.common.api.berry.Berry
import com.cobblemon.mod.common.block.BerryBlock
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * The event fired when [BerryBlock.grow] is invoked and the tree passes to [BerryBlock.MATURE_AGE].
 * This allows you to modify the possible mutations for the tree, for the event that decides which of these mutations is used see [BerryMutationResultEvent].
 *
 * @property world The [World] the berry tree is in.
 * @property state The [BlockState] of the berry tree.
 * @property pos The [BlockPos] of the berry tree.
 * @property mutations The possible [Berry] mutations.
 *
 * @author Licious
 * @since January 19th, 2022
 */
data class BerryMutationOfferEvent(
    override val berry: Berry,
    val world: World,
    val state: BlockState,
    val pos: BlockPos,
    val mutations: MutableSet<Berry>
) : BerryEvent