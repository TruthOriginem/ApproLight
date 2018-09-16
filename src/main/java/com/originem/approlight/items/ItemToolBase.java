package com.originem.approlight.items;

import com.originem.approlight.init.ModItems;
import com.originem.approlight.util.I18nUtil;
import com.originem.approlight.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemToolBase extends ItemTool implements IHasModel {
    protected ItemToolBase(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
        super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
        ModItems.initModItem(this, name);
    }

    @Override
    public void registerModels() {
        ModItems.registerModel(this);
    }

    protected String getTranslatedInfo() {
        return I18nUtil.translateToLocal(this.getTranslationKey() + ".info");
    }
}
