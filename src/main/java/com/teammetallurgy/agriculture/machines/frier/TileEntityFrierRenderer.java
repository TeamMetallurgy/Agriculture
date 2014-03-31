package com.teammetallurgy.agriculture.machines.frier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityFrierRenderer extends TileEntitySpecialRenderer {
    private static int[] rotations = { 0, 0, 180, 0, 90, -90 };
    private final ModelFrier model = new ModelFrier();

    private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/blocks/Frier.png");

    @Override
    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float unknown)
    {
        this.renderTileEntityAt((TileEntityFrier) tileentity, x, y, z, unknown);
    }

    public void renderTileEntityAt(final TileEntityFrier tileentity, final double x, final double y, final double z, final float partialTickTime)
    {
        int direction = 0;
        float yOffset = 0;
        if (tileentity.getWorldObj() != null)
        {
            direction = tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        }
        else
        {
            yOffset = -0.1f;
        }

        final int rotation = TileEntityFrierRenderer.rotations[direction];

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f + yOffset, (float) z + 0.5f);
        GL11.glScalef(1F, -1F, -1F);

        GL11.glRotatef(rotation, 0F, 1F, 0F);

        final float angle = tileentity.prevLeftDoorAngle + (tileentity.leftDoorAngle - tileentity.prevLeftDoorAngle) * partialTickTime;
        model.setDoorAngle(angle);
        model.renderAll();

        GL11.glPopMatrix();
    }

}
