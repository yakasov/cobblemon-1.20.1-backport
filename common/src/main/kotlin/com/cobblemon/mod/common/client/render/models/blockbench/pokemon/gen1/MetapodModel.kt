/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.ALL_POSES
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class MetapodModel(root: ModelPart) : PokemonPoseableModel() {
    override val rootPart = root.registerChildWithAllChildren("metapod")

    override var portraitScale = 2.0F
    override var portraitTranslation = Vec3d(-0.15, -1.25, 0.0)
    override var profileScale = 1.1F
    override var profileTranslation = Vec3d(0.0, 0.1, 0.0)

    lateinit var sleep: PokemonPose
    lateinit var standing: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("metapod", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("metapod", "blink")}
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("metapod", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            quirks = arrayOf(blink),
            poseTypes = ALL_POSES - PoseType.SLEEP,
            idleAnimations = arrayOf(bedrock("metapod", "ground_idle"))
        )
    }

    override fun getFaintAnimation(
        pokemonEntity: PokemonEntity,
        state: PoseableEntityState<PokemonEntity>
    ) = bedrockStateful("metapod", "faint")
}