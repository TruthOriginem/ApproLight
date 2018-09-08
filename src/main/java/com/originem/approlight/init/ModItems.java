package com.originem.approlight.init;

import com.originem.approlight.Main;
import com.originem.approlight.items.ItemBase;
import com.originem.approlight.items.ItemFoodBase;
import com.originem.approlight.items.analysis.ItemAnalysisPowder;
import com.originem.approlight.items.customTools.*;
import com.originem.approlight.items.shimmer.ItemShimmerFuel;
import com.originem.approlight.util.IHasModel;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

import static com.originem.approlight.items.ItemCustomUtil.ItemSpecialEffect;
import static net.minecraft.item.Item.ToolMaterial;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final ToolMaterial IMMERSED_PLATINUM = EnumHelper.addToolMaterial(
            "immersed_platinum", 2, 350, 8f, 2.5f, 20).setRepairItem(new ItemStack(ModItems.IMMERSED_PLATINUM_INGOT));

    public static final Item IMMERSED_IRON_INGOT = new ItemBase("immersed_iron_ingot");
    public static final Item IMMERSED_GOLD_INGOT = new ItemBase("immersed_gold_ingot");
    public static final Item IMMERSED_DIAMOND = new ItemBase("immersed_diamond");
    public static final Item IMMERSED_PLATINUM_INGOT = new ItemBase("immersed_platinum_ingot").setBaseRarity(EnumRarity.RARE);
    public static final Item IMMERSED_GLASS_GOLD_INGOT = new ItemBase("immersed_glass_gold_ingot").setBaseRarity(EnumRarity.EPIC);

    public static final Item SHIMMER_COAL = new ItemShimmerFuel("shimmer_coal",2000,800);
    public static final Item SHIMMER_IMPURITY = new ItemShimmerFuel("shimmer_impurity", 200, 200);
    public static final Item SHIMMER_GEL = new ItemShimmerFuel("shimmer_gel", 300, 400);
    public static final Item SHIMMER_STICK = new ItemBase("shimmer_stick");

    public static final Item ANALYSIS_POWDER = new ItemAnalysisPowder("analysis_powder", 1200);

    public static final Item IMMERSED_ROTTEN_FLESH = new ItemFoodBase("immersed_rotten_flesh", 5, 0.4f, true)
            .setPotionEffect(new PotionEffect(MobEffects.GLOWING, 600), 1f).setAlwaysEdible();

    public static final Item IMMERSED_PLATINUM_PICKAXE = new ItemPickaxeBase("immersed_platinum_pickaxe", IMMERSED_PLATINUM, ItemSpecialEffect.ThirstForLight);
    public static final Item IMMERSED_PLATINUM_SHOVEL = new ItemShovelBase("immersed_platinum_shovel", IMMERSED_PLATINUM, ItemSpecialEffect.ThirstForLight);
    public static final Item IMMERSED_PLATINUM_HOE = new ItemHoeBase("immersed_platinum_hoe", IMMERSED_PLATINUM, ItemSpecialEffect.ThirstForLight);
    public static final Item IMMERSED_PLATINUM_SWORD = new ItemSwordBase("immersed_platinum_sword", IMMERSED_PLATINUM, ItemSpecialEffect.ThirstForLight);
    public static final Item IMMERSED_PLATINUM_AXE = new ItemAxeBase("immersed_platinum_axe", IMMERSED_PLATINUM, 8, -3, ItemSpecialEffect.ThirstForLight);

    /**
     * Should be put in every mod item classes.
     *
     * @param item instance
     * @param name registry and translation name
     */
    public static void initModItem(Item item, String name) {
        item.setTranslationKey(name);
        item.setRegistryName(name);
        item.setCreativeTab(ModCreativeTab.APPROLIGHT_TAB);

        ITEMS.add(item);
    }

    /**
     * Should be used in <code>public void registerModels</code> which is in {@link IHasModel}
     */
    public static void registerModel(Item item) {
        Main.PROXY.registerItemRenderer(item, 0, "inventory");
    }
}
