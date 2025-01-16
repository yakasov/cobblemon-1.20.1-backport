/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.cobblemon.mod.common.util.codec.EXPRESSION_CODEC
import com.cobblemon.mod.common.util.getString
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.PacketByteBuf

/**
 * Configuration of the emitter component for a particle effect.
 *
 * @author Hiroku
 * @since January 4th, 2023
 */
class BedrockParticleEmitter(
    var startExpressions: MutableList<Expression> = mutableListOf(),
    var updateExpressions: MutableList<Expression> = mutableListOf(),
    var rate: ParticleEmitterRate = InstantParticleEmitterRate(),
    var shape: ParticleEmitterShape = SphereParticleEmitterShape(),
    var lifetime: ParticleEmitterLifetime = OnceEmitterLifetime(NumberExpression(1.0)),
    var eventTimeline: EventTriggerTimeline = EventTriggerTimeline(mutableMapOf()),
    var creationEvents: MutableList<SimpleEventTrigger> = mutableListOf(),
    var expirationEvents: MutableList<SimpleEventTrigger> = mutableListOf(),
    var travelDistanceEvents: EventTriggerTimeline = EventTriggerTimeline(mutableMapOf()),
    var loopingTravelDistanceEvents: MutableList<LoopingTravelDistanceEventTrigger> = mutableListOf()
) {
    companion object {
        val CODEC: Codec<BedrockParticleEmitter> = RecordCodecBuilder.create { instance ->
            instance.group(
                ListCodec(EXPRESSION_CODEC).fieldOf("startExpressions").forGetter { it.startExpressions },
                ListCodec(EXPRESSION_CODEC).fieldOf("updateExpressions").forGetter { it.updateExpressions },
                ParticleEmitterRate.codec.fieldOf("rate").forGetter { it.rate },
                ParticleEmitterShape.codec.fieldOf("shape").forGetter { it.shape },
                ParticleEmitterLifetime.codec.fieldOf("lifetime").forGetter { it.lifetime },
                EventTriggerTimeline.CODEC.fieldOf("eventTimeline").forGetter { it.eventTimeline },
                ListCodec(SimpleEventTrigger.CODEC).fieldOf("creationEvents").forGetter { it.creationEvents },
                ListCodec(SimpleEventTrigger.CODEC).fieldOf("expirationEvents").forGetter { it.expirationEvents },
                EventTriggerTimeline.CODEC.fieldOf("travelDistanceEvents").forGetter { it.travelDistanceEvents },
                ListCodec(LoopingTravelDistanceEventTrigger.CODEC).fieldOf("loopingTravelDistanceEvents").forGetter { it.loopingTravelDistanceEvents }
            ).apply(instance) { startExpressions, updateExpressions, rate, shape, lifetime, eventTimeline, creationEvents, expirationEvents, travelDistanceEvents, loopingTravelDistanceEvents ->
                BedrockParticleEmitter(
                    startExpressions = startExpressions,
                    updateExpressions = updateExpressions,
                    shape = shape,
                    rate = rate,
                    lifetime = lifetime,
                    eventTimeline = eventTimeline,
                    creationEvents = creationEvents,
                    expirationEvents = expirationEvents,
                    travelDistanceEvents = travelDistanceEvents,
                    loopingTravelDistanceEvents = loopingTravelDistanceEvents
                )
            }
        }
    }

    fun writeToBuffer(buffer: PacketByteBuf) {
        buffer.writeCollection(startExpressions) { pb, expression -> pb.writeString(expression.getString()) }
        buffer.writeCollection(updateExpressions) { pb, expression -> pb.writeString(expression.getString()) }
        ParticleEmitterRate.writeToBuffer(buffer, rate)
        ParticleEmitterShape.writeToBuffer(buffer, shape)
        ParticleEmitterLifetime.writeToBuffer(buffer, lifetime)
        eventTimeline.encode(buffer)
        buffer.writeCollection(creationEvents) { pb, event -> event.encode(pb) }
        buffer.writeCollection(expirationEvents) { pb, event -> event.encode(pb) }
        travelDistanceEvents.encode(buffer)
        buffer.writeCollection(loopingTravelDistanceEvents) { pb, event -> event.encode(pb) }
    }

    fun readFromBuffer(buffer: PacketByteBuf) {
        startExpressions = buffer.readList { MoLang.createParser(buffer.readString()).parseExpression() }
        updateExpressions = buffer.readList { MoLang.createParser(buffer.readString()).parseExpression() }
        rate = ParticleEmitterRate.readFromBuffer(buffer)
        shape = ParticleEmitterShape.readFromBuffer(buffer)
        lifetime = ParticleEmitterLifetime.readFromBuffer(buffer)
        eventTimeline.decode(buffer)
        creationEvents = buffer.readList { SimpleEventTrigger("").also { it.decode(buffer) } }
        expirationEvents = buffer.readList { SimpleEventTrigger("").also { it.decode(buffer) } }
        travelDistanceEvents.decode(buffer)
        loopingTravelDistanceEvents = buffer.readList { LoopingTravelDistanceEventTrigger(0.0, mutableListOf()).also { it.decode(buffer) } }
    }
}