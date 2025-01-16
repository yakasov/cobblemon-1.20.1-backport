/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen6

import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class DragalgeModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("dragalge")
    override val head = getPart("head")

    override var portraitScale = 2.4F
    override var portraitTranslation = Vec3d(-0.55, 1.5, 0.0)

    override var profileScale = 0.7F
    override var profileTranslation = Vec3d(0.0, 1.0, -6.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("dragalge", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("dragalge", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("dragalge", "water_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("dragalge", "water_idle")
            )
        )
    }
}