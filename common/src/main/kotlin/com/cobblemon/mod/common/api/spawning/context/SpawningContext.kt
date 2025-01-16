/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.spawning.context

import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.cobblemon.mod.common.api.molang.ObjectValue
import com.cobblemon.mod.common.api.spawning.SpawnCause
import com.cobblemon.mod.common.api.spawning.condition.BasicSpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.cobblemon.mod.common.api.spawning.influence.SpawningInfluence
import com.cobblemon.mod.common.api.spawning.spawner.Spawner
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.fluid.Fluid
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.structure.Structure

/**
 * A context upon which spawning is being attempted. This supplies all the information that can be used to asynchronously
 * check for suitability of spawning.
 *
 * @author Hiroku
 * @since January 24th, 2022
 */
abstract class SpawningContext {
    companion object {
        val contexts = mutableListOf<RegisteredSpawningContext<*>>()
        fun getByName(name: String) = contexts.find { it.name == name }
        fun getByClass(ctx: SpawningContext) = contexts.find { it.clazz == ctx::class.java }
        fun <T : SpawningContext> register(name: String, clazz: Class<T>, defaultCondition: String = BasicSpawningCondition.NAME) {
            contexts.add(
                RegisteredSpawningContext(
                    name = name,
                    clazz = clazz,
                    defaultCondition = defaultCondition
                )
            )
        }
    }

    /** What caused the spawn context, as a [SpawnCause]. */
    abstract val cause: SpawnCause
    val spawner: Spawner
        get() = cause.spawner
    /** The [ServerWorld] the spawning context exists in. */
    abstract val world: ServerWorld
    /** The location of the spawning attempt. */
    abstract val position: BlockPos
    /** The light level at this location. */
    abstract val light: Int
    /** The sky light level at this location. (15 - The distance to the nearest block that would be illuminated by the sun */
    abstract val skyLight: Int
    /** Whether or not the sky is visible at this location. */
    abstract val canSeeSky: Boolean
    /** A list of [SpawningInfluence]s that apply due to this specific context. */
    abstract val influences: MutableList<SpawningInfluence>
    /** Gets a cache of structures by block coordinates, grouped by chunk. */
    abstract fun getStructureCache(pos: BlockPos): StructureChunkCache

    /** The current phase of the moon at this location. */
    val moonPhase: Int by lazy { world.moonPhase }
    /** The biome of this location. */
    val biome: Biome by lazy { world.getBiome(position).value() }

    val biomeRegistry: Registry<Biome> by lazy { world.registryManager.get(RegistryKeys.BIOME) }
    val blockRegistry: Registry<Block> by lazy { world.registryManager.get(RegistryKeys.BLOCK) }
    val fluidRegistry: Registry<Fluid> by lazy { world.registryManager.get(RegistryKeys.FLUID)}

    val biomeName: Identifier
        get() = this.biomeRegistry.getId(biome)!!

    private val struct = VariableStruct()
    private var structCompiled = false

    class StructureChunkCache {
        val missingTags = mutableSetOf<TagKey<Structure>>()
        val foundTags = mutableSetOf<TagKey<Structure>>()

        val foundIdentifiers = mutableSetOf<Identifier>()

        var loadedStructures = false
        val structures = mutableSetOf<RegistryEntry<Structure>>()

        fun loadStructures(structureAccess: StructureAccessor, pos: BlockPos) {
            val registry = structureAccess.registryManager.get(RegistryKeys.STRUCTURE)
            structureAccess.getStructureStarts(ChunkPos(pos)) { structure ->
                val entry = registry.getEntry(structure) ?: return@getStructureStarts true
                structures.add(entry)
                foundIdentifiers.add(entry.key.get().value)
                false
            }
            loadedStructures = true
        }

        fun check(structureAccess: StructureAccessor, pos: BlockPos, tagKey: TagKey<Structure>): Boolean {

            if (!loadedStructures) {
                loadStructures(structureAccess, pos)
            }

            if (tagKey in missingTags) {
                return false
            } else if (tagKey in foundTags) {
                return true
            }

            structures.forEach { structure ->
                if (structure.isIn(tagKey)) {
                    foundTags.add(tagKey)
                    return true
                }
            }

            missingTags.add(tagKey)

            return false
        }

        fun check(structureAccess: StructureAccessor, pos: BlockPos, id: Identifier): Boolean {
            if (!loadedStructures) {
                loadStructures(structureAccess, pos)
            }

            return id in foundIdentifiers
        }
    }

    /**
     * Filters a spawning detail by some extra condition defined by the context itself. This is for API purposes.
     * @return true if the [SpawnDetail] is acceptable by the context's own logic.
     */
    open fun preFilter(detail: SpawnDetail): Boolean {
        /** Returns true if none of the influences.affectSpawnable return false */
        return influences.none { !it.affectSpawnable(detail, this) }
    }

    /**
     * Filters a spawning detail by some extra, more expensive condition defined by the context itself.
     * @return true if the [SpawnDetail] is acceptable by the context's own logic.
     */
    open fun postFilter(detail: SpawnDetail): Boolean = true

    open fun afterSpawn(entity: Entity) {
        influences.forEach { it.affectSpawn(entity) }
    }

    open fun getWeight(detail: SpawnDetail): Float {
        var weight = detail.weight
        for (influence in influences + detail.weightMultipliers) {
            weight = influence.affectWeight(detail, this, weight)
        }
        return weight
    }

    fun getOrSetupStruct(): VariableStruct {
        if (structCompiled) {
            return struct
        }

        struct.setDirectly("light", DoubleValue(light.toDouble()))
        struct.setDirectly("x", DoubleValue(position.x.toDouble()))
        struct.setDirectly("y", DoubleValue(position.y.toDouble()))
        struct.setDirectly("z", DoubleValue(position.z.toDouble()))
        struct.setDirectly("moon_phase", DoubleValue(moonPhase.toDouble()))
        struct.setDirectly("world", ObjectValue(world.registryManager.get(RegistryKeys.WORLD).getEntry(world)))
        struct.setDirectly("biome", ObjectValue(biomeRegistry.getEntry(biome)))

        structCompiled = true
        return struct
    }
}