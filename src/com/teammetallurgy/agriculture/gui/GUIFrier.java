package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.agriculture.gui.GuiLiquids.FluidWidget;

public class GUIFrier extends GuiLiquids
{
	public ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Frier.png");
	private final ContainerFrier counter;
    private FluidWidget fluidWidget;

	public GUIFrier(ContainerFrier containerFrier)
	{
		super(containerFrier);
		counter = containerFrier;
		
		fluidWidget = new GuiLiquids.FluidWidget(counter.getTE().getTank(), 44, 10, 178, 3, 19, 65);
        
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		fluidWidget.drawLiquid(this, guiLeft, guiTop, texture);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public FluidTank getTankAtCoord(int x, int y)
	{
		if (x >= 43 && x <= 60 && y >= 10 && y <= 74)
		{
			return counter.getTE().getTank();
		}

		return null;
	}

	@Override
	public void initGui()
	{
		xSize = 177;
		ySize = 167;

		super.initGui();
	}
}
