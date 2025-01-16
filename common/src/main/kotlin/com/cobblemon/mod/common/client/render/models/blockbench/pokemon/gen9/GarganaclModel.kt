/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen9

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class GarganaclModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("garganacl")
    override val head = getPart("waist")
    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")
    val shoulder = getPart("shoulder_right")

    override var portraitScale = 2.6F
    override var portraitTranslation = Vec3d(-0.4, 3.0, 0.0)

    override var profileScale = 0.45F
    override var profileTranslation = Vec3d(0.0, 1.0, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var battlestanding: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var portrait: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("garganacl", "cry") }

    override fun registerPoses() {
        standing = registerPose(
            poseName = "standing",
            condition = { !it.isBattling },
            poseTypes = STATIONARY_POSES + PoseType.PROFILE,
            transformedParts = arrayOf(
                shoulder.createTransformation().withVisibility(visibility = true)
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("garganacl", "ground_idle")
            )
        )

        battlestanding = registerPose(
            poseName = "battlestanding",
            condition = { it.isBattling },
            poseTypes = STATIONARY_POSES,
            transformedParts = arrayOf(
                shoulder.createTransformation().withVisibility(visibility = true)
            ),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("garganacl", "battle_idle")
            )
        )

        sleep = registerPose(
                poseType = PoseType.SLEEP,
            transformedParts = arrayOf(
                shoulder.createTransformation().withVisibility(visibility = true)
            ),
                idleAnimations = arrayOf(bedrock("garganacl", "sleep"))
        )

        portrait = registerPose(
            poseName = "portrait",
            poseType = PoseType.PORTRAIT,
            transformedParts = arrayOf(
                shoulder.createTransformation().withVisibility(visibility = false)
            ),
            idleAnimations = arrayOf(
                bedrock("garganacl", "ground_idle")
            )
        )

        walk = registerPose(
                poseName = "walk",
                condition = { !it.isBattling },
                poseTypes = MOVING_POSES,
            transformedParts = arrayOf(
                shoulder.createTransformation().withVisibility(visibility = true)
            ),
                idleAnimations = arrayOf(
                    singleBoneLook(),
                    bedrock("garganacl", "ground_idle"),
                    bedrock("garganacl", "ground_walk")
                )
        )

    }
    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = if (state.isNotPosedIn(sleep)) bedrockStateful("garganacl", "faint") else null
}