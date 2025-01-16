/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen7

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class WimpodModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("wimpod")
    override val head = getPart("head")

    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")

    override var portraitScale = 2.0F
    override var portraitTranslation = Vec3d(-0.35, -1.6, 0.0)

    override var profileScale = 0.7F
    override var profileTranslation = Vec3d(0.0, 0.71, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var floating: PokemonPose
    lateinit var swimming: PokemonPose
    lateinit var sleep: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("wimpod", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("wimpod", "blink")}
        val leftTwitch = quirk { bedrockStateful("wimpod", "twitch_quirk_left")}
        val rightTwitch = quirk { bedrockStateful("wimpod", "twitch_quirk_right")}

        sleep = registerPose(
            poseName = "sleeping",
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("wimpod", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.UI_POSES + PoseType.STAND,
            transformTicks = 10,
            quirks = arrayOf(blink, leftTwitch, rightTwitch),
            idleAnimations = arrayOf(
                singleBoneLook(disableX = true),
                bedrock("wimpod", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            transformTicks = 10,
            poseType = PoseType.WALK,
            quirks = arrayOf(blink, leftTwitch, rightTwitch),
            idleAnimations = arrayOf(
                singleBoneLook(disableX = true),
                bedrock("wimpod", "ground_walk")
            )
        )

        floating = registerPose(
            poseName = "floating",
            transformTicks = 10,
            poseType = PoseType.FLOAT,
            quirks = arrayOf(blink, leftTwitch, rightTwitch),
            idleAnimations = arrayOf(
                singleBoneLook(disableX = true),
                bedrock("wimpod", "water_idle")
            )
        )

        swimming = registerPose(
            poseName = "swimming",
            transformTicks = 10,
            poseType = PoseType.SWIM,
            quirks = arrayOf(blink, leftTwitch, rightTwitch),
            idleAnimations = arrayOf(
                singleBoneLook(disableX = true),
                bedrock("wimpod", "water_swim"),
            )
        )
    }
    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = if (state.isPosedIn(standing, walk, sleep)) bedrockStateful("wimpod", "faint") else null
}