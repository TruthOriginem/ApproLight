package com.originem.approlight.tiles;

import com.originem.approlight.network.PacketHandler;
import com.originem.approlight.network.PacketServerToClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityBase extends TileEntity implements ITickable {
    public boolean isRedstonePowered;
    public boolean isPulseMode;
    public boolean stopFromDropping;
    protected int lightStrength;
    protected int ticksElapsed;

    protected boolean firstPlaced;

    public TileEntityBase() {
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
        return compound;
    }

    @Override
    public final void readFromNBT(NBTTagCompound compound) {
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) super.writeToNBT(compound);

        if (type == NBTType.SAVE_TILE) {
            compound.setBoolean("Redstone", this.isRedstonePowered);
            compound.setInteger("TicksElapsed", this.ticksElapsed);
            compound.setBoolean("StopDrop", this.stopFromDropping);
        } else if (type == NBTType.SYNC) {
            if (stopFromDropping) {
                compound.setBoolean("StopDrop", this.stopFromDropping);
            }
            compound.setInteger("LightStrength", this.lightStrength);
        }
    }

    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) super.readFromNBT(compound);

        if (type == NBTType.SAVE_TILE) {
            this.isRedstonePowered = compound.getBoolean("Redstone");
            this.ticksElapsed = compound.getInteger("TicksElapsed");
            this.stopFromDropping = compound.getBoolean("StopDrop");
        } else if (type == NBTType.SYNC) {
            this.lightStrength = compound.getInteger("LightStrength");
            this.stopFromDropping = compound.getBoolean("StopDrop");
        }
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return new SPacketUpdateTileEntity(this.pos, -1, compound);
    }

    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readSyncableNBT(pkt.getNbtCompound(), NBTType.SYNC);
    }

    // when loading blocks, send messages from server to client.
    @Override
    public final NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return compound;
    }

    //messages from getUpdateTag
    @Override
    public final void handleUpdateTag(NBTTagCompound compound) {
        this.readSyncableNBT(compound, NBTType.SYNC);
    }

    public final void sendUpdate() {
        if (this.world != null && !this.world.isRemote) {
            NBTTagCompound compound = new NBTTagCompound();
            this.writeSyncableNBT(compound, NBTType.SYNC);

            NBTTagCompound data = new NBTTagCompound();
            data.setTag("Data", compound);
            data.setInteger("X", this.pos.getX());
            data.setInteger("Y", this.pos.getY());
            data.setInteger("Z", this.pos.getZ());
            PacketHandler.sendMessageToClient(new PacketServerToClient(data, PacketHandler.TILE_ENTITY_HANDLER), this.world, this.pos, 64);
//            PacketHandler.theNetwork.sendToAllAround(new PacketServerToClient(data, PacketHandler.TILE_ENTITY_HANDLER), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 64));
        }
    }

    public final boolean sendUpdateWithInterval(int interval) {
        if (this.ticksElapsed % interval == 0) {
            sendUpdate();
            return true;
        } else {
            return false;
        }
    }

    public final boolean sendUpdateWithInterval() {
        return sendUpdateWithInterval(4);
    }

    public boolean canPlayerUse(EntityPlayer player) {
        return player.getDistanceSq(this.getPos().getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64 && !this.isInvalid() && this.world.getTileEntity(this.pos) == this;
    }

    public void updateEntity() {
        this.ticksElapsed++;
        if (!world.isRemote) {
            if (world.isAreaLoaded(pos, 1)) {
                this.lightStrength = world.getLightFromNeighbors(pos);
            }
        }
    }

    public IItemHandler getItemHandler(EnumFacing facing) {
        return null;
    }

    @Override
    public void update() {
        updateEntity();
    }

    public enum NBTType {
        /**
         * Use when normal writeToNBT/readToNBT is expected.
         */
        SAVE_TILE,
        /**
         * Use when data needs to be sent to the client.
         */
        SYNC,
        /**
         * Wat
         */
        SAVE_BLOCK
    }
}
