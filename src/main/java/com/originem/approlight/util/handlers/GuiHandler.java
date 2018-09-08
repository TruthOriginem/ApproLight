package com.originem.approlight.util.handlers;

import com.originem.approlight.blocks.machines.immerse_furnace.ContainerImmerseFurnace;
import com.originem.approlight.blocks.machines.immerse_furnace.GuiImmerseFurnace;
import com.originem.approlight.blocks.tile.TileEntityImmerseFurnace;
import com.originem.approlight.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Reference.GUI_IMMERSE_FURNACE) return new ContainerImmerseFurnace(player.inventory, (TileEntityImmerseFurnace) world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Reference.GUI_IMMERSE_FURNACE) return new GuiImmerseFurnace(player.inventory, (TileEntityImmerseFurnace) world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }
}
