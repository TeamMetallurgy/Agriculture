package com.teammetallurgy.agriculture.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
		
		
		float scaledProcess = brewer.getTe().getProcessScaled();
		
		
		drawTexturedModalRect(guiLeft + 66, guiTop + 16, 180, 109, (int) (43 * scaledProcess), 9);
		drawTexturedModalRect(guiLeft + 79, guiTop + 12, 79, 12, 17, 17);
		
		displayLiquid(guiLeft, guiTop, 49, 10, (int)  (scaledLeft * 64), leftTank.getFluid());
		displayLiquid(guiLeft, guiTop, 109, 10, (int) (scaledRight * 64), rightTank.getFluid());


		
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		
		if (this.isShiftKeyDown())
			drawTankInfo(Mouse.getX() * width / mc.displayWidth - guiLeft, height - Mouse.getY() * height / mc.displayHeight - 1 - guiTop);

	}

	private void displayLiquid(int guiLeft, int guiTop, int i, int j, int scaledRight, FluidStack liquid)
	{
		if (liquid == null)
			return;

		int start = 0;

		Icon liquidIcon = null;
		Fluid fluid = liquid.getFluid();

		if (fluid != null && fluid.getStillIcon() != null)
		{
			liquidIcon = fluid.getStillIcon();
		}
		mc.renderEngine.func_110577_a(TextureMap.field_110575_b);

		if (liquidIcon != null)
		{
			while (true)
			{
				int x;

				if (scaledRight > 16)
				{
					x = 16;
					scaledRight -= 16;
				} else
				{
					x = scaledRight;
					scaledRight = 0;
				}
				drawTexturedModelRectFromIcon(guiLeft + i + 1, guiTop + 74 - x - start, liquidIcon, 16, 16 - (16 - x));
				start = start + 16;

				if (x == 0 || scaledRight == 0)
					break;
			}
		}
		//mc.renderEngine.func_110577_a(texture);
		//drawTexturedModalRect(guiLeft + i, guiTop + j, 178, 3, 17, 64);
	}

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();

	}

	public void drawTankInfo(int x, int y)
	{
		FluidTank tank = getTankAtCoord(x, y);

		if (tank == null)
			return;

		FluidStack fluidInfo = getFluidInfo(tank);

		if (fluidInfo == null)
			return;

		String fluidName = FluidRegistry.getFluidName(fluidInfo);
		int amount = fluidInfo.amount;

		List<String> ret = Arrays.asList(new String[]
		{ "Name: " + fluidName, "Amount: " + amount + "mB" });

		if (x > xSize / 2)
		{
			x += guiLeft - 30;
		}

		drawHoveringText(ret, x + guiLeft / 4, y + guiTop, fontRenderer);

	}

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

	public FluidStack getFluidInfo(FluidTank tank)
	{
		if (tank == null || tank.getFluid() == null)
			return null;

		return tank.getFluid().copy();
	}
}
