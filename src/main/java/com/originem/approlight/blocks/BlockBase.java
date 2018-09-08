package com.originem.approlight.blocks;

import com.originem.approlight.Main;
import com.originem.approlight.init.ModBlocks;
import com.originem.approlight.init.ModCreativeTab;
import com.originem.approlight.init.ModItems;
import com.originem.approlight.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material) {
        this(name, material, true);
    }

    public BlockBase(String name, Material material, boolean isInCreativeTab) {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        if (isInCreativeTab)
            setCreativeTab(ModCreativeTab.APPROLIGHT_TAB);

        ModBlocks.BLOCKS.add(this);
        registerItemBlock();
    }

    private void registerItemBlock() {
        ModItems.ITEMS.add(getItemBlock().setRegistryName(this.getRegistryName()));
    }

    protected ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @Override
    public void registerModels() {
        Main.PROXY.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
