/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen9

import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.util.asExpressionLike
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class GimmighoulChestModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("gimmighoul_chest")
    override val head = getPart("head")

    override val leftArm = getPart("arm_left")
    override val rightArm = getPart("arm_right")
    override val leftLeg = getPart("leg_left")
    override val rightLeg = getPart("leg_right")

    override var portraitScale = 2.54F
    override var portraitTranslation = Vec3d(-0.01, -1.6, 0.0)

    override var profileScale = 0.65F
    override var profileTranslation = Vec3d(0.0, 0.76, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var closed: PokemonPose
    lateinit var battle: PokemonPose

    override val cryAnimation = CryProvider { _, pose -> if (pose.isPosedIn(battle)) bedrockStateful("gimmighoul_chest", "battle_cry") else bedrockStateful("gimmighoul_chest", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("gimmighoul_chest", "blink") }
        val quirk = quirk(secondsBetweenOccurrences = 30F to 120F) { bedrockStateful("gimmighoul_chest", "idle_quirk") }

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink,quirk),
            condition = { it.ownerUuid != null && !it.isBattling },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("gimmighoul_chest", "ground_idle")
            )
        )

        closed = registerPose(
            poseName = "closed",
            poseTypes = PoseType.STATIONARY_POSES,
            quirks = arrayOf(blink),
            condition = { it.ownerUuid == null && !it.isBattling },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("gimmighoul_chest", "mimic")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("gimmighoul_chest", "ground_walk")
                //bedrock("gimmighoul_chest", "ground_walk")
            )
        )

        battle = registerPose(
            poseName = "battle",
            poseTypes = PoseType.STATIONARY_POSES,
            quirks = arrayOf(blink, quirk),
            condition = { it.isBattling },
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("gimmighoul_chest", "battle_idle"),
            )
        )

        closed.transitions[battle.poseName] = { _, _ ->
            bedrockStateful("gimmighoul_chest", "surprise")
        }
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("gimmighoul_chest", "faint") else null
}