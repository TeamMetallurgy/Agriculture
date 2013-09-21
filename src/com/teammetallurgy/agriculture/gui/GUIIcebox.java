package com.teammetallurgy.agriculture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;

public class GUIIcebox extends GuiContainer
{

	private ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Icebox.png");
	private ContainerIcebox oven;
	

	public GUIIcebox(ContainerIcebox containerOven)
	{
		super(containerOven);
		this.oven = containerOven;
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
		
		TileEntityIcebox oven = this.oven.getOven();

		double d = oven.getTemp() / 1000d;
		d = d >= 1 ? 1 : d;

		int temperature = (int) (d * 70f);
		
		//Temperature gauge
		drawTexturedModalRect(guiLeft + 39, guiTop + 7 + 70 - temperature, 179, 2, 18, temperature);
		
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
}
