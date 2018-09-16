package com.originem.approlight.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StackUtil {
    public static boolean canBeStacked(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    public static boolean isValid(ItemStack stack) {
        if (stack == null) return false;
        Item i = stack.getItem();
        if (i == null) return false;
        return !stack.isEmpty();
    }

    /**
     * Spawn item stack entity. DO NOT remove the origin item stack.
     */
    public static void spawnClonedItemStackAsEntity(World world, BlockPos pos, ItemStack itemStack) {
        float dX = world.rand.nextFloat() * 0.4F + 0.3F;
        float dY = world.rand.nextFloat() * 0.4F + 0.3F;
        float dZ = world.rand.nextFloat() * 0.4F + 0.3F;
        EntityItem entityItem = new EntityItem(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, itemStack.copy());
        entityItem.setDefaultPickupDelay();
        float factor = 0.02F;
        entityItem.motionX = world.rand.nextGaussian() * factor;
        entityItem.motionY = world.rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = world.rand.nextGaussian() * factor;
        world.spawnEntity(entityItem);
    }

    public static NBTTagCompound trySetDataInNBT(ItemStack itemStack, String key, Object value) {
        NBTTagCompound tag = null;
        if (itemStack.hasTagCompound()) {
            tag = itemStack.getTagCompound();
            if (!tag.hasKey("Data")) {
                tag.setTag("Data", new NBTTagCompound());
            }
        } else {
            tag = new NBTTagCompound();
            tag.setTag("Data", new NBTTagCompound());
            itemStack.setTagCompound(tag);
        }
        tag = tag.getCompoundTag("Data");
        if (value instanceof Integer) {
            tag.setInteger(key, (Integer) value);
        }
        return tag;
    }
}
