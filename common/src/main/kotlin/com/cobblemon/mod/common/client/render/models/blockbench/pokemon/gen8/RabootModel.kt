/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen8

import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class RabootModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("raboot")
    override val head = getPart("head")

    override var portraitScale = 2.5F
    override var portraitTranslation = Vec3d(-0.15, 0.3, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.56, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var battleIdle: PokemonPose
    lateinit var sleep: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("raboot", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("raboot", "blink") }
        val hipQuirk = quirk { bedrockStateful("raboot", "hip_quirk") }
        val sleepQuirk = quirk { bedrockStateful("raboot", "sleep_ear_quirk") }

        sleep = registerPose(
            poseName = "sleep",
            quirks = arrayOf(sleepQuirk),
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("raboot", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink, hipQuirk),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("raboot", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("raboot", "ground_walk")
            )
        )

        battleIdle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            condition = { it.isBattling },
            quirks = arrayOf(blink, hipQuirk),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("raboot", "battle_idle")
            )
        )
    }
}