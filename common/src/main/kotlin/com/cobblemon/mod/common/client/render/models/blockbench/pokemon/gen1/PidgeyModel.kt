/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.SHOULDER_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class PidgeyModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("pidgey")
    override val head = getPart("head")

    private val wingOpenRight = getPart("wing_open_right")
    private val wingOpenLeft = getPart("wing_open_left")
    private val wingClosedRight = getPart("wing_closed_right")
    private val wingClosedLeft = getPart("wing_closed_left")

    override var portraitScale = 3.5F
    override var portraitTranslation = Vec3d(-0.1, -2.1, 0.0)

    override var profileScale = 1.2F
    override var profileTranslation = Vec3d(0.0, -0.01, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var stand: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var hover: PokemonPose
    lateinit var fly: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("pidgey", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("pidgey", "blink") }

        sleep = registerPose(
            poseName = "sleeping",
            transformedParts = arrayOf(
                wingClosedLeft.createTransformation().withVisibility(visibility = true),
                wingClosedRight.createTransformation().withVisibility(visibility = true),
                wingOpenLeft.createTransformation().withVisibility(visibility = false),
                wingOpenRight.createTransformation().withVisibility(visibility = false)
            ),
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("pidgey", "sleep_PLACEHOLDER"))
        )

        stand = registerPose(
            poseName = "standing",
            poseTypes = SHOULDER_POSES + UI_POSES + PoseType.STATIONARY_POSES - PoseType.HOVER,
            transformTicks = 10,
            transformedParts = arrayOf(
                wingClosedLeft.createTransformation().withVisibility(visibility = true),
                wingClosedRight.createTransformation().withVisibility(visibility = true),
                wingOpenLeft.createTransformation().withVisibility(visibility = false),
                wingOpenRight.createTransformation().withVisibility(visibility = false)
            ),
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("pidgey", "ground_idle")
            )
        )

        hover = registerPose(
            poseName = "hover",
            poseType = PoseType.HOVER,
            transformedParts = arrayOf(
                wingClosedLeft.createTransformation().withVisibility(visibility = false),
                wingClosedRight.createTransformation().withVisibility(visibility = false),
                wingOpenLeft.createTransformation().withVisibility(visibility = true),
                wingOpenRight.createTransformation().withVisibility(visibility = true)
            ),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("pidgey", "air_idle")
            )
        )

        fly = registerPose(
            poseName = "fly",
            poseType = PoseType.FLY,
            transformedParts = arrayOf(
                wingClosedLeft.createTransformation().withVisibility(visibility = false),
                wingClosedRight.createTransformation().withVisibility(visibility = false),
                wingOpenLeft.createTransformation().withVisibility(visibility = true),
                wingOpenRight.createTransformation().withVisibility(visibility = true)
            ),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("pidgey", "air_fly")
            )
        )

        walk = registerPose(
            poseName = "walking",
            poseTypes = PoseType.MOVING_POSES - PoseType.FLY,
            transformedParts = arrayOf(
                wingClosedLeft.createTransformation().withVisibility(visibility = true),
                wingClosedRight.createTransformation().withVisibility(visibility = true),
                wingOpenLeft.createTransformation().withVisibility(visibility = false),
                wingOpenRight.createTransformation().withVisibility(visibility = false)
            ),
            transformTicks = 10,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("pidgey", "ground_walk")
            )
        )
    }
}