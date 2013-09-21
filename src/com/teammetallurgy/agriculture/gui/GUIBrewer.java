package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

public class GUIBrewer extends GuiLiquids
{
	private ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Brewer.png");
	ContainerBrewer brewer;

	public GUIBrewer(ContainerBrewer containerBrewer)
	{
		super(containerBrewer);
		this.brewer = containerBrewer;
	}

	@Override
	public void initGui()
	{
		this.xSize = 176;
		this.ySize = 166;

		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		FluidTank leftTank = brewer.getTe().getLeftTank();
		FluidTank rightTank = brewer.getTe().getRightTank();

		float scaledLeft = leftTank.getFluidAmount() / (float) leftTank.getCapacity();
		float scaledRight = rightTank.getFluidAmount() / (float) rightTank.getCapacity();

		float scaledProcess = brewer.getTe().getProcessScaled();

		drawTexturedModalRect(guiLeft + 66, guiTop + 16, 180, 109, (int) (43 * scaledProcess), 9);
		drawTexturedModalRect(guiLeft + 79, guiTop + 12, 79, 12, 17, 17);

		int fuelRemaining = 80 - this.brewer.getTe().getRemainingFuelLevel();
		if (fuelRemaining > 80)
		{
			fuelRemaining = 80;
		}
		if (fuelRemaining < 0)
		{
			fuelRemaining = 0;
		}
		float scale = fuelRemaining / 80f;

		drawTexturedModalRect(guiLeft + 81, guiTop + 43 + (int) (scale * 15), 179, (int) (82 + 15 * scale), 15, 15 - (int) (scale * 15));

		displayLiquid(guiLeft, guiTop, 49, 10, (int) (scaledLeft * 64), leftTank.getFluid());
		displayLiquid(guiLeft, guiTop, 109, 10, (int) (scaledRight * 64), rightTank.getFluid());

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();
	}

	@Override
	public FluidTank getTankAtCoord(int x, int y)
	{

		if (x >= 49 && x <= 66 && y >= 10 && y <= 74)
		{
			return brewer.getTe().getLeftTank();
		}

		if (x >= 109 && x <= 126 && y >= 10 && y <= 74)
		{
			return brewer.getTe().getRightTank();
		}

		return null;
	}
}
