package com.originem.approlight.items;

import com.originem.approlight.init.ModItems;
import com.originem.approlight.util.IHasModel;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood implements IHasModel {
    public ItemFoodBase(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        ModItems.initModItem(this, name);
    }


    @Override
    public void registerModels() {
        ModItems.registerModel(this);
    }
}
