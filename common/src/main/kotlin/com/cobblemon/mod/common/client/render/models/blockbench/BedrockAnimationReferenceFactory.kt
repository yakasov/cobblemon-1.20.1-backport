/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench

import com.cobblemon.mod.common.client.render.models.blockbench.animation.StatefulAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.animation.StatelessAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationRepository
import com.cobblemon.mod.common.client.render.models.blockbench.frame.ModelFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.entity.Entity

/**
 * An [AnimationReferenceFactory] that loads Bedrock-format animations from [BedrockAnimationRepository].
 *
 * The expected format is "bedrock(animation_group, animation_name)"
 *
 * @author Hiroku
 * @since June 28th, 2023
 */
object BedrockAnimationReferenceFactory : AnimationReferenceFactory {
    override fun <T : Entity> stateless(model: PoseableEntityModel<T>, animString: String): StatelessAnimation<T, ModelFrame> {
        val split = animString.replace("bedrock(", "").replace(")", "").split(",").map(String::trim)
        return model.bedrock(animationGroup = split[0], animation = split[1])
    }

    override fun <T : Entity> stateful(model: PoseableEntityModel<T>, animString: String, ): StatefulAnimation<T, ModelFrame> {
        val split = animString.replace("bedrock(", "").replace(")", "").split(",").map(String::trim)
        return model.bedrockStateful(
            animationGroup = split[0],
            animation = split[1]
        )
    }
}