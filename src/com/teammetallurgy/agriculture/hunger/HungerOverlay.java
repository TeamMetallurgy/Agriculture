package com.teammetallurgy.agriculture.hunger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HungerOverlay
{
    final int zLevel = 1000;

    final int u = 0, v = 0, width = 9, height = 9;

    private ResourceLocation texture = new ResourceLocation("agriculture:textures/gui/WellFedOverlay.png");
    private static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
   
    @ForgeSubscribe
    public void drawOverlay(RenderGameOverlayEvent.Pre event)
    {
        if (event.type == ElementType.FOOD)
        {
            Minecraft client = Minecraft.getMinecraft();
            
            client.renderEngine.bindTexture(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            int bars = (int) (9 * HungerSystem.getPercentage(client.thePlayer));
            //int bars = 4;
            
            for (int x = bars; x > 0 ; x--)
            {
                drawTexturedModalRect(event.resolution.getScaledWidth() / 2 + width + (-x * 8) + (9 * width), event.resolution.getScaledHeight() - 39, u, v, width, height);
            }
            client.getTextureManager().bindTexture(icons);
        }
    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        float f = 1/9F;
        float f1 = 1/9F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

}
