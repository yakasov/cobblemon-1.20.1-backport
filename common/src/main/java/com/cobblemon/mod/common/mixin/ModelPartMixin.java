/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.mixin;

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityModel;
import com.cobblemon.mod.common.client.render.models.blockbench.pose.Bone;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements Bone {
    @Shadow public abstract void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha);

    @Shadow public abstract void rotate(MatrixStack matrixStack);

    @Shadow @Final
    public Map<String, ModelPart> children;

    @NotNull
    @Override
    public Map<String, Bone> getChildren() {
        return (Map<String, Bone>) (Object) children;
    }

    @Override
    public void render(RenderContext context, MatrixStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        this.render(stack, buffer, packedLight, packedOverlay, r, g, b, a);
    }

    @Override
    public void transform(MatrixStack matrixStack) {
        this.rotate(matrixStack);
    }
}
