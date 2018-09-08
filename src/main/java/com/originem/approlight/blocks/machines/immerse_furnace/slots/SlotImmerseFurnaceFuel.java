package com.originem.approlight.blocks.machines.immerse_furnace.slots;

import com.originem.approlight.blocks.tile.TileEntityImmerseFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotImmerseFurnaceFuel extends Slot {
    public SlotImmerseFurnaceFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);

    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return TileEntityImmerseFurnace.isItemFuel(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }
}
