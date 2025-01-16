/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen3

import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class ArmaldoModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BimanualFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("armaldo")
    override val head = getPart("head")

    override val leftArm = getPart("arm1_left")
    override val rightArm = getPart("arm1_right")
    override val leftLeg = getPart("left_leg")
    override val rightLeg = getPart("right_leg")

    override var portraitTranslation = Vec3d(-0.46, 1.5, 0.0)
    override var portraitScale = 1.73F

    override var profileTranslation = Vec3d(0.0, 0.77, 0.0)
    override var profileScale = 0.63F

    lateinit var standing: PokemonPose
    lateinit var walking: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var battleidle: PokemonPose
    lateinit var water_surface_sleep: PokemonPose
    lateinit var water_sleep: PokemonPose
    lateinit var water_surface_idle: PokemonPose
    lateinit var water_idle: PokemonPose
    lateinit var water_surface_battleidle: PokemonPose
    lateinit var water_battleidle: PokemonPose
    lateinit var water_surface_swim: PokemonPose
    lateinit var water_swim:PokemonPose
    lateinit var ui_poses: PokemonPose

    val wateroffset = -4.5
    val watersurfaceoffset = 10

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("armaldo", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("armaldo", "blink") }
        val look = quirk { bedrockStateful("armaldo", "look_quirk") }

        ui_poses = registerPose (
                poseName = "ui_poses",
                poseTypes = PoseType.UI_POSES,
                quirks = arrayOf(blink, look),
                idleAnimations = arrayOf(
                        bedrock("armaldo", "ground_idle")
                )
        )

        sleep = registerPose(
                poseName = "sleep",
                poseType = PoseType.SLEEP,
                condition = { !it.isTouchingWater },
                idleAnimations = arrayOf(bedrock("armaldo", "sleep")
                )
        )

        water_surface_sleep = registerPose(
                poseName = "water_surface_sleep",
                poseType = PoseType.SLEEP,
                condition = { !it.isSubmergedInWater && it.isTouchingWater },
                idleAnimations = arrayOf(bedrock("armaldo", "water_sleep")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, watersurfaceoffset)
                )
        )

        water_sleep = registerPose(
                poseName = "water_sleep",
                poseType = PoseType.SLEEP,
                condition = { it.isSubmergedInWater && it.isTouchingWater },
                idleAnimations = arrayOf(bedrock("armaldo", "water_sleep")),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
                )
        )

        standing = registerPose(
                poseName = "standing",
                poseTypes = PoseType.STATIONARY_POSES,
                transformTicks = 10,
                condition = { !it.isBattling && !it.isTouchingWater},
                quirks = arrayOf(blink, look),
                idleAnimations = arrayOf(
                        bedrock("armaldo", "ground_idle")
                )
        )

        water_surface_idle = registerPose(
                poseName = "water_surface_idle",
                poseTypes = PoseType.STATIONARY_POSES,
                condition = { !it.isSubmergedInWater && it.isTouchingWater && !it.isBattling},
                transformTicks = 10,
                quirks = arrayOf(blink, look),
                idleAnimations = arrayOf(
                        bedrock("armaldo", "water_idle")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, watersurfaceoffset)
                )
        )

        water_idle = registerPose(
                poseName = "water_idle",
                poseTypes = PoseType.STATIONARY_POSES,
                condition = { it.isSubmergedInWater && it.isTouchingWater && !it.isBattling},
                transformTicks = 10,
                quirks = arrayOf(blink, look),
                idleAnimations = arrayOf(
                        bedrock("armaldo", "water_idle")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
                )
        )

        battleidle = registerPose(
                poseName = "battle_idle",
                poseTypes = PoseType.STATIONARY_POSES,
                transformTicks = 10,
                quirks = arrayOf(blink, look),
                condition = { it.isBattling && !it.isTouchingWater},
                idleAnimations = arrayOf(
                        bedrock("armaldo", "battle_idle")
                )
        )

        water_surface_battleidle = registerPose(
                poseName = "surface_battle_idle",
                poseTypes = PoseType.STATIONARY_POSES,
                condition = { !it.isSubmergedInWater && it.isTouchingWater && it.isBattling},
                transformTicks = 10,
                quirks = arrayOf(blink, look),
                idleAnimations = arrayOf(
                        bedrock("armaldo", "water_battle_idle")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, watersurfaceoffset)
                )
        )

        water_battleidle = registerPose(
                poseName = "water_battle_idle",
                poseTypes = PoseType.STATIONARY_POSES,
                condition = { it.isSubmergedInWater && it.isTouchingWater && it.isBattling},
                transformTicks = 10,
                quirks = arrayOf(blink, look),
                idleAnimations = arrayOf(
                        bedrock("armaldo", "water_battle_idle")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
                )
        )

        walking = registerPose(
                poseName = "walking",
                poseTypes = PoseType.MOVING_POSES,
                quirks = arrayOf(blink, look),
                condition = { !it.isSubmergedInWater && !it.isTouchingWater },
                transformTicks = 10,
                idleAnimations = arrayOf(
                        bedrock("armaldo", "ground_walk")
                )
        )

        water_surface_swim = registerPose(
                poseName = "surface_swim",
                poseTypes = PoseType.MOVING_POSES,
                quirks = arrayOf(blink, look),
                condition = { !it.isSubmergedInWater && it.isTouchingWater},
                transformTicks = 10,
                idleAnimations = arrayOf(
                        bedrock("armaldo", "water_swim")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, watersurfaceoffset)
                )
        )

        water_swim = registerPose(
                poseName = "water_swim",
                poseTypes = PoseType.MOVING_POSES,
                quirks = arrayOf(blink, look),
                condition = { it.isSubmergedInWater && it.isTouchingWater },
                transformTicks = 10,
                idleAnimations = arrayOf(
                        bedrock("armaldo", "water_swim")
                ),
                transformedParts = arrayOf(
                        rootPart.createTransformation().addPosition(ModelPartTransformation.Y_AXIS, wateroffset)
                )
        )
    }
//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walking)) bedrockStateful("armaldo", "faint") else null
}