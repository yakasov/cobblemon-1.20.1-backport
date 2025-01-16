/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class GarchompModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("garchomp")
    override val head = getPart("head")
    override val rightArm = getPart("arm_right")
    override val leftArm = getPart("arm_left")
    override val rightLeg = getPart("leg_right1")
    override val leftLeg = getPart("leg_left1")

    override var portraitScale = 2.4F
    override var portraitTranslation = Vec3d(-1.1, 1.9, 0.0)

    override var profileScale = 0.55F
    override var profileTranslation = Vec3d(0.0, 0.9, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var flyidle: PokemonPose
    lateinit var fly: PokemonPose
    lateinit var battleidle: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("garchomp", "cry") }

    override fun registerPoses() {

        val blink = quirk { bedrockStateful("garchomp", "blink") }

        standing = registerPose(
            poseName = "standing",
            poseTypes = setOf(PoseType.NONE, PoseType.STAND, PoseType.PORTRAIT, PoseType.PROFILE, PoseType.FLOAT, PoseType.SWIM),
            transformTicks = 10,
            quirks = arrayOf(blink),
            condition = { !it.isBattling },
            idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("garchomp", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = setOf(PoseType.WALK),
            transformTicks = 5,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("garchomp", "ground_idle"),
                    bedrock("garchomp", "ground_run")
            )
        )

        flyidle = registerPose(
            poseName = "hover",
            poseTypes = setOf(PoseType.HOVER),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("garchomp", "air_idle")
            )
        )

        fly = registerPose(
            poseName = "fly",
            poseTypes = setOf(PoseType.FLY),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("garchomp", "air_idle")
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            condition = { it.isBattling },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("garchomp", "battle_idle")
            )
        )
    }
}