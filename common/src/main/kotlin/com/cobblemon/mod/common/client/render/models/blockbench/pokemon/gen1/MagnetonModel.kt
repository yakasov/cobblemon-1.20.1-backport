/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class MagnetonModel(root: ModelPart) : PokemonPoseableModel() {
    override val rootPart = root.registerChildWithAllChildren("magneton")

    override var portraitScale = 1.2F
    override var portraitTranslation = Vec3d(0.0, 0.2, 0.0)

    override var profileScale = 0.84F
    override var profileTranslation = Vec3d(0.0, 0.5, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var sleep: PokemonPose


    override fun registerPoses() {
        val blink = quirk { bedrockStateful("magneton", "blink") }
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("magneton", "sleep"))
        )

        registerPose(
            poseName = "hover",
            poseTypes = PoseType.ALL_POSES - PoseType.FLY - PoseType.SLEEP - PoseType.SWIM,
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("magneton", "air_idle")
            )
        )

        registerPose(
            poseName = "fly",
            poseTypes = setOf(PoseType.FLY, PoseType.SWIM),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("magneton", "air_fly")
            )
        )

    }

    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = bedrockStateful("magneton", "faint")
}