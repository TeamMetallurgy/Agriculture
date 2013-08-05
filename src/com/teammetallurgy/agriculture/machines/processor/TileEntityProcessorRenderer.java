package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.teammetallurgy.agriculture.machines.FuelMachineTileEntity;
import com.teammetallurgy.agriculture.machines.oven.ModelOven;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

public class TileEntityProcessorRenderer extends TileEntitySpecialRenderer
{
	private ModelProcessor model = new ModelProcessor();
	private ResourceLocation texture = new ResourceLocation("agriculture", "textures/blocks/Processor.png");
	
	private static int[] rotations = {0, 0, 180, 0, 90, -90};
	
	public void renderTileEntityAt(FuelMachineTileEntity tileentity, double x, double y, double z, float unknown)
	{
		int direction = 0;
		float yOffset = 0;
		if(tileentity.worldObj != null)
			direction = tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		else
			yOffset = -0.1f;
		
		int rotation = rotations[direction];
		
		
		Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);
		
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslatef((float)x + 0.5f, (float)y + 1.5f + yOffset, (float)z + 0.5f);
		GL11.glScalef(1F, -1F, -1F);
		
		GL11.glRotatef((float)rotation, 0F, 1F, 0F);
		
		model.renderAll();
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float unknown)
	{
		this.renderTileEntityAt((FuelMachineTileEntity)tileentity, x, y, z, unknown);
	}
	
	
}
