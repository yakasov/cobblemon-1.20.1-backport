/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
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

class CranidosModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("cranidos")
    override val head = getPart("neck")

    override var portraitScale = 2.0F
    override var portraitTranslation = Vec3d(-0.3, -0.2, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.6, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var battleidle: PokemonPose
    lateinit var shoulderLeft: PokemonPose
    lateinit var shoulderRight: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("cranidos", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("cranidos", "blink") }
        val idlequirk = quirk(secondsBetweenOccurrences = 60F to 120F) { bedrockStateful("cranidos", "quirk_idle") }
        val shakequirk = quirk(secondsBetweenOccurrences = 60F to 120F) { bedrockStateful("cranidos", "quirk_shake") }

        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("cranidos", "sleep")
            )
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink, idlequirk, shakequirk),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("cranidos", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("cranidos", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battleidle",
            poseTypes = PoseType.STATIONARY_POSES,
            condition = { it.isBattling },
            quirks = arrayOf(blink, shakequirk),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("cranidos", "battle_idle")
            )
        )
    }

    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = bedrockStateful("cranidos", "faint")
}