package com.originem.approlight.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void init() {
        GameRegistry.addSmelting(new ItemStack(ModItems.SHIMMER_IMPURITY), new ItemStack(ModItems.SHIMMER_GEL, 1), 0.2f);
    }
}
