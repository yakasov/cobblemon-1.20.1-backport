/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen6

import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class BraixenModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("braixen")
    override val head = getPart("head")
    override val rightArm = getPart("arm_right")
    override val leftArm = getPart("arm_left")
    override val rightLeg = getPart("leg_right")
    override val leftLeg = getPart("leg_left")

    val stick = getPart("hand_stick")
    val sticktail = getPart("stick_tail")

    override var portraitScale = 2.2F
    override var portraitTranslation = Vec3d(-0.3, 1.8, 0.0)

    override var profileScale = 0.55F
    override var profileTranslation = Vec3d(0.0, 1.0, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var battleidle: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("braixen", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("braixen", "blink")}
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + UI_POSES,
            transformTicks = 10,
            condition = { !it.isBattling },
            transformedParts = arrayOf(
                stick.createTransformation().withVisibility(visibility = false),
                sticktail.createTransformation().withVisibility(visibility = true)
            ),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("braixen", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            transformTicks = 10,
            transformedParts = arrayOf(
                stick.createTransformation().withVisibility(visibility = false),
                sticktail.createTransformation().withVisibility(visibility = true)
            ),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("braixen", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            transformedParts = arrayOf(
                stick.createTransformation().withVisibility(visibility = true),
                sticktail.createTransformation().withVisibility(visibility = false)
            ),
            quirks = arrayOf(blink),
            condition = { it.isBattling },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("braixen", "battle_idle")
            )
        )
    }
}