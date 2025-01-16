/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class VenonatModel(root: ModelPart) : PokemonPoseableModel() {
    override val rootPart = root.registerChildWithAllChildren("venonat")

    override var portraitScale = 1.66F
    override var portraitTranslation = Vec3d(-0.08, -0.66, 0.0)

    override var profileScale = 0.86F
    override var profileTranslation = Vec3d(0.0, 0.43, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var battleidle: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("venonat", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("venonat", "blink") }
        val quirk1 = quirk { bedrockStateful("venonat", "quirk1") }
        val quirk2 = quirk { bedrockStateful("venonat", "quirk2") }

        sleep = registerPose(
            poseType = PoseType.SLEEP,
            quirks = arrayOf(quirk1, quirk2),
            idleAnimations = arrayOf(bedrock("venonat", "sleep"))
        )
        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink, quirk1, quirk2),
            idleAnimations = arrayOf(
                bedrock("venonat", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            quirks = arrayOf(blink, quirk1, quirk2),
            idleAnimations = arrayOf(
                bedrock("venonat", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink, quirk1, quirk2),
            condition = { it.isBattling },
            idleAnimations = arrayOf(
                bedrock("venonat", "battle_idle")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("venonat", "faint") else null
}