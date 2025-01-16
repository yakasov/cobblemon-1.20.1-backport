/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BiWingedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation.Companion.Y_AXIS
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class BeedrillModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BiWingedFrame {
    override val rootPart = root.registerChildWithSpecificChildren("beedrill", listOf("body","antenna_right","antenna_right2","antenna_left","antenna_left2","wing_right","wing_right2","wing_left","wing_left2","abdomen","arm_right","arm_left","leg_right","leg_left","leg_left2","leg_right2","head_AI","head","arm_right2","arm_left2"))
    override val head = getPart("head")
    override val leftWing = getPart("wing_left")
    override val rightWing = getPart("wing_right")

    override var portraitScale = 1.9F
    override var portraitTranslation = Vec3d(0.05, 0.9, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(-0.1, 0.9, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("beedrill", "cry") }

    override fun registerPoses() {
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("beedrill", "sleep")),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(Y_AXIS, -3F)
            )
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("beedrill", "air_idle")
            ),
            transformedParts = arrayOf(rootPart.createTransformation().addPosition(Y_AXIS, -10F))
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("beedrill", "air_fly")
            ),
            transformedParts = arrayOf(rootPart.createTransformation().addPosition(Y_AXIS, -5F))
        )
    }
}