/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen6

import com.cobblemon.mod.common.client.render.models.blockbench.animation.BimanualSwingAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class SliggooModel (root: ModelPart) : PokemonPoseableModel(), BimanualFrame, HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("sliggoo")
    override val head = getPart("head")

    override val leftArm = getPart("arm_right")
    override val rightArm = getPart("arm_left")

    override var portraitScale = 1.72F
    override var portraitTranslation = Vec3d(-0.38, 0.69, 0.0)

    override var profileScale = 0.56F
    override var profileTranslation = Vec3d(0.07, 0.94, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("sliggoo", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("sliggoo", "blink") }

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("sliggoo", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("sliggoo", "ground_idle"),
                BimanualSwingAnimation(this, 0.4F, 1F)
            )
        )
    }
}