package com.teammetallurgy.agriculture.hunger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HungerOverlay
{
	final int zLevel = 1000;

	final int u = 0, v = 0, width = 9, height = 9;

	private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/gui/WellFedOverlay.png");
	private static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");	
	
	@ForgeSubscribe
	public void drawOverlay(RenderGameOverlayEvent.Post event)
	{
	    if(!Loader.isModLoaded("tukmc_Vz"))
	    {
    		if (event.type == ElementType.FOOD)
    		{
    			final Minecraft client = Minecraft.getMinecraft();
    
    			client.renderEngine.bindTexture(texture);
    			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
    			final int bars = (int) (9 * HungerSystem.getPercentage(client.thePlayer));
    			// int bars = 4;
    
    			for (int x = bars; x > 0; x--)
    			{
    				drawTexturedModalRect(event.resolution.getScaledWidth() / 2 + width + -x * 8 + 9 * width, event.resolution.getScaledHeight() - 39, u, v, width, height);
    			}
    			client.renderEngine.bindTexture(icons);
    		}
	    }
	}

	public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
	{
		final float f = 1 / 9F;
		final float f1 = 1 / 9F;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, (u + 0) * f, (v + height) * f1);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) * f, (v + height) * f1);
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, (u + width) * f, (v + 0) * f1);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (u + 0) * f, (v + 0) * f1);
		tessellator.draw();
	}

}
