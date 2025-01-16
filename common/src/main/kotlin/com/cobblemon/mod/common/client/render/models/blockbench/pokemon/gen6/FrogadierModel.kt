/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen6

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

class FrogadierModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("frogadier")
    override val head = getPart("head")
    override val rightArm = getPart("arm_right")
    override val leftArm = getPart("arm_left")
    override val rightLeg = getPart("leg_right")
    override val leftLeg = getPart("leg_left")

    override var portraitScale = 1.9F
    override var portraitTranslation = Vec3d(-0.1, 1.4, 0.0)

    override var profileScale = 0.7F
    override var profileTranslation = Vec3d(0.0, 0.7, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var float: PokemonPose
    lateinit var swim: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var battleidle: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("frogadier", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("frogadier", "blink")}
        sleep = registerPose(
                poseType = PoseType.SLEEP,
                transformTicks = 10,
                quirks = arrayOf(blink),
                idleAnimations = arrayOf(bedrock("frogadier", "sleep"))
        )

        standing = registerPose(
                poseName = "standing",
                poseTypes = PoseType.UI_POSES + PoseType.STATIONARY_POSES - PoseType.FLOAT,
                transformTicks = 10,
                quirks = arrayOf(blink),
                condition = { !it.isBattling },
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("frogadier", "ground_idle")
                )
        )

        walk = registerPose(
                poseName = "walk",
                poseType = PoseType.WALK,
                transformTicks = 10,
                quirks = arrayOf(blink),
                condition = { !it.isBattling },
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("frogadier", "ground_walk")
                )
        )

        float = registerPose(
                poseName = "swim_idle",
                poseTypes = setOf(PoseType.FLOAT, PoseType.HOVER),
                transformTicks = 10,
                quirks = arrayOf(blink),
                condition = { !it.isBattling },
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("frogadier", "water_idle")
                )
        )

        swim = registerPose(
                poseName = "swim",
                poseTypes = setOf(PoseType.SWIM, PoseType.FLY),
                transformTicks = 10,
                quirks = arrayOf(blink),
                condition = { !it.isBattling },
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("frogadier", "water_swim")
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
                        bedrock("frogadier", "battle_idle")
                )

        )
    }

    override fun getFaintAnimation(
            pokemonEntity: PokemonEntity,
            state: PoseableEntityState<PokemonEntity>
    ) = if (state.isPosedIn(standing, walk, battleidle, swim, float, sleep)) bedrockStateful("frogadier", "faint") else null
}