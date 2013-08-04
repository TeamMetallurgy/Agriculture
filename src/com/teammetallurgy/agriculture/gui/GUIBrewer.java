package com.teammetallurgy.agriculture.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class GUIBrewer extends GuiContainer
{
	private ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/Brewer.png");
	private ContainerBrewer brewer;

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
		Minecraft.getMinecraft().renderEngine.func_110577_a(texture);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		FluidTank leftTank = brewer.getTe().getLeftTank();
		FluidTank rightTank = brewer.getTe().getRightTank();

		float scaledLeft = leftTank.getFluidAmount() / (float) leftTank.getCapacity();
		float scaledRight = rightTank.getFluidAmount() / (float) rightTank.getCapacity();
		
		if (leftTank.getFluid() != null)
		{
			FluidStack fluidStack = leftTank.getFluid();
			int color = fluidStack.getFluid().getColor(fluidStack);

			if (color == 16777215)
			{
				String name = fluidStack.getFluid().getName();
				
				if(name.equals("water")) {
					color = 0x00ff00;
				}
			}

			float red = (float) (color >> 16 & 255) / 255.0F;
			float blue = (float) (color >> 8 & 255) / 255.0F;
			float green = (float) (color & 255) / 255.0F;

			GL11.glColor4f(red, green, blue, 0.15f);
		}

		drawTexturedModalRect(guiLeft + 49, guiTop + 10 + 64 - (int) (scaledLeft * 64), 178 , 3+ 64 - (int) (scaledLeft * 64), 17, (int) (64 * scaledLeft));
		
		if (rightTank.getFluid() != null)
		{
			FluidStack fluidStack = rightTank.getFluid();
			int color = fluidStack.getFluid().getColor(fluidStack);

			if (color == 16777215)
			{
				String name = fluidStack.getFluid().getName();
				
				if(name.equals("water")) {
					color = 0x00ff00;
				}
			}

			float red = (float) (color >> 16 & 255) / 255.0F;
			float blue = (float) (color >> 8 & 255) / 255.0F;
			float green = (float) (color & 255) / 255.0F;

			GL11.glColor4f(red, green, blue, 0.15f);
		}
		drawTexturedModalRect(guiLeft + 109, guiTop + 10 + 64 - (int) (scaledRight * 64), 178 , 3+ 64 - (int) (scaledRight * 64), 17, (int) (64 * scaledRight));

		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
