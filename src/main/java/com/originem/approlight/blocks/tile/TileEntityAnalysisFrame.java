package com.originem.approlight.blocks.tile;

import com.originem.approlight.blocks.recipes.AnalysisShimmerRecipes;
import com.originem.approlight.init.ModItems;
import com.originem.approlight.interfaces.ILightRequiredProperties;
import com.originem.approlight.tiles.TileEntityInventoryBase;
import com.originem.approlight.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class TileEntityAnalysisFrame extends TileEntityInventoryBase implements ILightRequiredProperties {

    public TileEntityAnalysisFrame() {
        super(1);
    }

    private int analysingTime;
    private boolean analysisCompleted;
    private int anaDurability;
    private boolean showParticle = false;
    private int anaSpeed;


    private int lastAnalysingTime;

    public TileEntityAnalysisFrame initialize(int anaSpeed, int anaDurability) {
        this.anaSpeed = anaSpeed;
        this.anaDurability = anaDurability;
        this.lastAnalysingTime = -1;
        return this;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            this.anaSpeed = compound.getInteger("AnalysisSpeed");
        }
        //sync and tile
        this.analysingTime = compound.getInteger("AnalysingTime");
        this.analysisCompleted = compound.getBoolean("AnalysisCompleted");
        this.anaDurability = compound.getInteger("AnaDurability");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            compound.setInteger("AnalysisSpeed", (short) this.anaSpeed);
        }
        //sync and tile
        compound.setInteger("AnalysingTime", (short) this.analysingTime);
        compound.setBoolean("AnalysisCompleted", this.analysisCompleted);
        compound.setInteger("AnaDurability", (short) this.anaDurability);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!world.isRemote) {
            //If not empty, needs to sync the client and server
            if (!getStack().isEmpty()) {
                if (isAnalysing()) {
                    analysingTime += anaSpeed;
                    anaDurability -= anaSpeed;
                    if (analysingTime >= getAnalysingCostTime()) {
                        analysisCompleted = true;
                        ItemStack result = AnalysisShimmerRecipes.getInstance().getAnalysedResult(getStack());
                        result = result.copy();
                        this.inv.setStackInSlot(0, result);
                        spawnShimmerImpurity();
                        markDirty();
                    }
                }
                if (anaDurability <= 0) {
                    stopFromDropping = true;
                    IBlockState iblockstate = this.world.getBlockState(pos);
                    Block block = iblockstate.getBlock();
                    block.dropBlockAsItemWithChance(world, pos, iblockstate, 1f, 0);
                    world.destroyBlock(pos, false);
                    markDirty();
                }
            } else if (isAnalysisCompleted()) {
                resetAnalysis();
            }
            if (this.lastAnalysingTime != this.analysingTime && this.sendUpdateWithInterval()) {
                this.lastAnalysingTime = this.analysingTime;
            }
        }
        //client
        else if (!showParticle && analysisCompleted && !getStack().isEmpty()) {
            for (int i = 0; i < 20; i++) {
                Random rand = new Random();
                double x = pos.getX() + 0.5 + MathHelper.nextDouble(rand, -0.25, 0.25);
                double z = pos.getZ() + 0.5 + MathHelper.nextDouble(rand, -0.25, 0.25);
                double y = pos.getY() + 0.3 + MathHelper.nextDouble(rand, -0.1, 0.15);
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0, 0, 0);
            }
            world.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            showParticle = true;
        } else if (!analysisCompleted) {
            showParticle = false;
        }

    }

    private void spawnShimmerImpurity() {
        if (world.rand.nextFloat() < 0.33f) {
            StackUtil.spawnClonedItemStackAsEntity(world, pos, new ItemStack(ModItems.SHIMMER_IMPURITY));
        }
    }

    //needs client
    public boolean isAnalysing() {
        return isLightSufficient() && !isAnalysisCompleted();
    }


    public boolean couldShowHud() {
        return !getStack().isEmpty();
    }

    //client or server
    public int getAnaDurability() {
        return anaDurability;
    }

    public void addAnaDurability(int amount) {

        this.anaDurability += amount;
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 1;
    }

    public ItemStack getStack() {
        return this.inv.getStackInSlot(0);
    }

    private int getAnalysingCostTime() {
        return AnalysisShimmerRecipes.getInstance().getAnalysedTime(getStack());
    }


    public void resetAnalysis() {
        this.analysingTime = 0;
        this.analysisCompleted = false;
        markDirty();
    }

    public boolean isAnalysisCompleted() {
        return analysisCompleted;
    }

    @Override
    public boolean isLightSufficient() {
        return lightStrength > getNeededLight();
    }

    @Override
    public int getNeededLight() {
        return 8;
    }
}
