/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.spawning

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.Cobblemon.config
import com.cobblemon.mod.common.api.spawning.prospecting.SpawningProspector
import com.cobblemon.mod.common.api.spawning.spawner.Spawner
import com.cobblemon.mod.common.api.spawning.spawner.SpawningArea
import com.cobblemon.mod.common.api.tags.CobblemonBlockTags
import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.ChunkSectionPos.getSectionCoord
import net.minecraft.util.math.Vec3d
import net.minecraft.world.LightType
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.chunk.ChunkStatus

/**
 * A spawning prospector that takes a straightforward approach
 * in slicing out a [WorldSlice]. If you want to replace this,
 * change over the value of [Cobblemon.prospector].
 *
 * @author Hiroku
 * @since February 5th, 2022
 */
object CobblemonSpawningProspector : SpawningProspector {
    override fun prospect(
        spawner: Spawner,
        area: SpawningArea
    ): WorldSlice {
        val world = area.world
        var baseY = area.baseY
        var height = area.height
        if (baseY < world.bottomY) {
            val difference = world.bottomY - baseY
            baseY += difference
            height -= difference
            if (height < 1) {
                throw IllegalStateException("World slice was attempted with totally awful base and dimensions")
            }
        }

        if (baseY + height >= world.topY) {
            val difference = baseY + height - 1 - world.topY
            height -= difference
            if (height < 1) {
                throw IllegalStateException("World slice was attempted with totally awful base and dimensions")
            }
        }

        val minimumDistanceBetweenEntities = config.minimumDistanceBetweenEntities
        val nearbyEntityPositions = area.world.getOtherEntities(
            area.cause.entity,
            Box.of(
                Vec3d(area.baseX + area.length / 2.0, baseY + height / 2.0, area.baseZ + area.width / 2.0),
                area.length + minimumDistanceBetweenEntities,
                height + minimumDistanceBetweenEntities,
                area.width + minimumDistanceBetweenEntities
            )
        ).filterIsInstance<LivingEntity>()
            .map { it.pos }

        val defaultState = Blocks.STONE.defaultState
        val defaultBlockData = WorldSlice.BlockData(defaultState, 0, 0)

        val blocks = Array(area.length) { Array(height) { Array(area.width) { defaultBlockData } } }
        val skyLevel = Array(area.length) { Array(area.width) { world.topY } }
        val pos = BlockPos.Mutable()

        val chunks = mutableMapOf<Pair<Int, Int>, Chunk?>()
        val yRange = (baseY until baseY + height).reversed()
        val lightingProvider = world.lightingProvider
        for (x in area.baseX until area.baseX + area.length) {
            for (z in area.baseZ until area.baseZ + area.width) {
                val query = chunks.computeIfAbsent(Pair(getSectionCoord(x), getSectionCoord(z))) {
                    world.getChunk(it.first, it.second, ChunkStatus.FULL, false)
                } ?: continue

                var canSeeSky = world.isSkyVisibleAllowingSea(pos.set(x, yRange.first, z))
                for (y in yRange) {
                    val skyLight = lightingProvider.get(LightType.SKY).getLightLevel(pos.set(x, y, z))
                    val state = query.getBlockState(pos.set(x, y, z))
                    blocks[x - area.baseX][y - baseY][z - area.baseZ] = WorldSlice.BlockData(
                        state = state,
                        light = world.getLightLevel(pos),
                        skyLight = skyLight
                    )
                    if (canSeeSky) {
                        skyLevel[x - area.baseX][z - area.baseZ] = y
                    }
                    if (state.fluidState.isEmpty && !state.isIn(CobblemonBlockTags.SEES_SKY)) {
                        canSeeSky = false
                    }
                }
            }
        }

        return WorldSlice(
            cause = area.cause,
            world = world,
            baseX = area.baseX,
            baseY = baseY,
            baseZ = area.baseZ,
            blocks = blocks,
            skyLevel = skyLevel,
            nearbyEntityPositions = nearbyEntityPositions
        )
    }
}