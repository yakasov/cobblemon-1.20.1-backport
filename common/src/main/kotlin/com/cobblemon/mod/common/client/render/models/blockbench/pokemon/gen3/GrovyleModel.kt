/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen3

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class GrovyleModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("grovyle")
    override val head = getPart("head")

    override val leftLeg = getPart("left_upper_leg")
    override val rightLeg = getPart("right_upper_leg")

    override var portraitScale = 2.31F
    override var portraitTranslation = Vec3d(-0.24, 1.29, 0.0)

    override var profileScale = 0.66F
    override var profileTranslation = Vec3d(0.01, 0.87, 0.0)

    //lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("grovyle", "cry") }

    override fun registerPoses() {
        //sleep = registerPose(
        //    poseType = PoseType.SLEEP,
        //    idleAnimations = arrayOf(bedrock("grovyle", "sleep"))
        //)

        val blink = quirk { bedrockStateful("grovyle", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("grovyle", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("grovyle", "ground_idle"),
                    BipedWalkAnimation(this, periodMultiplier = 0.6F, amplitudeMultiplier = 0.9F)
            )
        )
    }
    //override fun getFaintAnimation(
    //    pokemonEntity: PokemonEntity,
    //    state: PoseableEntityState<PokemonEntity>
    //) = if (state.isNotPosedIn(sleep)) bedrockStateful("grovyle", "faint") else null
}
