package com.originem.approlight.util.handlers;

import com.originem.approlight.blocks.render.RenderBlockAnalysisFrame;
import com.originem.approlight.blocks.tile.TileEntityAnalysisFrame;
import com.originem.approlight.blocks.tile.TileEntityImmerseFurnace;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityImmerseFurnace.class, "immerse_furnace");
        ClientRegistry.registerTileEntity(TileEntityAnalysisFrame.class, "analysis_frame", new RenderBlockAnalysisFrame());
        //GameRegistry.registerTileEntity(TileEntityAnalysisFrame.class, "analysis_diamond_frame");
    }
}
