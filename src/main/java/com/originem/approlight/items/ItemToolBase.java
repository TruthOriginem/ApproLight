package com.originem.approlight.items;

import com.originem.approlight.Main;
import com.originem.approlight.init.ModCreativeTab;
import com.originem.approlight.init.ModItems;
import com.originem.approlight.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemToolBase extends ItemTool implements IHasModel {
    protected ItemToolBase(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
        super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.APPROLIGHT_TAB);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.PROXY.registerItemRenderer(this, 0, "inventory");
    }
}
