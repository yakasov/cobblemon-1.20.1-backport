/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen3

import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class WhismurModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("whismur")
    override val head = getPart("torso")

    override val leftLeg = getPart("foot_left")
    override val rightLeg = getPart("foot_right")

    override var portraitScale = 2.3F
    override var portraitTranslation = Vec3d(-0.15, -1.2, 0.0)

    override var profileScale = 0.9F
    override var profileTranslation = Vec3d(0.0, 0.45, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override fun registerPoses() {
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("whismur", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("whismur", "ground_idle"),
                BipedWalkAnimation(this, periodMultiplier = 0.6F, amplitudeMultiplier = 0.9F),
                //bedrock("whismur", "ground_walk")
            )
        )
    }
}