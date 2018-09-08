package com.originem.approlight.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTab {
    public static final CreativeTabs APPROLIGHT_TAB = new CreativeTabs("approlight") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.IMMERSED_IRON_INGOT);
        }
    };
}
