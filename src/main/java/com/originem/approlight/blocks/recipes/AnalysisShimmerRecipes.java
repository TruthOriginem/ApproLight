package com.originem.approlight.blocks.recipes;

import com.google.common.collect.Maps;
import com.originem.approlight.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class AnalysisShimmerRecipes {
    private static final AnalysisShimmerRecipes INSTANCE = new AnalysisShimmerRecipes();
    private final Map<ItemStack, ItemStack> analysisList = Maps.newHashMap();
    private final Map<ItemStack, Integer> timeList = Maps.newHashMap();

    public static AnalysisShimmerRecipes getInstance() {
        return INSTANCE;
    }

    public AnalysisShimmerRecipes() {
        addRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.IMMERSED_IRON_INGOT), 40 * 20);
        addRecipes(new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.IMMERSED_GOLD_INGOT), 60 * 20);
        addRecipes(new ItemStack(Items.DIAMOND), new ItemStack(ModItems.IMMERSED_DIAMOND), 120 * 20);
        addRecipes(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(ModItems.IMMERSED_ROTTEN_FLESH), 10 * 20);
        addRecipes(new ItemStack(Items.STICK), new ItemStack(ModItems.SHIMMER_STICK), 10 * 20);
        addRecipes(new ItemStack(Items.COAL), new ItemStack(ModItems.SHIMMER_COAL), 20 * 20);
        addRecipes(new ItemStack(Items.COAL, 1, 1), new ItemStack(ModItems.SHIMMER_COAL), 30 * 20);
    }

    private void addRecipes(ItemStack source, ItemStack result, int ticks) {
        if (getAnalysedResult(source) != ItemStack.EMPTY) return;
        analysisList.put(source, result);
        timeList.put(source, ticks);
    }

    public ItemStack getAnalysedResult(ItemStack itemStack) {
        for (ItemStack source : analysisList.keySet()) {
            if (compareItemStacks(source, itemStack)) {
                return analysisList.get(source);
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Get time required.
     *
     * @param itemStack
     * @return ticks.
     */
    public int getAnalysedTime(ItemStack itemStack) {
        for (ItemStack source : timeList.keySet()) {
            if (compareItemStacks(source, itemStack)) {
                return timeList.get(source);
            }
        }
        return -1;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}
