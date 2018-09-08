package com.originem.approlight.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class I18nUtil {
    /***
     * Key. e.g:name
     * @param itemStack
     * @param key
     * @return
     */
    public static String translateToLocal(ItemStack itemStack, String key) {
        return translateToLocal(itemStack.getItem().getUnlocalizedNameInefficiently(itemStack) + "." + key);
    }

    public static String translateToLocal(String key) {
        if (I18n.canTranslate(key)) {
            return I18n.translateToLocal(key);
        } else {
            return I18n.translateToFallback(key);
        }
    }

    public static String translateToLocal(NameSpace nameSpace, String key) {
        return translateToLocal(nameSpace.nameSpace + "." + key);
    }

    public static enum NameSpace {
        LightRequied("al.lightrequired"),
        ItemSpecialEffect("item.specialeffect");

        private final String nameSpace;

        NameSpace(String nameSpace) {
            this.nameSpace = nameSpace;
        }
    }
}
