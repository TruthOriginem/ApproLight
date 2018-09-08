package com.originem.approlight.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ImmersedIngotBlock extends BlockBase {
    public ImmersedIngotBlock(String name, Material material) {
        super(name, material);
//        setSoundType(SoundType.METAL);
        setHardness(5f);
        setResistance(45f);
        setHarvestLevel("pickaxe",1);
        setLightLevel(5f);
    }
}
