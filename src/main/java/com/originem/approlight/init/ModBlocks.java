package com.originem.approlight.init;

import com.originem.approlight.blocks.ImmersedIngotBlock;
import com.originem.approlight.blocks.machines.analysis_frame.BlockAnalysisFrame;
import com.originem.approlight.blocks.machines.immerse_furnace.BlockImmerseFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block IMMERSED_IRON_BLOCK = new ImmersedIngotBlock("immersed_iron_block", Material.IRON);
    public static final Block IMMERSED_GOLD_BLOCK = new ImmersedIngotBlock("immersed_gold_block", Material.IRON);

    public static final Block IMMERSE_FURNACE = new BlockImmerseFurnace("immerse_furnace", false);
    public static final Block LIT_IMMERSE_FURNACE = new BlockImmerseFurnace("lit_immerse_furnace", true).setTranslationKey("immerse_furnace");

    public static final Block ANALYSIS_GOLD_FRAME = new BlockAnalysisFrame("analysis_gold_frame", 2400, 1);
    public static final Block ANALYSIS_DIAMOND_FRAME = new BlockAnalysisFrame("analysis_diamond_frame", 12000, 2);
}
