package com.originem.approlight.util.compat.jei.analysis;

import com.originem.approlight.util.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractAnalysisRecipeCategory <T extends IRecipeWrapper> implements IRecipeCategory<T> {
    protected static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID,"textures/gui/jei_recipe.png");

    protected  static final int input = 0;
    protected  static final int output = 1;
    protected final IDrawableAnimated animatedArrow;
    public AbstractAnalysisRecipeCategory(IGuiHelper helper) {
        IDrawableStatic staticArrow = helper.createDrawable(TEXTURES, 77, 0, 24, 17);
        animatedArrow = helper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }
}
