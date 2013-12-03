package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.teammetallurgy.agriculture.gui.FluidRender;

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
		
		FluidStack fluid = tileentity.getRightTank().getFluid();
//		int color = tileentity.getRightTank().
		if(fluid == null || fluid.amount <= 0) 
		{
		    return;
		}
		
		int[] displayList = FluidRender.getFluidDisplayLists(fluid, tileentity.worldObj, false);
		if (displayList == null)
		{
		    return;
		}
        
		GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        bindTexture(FluidRender.getFluidSheet(fluid));
//        float red = (float) (color >> 16 & 255) / 255.0F;
//        float green = (float) (color >> 8 & 255) / 255.0F;
//        float blue = (float) (color & 255) / 255.0F;
//        GL11.glColor4f(red, green, blue, 1.0F);
                
        GL11.glTranslatef((float) x + 0.125F, (float) y + 0.5F, (float) z + 0.125F);
        GL11.glScalef(0.75F, 0.999F, 0.75F);
        GL11.glTranslatef(0, -0.5F, 0);

        GL11.glCallList(displayList[(int) ((float) fluid.amount / (float) (tileentity.getRightTank().getCapacity()) * (FluidRender.DISPLAY_STAGES - 1))]);

        GL11.glPopAttrib();
        GL11.glPopMatrix();
	}

}
