package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

public class GUIFrier extends GuiLiquids
{
	private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Frier.png");
	private final ContainerFrier counter;

	public GUIFrier(ContainerFrier containerFrier)
	{
		super(containerFrier);
		counter = containerFrier;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.func_110577_a(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		final FluidTank tank = counter.getTE().getTank();

		final float scaled = tank.getFluidAmount() / (float) tank.getCapacity();

		displayLiquid(guiLeft, guiTop, 43, 10, (int) (scaled * 64), tank.getFluid());
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
