/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen8

import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class ThwackeyModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("thwackey")
    override val head = getPart("head")

    override val leftArm = getPart("arm_left")
    override val rightArm = getPart("arm_right")
    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")
    val stick_head_left = getPart("hair_stick_left")
    val stick_head_right = getPart("hair_stick_right")
    val stick_left = getPart("stick_left")
    val stick_right = getPart("stick_right")

    override var portraitScale = 2.2F
    override var portraitTranslation = Vec3d(-0.35, 0.3, 0.0)

    override var profileScale = 0.65F
    override var profileTranslation = Vec3d(0.0, 0.76, 0.0)

    lateinit var battleidle: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("thwackey", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("thwackey", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink),
            condition = { !it.isBattling },
            transformedParts = arrayOf(
                stick_head_left.createTransformation().withVisibility(visibility = false),
                stick_head_right.createTransformation().withVisibility(visibility = false),
                stick_left.createTransformation().withVisibility(visibility = true),
                stick_right.createTransformation().withVisibility(visibility = true)
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("thwackey", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            transformedParts = arrayOf(
                stick_head_left.createTransformation().withVisibility(visibility = false),
                stick_head_right.createTransformation().withVisibility(visibility = false),
                stick_left.createTransformation().withVisibility(visibility = true),
                stick_right.createTransformation().withVisibility(visibility = true)
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("thwackey", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            condition = { it.isBattling },
            transformedParts = arrayOf(
                stick_head_left.createTransformation().withVisibility(visibility = false),
                stick_head_right.createTransformation().withVisibility(visibility = false),
                stick_left.createTransformation().withVisibility(visibility = true),
                stick_right.createTransformation().withVisibility(visibility = true)
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("thwackey", "ground_idle")
            )

        )
    }
}