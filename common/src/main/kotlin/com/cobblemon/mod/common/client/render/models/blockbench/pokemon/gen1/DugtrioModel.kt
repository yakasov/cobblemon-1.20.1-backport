/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class DugtrioModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("dugtrio")
    override val head = getPart("body3")

    val lefthead = object : HeadedFrame {
        override val rootPart = this@DugtrioModel.rootPart
        override val head: ModelPart = getPart("body2")
    }
    val righthead = object : HeadedFrame {
        override val rootPart = this@DugtrioModel.rootPart
        override val head: ModelPart = getPart("body1")
    }

    override var portraitScale = 1.3F
    override var portraitTranslation = Vec3d(-0.11, -0.1, 0.0)

    override var profileScale = 0.9F
    override var profileTranslation = Vec3d(0.0, 0.29, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walking: PokemonPose
    lateinit var battleidle: PokemonPose
    lateinit var sleep: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("dugtrio", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("dugtrio", "blink")}
        val blink2 = quirk { bedrockStateful("dugtrio", "blink2")}
        val blink3 = quirk { bedrockStateful("dugtrio", "blink3")}

        val quirk = quirk { bedrockStateful("dugtrio", "quirk_idle")}
        val quirk2 = quirk { bedrockStateful("dugtrio", "quirk_idle2")}
        val quirk3 = quirk { bedrockStateful("dugtrio", "quirk_idle3")}

        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("dugtrio", "sleep"))
        )

        standing = registerPose(
            poseName = "stand",
            poseTypes = STATIONARY_POSES + UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink, blink2, blink3, quirk, quirk2, quirk3),
            idleAnimations = arrayOf(
                    singleBoneLook(pitchMultiplier = 0.6F, yawMultiplier = 0.4F, maxPitch = 10F, minPitch = -30F),
                    SingleBoneLookAnimation(lefthead, false, false, false, false, 1F, 1.4F, 0F, -30F, 20F, -45F),
                    SingleBoneLookAnimation(righthead, false, false, false, false, 1F, 1.4F, 0F, -30F, 45F, -25F),
                    bedrock("dugtrio", "ground_idle"))
        )

        walking = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            quirks = arrayOf(blink, blink2, blink3),
            idleAnimations = arrayOf(
                    singleBoneLook(pitchMultiplier = 0.6F, yawMultiplier = 0.4F, maxPitch = 10F, minPitch = -30F),
                    SingleBoneLookAnimation(lefthead, false, false, false, false, 1F, 1.4F, 0F, -30F, 20F, -45F),
                    SingleBoneLookAnimation(righthead, false, false, false, false, 1F, 1.4F, 0F, -30F, 45F, -25F),
                    bedrock("dugtrio", "ground_walk"))
        )

        battleidle = registerPose(
            poseName = "battleidle",
            poseTypes = STATIONARY_POSES,
            condition = { it.isBattling },
            quirks = arrayOf(blink, blink2, blink3, quirk, quirk2, quirk3),
            idleAnimations = arrayOf(bedrock("dugtrio", "battle_idle"))
        )
    }

    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = bedrockStateful("dugtrio", "faint")
}