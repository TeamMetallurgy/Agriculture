package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GUICounter extends GuiContainer
{
	private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Counter.png");

	public GUICounter(ContainerCounter containerCounter)
	{
		super(containerCounter);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
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
