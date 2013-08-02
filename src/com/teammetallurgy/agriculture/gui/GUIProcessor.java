package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GUIProcessor extends GuiContainer
{

	private ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Processor.png");
	private ContainerProcessor processor;
	
	public GUIProcessor(ContainerProcessor processor)
	{
		super(processor);
		this.processor = processor;
	}
	
	@Override
	public void initGui()
	{
		this.xSize = 177;
		this.ySize = 167;
		
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		Minecraft.getMinecraft().renderEngine.func_110577_a(texture);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);		
		GL11.glEnable(GL11.GL_LIGHTING);
	}

}
