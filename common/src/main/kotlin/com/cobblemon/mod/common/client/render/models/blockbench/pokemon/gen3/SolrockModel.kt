/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen3

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class SolrockModel (root: ModelPart) : PokemonPoseableModel() {
    override val rootPart = root.registerChildWithAllChildren("solrock")

    override var portraitScale = 1.6F
    override var portraitTranslation = Vec3d(-0.08, 0.31, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.74, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("solrock", "blink") }
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("solrock", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.ALL_POSES - PoseType.SLEEP,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("solrock", "ground_idle")
            )
        )
    }
//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(sleep)) bedrockStateful("solrock", "faint") else null
}