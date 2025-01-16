/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.compat;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.client.render.layer.PokemonOnShoulderRenderer;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.FormData;
import com.cobblemon.mod.common.pokemon.lighthing.LightingData;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// Java class due to relying on entrypoint on Fabric side
public class LambDynamicLightsCompat {

    // This is all called on client side
    // These things are checked every tick so try to keep it as free as possible
    // That being said it's unlikely school Toshiba person is running dynamic lighting and shaders to enjoy this
    // Make sure not to call anything Fabric specific :)
    public static void hookCompat() {
        // Pokémon entities
        DynamicLightHandlers.registerDynamicLightHandler(
                CobblemonEntities.POKEMON,
                DynamicLightHandler.makeHandler(
                        pokemon -> resolvedPokemonLightLevel(pokemon, false),
                        pokemon -> true
                )
        );
        DynamicLightHandlers.registerDynamicLightHandler(
                CobblemonEntities.POKEMON,
                DynamicLightHandler.makeHandler(
                        pokemon -> resolvedPokemonLightLevel(pokemon, true),
                        pokemon -> false
                )
        );

        // Shouldered Pokémon
        DynamicLightHandlers.registerDynamicLightHandler(
                EntityType.PLAYER,
                DynamicLightHandler.makeHandler(
                        player -> resolvedShoulderLightLevel(player, false),
                        player -> true
                )
        );
        DynamicLightHandlers.registerDynamicLightHandler(
                EntityType.PLAYER,
                DynamicLightHandler.makeHandler(
                        player -> resolvedShoulderLightLevel(player, true),
                        player -> false
                )
        );
    }

    private static int resolvedPokemonLightLevel(PokemonEntity pokemon, boolean underwater) {
        return extractFormLightLevel(pokemon.getForm(), underwater).orElse(0);
    }

    private static int resolvedShoulderLightLevel(PlayerEntity player, boolean underwater) {
        final Pair<PokemonOnShoulderRenderer.ShoulderData, PokemonOnShoulderRenderer.ShoulderData> shoulderDataPair = PokemonOnShoulderRenderer.shoulderDataOf(player);
        final Optional<Integer> leftLightLevel = extractShoulderLightLevel(shoulderDataPair.getLeft(), underwater);
        final Optional<Integer> rightLightLevel = extractShoulderLightLevel(shoulderDataPair.getRight(), underwater);
        return Math.max(leftLightLevel.orElse(0), rightLightLevel.orElse(0));
    }

    private static Optional<Integer> extractFormLightLevel(@NotNull FormData form, boolean underwater) {
        if (form.getLightingData() == null || !liquidGlowModeSupport(form.getLightingData().getLiquidGlowMode(), underwater)) {
            return Optional.empty();
        }
        return Optional.of(form.getLightingData().getLightLevel());
    }

    private static Optional<Integer> extractShoulderLightLevel(@Nullable PokemonOnShoulderRenderer.ShoulderData shoulderData, boolean underwater) {
        if (shoulderData == null) {
            return Optional.empty();
        }
        return extractFormLightLevel(shoulderData.getForm(), underwater);
    }

    private static boolean liquidGlowModeSupport(@NotNull LightingData.LiquidGlowMode liquidGlowMode, boolean underwater) {
        return underwater ? liquidGlowMode.getGlowsUnderwater() : liquidGlowMode.getGlowsInLand();
    }

}
