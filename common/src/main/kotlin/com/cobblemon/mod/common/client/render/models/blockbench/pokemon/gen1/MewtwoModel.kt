/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.animation.BimanualSwingAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class MewtwoModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BimanualFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("mewtwo")
    override val head = getPart("head")
    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")
    override val leftArm = getPart("arm_left")
    override val rightArm = getPart("arm_right")

    override var portraitScale = 2.8F
    override var portraitTranslation = Vec3d(-0.15, 1.5, 0.0)

    override var profileScale = 0.7F
    override var profileTranslation = Vec3d(0.0, 0.61, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var hover: PokemonPose
    lateinit var fly: PokemonPose

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("mewtwo", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = UI_POSES + PoseType.STAND,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("mewtwo", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseType = PoseType.WALK,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("mewtwo", "ground_idle"),
                BipedWalkAnimation(this, periodMultiplier = 0.6F, amplitudeMultiplier = 0.9F),
                BimanualSwingAnimation(this, swingPeriodMultiplier = 0.6F, amplitudeMultiplier = 0.9F)
            )
        )

        hover = registerPose(
            poseName = "hover",
            poseTypes = setOf(PoseType.FLOAT, PoseType.HOVER),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("mewtwo", "air_idle")
            )
        )

        fly = registerPose(
            poseName = "fly",
            poseTypes = setOf(PoseType.FLY, PoseType.SWIM),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("mewtwo", "air_fly")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("mewtwo", "faint") else null
}