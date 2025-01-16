/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pose

import com.cobblemon.mod.common.util.math.geometry.toRadians
import net.minecraft.client.model.ModelPart

/**
 * Represents a [ModelPart] with some changes to position and rotation. This is to take a snapshot
 * and store mutations for the purpose of poses.
 *
 * @author Hiroku
 * @since December 5th, 2021
 */
class ModelPartTransformation(val modelPart: ModelPart) {
    companion object {
        const val X_AXIS = 0
        const val Y_AXIS = 1
        const val Z_AXIS = 2

        fun derive(modelPart: ModelPart) = ModelPartTransformation(modelPart)
            .withPosition(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ)
            .withRotation(modelPart.pitch, modelPart.yaw, modelPart.roll)
            .withScale(modelPart.xScale, modelPart.yScale, modelPart.zScale)
            .withVisibility(modelPart.visible)
    }

    var position = floatArrayOf(0F, 0F, 0F)
    var rotation = floatArrayOf(0F, 0F, 0F)
    val scale = floatArrayOf(1F, 1F, 1F)

    var visibility: Boolean? = null

    /** Applies the transformation to the model part. */
    fun apply(intensity: Float) {
        modelPart.pivotX += position[0] * intensity
        modelPart.pivotY += position[1] * intensity
        modelPart.pivotZ += position[2] * intensity
        modelPart.pitch += rotation[0] * intensity
        modelPart.yaw += rotation[1] * intensity
        modelPart.roll += rotation[2] * intensity
        modelPart.xScale *= (1 - scale[0]) * intensity + 1
        modelPart.yScale *= (1 - scale[1]) * intensity + 1
        modelPart.zScale *= (1 - scale[2]) * intensity + 1
        visibility?.let { modelPart.visible = it }
    }

    fun set() {
        modelPart.pivotX = position[0]
        modelPart.pivotY = position[1]
        modelPart.pivotZ = position[2]
        modelPart.pitch = rotation[0]
        modelPart.yaw = rotation[1]
        modelPart.roll = rotation[2]
        modelPart.xScale = scale[0]
        modelPart.yScale = scale[1]
        modelPart.zScale = scale[2]
        visibility?.let { modelPart.visible = it }
    }

    fun withVisibility(visibility: Boolean): ModelPartTransformation {
        this.visibility = visibility
        return this
    }

    var xPos: Float
        get() = position[0]
        set(value) {
            position[0] = value
        }
    var yPos: Float
        get() = position[1]
        set(value) {
            position[1] = value
        }
    var zPos: Float
        get() = position[2]
        set(value) {
            position[2] = value
        }
    var pitch: Float
        get() = rotation[0]
        set(value) {
            rotation[0] = value
        }
    var yaw: Float
        get() = rotation[1]
        set(value) {
            rotation[1] = value
        }
    var roll: Float
        get() = rotation[2]
        set(value) {
            rotation[2] = value
        }

    fun withPosition(axis: Int, position: Number): ModelPartTransformation {
        this.position[axis] = position.toFloat()
        return this
    }

    fun withPosition(xPos: Number, yPos: Number, zPos: Number): ModelPartTransformation {
        return withPosition(X_AXIS, xPos).withPosition(Y_AXIS, yPos).withPosition(Z_AXIS, zPos)
    }

    fun withRotation(axis: Int, angleRadians: Number): ModelPartTransformation {
        this.rotation[axis] = angleRadians.toFloat()
        return this
    }

    fun withRotation(pitch: Number, yaw: Number, roll: Number): ModelPartTransformation {
        return withRotation(X_AXIS, pitch).withRotation(Y_AXIS, yaw).withRotation(Z_AXIS, roll)
    }

    fun addPosition(axis: Int, distance: Number): ModelPartTransformation {
        return withPosition(axis, position[axis] + distance.toFloat())
    }

    fun addPosition(xDist: Number, yDist: Number, zDist: Number): ModelPartTransformation {
        return addPosition(X_AXIS, xDist).addPosition(Y_AXIS, yDist).addPosition(Z_AXIS, zDist)
    }

    fun addRotation(axis: Int, angleRadians: Number): ModelPartTransformation {
        return withRotation(axis, rotation[axis] + angleRadians.toFloat())
    }

    fun addRotation(pitchRadians: Number, yawRadians: Number, rollRadians: Number): ModelPartTransformation {
        return addRotation(X_AXIS, pitchRadians).addRotation(Y_AXIS, yawRadians).addRotation(Z_AXIS, rollRadians)
    }

    fun addRotationDegrees(pitch: Number, yaw: Number, roll: Number): ModelPartTransformation {
        return addRotation(X_AXIS, pitch.toFloat().toRadians()).addRotation(Y_AXIS, yaw.toFloat().toRadians()).addRotation(Z_AXIS, roll.toFloat().toRadians())
    }

    fun multiplyScale(axis: Int, scale: Number): ModelPartTransformation {
        return withScale(axis, scale.toFloat() * this.scale[axis])
    }

    fun multiplyScale(scaleX: Number, scaleY: Number, scaleZ: Number): ModelPartTransformation {
        return multiplyScale(X_AXIS, scaleX).multiplyScale(Y_AXIS, scaleY).multiplyScale(Z_AXIS, scaleZ)
    }

    fun withRotationDegrees(pitch: Number, yaw: Number, roll: Number): ModelPartTransformation {
        return withRotation(pitch.toFloat().toRadians(), yaw.toFloat().toRadians(), roll.toFloat().toRadians())
    }

    fun addRotationDegrees(axis: Int, angle: Number): ModelPartTransformation {
        return addRotation(axis, rotation[axis] + angle.toFloat().toRadians())
    }

    fun withScale(axis: Int, scale: Number): ModelPartTransformation {
        this.scale[axis] = scale.toFloat()
        return this
    }

    fun withScale(scaleX: Number, scaleY: Number, scaleZ: Number): ModelPartTransformation {
        return withScale(X_AXIS, scaleX).withScale(Y_AXIS, scaleY).withScale(Z_AXIS, scaleZ)
    }
}