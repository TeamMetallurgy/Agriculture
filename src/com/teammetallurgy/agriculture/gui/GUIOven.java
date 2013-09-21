package com.teammetallurgy.agriculture.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIOven extends GuiContainer
{

	private ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Oven.png");
	private float sliderPosition = 0;
	private boolean isDragging = false;
	private ContainerOven oven;
	

	public GUIOven(ContainerOven containerOven)
	{
		super(containerOven);
		this.oven = containerOven;
		
		sliderPosition = 67 - (67 * (oven.getOven().getMaxTemp() / 1000f));
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
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		
		TileEntityOven oven = this.oven.getOven();

		double d = oven.getTemp() / 1000d;
		int temperature = (int) (d * 71f);
		
		//Temperature gauge
		drawTexturedModalRect(guiLeft + 30, guiTop + 8 + 70 - temperature, 195, 13, 18, temperature);
		
		//ScrollBar
		drawTexturedModalRect(guiLeft + 27, (int) (guiTop + 5 + sliderPosition), 180, 4, 14, 8);
		
		//TO BE CONTINUED
		if(this.oven.getOven().isCooking() || true) {
			int fuelRemaining = 80 - this.oven.getOven().getFuelRemaining();
			if (fuelRemaining > 80) 
			{
				fuelRemaining = 80;
			}
			if (fuelRemaining < 0)
			{
				fuelRemaining = 0;
			}
			//fuelRemaining = 0;
			
			float scale = fuelRemaining/80f;
			drawTexturedModalRect(guiLeft + 7, guiTop + 16 + (int)(scale * 15), 178, (int) (88 + 15 * scale), 15, 15 - (int)(scale * 15));
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		

	}

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();

		int mouseX = Mouse.getEventX() * width / mc.displayWidth - guiLeft;
		int mouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1 - guiTop;

		if (Mouse.isButtonDown(0) && mouseX >= 25 && mouseX <= 40 && mouseY >= 5 && mouseY <= 76)
		{
			handleDragging(mouseY - 5);
			isDragging = true;
		} else if (isDragging) {
			isDragging = false;
			
			float newMax = 1000 * ((67 - sliderPosition) / 67f);
			if(oven.getOven().getMaxTemp() != newMax)
			{
				oven.getOven().setMaxTemp((int) newMax);
				oven.getOven().sendPacket();
			}
		}
	}

	private void handleDragging(int mouseY)
	{
		sliderPosition = mouseY;
		
		if(sliderPosition <= 0) 
			sliderPosition = 0;
		
		if (sliderPosition >= 67) 
			sliderPosition = 67;
	}
}
