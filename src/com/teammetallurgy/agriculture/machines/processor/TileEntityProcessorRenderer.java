package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityProcessorRenderer extends TileEntitySpecialRenderer
{
    private static int[] rotations = { 0, 0, 180, 0, 90, -90 };
    private final ModelProcessor model = new ModelProcessor();

    private final ResourceLocation texture = new ResourceLocation("agriculture:textures/blocks/Processor.png");

    @Override
    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float unknown)
    {
        this.renderTileEntityAt((TileEntityProcessor) tileentity, x, y, z, unknown);
    }

    private void renderTileEntityAt(final TileEntityProcessor tileentity, final double x, final double y, final double z, final float unknown)
    {
        int direction = 0;
        float yOffset = 0;
        if (tileentity.worldObj != null)
        {
            direction = tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        }
        else
        {
            yOffset = -0.1f;
        }

        final int rotation = TileEntityProcessorRenderer.rotations[direction];

        Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f + yOffset, (float) z + 0.5f);
        GL11.glScalef(1F, -1F, -1F);

        GL11.glRotatef(rotation, 0F, 1F, 0F);

        this.model.renderAll();

        GL11.glPopMatrix();
    }
}