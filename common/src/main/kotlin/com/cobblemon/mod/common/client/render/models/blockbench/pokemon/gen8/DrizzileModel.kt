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

class DrizzileModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("drizzile")
    override val head = getPart("head")

    override val leftArm = getPart("arm_left")
    override val rightArm = getPart("arm_right")
    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")
    val tongue = getPart("tongue")
    val bomb = getPart("bomb")

    override var portraitScale = 2.3F
    override var portraitTranslation = Vec3d(-0.5, -0.2, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.56, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("drizzile", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("drizzile", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink),
            transformedParts = arrayOf(
                tongue.createTransformation().withVisibility(visibility = false),
                bomb.createTransformation().withVisibility(visibility = false),
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("drizzile", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            transformedParts = arrayOf(
                tongue.createTransformation().withVisibility(visibility = false),
                bomb.createTransformation().withVisibility(visibility = false),
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("drizzile", "ground_walk")
            )
        )
    }
}