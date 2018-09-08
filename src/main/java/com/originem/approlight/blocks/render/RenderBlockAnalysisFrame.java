package com.originem.approlight.blocks.render;

import com.originem.approlight.Main;
import com.originem.approlight.blocks.machines.analysis_frame.ItemBlockAnalysisFrame;
import com.originem.approlight.blocks.tile.TileEntityAnalysisFrame;
import com.originem.approlight.items.analysis.ItemAnalysisPowder;
import com.originem.approlight.util.AssetUtil;
import com.originem.approlight.util.I18nUtil;
import com.originem.approlight.util.I18nUtil.NameSpace;
import com.originem.approlight.util.StackUtil;
import com.originem.approlight.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class RenderBlockAnalysisFrame extends TileEntitySpecialRenderer<TileEntityAnalysisFrame> {
    @Override
    public void render(TileEntityAnalysisFrame tile, double x, double y, double z, float par5, int par6, float f) {

        if (!(tile instanceof TileEntityAnalysisFrame)) {
            return;
        }
        ItemStack stack = tile.getStack();
        if (StackUtil.isValid(stack)) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

            double boop = Minecraft.getSystemTime() / 1000D;
            GlStateManager.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
            GlStateManager.rotate((float) (((boop * 40D) % 360)), 0, 1, 0);

            float scale = stack.getItem() instanceof ItemBlock ? 0.7F : 0.5F;
            GlStateManager.scale(scale, scale, scale);
            try {
                AssetUtil.renderItemInWorld(stack);
            } catch (Exception e) {
                Main.LOGGER.error("Something went wrong trying to render an item in a display stand! The item is " + stack.getItem().getRegistryName() + "!", e);
            }

            GlStateManager.popMatrix();
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.player.isSneaking()) {
            ItemStack heldItem = mc.player.getHeldItem(EnumHand.MAIN_HAND);
            if (StackUtil.isValid(heldItem) && heldItem.getItem() instanceof ItemAnalysisPowder) {

            } else if (!tile.couldShowHud()) return;
        }

        double dx = x + 0.5;
        double dy = y + 0.5;
        double dz = z + 0.5;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (distance > 4D) return;
        double multiplier = distance / 5D; //mobs only render ~120 blocks away
        double scale = 0.02;
        multiplier = MathHelper.clamp(multiplier, 0, 1);

        FontRenderer fontRenderer = mc.fontRenderer;
        RenderManager renderManager = mc.getRenderManager();

        String renderString;
        Color renderColor;
        if (tile.isAnalysisCompleted()) {
            renderString = I18nUtil.translateToLocal(NameSpace.LightRequied, "analysisCompleted");
            renderColor = new Color(0.2f, 1f, 0.2f, (float) (1f - multiplier));
        } else {
            if (!tile.isLightSufficient()) {
                renderString = I18nUtil.translateToLocal(NameSpace.LightRequied, "insufficient");
                renderColor = new Color(1, 0.2f, 0.2f, (float) (1f - multiplier));
            } else {
                renderString = ItemBlockAnalysisFrame.getAnalysisDurabilityString(tile.getAnaDurability());
                renderColor = new Color(1, 1, 1, (float) (1f - multiplier));
            }
        }
        int textWidth = fontRenderer.getStringWidth(renderString);
        int textHeight = fontRenderer.FONT_HEIGHT;
        int startX = -textWidth >> 1;

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) dx, (float) dy + 0.5F, (float) dz);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();

        Gui.drawRect(startX - 2, textHeight, -startX + 2, -1, TextUtil.getAlphaColor(Color.darkGray, (float) (1f - multiplier)).getRGB());

        GlStateManager.enableBlend();
        fontRenderer.drawString(renderString, startX, 0, renderColor.getRGB(), false);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }

}
