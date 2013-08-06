package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityBrewerRenderer extends TileEntitySpecialRenderer
{
	private final ModelBrewer model = new ModelBrewer();
	private final ResourceLocation texture = new ResourceLocation("agriculture", "textures/blocks/Brewer.png");

	private static int[] rotations =
	{ 0, 0, 180, 0, 90, -90 };

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float unknown)
	{
		this.renderTileEntityAt((TileEntityBrewer) tileentity, x, y, z, unknown);
	}

	public void renderTileEntityAt(TileEntityBrewer tileentity, double x, double y, double z, float partialTickTime)
	{
		int direction = 0;
		float yOffset = 0;
		if (tileentity.worldObj != null)
		{
			direction = tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		} else
		{
			yOffset = -0.1f;
		}

		final int rotation = rotations[direction];

		Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f + yOffset, (float) z + 0.5f);
		GL11.glScalef(1F, -1F, -1F);

		GL11.glRotatef(rotation, 0F, 1F, 0F);

		model.getLiquidIcon(tileentity.getRightTank().getFluid());

		model.setLiquidLevel(tileentity.getLiquidScaled());
		final float angle = tileentity.prevLeftDoorAngle + (tileentity.leftDoorAngle - tileentity.prevLeftDoorAngle) * partialTickTime;
		model.setDoorAngle(angle);

		model.setFlowing(tileentity.getAmountRightInput());

		model.renderAll();

		Minecraft.getMinecraft().func_110434_K().func_110577_a(TextureMap.field_110575_b);
		model.renderLiquids();

		GL11.glPopMatrix();
	}

}
