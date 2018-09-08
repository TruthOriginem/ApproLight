package com.originem.approlight.items;

import com.originem.approlight.interfaces.IHasItemSpecialEffects;
import com.originem.approlight.util.I18nUtil;
import com.originem.approlight.util.I18nUtil.NameSpace;
import com.originem.approlight.util.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemCustomUtil {


    public static enum ItemSpecialEffect {
        ThirstForLight("thirstforlight");

        private final String translationKey;

        ItemSpecialEffect(String translationKey) {
            this.translationKey = translationKey;
        }

        public String getAdvanceToolTip() {
            return TextFormatting.LIGHT_PURPLE + I18nUtil.translateToLocal(NameSpace.ItemSpecialEffect, translationKey + ".advanceTooltip");
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDetermingBreakSpeed(PlayerEvent.BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack itemStack = player.getHeldItemMainhand();
        if (StackUtil.isValid(itemStack) && itemStack.getItem() instanceof IHasItemSpecialEffects) {
            IHasItemSpecialEffects item = (IHasItemSpecialEffects) itemStack.getItem();
            if (!item.getEffects().isEmpty()) {
                int light = getLight(player);
                for (ItemSpecialEffect effect : item.getEffects()) {
                    /*Speed up break speed*/
                    if (effect.equals(ItemSpecialEffect.ThirstForLight)) {
                        if (light <= 8) {
                            float newSpeed = event.getOriginalSpeed() * 1.5f;
                            event.setNewSpeed(newSpeed);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void OnToolTipChanged(ItemTooltipEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack itemStack = event.getItemStack();
        if (player != null && StackUtil.isValid(itemStack) && itemStack.getItem() instanceof IHasItemSpecialEffects) {
            BlockPos pos = new BlockPos(player);
            World world = player.world;
            IHasItemSpecialEffects item = (IHasItemSpecialEffects) itemStack.getItem();
            if (!item.getEffects().isEmpty()) {
                List<String> toAdd = new ArrayList<>();
                boolean showAdvance = GuiScreen.isShiftKeyDown();
                for (ItemSpecialEffect effect : item.getEffects()) {
                    String name = getTranslated(effect.translationKey + ".name");
                    if (effect.equals(ItemSpecialEffect.ThirstForLight)) {
                        boolean isActive = getLight(player) <= 8;
                        TextFormatting activeFormat = isActive ? TextFormatting.RED : TextFormatting.GREEN;
                        toAdd.add(name + activeFormat + "(" + getTranslated(effect.translationKey + (isActive ? ".dark" : ".light")) + ")");
                    }
                    if (showAdvance) {
                        toAdd.add(TextFormatting.ITALIC + effect.getAdvanceToolTip());
                    }
                }
                if (!showAdvance) {
                    toAdd.add(TextFormatting.ITALIC + I18nUtil.translateToLocal(NameSpace.ItemSpecialEffect, "toAdvance"));
                }
                event.getToolTip().addAll(1, toAdd);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void OnLivingHurt(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource) {
            EntityDamageSource entityDamageSource = (EntityDamageSource) event.getSource();
            if (entityDamageSource.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityDamageSource.getTrueSource();
                ItemStack heldMain = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                if (player != null && StackUtil.isValid(heldMain) && heldMain.getItem() instanceof IHasItemSpecialEffects) {
                    IHasItemSpecialEffects item = (IHasItemSpecialEffects) heldMain.getItem();
                    int light = getLight(player);
                    for (ItemSpecialEffect effect : item.getEffects()) {
                        /*Speed up break speed*/
                        if (effect.equals(ItemSpecialEffect.ThirstForLight)) {
                            if (light <= 8) {
                                float newDamage = event.getAmount() * 1.25f;
                                event.setAmount(newDamage);
                            } else if (player.getRNG().nextFloat() < 0.2f) {
                                heldMain.damageItem(-1, player);
                            }
                        }
                    }
                }
            }
        }

    }

    private static String getTranslated(String key) {
        return I18nUtil.translateToLocal(NameSpace.ItemSpecialEffect, key);
    }

    public static int getLight(Entity entity) {
        AxisAlignedBB entityBox = entity.getEntityBoundingBox();
        World world = entity.world;
        BlockPos pos = new BlockPos(entity.posX, entityBox.minY + entity.getEyeHeight(), entity.posZ);
        if (!world.isRemote) {
            return world.getLightFromNeighbors(pos);
        } else {
            int value;
            int origin = world.getSkylightSubtracted();
            world.setSkylightSubtracted(world.calculateSkylightSubtracted(0));
            value = world.getLightFromNeighbors(pos);
            world.setSkylightSubtracted(origin);
            return value;
        }
    }


    public static boolean onBlockDestoryed(ItemSpecialEffect effect, ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (!worldIn.isRemote)
            switch (effect) {
                case ThirstForLight: {
                    int light = getLight(entityLiving);
                    if (light > 8 && worldIn.rand.nextFloat() < 0.2f) {
                        stack.damageItem(-1, entityLiving);
                    }
                    break;
                }
            }
        return true;
    }
}
