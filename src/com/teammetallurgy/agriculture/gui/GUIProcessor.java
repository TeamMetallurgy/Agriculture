package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GUIProcessor extends GuiContainer
{

	private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Processor.png");
	private final ContainerProcessor processor;

	public GUIProcessor(ContainerProcessor processor)
	{
		super(processor);
		this.processor = processor;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		final float scale = processor.getProcessor().getCurrentItemBurnTime() / 20f;

		// drawTexturedModalRect(guiLeft + 76, guiTop + 36 + (int) (scale * 15),
		// 178, (int) (88 + 15 * scale), 15, 15 - (int) (scale * 15));
		drawTexturedModalRect(guiLeft + 77, guiTop + 37, 177, 11, (int) (21 * scale), 4);

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
