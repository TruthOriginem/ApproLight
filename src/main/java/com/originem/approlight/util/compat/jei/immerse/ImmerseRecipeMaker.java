package com.originem.approlight.util.compat.jei.immerse;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.originem.approlight.blocks.recipes.ImmerseFurnaceRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class ImmerseRecipeMaker {
    public static List<ImmerseRecipe> getRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        ImmerseFurnaceRecipes instance = ImmerseFurnaceRecipes.getInstance();
        Table<ItemStack, ItemStack, ItemStack> recipes = instance.getDualSmeltingList();
        List<ImmerseRecipe> jeiRecipes = Lists.newArrayList();

        for (Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : recipes.columnMap().entrySet()) {
            for (Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                ItemStack input1 = entry.getKey();
                ItemStack input2 = ent.getKey();
                ItemStack output = ent.getValue();
                List<ItemStack> inputs = Lists.newArrayList(input1, input2);
                ImmerseRecipe recipe = new ImmerseRecipe(inputs, output);
                jeiRecipes.add(recipe);
            }
        }

        return jeiRecipes;
    }
}
