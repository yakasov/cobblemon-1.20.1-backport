/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.spawn

import com.cobblemon.mod.common.client.entity.GenericBedrockClientDelegate
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.generic.GenericBedrockEntity
import com.cobblemon.mod.common.net.IntSize
import com.cobblemon.mod.common.util.cobblemonResource
import com.cobblemon.mod.common.util.writeSizedInt
import net.minecraft.entity.Entity
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
import net.minecraft.util.Identifier

/**
 * Spawn packet for [GenericBedrockEntity]. Wraps around vanilla spawn packet behaviour.
 *
 * @author Hiroku
 * @since May 22nd, 2023
 */
class SpawnGenericBedrockPacket(
    val category: Identifier,
    val aspects: Set<String>,
    val poseType: PoseType,
    val scale: Float,
    val width: Float,
    val height: Float,
    val startAge: Int,
    vanillaSpawnPacket: EntitySpawnS2CPacket
) : SpawnExtraDataEntityPacket<SpawnGenericBedrockPacket, GenericBedrockEntity>(vanillaSpawnPacket) {
    override val id: Identifier = ID

    override fun encodeEntityData(buffer: PacketByteBuf) {
        buffer.writeIdentifier(this.category)
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect) }
        buffer.writeSizedInt(size = IntSize.U_BYTE, poseType.ordinal)
        buffer.writeFloat(scale)
        buffer.writeFloat(width)
        buffer.writeFloat(height)
        buffer.writeInt(startAge)
    }

    override fun applyData(entity: GenericBedrockEntity) {
        entity.category = this.category
        entity.aspects = this.aspects
        entity.dataTracker.set(GenericBedrockEntity.POSE_TYPE, this.poseType)
        entity.scale = this.scale
        entity.colliderWidth = this.width
        entity.colliderHeight = this.height
        entity.delegate.initialize(entity)
        entity.age = startAge
        (entity.delegate as GenericBedrockClientDelegate).updateAge(startAge)
    }

    override fun checkType(entity: Entity): Boolean = entity is GenericBedrockEntity

    companion object {
        val ID = cobblemonResource("spawn_generic_bedrock_entity")
        fun decode(buffer: PacketByteBuf): SpawnGenericBedrockPacket {
            val category = buffer.readIdentifier()
            val aspects = buffer.readList { it.readString() }.toSet()
            val poseType = buffer.readEnumConstant(PoseType::class.java)
            val scale = buffer.readFloat()
            val width = buffer.readFloat()
            val height = buffer.readFloat()
            val startAge = buffer.readInt()
            val vanillaPacket = decodeVanillaPacket(buffer)
            return SpawnGenericBedrockPacket(category, aspects, poseType, scale, width, height, startAge, vanillaPacket)
        }
    }
}