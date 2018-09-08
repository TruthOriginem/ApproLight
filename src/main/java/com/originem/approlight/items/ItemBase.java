package com.originem.approlight.items;

import com.originem.approlight.init.ModItems;
import com.originem.approlight.util.IHasModel;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IHasModel {
    private EnumRarity baseRarity;

    public ItemBase(String name) {
        ModItems.initModItem(this, name);
    }

    @Override
    public void registerModels() {
        ModItems.registerModel(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        if (baseRarity != null) {
            return baseRarity;
        } else {
            return super.getRarity(stack);
        }
    }

    public Item setBaseRarity(EnumRarity baseRarity) {
        this.baseRarity = baseRarity;
        return this;
    }

}
