package com.originem.approlight.items.customTools;

import com.google.common.collect.Sets;
import com.originem.approlight.init.ModItems;
import com.originem.approlight.items.ItemToolBase;
import com.originem.approlight.util.handlers.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCrystalChisel extends ItemToolBase {

    public ItemCrystalChisel(String name, ToolMaterial materialIn) {
        super(name, 0f, -2.8f, materialIn, Sets.newHashSet());
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block blockType = worldIn.getBlockState(pos).getBlock();
        ItemStack itemStack = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos.offset(facing), facing, itemStack)) {
            return EnumActionResult.FAIL;
        } else if (blockType.equals(Blocks.OBSIDIAN)) {
            worldIn.playSound(player, pos, SoundHandler.BLOCK_CRYSTAL_CHISEL_DIG, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!worldIn.isRemote) {
                if (worldIn.rand.nextFloat() < 0.5) {
                    worldIn.destroyBlock(pos, false);
                }
                ItemStack spawn = new ItemStack(ModItems.OBSIDIAN_DEBRIS);
                Block.spawnAsEntity(worldIn, pos, spawn);
                itemStack.damageItem(100, player);
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(getTranslatedInfo());
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
