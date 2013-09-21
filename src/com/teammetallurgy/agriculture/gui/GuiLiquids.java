package com.teammetallurgy.agriculture.gui;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.input.Mouse;

public abstract class GuiLiquids extends GuiContainer
{

	public GuiLiquids(Container par1Container)
	{
		super(par1Container);
	}

	protected void displayLiquid(int guiLeft, int guiTop, int i, int j, int scaledRight, FluidStack liquid)
	{
		if (liquid == null)
		{
			return;
		}

		int start = 0;

		Icon liquidIcon = null;
		final Fluid fluid = liquid.getFluid();

		if (fluid != null && fluid.getStillIcon() != null)
		{
			liquidIcon = fluid.getStillIcon();
		}
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

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
				{
					break;
				}
			}
		}
		// mc.renderEngine.bindTexture(texture);
		// drawTexturedModalRect(guiLeft + i, guiTop + j, 178, 3, 17, 64);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{

	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);

		if (GuiScreen.isShiftKeyDown())
		{
			drawTankInfo(Mouse.getX() * width / mc.displayWidth - guiLeft, height - Mouse.getY() * height / mc.displayHeight - 1 - guiTop);
		}
	}

	public void drawTankInfo(int x, int y)
	{
		final FluidTank tank = getTankAtCoord(x, y);

		if (tank == null)
		{
			return;
		}

		final FluidStack fluidInfo = getFluidInfo(tank);

		if (fluidInfo == null)
		{
			return;
		}

		final String fluidName = FluidRegistry.getFluidName(fluidInfo);
		final int amount = fluidInfo.amount;

		final List<String> ret = Arrays.asList(new String[]
		{ "Name: " + fluidName, "Amount: " + amount + "mB" });

		if (x > xSize / 2)
		{
			x += guiLeft - 30;
		}

		drawHoveringText(ret, x + guiLeft / 4, y + guiTop, fontRenderer);

	}

	public FluidStack getFluidInfo(FluidTank tank)
	{
		if (tank == null || tank.getFluid() == null)
		{
			return null;
		}

		return tank.getFluid().copy();
	}

	public abstract FluidTank getTankAtCoord(int x, int y);

}
