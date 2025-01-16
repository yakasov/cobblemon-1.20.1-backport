/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1

import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class VoltorbModel(root: ModelPart) : PokemonPoseableModel() {
    override val rootPart = root.registerChildWithAllChildren("voltorb")

    override var portraitScale = 2.0F
    override var portraitTranslation = Vec3d(-0.2, -1.2, 0.0)

    override var profileScale = 1.1F
    override var profileTranslation = Vec3d(0.0, 0.1, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("voltorb", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("voltorb", "blink")}
        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            transformTicks = 0,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                bedrock("voltorb", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            onTransitionedInto = { it?.reset() },
            transformTicks = 0,
            idleAnimations = arrayOf(
                bedrock("voltorb", "ground_walk")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("voltorb", "faint") else null
}