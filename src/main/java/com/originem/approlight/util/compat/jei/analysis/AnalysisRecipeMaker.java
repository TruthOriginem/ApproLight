package com.originem.approlight.util.compat.jei.analysis;

import com.google.common.collect.Lists;
import com.originem.approlight.blocks.recipes.AnalysisShimmerRecipes;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class AnalysisRecipeMaker {
    public static List<AnalysisRecipe> getRecipes(IJeiHelpers helpers){
        AnalysisShimmerRecipes instance = AnalysisShimmerRecipes.getInstance();
        List<AnalysisRecipe> recipes = Lists.newArrayList();

        for (Map.Entry<ItemStack,ItemStack> entry : instance.getRecipes().entrySet()){
            recipes.add(new AnalysisRecipe(entry.getKey(),entry.getValue()));
        }
        return recipes;
    }
}
