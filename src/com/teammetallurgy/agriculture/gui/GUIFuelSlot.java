package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GUIFuelSlot extends GuiContainer
{

	private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Fuel.png");
	private final ContainerFuel containerFuel;

	public GUIFuelSlot(ContainerFuel containerFuel)
	{
		super(containerFuel);
		this.containerFuel = containerFuel;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int fuelRemaining = 80 - containerFuel.getTe().getRemainingFuelLevel();
		if (fuelRemaining > 80)
		{
			fuelRemaining = 80;
		}
		if (fuelRemaining < 0)
		{
			fuelRemaining = 0;
		}
		// fuelRemaining = 0;

		final float scale = fuelRemaining / 80f;
		drawTexturedModalRect(guiLeft + 80, guiTop + 22 + (int) (scale * 15), 180, (int) (4 + 15 * scale), 15, 15 - (int) (scale * 15));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void initGui()
	{
		xSize = 177;
		ySize = 167;

		super.initGui();
	}

}
