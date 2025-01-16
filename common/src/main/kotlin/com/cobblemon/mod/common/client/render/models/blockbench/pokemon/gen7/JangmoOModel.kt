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
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class JangmoOModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, QuadrupedFrame {
    override val rootPart = root.registerChildWithAllChildren("jangmo_o")
    override val head = getPart("head")

    override val foreLeftLeg = getPart("leg_front_left1")
    override val foreRightLeg = getPart("leg_front_right1")
    override val hindLeftLeg = getPart("leg_back_left1")
    override val hindRightLeg = getPart("leg_back_right1")

    override var portraitScale = 1.75F
    override var portraitTranslation = Vec3d(-0.22, -0.1, 0.0)

    override var profileScale = 0.64F
    override var profileTranslation = Vec3d(0.0, 0.77, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

//    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("jangmo-o", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("jangmo-o", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.UI_POSES + PoseType.STATIONARY_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    bedrock("jangmo-o", "ground_idle"),
                singleBoneLook()
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    bedrock("jangmo-o", "ground_idle"),
                QuadrupedWalkAnimation(this, periodMultiplier = 1.1F),
                singleBoneLook()
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("jangmo-o", "faint") else null
}