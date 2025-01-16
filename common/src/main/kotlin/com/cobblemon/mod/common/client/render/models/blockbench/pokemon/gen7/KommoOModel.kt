/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen7

import com.cobblemon.mod.common.client.render.models.blockbench.animation.BimanualSwingAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class KommoOModel (root: ModelPart) : PokemonPoseableModel(), BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("kommo_o")

    override val leftArm = getPart("arm_right")
    override val rightArm = getPart("arm_left")
    override val leftLeg = getPart("leg_right")
    override val rightLeg = getPart("leg_left")

    override var portraitScale = 1.69F
    override var portraitTranslation = Vec3d(-0.65, 3.56, 0.0)

    override var profileScale = 0.39F
    override var profileTranslation = Vec3d(0.0, 1.16, -6.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("kommo-o", "cry") }
    override fun registerPoses() {
        val blink = quirk { bedrockStateful("kommo-o", "blink") }

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("kommo-o", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("kommo-o", "ground_idle"),
                BipedWalkAnimation(this,0.6F, 1F),
                BimanualSwingAnimation(this, 0.6F, 1F)
            )
        )
    }
}