package com.originem.approlight.util.compat.jei.immerse;

import com.originem.approlight.blocks.recipes.ImmerseFurnaceRecipes;
import com.originem.approlight.util.compat.jei.JEICompat;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.List;

public class ImmerseRecipe implements IRecipeWrapper {
    private final List<ItemStack> inputs;
    private final ItemStack output;

    public ImmerseRecipe(List<ItemStack> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ImmerseFurnaceRecipes recipes = ImmerseFurnaceRecipes.getInstance();
        float experience = recipes.getImmerseExperience(output);
        if (experience > 0) {
            String experienceString = JEICompat.translateToLocalFormatted("gui.jei.category.smelting.experience", experience);
            FontRenderer render = minecraft.fontRenderer;
            int stringWidth = render.getStringWidth(experienceString);
            render.drawString(experienceString, recipeWidth - stringWidth, 0, Color.gray.getRGB());
        }
    }
}
