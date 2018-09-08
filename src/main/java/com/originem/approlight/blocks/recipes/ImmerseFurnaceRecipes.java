package com.originem.approlight.blocks.recipes;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.originem.approlight.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map;

public class ImmerseFurnaceRecipes {
    public static final int COOKTIME_FOR_EACH_RECIPE = 200;
    private static final ImmerseFurnaceRecipes INSTANCE = new ImmerseFurnaceRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.create();
    private final Map<ItemStack, Float> experienceList = Maps.newHashMap();

    private ImmerseFurnaceRecipes() {
        //example
        addImmerseRecipe(new ItemStack(ModItems.IMMERSED_IRON_INGOT), new ItemStack(ModItems.IMMERSED_GOLD_INGOT),
                new ItemStack(ModItems.IMMERSED_PLATINUM_INGOT), 10.0F);
        addImmerseRecipe(new ItemStack(ModItems.IMMERSED_PLATINUM_INGOT), new ItemStack(ModItems.IMMERSED_DIAMOND),
                new ItemStack(ModItems.IMMERSED_GLASS_GOLD_INGOT), 20.0F);
    }

    public static ImmerseFurnaceRecipes getInstance() {
        return INSTANCE;
    }

    public void addImmerseRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) {
        if (getImmerseResult(input1, input2) != ItemStack.EMPTY) return;
        this.smeltingList.put(input1, input2, result);
        //this.smeltingList.put(input2, input1, result);
        this.experienceList.put(result, experience);
    }

    public ItemStack getImmerseResult(ItemStack input1, ItemStack input2) {
        ItemStack result;
        if ((result = tryResult(input1, input2)) != ItemStack.EMPTY) return result;
        if ((result = tryResult(input2, input1)) != ItemStack.EMPTY) return result;
        return ItemStack.EMPTY;
    }

    private ItemStack tryResult(ItemStack input1, ItemStack input2) {
        for (Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet()) {
            if (this.compareItemStacks(input1, entry.getKey())) {
                for (Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                    if (this.compareItemStacks(input2, ent.getKey())) {
                        return ent.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean containedInRecipes(ItemStack stack) {
        for (ItemStack input : smeltingList.columnKeySet()) {
            if (this.compareItemStacks(input, stack)) {
                return true;
            }
        }
        for (ItemStack input : smeltingList.rowKeySet()) {
            if (this.compareItemStacks(input, stack)) {
                return true;
            }
        }
        return false;
    }


    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() {
        return this.smeltingList;
    }

    public float getImmerseExperience(ItemStack stack) {
        for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0.0F;
    }
}