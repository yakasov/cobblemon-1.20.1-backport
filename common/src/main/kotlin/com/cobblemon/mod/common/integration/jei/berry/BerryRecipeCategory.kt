/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.integration.jei.berry

import com.cobblemon.mod.common.CobblemonItems
import com.cobblemon.mod.common.util.cobblemonResource
import mezz.jei.api.constants.ModIds
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import mezz.jei.api.registration.IRecipeCategoryRegistration
import net.minecraft.recipe.Ingredient
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class BerryRecipeCategory(private val registration: IRecipeCategoryRegistration) : IRecipeCategory<BerryMutationRecipe> {
    private var background: IDrawable
    private var icon: IDrawable
    init {
        val guiHelper = registration.jeiHelpers.guiHelper
        background = guiHelper.createDrawable(GUI_TEXTURE_ID, 0, 0, WIDTH, HEIGHT)
        icon = guiHelper.createDrawableItemStack(CobblemonItems.SURPRISE_MULCH.defaultStack)
    }
    override fun getRecipeType(): RecipeType<BerryMutationRecipe> {
        return RECIPE_TYPE
    }

    override fun getTitle(): Text {
        return Text.of("Berry Mutation")
    }

    override fun getBackground(): IDrawable {
        return background
    }

    override fun getIcon(): IDrawable {
        return icon
    }

    override fun setRecipe(p0: IRecipeLayoutBuilder, p1: BerryMutationRecipe, p2: IFocusGroup) {
        //These magic numbers are where the items should be positioned on the gui texture
        p0.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(Ingredient.ofStacks(p1.berryOne.defaultStack))
        p0.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(Ingredient.ofStacks(p1.berryTwo.defaultStack))
        p0.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addIngredients(Ingredient.ofStacks(p1.berryResult.defaultStack))
    }

    companion object {
        val RECIPE_TYPE = RecipeType.create("cobblemon", "berry_recipe", BerryMutationRecipe::class.java)!!
        val GUI_TEXTURE_ID = cobblemonResource("textures/gui/jei/berry_mutation.png")
        const val WIDTH = 124
        const val HEIGHT = 17
    }
}
