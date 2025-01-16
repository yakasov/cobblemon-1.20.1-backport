/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen9

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class MausholdfourModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("maushold")
    override val head = getPart("head")

    override var portraitScale = 1.0F
    override var portraitTranslation = Vec3d(0.1, 0.0, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.4, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var sleep: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("maushold_four", "cry") }

    override fun registerPoses() {

        val blink1 = quirk { bedrockStateful("maushold_four", "blink1")}
        val blink2 = quirk { bedrockStateful("maushold_four", "blink2")}
        val blink3 = quirk { bedrockStateful("maushold_four", "blink3")}
        val blink4 = quirk { bedrockStateful("maushold_four", "blink4")}

        val head2 = object : HeadedFrame {
            override val rootPart = this@MausholdfourModel.rootPart
            override val head: ModelPart = getPart("head2")
        }

        val head3 = object : HeadedFrame {
            override val rootPart = this@MausholdfourModel.rootPart
            override val head: ModelPart = getPart("head3")
        }

        val head4 = object : HeadedFrame {
            override val rootPart = this@MausholdfourModel.rootPart
            override val head: ModelPart = getPart("head4")
        }

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink1, blink2, blink3, blink4),
            idleAnimations = arrayOf(
                singleBoneLook(),
                SingleBoneLookAnimation(head2, false, false, disableX = false, disableY = false),
                SingleBoneLookAnimation(head3, false, false, disableX = false, disableY = false),
                SingleBoneLookAnimation(head4, false, false, disableX = false, disableY = false),
                bedrock("maushold_four", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink1, blink2, blink3, blink4),
            idleAnimations = arrayOf(
                singleBoneLook(),
                SingleBoneLookAnimation(head2, false, false, disableX = false, disableY = false),
                SingleBoneLookAnimation(head3, false, false, disableX = false, disableY = false),
                SingleBoneLookAnimation(head4, false, false, disableX = false, disableY = false),
                bedrock("maushold_four", "ground_walk")
            )
        )

        //sleep = registerPose(
        //    poseName = "sleep",
        //    poseType = PoseType.SLEEP,
        //    transformTicks = 10,
        //    idleAnimations = arrayOf(
        //        bedrock("maushold_four", "sleep")
        //    )
        //)
    }
    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = if (state.isNotPosedIn(sleep)) bedrockStateful("maushold_four", "faint") else null
}