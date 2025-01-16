/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen7

import com.cobblemon.mod.common.client.render.models.blockbench.animation.QuadrupedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.QuadrupedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class LittenModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, QuadrupedFrame {
    override val rootPart = root.registerChildWithAllChildren("litten")
    override val head = getPart("head")

    override val hindLeftLeg = getPart("leg_back_left1")
    override val hindRightLeg = getPart("leg_back_right1")
    override val foreLeftLeg= getPart("leg_front_left1")
    override val foreRightLeg = getPart("leg_front_right1")

    override var portraitScale = 2.2F
    override var portraitTranslation = Vec3d(-0.35, -1.0, 0.0)

    override var profileScale = 0.85F
    override var profileTranslation = Vec3d(0.0, 0.48, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("litten", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("litten", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("litten", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("litten", "ground_idle"),
                QuadrupedWalkAnimation(this, amplitudeMultiplier = 0.7F, periodMultiplier = 0.9F)
                //bedrock("litten", "ground_walk")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("litten", "faint") else null
}