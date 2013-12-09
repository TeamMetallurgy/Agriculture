package com.teammetallurgy.agriculture.gui;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
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
	
	
	protected class FluidWidget
	{
	    private FluidTank tank;
	    private int x, y, u, v, w, h;


        public FluidWidget(FluidTank tank, int x, int y, int u, int v, int w, int h)
        {
            this.tank = tank;
            this.x = x;
            this.y = y;
            this.u = u;
            this.v = v;
            this.w = w;
            this.h = h;
        }
	    
	    
        public void drawLiquid(GuiLiquids gui, int guiX, int guiY, ResourceLocation texture) {
            if (tank == null)
                return;
            FluidStack fluidStack = tank.getFluid();
            if (fluidStack == null || fluidStack.amount <= 0 || fluidStack.getFluid() == null)
                return;
    
            Icon liquidIcon = FluidRender.getFluidTexture(fluidStack, false);
    
            if (liquidIcon == null)
                return;
    
            float scale = Math.min(fluidStack.amount, tank.getCapacity()) / (float) tank.getCapacity();
    
            gui.bindTexture(FluidRender.getFluidSheet(fluidStack));
    
            for (int col = 0; col < w / 16; col++) {
                for (int row = 0; row < h / 16; row++) {
                    gui.drawTexturedModelRectFromIcon(guiX + x + col * 16, guiY + y + row * 16, liquidIcon, 16, 16);
                }
            }
    
            gui.bindTexture(texture);
    
            gui.drawTexturedModalRect(guiX + x, guiY + y - 1, x, y - 1, w, h - (int) Math.floor(h * scale) + 1);
            gui.drawTexturedModalRect(guiX + x, guiY + y, u, v, w, h);
        }

	}

    @Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{

	}

	public void bindTexture(ResourceLocation texture)
    {
	    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
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
