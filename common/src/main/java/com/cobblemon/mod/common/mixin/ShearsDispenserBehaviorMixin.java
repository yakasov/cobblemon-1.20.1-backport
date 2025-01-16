/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.mixin;

import com.cobblemon.mod.common.block.ShearableBlock;
import kotlin.Unit;
import net.minecraft.block.BlockState;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsDispenserBehavior.class)
public abstract class ShearsDispenserBehaviorMixin {

    // This exists, so we can execute our custom interaction only if no one redirected shears interactions to overwrite the vanilla ones.
    @Inject(method = "tryShearBlock", at = @At("HEAD"), cancellable = true)
    private static void cobblemon$tryApricornHarvest(ServerWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        final BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof ShearableBlock shearableBlock) {
            cir.setReturnValue(shearableBlock.attemptShear(world, state, pos, () -> Unit.INSTANCE));
        }
    }

}
