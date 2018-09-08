package com.originem.approlight.util.compat.jei.immerse;

import com.originem.approlight.init.ModBlocks;
import com.originem.approlight.util.I18nUtil;
import com.originem.approlight.util.Reference;
import com.originem.approlight.util.compat.jei.RecipeCategories;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ImmerseRecipeCatogory extends AbstractImmerseRecipeCategory<ImmerseRecipe> {
    private final IDrawable background;
    private final String name;
    private final IDrawable icon;
    private static final int OFFSET_X = 4;
    private static final int OFFSET_Y = 12;

    public ImmerseRecipeCatogory(IGuiHelper helper) {
        super(helper);
        background = helper.createDrawable(TEXTURES, OFFSET_X, OFFSET_Y, 150, 60);
        name = I18nUtil.translateToLocal(ModBlocks.IMMERSE_FURNACE.getLocalizedName());
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.IMMERSE_FURNACE));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        animatedFlame.draw(minecraft, 47 - OFFSET_X, 37 - OFFSET_Y);
        animatedArrow.draw(minecraft, 79 - OFFSET_X, 34 - OFFSET_Y);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getModName() {
        return Reference.NAME;
    }

    @Override
    public String getUid() {
        return RecipeCategories.IMMERSE;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ImmerseRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input1, true, 35 - OFFSET_X - 1, 17 - OFFSET_Y - 1);
        stacks.init(input2, true, 56 - OFFSET_X - 1, 17 - OFFSET_Y - 1);
        stacks.init(output, false, 116 - OFFSET_X - 1, 35 - OFFSET_Y - 1);
        stacks.set(ingredients);
    }
}
