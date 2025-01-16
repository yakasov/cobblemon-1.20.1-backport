/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client

import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import com.cobblemon.mod.common.api.molang.MoLangFunctions.addFunctions
import com.cobblemon.mod.common.api.molang.MoLangFunctions.getQueryStruct
import com.cobblemon.mod.common.api.text.text
import com.cobblemon.mod.common.util.asIdentifierDefaultingNamespace
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvent

object ClientMoLangFunctions {
    val clientFunctions = hashMapOf<String, java.util.function.Function<MoParams, Any>>(
        "sound" to java.util.function.Function { params ->
            if (params.get<MoValue>(0) !is StringValue) {
                return@Function Unit
            }
            val soundEvent = SoundEvent.of(params.getString(0).asIdentifierDefaultingNamespace())
            if (soundEvent != null) {
                val pitch = if (params.contains(2)) params.getDouble(2).toFloat() else 1F
                MinecraftClient.getInstance().soundManager.play(PositionedSoundInstance.master(soundEvent, pitch))
            }
        },
        "is_time" to java.util.function.Function { params ->
            val time = (MinecraftClient.getInstance().world?.timeOfDay ?: 0) % 24000
            val min = params.getInt(0)
            val max = params.getInt(1)
            time in min..max
        },
        "say" to java.util.function.Function { params -> MinecraftClient.getInstance().player?.sendMessage(params.getString(0).text()) ?: Unit },
    )

    fun MoLangRuntime.setupClient(): MoLangRuntime {
        environment.getQueryStruct().addFunctions(clientFunctions)
        return this
    }
}