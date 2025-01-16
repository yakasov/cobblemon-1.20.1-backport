/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.spawning.selection

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.spawning.context.RegisteredSpawningContext
import com.cobblemon.mod.common.api.spawning.context.SpawningContext
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.cobblemon.mod.common.api.spawning.spawner.Spawner
import com.cobblemon.mod.common.util.weightedSelection
import kotlin.random.Random

/**
 * A spawning selector that compiles a distinct list of all spawn details that
 * are possible across any context type and performs a weighted selection of spawns.
 *
 * The goal of this algorithm is to be kinder to spawns that are only possible
 * in very specific locational conditions or context types by not letting the scarcity
 * of suitable locations hurt its chances of spawning. It differs from the
 * [FlatContextWeightedSelector] because here a spawning context type only appearing once
 * will not make it any less likely to be selected.
 *
 * A spawn detail's weight when selecting between all possible spawn details will be the
 * highest of its contextual weights. In other words, if a spawn is possible at both A and
 * B contexts but is weighed more highly at A due to a weight multiplier, it will use the
 * weight of the spawn at A when choosing which spawn will run.
 *
 * When choosing which context a specific spawn detail will spawn at once that spawn detail
 * has been chosen, it does a context-weighted random selection across those contexts.
 *
 * At a glance:
 * - Spawn detail selection is flat across context quantity
 * - The popularity of specific context types has no bearing on the spawn chance.
 *
 * @author Hiroku
 * @since July 10th, 2022
 */
open class FlatSelector : SpawningSelector {
    protected class SelectingSpawnInformation {
        val spawningContexts = mutableMapOf<SpawningContext, Float>()
        var highestWeight = 0F

        fun add(spawningContext: SpawningContext, contextWeight: Float) {
            spawningContexts[spawningContext] = contextWeight
            if (contextWeight > highestWeight) {
                highestWeight = contextWeight
            }
        }

        fun chooseContext() = spawningContexts.entries.weightedSelection { it.value }!!.key
    }

    protected class ContextSelectionData(
        val spawnsToContexts: MutableMap<SpawnDetail, SelectingSpawnInformation>,
        var percentSum: Float
    ) {
        val size: Int
            get() = spawnsToContexts.size
    }

    protected fun getSelectionData(
        spawner: Spawner,
        contexts: List<SpawningContext>
    ): ContextSelectionData {
        val spawnsToContexts: MutableMap<SpawnDetail, SelectingSpawnInformation> = mutableMapOf()
        var percentSum = 0F

        contexts.forEach { ctx ->
            spawner.getMatchingSpawns(ctx).forEach {
                // Only add to percentSum if this is the first time we've seen this SpawnDetail, otherwise
                // the percentage will get amplified for every context the thing was possible, completely
                // ruining the point of this pre-selection percentage.
                if (it.percentage > 0 && !spawnsToContexts.containsKey(it)) {
                    percentSum += it.percentage
                }

                val selectingSpawnInformation = spawnsToContexts.getOrPut(
                    it,
                    ::SelectingSpawnInformation
                )
                selectingSpawnInformation.add(ctx, ctx.getWeight(it))
            }
        }

        return ContextSelectionData(spawnsToContexts, percentSum)
    }

    override fun select(
        spawner: Spawner,
        contexts: List<SpawningContext>
    ): Pair<SpawningContext, SpawnDetail>? {
        val selectionData = getSelectionData(spawner, contexts)

        if (selectionData.size == 0) {
            return null
        }

        val spawnsToContexts = selectionData.spawnsToContexts
        var percentSum = selectionData.percentSum

        // First pass is doing percentage checks.
        if (percentSum > 0) {

            if (percentSum > 100) {
                Cobblemon.LOGGER.warn(
                    """
                        A spawn list for ${spawner.name} exceeded 100% on percentage sums...
                        This means you don't understand how this option works.
                    """.trimIndent()
                )
                return null
            }

            /*
             * It's [0, 1) and I want (0, 1]
             * See half-open intervals here https://en.wikipedia.org/wiki/Interval_(mathematics)#Terminology
             */
            val selectedPercentage = 100 - Random.Default.nextFloat() * 100
            percentSum = 0F
            for ((spawnDetail, info) in spawnsToContexts) {
                if (spawnDetail.percentage > 0) {
                    percentSum += spawnDetail.percentage
                    if (percentSum >= selectedPercentage) {
                        return info.chooseContext() to spawnDetail
                    }
                }
            }
        }

        val selectedSpawn = spawnsToContexts.entries.toList().weightedSelection { it.value.highestWeight }!!
        return selectedSpawn.value.chooseContext() to selectedSpawn.key
    }

    override fun getTotalWeights(
        spawner: Spawner,
        contexts: List<SpawningContext>
    ): Map<SpawnDetail, Float> {
        val selectionData = getSelectionData(spawner, contexts)

        if (selectionData.size == 0) {
            return emptyMap()
        }

        val totalWeights = mutableMapOf<SpawnDetail, Float>()

        for ((spawnDetail, info) in selectionData.spawnsToContexts.entries) {
            totalWeights[spawnDetail] = info.highestWeight
        }

        return totalWeights
    }
}