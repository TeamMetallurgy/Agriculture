package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

public class GUIBrewer extends GuiLiquids {
    ContainerBrewer brewer;
    private final FluidWidget fluidWidgetLeft;
    private final FluidWidget fluidWidgetRight;
    private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Brewer.png");

    public GUIBrewer(final ContainerBrewer containerBrewer)
    {
        super(containerBrewer);
        brewer = containerBrewer;

        fluidWidgetLeft = new GuiLiquids.FluidWidget(brewer.getTe().getLeftTank(), 50, 10, 179, 3, 19, 65);
        fluidWidgetRight = new GuiLiquids.FluidWidget(brewer.getTe().getRightTank(), 110, 10, 179, 3, 19, 65);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor3f(1f, 1f, 1f);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        brewer.getTe().getLeftTank();
        brewer.getTe().getRightTank();

        final float scaledProcess = brewer.getTe().getProcessScaled();

        drawTexturedModalRect(guiLeft + 66, guiTop + 16, 180, 109, (int) (43 * scaledProcess), 9);
        drawTexturedModalRect(guiLeft + 79, guiTop + 12, 79, 12, 17, 17);

        int fuelRemaining = 80 - brewer.getTe().getRemainingFuelLevel();
        if (fuelRemaining > 80)
        {
            fuelRemaining = 80;
        }
        if (fuelRemaining < 0)
        {
            fuelRemaining = 0;
        }
        final float scale = fuelRemaining / 80f;

        drawTexturedModalRect(guiLeft + 81, guiTop + 43 + (int) (scale * 15), 179, (int) (82 + 15 * scale), 15, 15 - (int) (scale * 15));

        fluidWidgetLeft.drawLiquid(this, guiLeft, guiTop, texture);
        fluidWidgetRight.drawLiquid(this, guiLeft, guiTop, texture);

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    @Override
    public FluidTank getTankAtCoord(final int x, final int y)
    {

        if (x >= 49 && x <= 66 && y >= 10 && y <= 74) { return brewer.getTe().getLeftTank(); }

        if (x >= 109 && x <= 126 && y >= 10 && y <= 74) { return brewer.getTe().getRightTank(); }

        return null;
    }

    @Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
    }

    @Override
    public void initGui()
    {
        xSize = 176;
        ySize = 166;

        super.initGui();
    }
}
