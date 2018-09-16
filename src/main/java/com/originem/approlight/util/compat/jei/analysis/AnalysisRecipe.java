package com.originem.approlight.util.compat.jei.analysis;

import com.originem.approlight.blocks.recipes.AnalysisShimmerRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class AnalysisRecipe implements IRecipeWrapper {
    private final ItemStack input;
    private final ItemStack output;

    public AnalysisRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);

    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer renderer = minecraft.fontRenderer;
        AnalysisShimmerRecipes recipes = AnalysisShimmerRecipes.getInstance();
        String time = recipes.getAnalysedTime(input) + "";
        int width = renderer.getStringWidth(time) / 2;
        renderer.drawString(time, recipeWidth / 2 - width - 6, -1, Color.gray.getRGB());
    }
}
