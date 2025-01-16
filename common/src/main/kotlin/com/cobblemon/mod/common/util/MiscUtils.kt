/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.util

import com.cobblemon.mod.common.Cobblemon
import java.util.function.Consumer
import kotlin.math.min
import kotlin.random.Random
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape

fun cobblemonResource(path: String) = Identifier(Cobblemon.MODID, path)
fun cobblemonModel(path: String, variant: String) = ModelIdentifier("cobblemon", path, variant)

fun String.asTranslated() = Text.translatable(this)
fun String.asResource() = Identifier(this)
fun String.asTranslated(vararg data: Any) = Text.translatable(this, *data)
fun String.isInt() = this.toIntOrNull() != null
fun String.isHigherVersion(other: String): Boolean {
    val thisSplits = split(".")
    val thatSplits = other.split(".")

    val thisCount = thisSplits.size
    val thatCount = thatSplits.size

    val min = min(thisCount, thatCount)
    for (i in 0 until min) {
        val thisDigit = thisSplits[i].toIntOrNull()
        val thatDigit = thatSplits[i].toIntOrNull()
        if (thisDigit == null || thatDigit == null) {
            return false
        }

        if (thisDigit > thatDigit) {
            return true
        } else if (thisDigit < thatDigit) {
            return false
        }
    }

    return thisCount > thatCount
}

fun String.substitute(placeholder: String, value: Any?) = replace("{{$placeholder}}", value?.toString() ?: "")

val Pair<Boolean, Boolean>.either: Boolean get() = first || second

fun Random.nextBetween(min: Float, max: Float): Float {
    return nextFloat() * (max - min) + min;
}

fun Random.nextBetween(min: Double, max: Double): Double {
    return nextDouble() * (max - min) + min;
}

fun Random.nextBetween(min: Int, max: Int): Int {
    return nextInt(max - min + 1) + min
}

infix fun <A, B> A.toDF(b: B): com.mojang.datafixers.util.Pair<A, B> = com.mojang.datafixers.util.Pair(this, b)

fun isUuid(string: String) : Boolean {
    return Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$").matches(string)
}

fun VoxelShape.blockPositionsAsList(): List<BlockPos> {
    val result = mutableListOf<BlockPos>()
    forEachBox { minX, minY, minZ, maxX, maxY, maxZ ->
        for (x in minX.toInt() until maxX.toInt()) {
            for (y in minY.toInt() until maxY.toInt()) {
                for (z in minZ.toInt() until maxZ.toInt()) {
                    result.add(BlockPos(x, y, z))
                }
            }
        }
    }

    return result
}

operator fun <T> Consumer<T>.plus(action: (T) -> Unit): Consumer<T> {
    return andThen(action)
}