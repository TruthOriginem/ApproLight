package com.originem.approlight.items.shimmer;

import com.originem.approlight.items.ItemBase;
import com.originem.approlight.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemShimmerFuel extends ItemBase {
    private int vanillaBurnTime;
    private int shimmerBurnTime;

    public ItemShimmerFuel(String name) {
        this(name, -1, -1);
    }

    public ItemShimmerFuel(String name, int vanillaBurnTime, int shimmerBurnTime) {
        super(name);
        this.vanillaBurnTime = vanillaBurnTime;
        this.shimmerBurnTime = shimmerBurnTime;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18nUtil.translateToLocal("info.shimmerfuel.name"));
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        if (vanillaBurnTime == -1) {
            return super.getItemBurnTime(itemStack);
        }
        return vanillaBurnTime;
    }

    public int getItemShimmerBurnTime() {
        return shimmerBurnTime;
    }
}
