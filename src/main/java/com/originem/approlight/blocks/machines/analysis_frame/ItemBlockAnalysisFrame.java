package com.originem.approlight.blocks.machines.analysis_frame;

import com.originem.approlight.util.I18nUtil;
import com.originem.approlight.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockAnalysisFrame extends ItemBlock {

    public ItemBlockAnalysisFrame(Block block) {
        super(block);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    //    @Override
//    public double getDurabilityForDisplay(ItemStack stack) {
//        stack.setItemDamage(((NBTTagCompound) stack.getTagCompound().getTag("Data")).getInteger("AnaDurability"));
//        return super.getDurabilityForDisplay(stack);
//    }
    //    @Override
//    public int getDamage(ItemStack stack) {
//        int anaDurability = ((NBTTagCompound) stack.getTagCompound().getTag("Data")).getInteger("AnaDurability");
//        return anaDurability;
//    }
//    @Override
//    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
//        ItemUtil.trySetDataInNBT(stack, "AnaDurability", TileEntityAnalysisFrame.MAX_ANADURABILITY);
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (StackUtil.isValid(stack)) {
            int durability;
            if (stack.hasTagCompound()) {
                NBTTagCompound tag = (NBTTagCompound) stack.getTagCompound().getTag("Data");
                if (tag != null && tag.hasKey("AnaDurability")) {
                    durability = tag.getInteger("AnaDurability");
                } else {
                    durability = ((BlockAnalysisFrame) block).getAnaDurability();
                }
            } else {
                durability = ((BlockAnalysisFrame) block).getAnaDurability();
            }
            tooltip.add(getAnalysisDurabilityString(durability));
        }
    }

    public static String getAnalysisDurabilityString(float durability) {
        return I18nUtil.translateToLocal("info.anadurability.name") + ":" + (int) durability;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getOrCreateSubCompound("Data");
            if (tag != null && tag.hasKey("AnaDurability")) {
                int durability = tag.getInteger("AnaDurability");
                return ((BlockAnalysisFrame) block).getAnaDurability() != durability;
            }
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getOrCreateSubCompound("Data");
            if (tag != null && tag.hasKey("AnaDurability")) {
                double durability = tag.getInteger("AnaDurability");
                double maxDurability = ((BlockAnalysisFrame) block).getAnaDurability();
                return 1 - durability / maxDurability;
            }
        }
        return 0;
    }
}
