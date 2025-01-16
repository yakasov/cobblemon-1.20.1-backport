/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.api.scheduling.afterOnServer
import com.cobblemon.mod.common.client.render.models.blockbench.animation.BimanualSwingAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
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

class BuizelModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("buizel")
    override val head = getPart("head")

    override var portraitScale = 2.3F
    override var portraitTranslation = Vec3d(-0.2, 0.1, 0.0)

    override var profileScale = 0.7F
    override var profileTranslation = Vec3d(0.0, 0.65, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walking: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var waterSleep: PokemonPose
    lateinit var float: PokemonPose
    lateinit var swim: PokemonPose
    lateinit var surfaceWaterIdle: PokemonPose
    lateinit var surfaceWaterSwim: PokemonPose
    lateinit var surfaceWaterSleep: PokemonPose

    val wateroffset = -8

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("buizel", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("buizel", "blink") }
        sleep = registerPose(
            poseName = "sleep",
            condition = { !it.isTouchingWater},
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("buizel", "sleep"))
        )

        waterSleep = registerPose(
            poseName = "water_sleep",
            condition = { it.isSubmergedInWater },
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("buizel", "water_sleep"))
        )

        surfaceWaterSleep = registerPose(
            poseName = "surface_water_sleep",
            condition = { it.isTouchingWater && !it.isSubmergedInWater },
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(
                bedrock("buizel", "surfacewater_sleep")
            ),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
            )
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES - PoseType.FLOAT,
            condition = { !it.isTouchingWater },
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buizel", "ground_idle")
            )
        )

        walking = registerPose(
            poseName = "walking",
            poseTypes = PoseType.MOVING_POSES - PoseType.SWIM,
            condition = { !it.isTouchingWater },
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buizel", "ground_walk")
            )
        )

        float = registerPose(
            poseName = "float",
            poseType = PoseType.FLOAT,
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buizel", "water_idle")
            ),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
            )
        )

        swim = registerPose(
            poseName = "swim",
            poseType = PoseType.SWIM,
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buizel", "water_swim")
            )
        )

        surfaceWaterIdle = registerPose(
            poseName = "surface_water_idle",
            condition = { it.isTouchingWater && !it.isSubmergedInWater },
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buizel", "surfacewater_idle")
            ),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
            )
        )

        surfaceWaterSwim = registerPose(
            poseName = "surface_water_swim",
            condition = { it.isTouchingWater && !it.isSubmergedInWater },
            poseTypes = PoseType.MOVING_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buizel", "surfacewater_swim")
            ),
            transformedParts = arrayOf(
                rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
            )
        )
    }
//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walking, sleep)) bedrockStateful("buizel", "faint") else null
}