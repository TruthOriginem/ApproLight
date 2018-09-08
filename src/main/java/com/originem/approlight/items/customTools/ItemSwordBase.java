package com.originem.approlight.items.customTools;

import com.originem.approlight.init.ModItems;
import com.originem.approlight.interfaces.IHasItemSpecialEffects;
import com.originem.approlight.util.IHasModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.originem.approlight.items.ItemCustomUtil.ItemSpecialEffect;
import static com.originem.approlight.items.ItemCustomUtil.onBlockDestoryed;

public class ItemSwordBase extends ItemSword implements IHasModel, IHasItemSpecialEffects {

    private final EnumSet<ItemSpecialEffect> effects;

    public ItemSwordBase(String name, ToolMaterial material, ItemSpecialEffect... effects) {
        super(material);
        if (effects.length > 0) {
            List<ItemSpecialEffect> collection = new ArrayList<>(java.util.Arrays.asList(effects));
            this.effects = EnumSet.copyOf(collection);
        } else {
            this.effects = EnumSet.noneOf(ItemSpecialEffect.class);
        }
        ModItems.initModItem(this, name);
    }

    @Override
    public void registerModels() {
        ModItems.registerModel(this);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        boolean origin = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        if (origin) {
            origin &= onBlockDestoryed(ItemSpecialEffect.ThirstForLight, stack, worldIn, entityLiving);
        }
        return origin;
    }

    @Override
    public EnumSet<ItemSpecialEffect> getEffects() {
        return effects;
    }
}
