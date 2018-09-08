package com.originem.approlight.blocks.machines.immerse_furnace;

import com.originem.approlight.blocks.recipes.ImmerseFurnaceRecipes;
import com.originem.approlight.blocks.tile.TileEntityImmerseFurnace;
import com.originem.approlight.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiImmerseFurnace extends GuiContainer {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/immerse_furnace.png");
    private final InventoryPlayer player;
    private final TileEntityImmerseFurnace tileentity;

    public GuiImmerseFurnace(InventoryPlayer player, TileEntityImmerseFurnace tileentity) {
        super(new ContainerImmerseFurnace(player, tileentity));
        this.player = player;
        this.tileentity = tileentity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = this.tileentity.getDisplayName().getUnformattedText();
//        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
        this.fontRenderer.drawString(tileName, (this.xSize / 4 * 3 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, Color.BLUE.getRGB());
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if (TileEntityImmerseFurnace.isBurning(tileentity)) {
            int k = this.getBurnLeftScaled(14);
            this.drawTexturedModalRect(this.guiLeft + 47, this.guiTop + 37 + 14 - k, 176, 14 - k, 14, k);
        }
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 14, l, 17);
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.tileentity.getField(1);
        if (i == 0) i = ImmerseFurnaceRecipes.COOKTIME_FOR_EACH_RECIPE;
        return this.tileentity.getField(0) * pixels / i;
    }

    private int getCookProgressScaled(int pixels) {
        int i = this.tileentity.getField(2);
        int j = this.tileentity.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
