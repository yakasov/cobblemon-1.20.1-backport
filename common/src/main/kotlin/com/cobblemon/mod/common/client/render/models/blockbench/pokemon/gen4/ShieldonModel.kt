/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.animation.QuadrupedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.QuadrupedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class ShieldonModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("shieldon")
    override val head = getPart("head")

    override var portraitScale = 1.8F
    override var portraitTranslation = Vec3d(-0.45, -0.9, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.5, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var battleidle: PokemonPose
    lateinit var shoulderLeft: PokemonPose
    lateinit var shoulderRight: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("shieldon", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("shieldon", "blink") }
        val glancequirk = quirk(secondsBetweenOccurrences = 60F to 120F) { bedrockStateful("shieldon", "glance_quirk") }
        val grazequirk = quirk(secondsBetweenOccurrences = 60F to 120F) { bedrockStateful("shieldon", "graze_quirk") }

        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("shieldon", "sleep")
            )
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink, glancequirk, grazequirk),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("shieldon", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("shieldon", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battleidle",
            poseTypes = PoseType.STATIONARY_POSES,
            condition = { it.isBattling },
            quirks = arrayOf(blink, glancequirk),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("shieldon", "battle_idle")
            )
        )
    }

    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = bedrockStateful("shieldon", "faint")
}