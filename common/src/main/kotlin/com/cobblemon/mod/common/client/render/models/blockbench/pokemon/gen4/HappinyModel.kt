/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class HappinyModel(root: ModelPart) : PokemonPoseableModel(), BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("happiny")
    override val leftLeg = getPart("left_foot")
    override val rightLeg = getPart("right_foot")

    override var portraitScale = 2.09F
    override var portraitTranslation = Vec3d(-0.1, -0.8, 0.0)

    override var profileScale = 0.76F
    override var profileTranslation = Vec3d(0.0, 0.6, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var battle_idle: PokemonPose

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("happiny", "blink") }
        val hairQuirk = quirk { bedrockStateful("happiny", "hairshake_quirk") }
        val happy = quirk { bedrockStateful("happiny", "happy_quirk") }

        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("happiny", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink, hairQuirk, happy),
            transformTicks = 10,
            idleAnimations = arrayOf(
                bedrock("happiny", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            quirks = arrayOf(blink),
            transformTicks = 10,
            idleAnimations = arrayOf(
                bedrock("happiny", "ground_walk")
            )
        )

        battle_idle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            quirks = arrayOf(blink, hairQuirk, happy),
            transformTicks = 10,
            condition = { it.isBattling },
            idleAnimations = arrayOf(
                bedrock("happiny", "battle_idle")
            )
        )
    }

    override fun getFaintAnimation(
            pokemonEntity: PokemonEntity,
            state: PoseableEntityState<PokemonEntity>
    ) = if (state.isPosedIn(standing, walk, sleep, battle_idle)) bedrockStateful("happiny", "faint") else null
}