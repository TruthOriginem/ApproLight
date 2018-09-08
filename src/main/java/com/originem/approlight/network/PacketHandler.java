package com.originem.approlight.network;

import com.originem.approlight.tiles.TileEntityBase;
import com.originem.approlight.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public final class PacketHandler {

    public static final List<IDataHandler> DATA_HANDLERS = new ArrayList<>();

    public static final IDataHandler TILE_ENTITY_HANDLER = new IDataHandler() {
        @Override
        @SideOnly(Side.CLIENT)
        public void handleData(NBTTagCompound compound, MessageContext context) {
            World world = Minecraft.getMinecraft().world;
            if (world != null) {
                TileEntity tile = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));
                if (tile instanceof TileEntityBase) {
                    ((TileEntityBase) tile).readSyncableNBT(compound.getCompoundTag("Data"), TileEntityBase.NBTType.SYNC);
                }
            }
        }
    };

    public static SimpleNetworkWrapper theNetwork;

    public static void init() {
        theNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
        theNetwork.registerMessage(PacketServerToClient.Handler.class, PacketServerToClient.class, 0, Side.CLIENT);

        DATA_HANDLERS.add(TILE_ENTITY_HANDLER);
    }

    public static void sendMessageToClient(IMessage message, World world, BlockPos pos, float checkRange) {
        theNetwork.sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), checkRange));
    }
}