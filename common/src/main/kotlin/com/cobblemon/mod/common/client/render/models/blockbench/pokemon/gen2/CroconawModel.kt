/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen2

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class CroconawModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("croconaw")
    override val head = getPart("head")

    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")

    override var portraitScale = 1.3F
    override var portraitTranslation = Vec3d(-0.2, 1.0, 0.0)

    override var profileScale = 0.6F
    override var profileTranslation = Vec3d(0.0, 0.76, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var floating: PokemonPose
    lateinit var swimming: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var watersleep: PokemonPose
    lateinit var battleidle: PokemonPose
    lateinit var water_surface_idle: PokemonPose
    lateinit var water_surface_swim: PokemonPose

    val wateroffset = -10

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("croconaw", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("croconaw", "blink")}

        sleep = registerPose(
            poseName = "sleeping",
            poseType = PoseType.SLEEP,
            condition = { !it.isTouchingWater },
            idleAnimations = arrayOf(bedrock("croconaw", "sleep"))
        )

        watersleep = registerPose(
            poseName = "water_sleeping",
            poseType = PoseType.SLEEP,
            condition = { it.isTouchingWater },
            idleAnimations = arrayOf(bedrock("croconaw", "water_sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.UI_POSES + PoseType.STAND,
            transformTicks = 10,
            condition = { !it.isBattling && !it.isTouchingWater && !it.isSubmergedInWater},
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            transformTicks = 10,
            poseType = PoseType.WALK,
            condition = { !it.isTouchingWater && !it.isSubmergedInWater},
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "ground_walk")
            )
        )

        floating = registerPose(
            poseName = "floating",
            transformTicks = 10,
            poseType = PoseType.FLOAT,
            condition = { it.isSubmergedInWater },
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "water_idle")
            )
        )

        swimming = registerPose(
            poseName = "swimming",
            transformTicks = 10,
            condition = { it.isSubmergedInWater },
            poseType = PoseType.SWIM,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "water_swim"),
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            condition = { it.isBattling && !it.isTouchingWater },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "battle_idle")
            )
        )

        water_surface_idle = registerPose(
            poseName = "surface_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            quirks = arrayOf(blink),
            condition = { !it.isSubmergedInWater && it.isTouchingWater },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "surfacewater_idle"),
            ),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
            )
        )

        water_surface_swim = registerPose(
            poseName = "surface_swim",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            condition = { !it.isSubmergedInWater && it.isTouchingWater },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("croconaw", "surfacewater_swim"),
            ),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
            )
        )
    }
    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = if (state.isPosedIn(standing, walk, battleidle, sleep)) bedrockStateful("croconaw", "faint") else if (state.isPosedIn(water_surface_idle, water_surface_swim, watersleep )) bedrockStateful("croconaw", "faint") else null
}