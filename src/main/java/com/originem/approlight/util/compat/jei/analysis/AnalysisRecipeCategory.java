package com.originem.approlight.util.compat.jei.analysis;

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

public class AnalysisRecipeCategory extends AbstractAnalysisRecipeCategory<AnalysisRecipe> {
    private final IDrawable icon;
    private final IDrawable background;
    private final String name;

    public AnalysisRecipeCategory(IGuiHelper helper) {
        super(helper);
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.ANALYSIS_GOLD_FRAME));
        background = helper.createDrawable(TEXTURES,0 , 0, 77, 20);
        name = I18nUtil.translateToLocal("gui.jei.al.analysis.name");
    }

    @Override
    public String getUid() {
        return RecipeCategories.ANALYSIS;
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
        animatedArrow.draw(minecraft, 26, 1);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AnalysisRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input,true , 2 - 1, 2 - 1);
        stacks.init(output, false, 59 - 1, 2 - 1);
        stacks.set(ingredients);
    }

}
