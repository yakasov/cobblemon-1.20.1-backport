/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pose;

import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Map;

/**
 * A bone represents a particular joint of a model. Bones are capable of containing child bones identifiable
 * by a String ideally representing the name of a particular bone.
 *
 * This interface is meant to allow projects and sidemods to implement their own rendering logic in reference
 * to the bones.
 *
 * @author Waterpicker
 * @since 1.4.0
 */

public interface Bone {

    /**
     * A map of identifiable names to child bones
     *
     * @since 1.4.0
     */
    Map<String, Bone> getChildren();

    /**
     * Renders a particular bone in relation to a particular entity.
     *
     * @param context The render context this bone is being drawn for.
     * @param stack The current [MatrixStack] for the renderer
     * @param buffer The vertex consumer deployed by the renderer
     * @param packedLight The light level to be applied to the bone
     * @param packedOverlay The overlay value to be applied to the bone
     * @param r A value representing the amount of red coloring that should be applied on the RGB scale
     * @param g A value representing the amount of green coloring that should be applied on the RGB scale
     * @param b A value representing the amount of blue coloring that should be applied on the RGB scale
     * @param a The alpha value to apply to the model based on the RGB scale
     *
     * @since 1.4.0
     */
    void render(
            RenderContext context,
            MatrixStack stack,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            float r,
            float g,
            float b,
            float a
    );

    /**
     * Allows for direct transformation of a bone via a given matrix stack
     *
     * @since 1.4.0
     */
    void transform(MatrixStack matrixStack);
}
