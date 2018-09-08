package com.originem.approlight.util.compat.jei;

import com.originem.approlight.blocks.machines.immerse_furnace.ContainerImmerseFurnace;
import com.originem.approlight.blocks.machines.immerse_furnace.GuiImmerseFurnace;
import com.originem.approlight.util.I18nUtil;
import com.originem.approlight.util.compat.jei.immerse.ImmerseRecipeCatogory;
import com.originem.approlight.util.compat.jei.immerse.ImmerseRecipeMaker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;

import java.util.IllegalFormatException;

@JEIPlugin
public class JEICompat implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IJeiHelpers helpers = registry.getJeiHelpers();
        final IGuiHelper gui = helpers.getGuiHelper();

        registry.addRecipeCategories(new ImmerseRecipeCatogory(gui));
    }

    @Override
    public void register(IModRegistry registry) {
        final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

        registry.addRecipes(ImmerseRecipeMaker.getRecipes(jeiHelpers), RecipeCategories.IMMERSE);
        //registry.addRecipeClickArea(GuiImmerseFurnace.class, , 0, 50, 50, RecipeCategories.IMMERSE);
        //recipeTransfer.addRecipeTransferHandler(ContainerImmerseFurnace.class,RecipeCategories.IMMERSE,0,1,3,36);
    }

    public static String translateToLocal(String key) {
        return I18nUtil.translateToLocal(key);
    }

    public static String translateToLocalFormatted(String key, Object... format) {
        String s = translateToLocal(key);
        try {
            return String.format(s, format);
        } catch (IllegalFormatException e) {
            return "Format error: " + s;
        }
    }
}
