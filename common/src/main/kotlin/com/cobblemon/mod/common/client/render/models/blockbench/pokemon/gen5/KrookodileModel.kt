/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen5

import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.util.asExpressionLike
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class KrookodileModel (root: ModelPart) : PokemonPoseableModel() {
    override val rootPart = root.registerChildWithAllChildren("krookodile")

    override var portraitScale = 1.26F
    override var portraitTranslation = Vec3d(-0.57, 1.6, 0.0)

    override var profileScale = 0.52F
    override var profileTranslation = Vec3d(0.06, 0.91, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var battle_idle: PokemonPose

    override fun registerPoses() {
//        animations["physical"] = "q.bedrock_primary('krookodile', 'physical', 'look', q.curve('symmetrical_wide'))".asExpressionLike()
//        animations["special"] = "q.bedrock_primary('krookodile', 'special', 'look', q.curve('symmetrical_wide'))".asExpressionLike()
//        animations["status"] = "q.bedrock_primary('krookodile', 'status', q.curve('symmetrical_wide'))".asExpressionLike()
//        animations["recoil"] = "q.bedrock_stateful('krookodile', 'recoil')".asExpressionLike()
        animations["cry"] = "q.bedrock_stateful('krookodile', 'cry')".asExpressionLike()

        val faint = "q.bedrock_primary('krookodile', 'faint', q.curve('one'))".asExpressionLike()

        val blink = quirk { bedrockStateful("krookodile", "blink") }
        val look = quirk { bedrockStateful("krookodile", "look_quirk") }

        sleep = registerPose(
            poseType = PoseType.SLEEP,
            animations = mutableMapOf("faint" to faint),
            idleAnimations = arrayOf(bedrock("krookodile", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
            quirks = arrayOf(blink, look),
            condition = { !it.isBattling },
            animations = mutableMapOf("faint" to faint),
            idleAnimations = arrayOf(
                bedrock("krookodile", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            quirks = arrayOf(blink),
            animations = mutableMapOf("faint" to faint),
            idleAnimations = arrayOf(
                bedrock("krookodile", "ground_walk")
            )
        )

        battle_idle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            quirks = arrayOf(blink, look),
            condition = { it.isBattling },
            animations = mutableMapOf("faint" to faint),
            idleAnimations = arrayOf(
                bedrock("krookodile", "battle_idle")
            )
        )
    }
}