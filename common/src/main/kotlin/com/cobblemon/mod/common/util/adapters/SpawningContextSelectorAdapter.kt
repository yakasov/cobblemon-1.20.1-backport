/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.util.adapters

import com.cobblemon.mod.common.api.spawning.rules.selector.ConditionalSpawningContextSelector
import com.cobblemon.mod.common.api.spawning.rules.selector.ExpressionSpawningContextSelector
import com.cobblemon.mod.common.api.spawning.rules.selector.SpawningContextSelector
import com.cobblemon.mod.common.util.asExpression
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

object SpawningContextSelectorAdapter : JsonDeserializer<SpawningContextSelector> {
    override fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawningContextSelector {
        return if (json.isJsonPrimitive) {
            val expression = json.asString.asExpression()
            ExpressionSpawningContextSelector().also { it.expression = expression }
        } else {
            json as JsonObject
            val type = json.get("type")?.asString ?: return ctx.deserialize(json, ConditionalSpawningContextSelector::class.java)
            val clazz = SpawningContextSelector.types[type] ?: throw IllegalArgumentException("Unknown spawn detail selector type: $type")
            ctx.deserialize(json, clazz)
        }
    }
}