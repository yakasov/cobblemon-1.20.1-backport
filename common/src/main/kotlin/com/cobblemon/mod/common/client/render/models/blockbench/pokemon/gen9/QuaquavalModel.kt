/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen9

import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class QuaquavalModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("quaquaval")
    override val head = getPart("head")

    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")

    val water_feathers = getPart("water_feathers")

    override var portraitScale = 2.3F
    override var portraitTranslation = Vec3d(-0.42, 3.7, 0.0)

    override var profileScale = 0.32F
    override var profileTranslation = Vec3d(0.0, 1.24, 0.0)

    lateinit var standing: PokemonPose
    lateinit var standing2: PokemonPose
    lateinit var walking: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var battleidle: PokemonPose
    lateinit var floating: PokemonPose
    lateinit var swimming: PokemonPose
    lateinit var surface_floating: PokemonPose
    lateinit var surface_swimming: PokemonPose

    val wateroffset = 19

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("quaquaval", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("quaquaval", "blink") }
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("quaquaval", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            transformTicks = 10,
            condition = { !it.isBattling && !it.isTouchingWaterOrRain},
            transformedParts = arrayOf(
                water_feathers.createTransformation().withVisibility(visibility = false)
            ),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("quaquaval", "ground_idle")
            )
        )

        standing2 = registerPose(
            poseName = "standing2",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            condition = { !it.isBattling && it.isTouchingWaterOrRain && !it.isSubmergedInWater},
            transformedParts = arrayOf(
                water_feathers.createTransformation().withVisibility(visibility = false)
            ),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("quaquaval", "ground_idle2")
            )
        )

        walking = registerPose(
            poseName = "walking",
            poseTypes = PoseType.MOVING_POSES,
                condition = { !it.isTouchingWater},
            transformTicks = 10,
            transformedParts = arrayOf(
                water_feathers.createTransformation().withVisibility(visibility = false)
            ),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("quaquaval", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            transformedParts = arrayOf(
                water_feathers.createTransformation().withVisibility(visibility = true)
            ),
            condition = { it.isBattling },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("quaquaval", "battle_idle")
            )
        )

        floating = registerPose(
                transformTicks = 10,
                condition = { it.isTouchingWater },
                poseType = PoseType.FLOAT,
                quirks = arrayOf(blink),
                transformedParts = arrayOf(
                        water_feathers.createTransformation().withVisibility(visibility = false)
                ),
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("quaquaval", "water_idle"),
                )
        )

        swimming = registerPose(
                transformTicks = 10,
                condition = { it.isTouchingWater },
                poseType = PoseType.SWIM,
                quirks = arrayOf(blink),
                transformedParts = arrayOf(
                        water_feathers.createTransformation().withVisibility(visibility = false)
                ),
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("quaquaval", "water_swim"),
                )
        )

        surface_floating = registerPose(
                transformTicks = 10,
                condition = { it.isTouchingWater && !it.isSubmergedInWater},
                poseType = PoseType.STAND,
                quirks = arrayOf(blink),
                transformedParts = arrayOf(
                        water_feathers.createTransformation().withVisibility(visibility = false),
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
                ),
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("quaquaval", "water_idle"),
                )
        )

        surface_swimming = registerPose(
                transformTicks = 10,
                condition = { it.isTouchingWater && !it.isSubmergedInWater},
                poseType = PoseType.WALK,
                quirks = arrayOf(blink),
                transformedParts = arrayOf(
                        water_feathers.createTransformation().withVisibility(visibility = false),
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
                ),
                idleAnimations = arrayOf(
                        singleBoneLook(),
                        bedrock("quaquaval", "water_swim"),
                )
        )
    }
//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walking, battleidle, sleep)) bedrockStateful("quaquaval", "faint") else null
}