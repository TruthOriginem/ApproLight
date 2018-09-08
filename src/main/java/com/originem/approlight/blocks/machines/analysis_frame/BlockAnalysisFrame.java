package com.originem.approlight.blocks.machines.analysis_frame;

import com.originem.approlight.blocks.BlockContainerBase;
import com.originem.approlight.blocks.recipes.AnalysisShimmerRecipes;
import com.originem.approlight.blocks.tile.TileEntityAnalysisFrame;
import com.originem.approlight.items.analysis.ItemAnalysisPowder;
import com.originem.approlight.util.StackUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAnalysisFrame extends BlockContainerBase {
    private final int anaDurability;
    private final int anaSpeed;
    public static final AxisAlignedBB GOLD_FRAME_AABB = new AxisAlignedBB(0, 0, 0, 1, 6 / 16d, 1);

    public BlockAnalysisFrame(String name, int anaDurability, int anaSpeed) {
        super(name, Material.GLASS);
        setHarvestLevel("pickaxe", 0);
        setSoundType(SoundType.GLASS);
        setHardness(1f);
        setResistance(15f);
        this.anaDurability = anaDurability;
        this.anaSpeed = anaSpeed;
    }

    @Override
    protected ItemBlock getItemBlock() {
        return new ItemBlockAnalysisFrame(this);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    //
//    @Override
//    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
//        return true;
//    }


    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return GOLD_FRAME_AABB;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntityAnalysisFrame frame = (TileEntityAnalysisFrame) worldIn.getTileEntity(pos);
        if (StackUtil.isValid(frame.getStack())) {
            if (rand.nextDouble() < 0.5d && frame.isAnalysing()) {
                double x = pos.getX() + 0.5 + MathHelper.nextDouble(rand, -0.2, 0.2);
                double z = pos.getZ() + 0.5 + MathHelper.nextDouble(rand, -0.2, 0.2);
                double y = pos.getY() + 0.3 + MathHelper.nextDouble(rand, -0.1, 0.1);
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!world.isRemote) {
            TileEntityAnalysisFrame analysisFrame = (TileEntityAnalysisFrame) world.getTileEntity(pos);
            if (analysisFrame != null) {
                ItemStack display = analysisFrame.inv.getStackInSlot(0);
                if (!StackUtil.isValid(display) && StackUtil.isValid(heldItem) && heldItem.getItem() instanceof ItemAnalysisPowder) {
                    int repairAmount = ((ItemAnalysisPowder) heldItem.getItem()).getRepairAmount();
                    if (analysisFrame.getAnaDurability() >= getAnaDurability()) {
                        return false;
                    } else {
                        analysisFrame.addAnaDurability(Math.min(repairAmount, getAnaDurability() - analysisFrame.getAnaDurability()));
                        if (!player.capabilities.isCreativeMode) heldItem.shrink(1);
                        analysisFrame.markDirty();
                        return true;
                    }
                } else {
                    analysisFrame.resetAnalysis();
                    if (StackUtil.isValid(heldItem)) {
                        //display is empty
                        if (!StackUtil.isValid(display)) {
                            if (AnalysisShimmerRecipes.getInstance().getAnalysedResult(heldItem) != ItemStack.EMPTY) {
                                ItemStack toPut = heldItem.copy();
                                toPut.setCount(1);
                                analysisFrame.inv.setStackInSlot(0, toPut);
                                if (!player.capabilities.isCreativeMode) heldItem.shrink(1);
                                return true;
                            }
                        } else if (StackUtil.canBeStacked(heldItem, display)) {
                            int maxTransfer = Math.min(display.getCount(), heldItem.getMaxStackSize() - heldItem.getCount());
                            if (maxTransfer > 0) {
                                heldItem.grow(maxTransfer);
                                ItemStack newDisplay = display.copy();
                                newDisplay.shrink(maxTransfer);
                                analysisFrame.inv.setStackInSlot(0, newDisplay);
                                return true;
                            }
                        } else {
                            StackUtil.spawnClonedItemStackAsEntity(world, pos, display);
                            analysisFrame.inv.setStackInSlot(0, ItemStack.EMPTY);
                            return true;
                        }
                    } else {
                        if (StackUtil.isValid(display)) {
                            player.setHeldItem(hand, display.copy());
                            analysisFrame.inv.setStackInSlot(0, ItemStack.EMPTY);
                            return true;
                        }
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }


    public int getAnaDurability() {
        return this.anaDurability;
    }

    public int getAnaSpeed() {
        return this.anaSpeed;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAnalysisFrame().initialize(getAnaSpeed(), getAnaDurability());
    }
}
