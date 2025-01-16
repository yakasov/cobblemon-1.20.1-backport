/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.block.entity

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.CobblemonBlockEntities
import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.api.text.green
import com.cobblemon.mod.common.block.HealingMachineBlock
import com.cobblemon.mod.common.pokeball.PokeBall
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.DataKeys
import com.cobblemon.mod.common.util.getPlayer
import com.cobblemon.mod.common.util.lang
import com.cobblemon.mod.common.util.party
import com.cobblemon.mod.common.util.playSoundServer
import com.cobblemon.mod.common.util.toVec3d
import java.util.UUID
import kotlin.math.floor
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@Suppress("MemberVisibilityCanBePrivate", "unused")
class HealingMachineBlockEntity(
    blockPos: BlockPos,
    blockState: BlockState
) : BlockEntity(CobblemonBlockEntities.HEALING_MACHINE, blockPos, blockState) {
    var currentUser: UUID? = null
        private set

    @Deprecated("This property will be removed in the future", ReplaceWith("pokeBalls()"))
    val pokeBalls: MutableList<PokeBall>
        get() = this.pokeBalls().values.toMutableList()
    var healTimeLeft: Int = 0
    var healingCharge: Float = 0.0F
    val isInUse: Boolean
        get() = currentUser != null
    var infinite: Boolean = false

    var currentSignal = 0
        private set

    var maxCharge: Float = 6F

    private var dataSnapshot: DataSnapshot? = null
    // TODO: Rename me when the deprecated field is removed
    /**
     * Represents the PokéBalls occupying this entity.
     * The key is the equivalent party slot in index form.
     */
    private val pokeBallMap: MutableMap<Int, PokeBall> = hashMapOf()

    init {
        maxCharge = (Cobblemon.config.maxHealerCharge).coerceAtLeast(6F)
        this.updateRedstoneSignal()
        this.updateBlockChargeLevel()
    }

    /**
     * Resolves the currently occupying PokéBalls.
     * The key is the equivalent party index of the [currentUser].
     * The value is the [Pokemon.caughtBall] of the Pokémon in said party index.
     *
     * @return The PokéBalls in this healing machine.
     */
    fun pokeBalls(): Map<Int, PokeBall> = this.pokeBallMap

    fun setUser(user: UUID) {
        this.clearData()

        val player = user.getPlayer() ?: return
        val party = player.party()

        this.pokeBallMap.clear()
        party.toGappyList().forEachIndexed { index, pokemon ->
            if (pokemon != null) {
                this.pokeBallMap[index] = pokemon.caughtBall
            }
        }
        this.currentUser = user
        this.healTimeLeft = 24

        markUpdated()
    }

    fun canHeal(player: ServerPlayerEntity): Boolean {
        if (Cobblemon.config.infiniteHealerCharge || this.infinite) {
            return true
        }
        val neededHealthPercent = player.party().getHealingRemainderPercent()
        return this.healingCharge >= neededHealthPercent
    }

    fun activate(player: ServerPlayerEntity) {
        if (!Cobblemon.config.infiniteHealerCharge && this.healingCharge != maxCharge) {
            val neededHealthPercent = player.party().getHealingRemainderPercent()
            this.healingCharge = (healingCharge - neededHealthPercent).coerceIn(0F..maxCharge)
            this.updateRedstoneSignal()
        }
        this.setUser(player.uuid)
        alreadyHealing.add(player.uuid)
        updateBlockChargeLevel(HealingMachineBlock.MAX_CHARGE_LEVEL + 1)
        if (world != null && !world!!.isClient) world!!.playSoundServer(position = pos.toVec3d(), sound = CobblemonSounds.HEALING_MACHINE_ACTIVE, volume = 1F, pitch = 1F)
    }

    fun completeHealing() {
        val player = this.currentUser?.getPlayer() ?: return clearData()
        val party = player.party()

        party.heal()
        player.sendMessage(lang("healingmachine.healed").green(), true)
        updateBlockChargeLevel()
        clearData()
    }

    override fun readNbt(compoundTag: NbtCompound) {
        super.readNbt(compoundTag)

        this.pokeBallMap.clear()

        if (compoundTag.containsUuid(DataKeys.HEALER_MACHINE_USER)) {
            this.currentUser = compoundTag.getUuid(DataKeys.HEALER_MACHINE_USER)
        }
        if (compoundTag.contains(DataKeys.HEALER_MACHINE_POKEBALLS)) {
            val pokeBallsTag = compoundTag.getCompound(DataKeys.HEALER_MACHINE_POKEBALLS)
            // Keep around for compat with old format
            var index = 0
            for (key in pokeBallsTag.keys) {
                val pokeBallId = pokeBallsTag.getString(key)
                if (pokeBallId.isEmpty()) {
                    continue
                }
                val actualIndex = key.toIntOrNull() ?: index
                val pokeBall = PokeBalls.getPokeBall(Identifier(pokeBallId))
                if (pokeBall != null) {
                    this.pokeBallMap[actualIndex] = pokeBall
                }
                index++
            }
        }
        if (compoundTag.contains(DataKeys.HEALER_MACHINE_TIME_LEFT)) {
            this.healTimeLeft = compoundTag.getInt(DataKeys.HEALER_MACHINE_TIME_LEFT)
        }
        if (compoundTag.contains(DataKeys.HEALER_MACHINE_CHARGE)) {
            this.healingCharge = compoundTag.getFloat(DataKeys.HEALER_MACHINE_CHARGE).coerceIn(0F..maxCharge)
        }
        if (compoundTag.contains(DataKeys.HEALER_MACHINE_INFINITE)) {
            this.infinite = compoundTag.getBoolean(DataKeys.HEALER_MACHINE_INFINITE)
        }
    }

    override fun writeNbt(compoundTag: NbtCompound) {
        super.writeNbt(compoundTag)

        if (this.currentUser != null) {
            compoundTag.putUuid(DataKeys.HEALER_MACHINE_USER, this.currentUser!!)
        } else {
            compoundTag.remove(DataKeys.HEALER_MACHINE_USER)
        }

        if (this.pokeBalls().isNotEmpty()) {
            val pokeBallsTag = NbtCompound()
            this.pokeBalls().forEach { (index, pokeBall) ->
                pokeBallsTag.putString(index.toString(), pokeBall.name.toString())
            }
            compoundTag.put(DataKeys.HEALER_MACHINE_POKEBALLS, pokeBallsTag)
        } else {
            compoundTag.remove(DataKeys.HEALER_MACHINE_POKEBALLS)
        }

        compoundTag.putInt(DataKeys.HEALER_MACHINE_TIME_LEFT, this.healTimeLeft)
        compoundTag.putFloat(DataKeys.HEALER_MACHINE_CHARGE, this.healingCharge)
        compoundTag.putBoolean(DataKeys.HEALER_MACHINE_INFINITE, this.infinite)
    }

    override fun toUpdatePacket(): BlockEntityUpdateS2CPacket =  BlockEntityUpdateS2CPacket.create(this)
    override fun toInitialChunkDataNbt(): NbtCompound {
        return super.createNbtWithIdentifyingData()
    }

    override fun markRemoved() {
        this.snapshotAndClearData()
        super.markRemoved()
    }

    override fun cancelRemoval() {
        this.restoreSnapshot()
        super.cancelRemoval()
    }

    private fun updateRedstoneSignal() {
        if (Cobblemon.config.infiniteHealerCharge || this.infinite) {
            this.currentSignal = MAX_REDSTONE_SIGNAL
        }
        val remainder = ((this.healingCharge / maxCharge) * 100).toInt() / 10
        this.currentSignal = remainder.coerceAtMost(MAX_REDSTONE_SIGNAL)
    }

    private fun updateBlockChargeLevel(level: Int? = null) {
        if (world != null && !world!!.isClient) {
            val chargeLevel = (level ?:
                if (Cobblemon.config.infiniteHealerCharge || this.infinite) HealingMachineBlock.MAX_CHARGE_LEVEL
                else floor((healingCharge / maxCharge) * HealingMachineBlock.MAX_CHARGE_LEVEL).toInt()
            ).coerceIn(0..HealingMachineBlock.MAX_CHARGE_LEVEL + 1)

            val state = world!!.getBlockState(pos)
            if (state != null && state.block is HealingMachineBlock) {
                val currentCharge = state.get(HealingMachineBlock.CHARGE_LEVEL).toInt()
                if (chargeLevel != currentCharge) world!!.setBlockState(pos, state.with(HealingMachineBlock.CHARGE_LEVEL, chargeLevel))
            }
        }
    }

    private fun markUpdated() {
        this.markDirty()
        world!!.updateListeners(pos, this.cachedState, this.cachedState, 3)
    }

    private fun snapshotAndClearData() {
        this.dataSnapshot = DataSnapshot(
            this.currentUser,
            this.pokeBalls(),
            this.healTimeLeft
        )
        this.clearData()
    }

    private fun clearData() {
        this.currentUser?.let(alreadyHealing::remove)
        this.currentUser = null
        this.pokeBallMap.clear()
        this.healTimeLeft = 0
        markUpdated()
    }

    private fun restoreSnapshot() {
        this.dataSnapshot?.let {
            pokeBallMap.clear()
            currentUser = it.currentUser
            pokeBallMap.putAll(it.pokeBalls)
            healTimeLeft = it.healTimeLeft
        }
    }

    private data class DataSnapshot(
        val currentUser: UUID?,
        val pokeBalls: Map<Int, PokeBall>,
        val healTimeLeft: Int
    )

    companion object {
        private val alreadyHealing = hashSetOf<UUID>()
        const val MAX_REDSTONE_SIGNAL = 10

        internal val TICKER = BlockEntityTicker<HealingMachineBlockEntity> { world, _, _, blockEntity ->
            if (world.isClient) return@BlockEntityTicker

            // Healing progression
            if (blockEntity.isInUse) {
                if (blockEntity.healTimeLeft > 0) {
                    blockEntity.healTimeLeft--
                } else {
                    blockEntity.completeHealing()
                }
            } else {
                // Recharging
                if (blockEntity.healingCharge < blockEntity.maxCharge) {
                    val chargePerTick = (Cobblemon.config.chargeGainedPerTick).coerceAtLeast(0F)
                    blockEntity.healingCharge = (blockEntity.healingCharge + chargePerTick).coerceIn(0F..blockEntity.maxCharge)
                    blockEntity.updateBlockChargeLevel()
                    blockEntity.updateRedstoneSignal()
                    blockEntity.markUpdated()
                }
            }
        }

        fun isUsingHealer(player: PlayerEntity) = this.alreadyHealing.contains(player.uuid)

    }
}