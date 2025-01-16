/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen5

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class PigniteModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("pignite")
    override val head = getPart("head")
    override val rightArm = getPart("arm_right")
    override val leftArm = getPart("arm_left")
    override val rightLeg = getPart("leg_right")
    override val leftLeg = getPart("leg_left")

    override var portraitScale = 2.0F
    override var portraitTranslation = Vec3d(-0.15, 0.45, 0.0)
    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.55, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("pignite", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("pignite", "blink") }
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            transformTicks = 10,
            idleAnimations = arrayOf(bedrock("pignite", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = setOf(PoseType.NONE, PoseType.STAND, PoseType.PORTRAIT, PoseType.PROFILE),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("pignite", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walking",
            poseTypes = setOf(PoseType.SWIM, PoseType.WALK),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("pignite", "ground_walk")
            )
        )
    }

    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = if (state.isPosedIn(standing, walk, sleep)) bedrockStateful("pignite", "faint") else null
}