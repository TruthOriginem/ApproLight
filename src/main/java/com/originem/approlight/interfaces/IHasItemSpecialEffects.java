package com.originem.approlight.interfaces;

import com.originem.approlight.items.ItemCustomUtil.ItemSpecialEffect;

import java.util.EnumSet;

public interface IHasItemSpecialEffects {
    public EnumSet<ItemSpecialEffect> getEffects();
}
