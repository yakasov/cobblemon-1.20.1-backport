/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.item

import com.cobblemon.mod.common.block.ApricornBlock
import com.cobblemon.mod.common.block.ApricornSaplingBlock
import net.minecraft.block.BlockState
import net.minecraft.item.AliasedBlockItem
import net.minecraft.item.ItemPlacementContext

class ApricornSeedItem(block: ApricornSaplingBlock, val apricornBlock: ApricornBlock) : AliasedBlockItem(block, Settings()) {

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        // Verify the feature is enabled similar to what's done at the top of place
        if (this.apricornBlock.isEnabled(context.world.enabledFeatures)) {
            // Get a contextualized apricorn block state
            val apricornState = this.apricornBlock.getPlacementState(context)
            // If placeable return otherwise let default impl run, DO NOT return a null
            if (apricornState != null && this.canPlace(context, apricornState)) {
                return apricornState
            }
        }
        return super.getPlacementState(context)
    }

}