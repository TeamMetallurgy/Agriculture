package com.teammetallurgy.agriculture;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;

public class TexturedQuadIcon extends TexturedQuad
{
	private boolean invertNormal;

	public TexturedQuadIcon(PositionTextureVertex[] par1ArrayOfPositionTextureVertex, int par2, int par3, int par4, int par5, float par6, float par7)
	{
		super(par1ArrayOfPositionTextureVertex, par2, par3, par4, par5, par6, par7);
	}

	public void draw(Tessellator par1Tessellator, float par2, Icon icon)
	{
		final Vec3 vec3 = vertexPositions[1].vector3D.subtract(vertexPositions[0].vector3D);
		final Vec3 vec31 = vertexPositions[1].vector3D.subtract(vertexPositions[2].vector3D);
		final Vec3 vec32 = vec31.crossProduct(vec3).normalize();
		par1Tessellator.startDrawingQuads();

		if (invertNormal)
		{
			par1Tessellator.setNormal(-((float) vec32.xCoord), -((float) vec32.yCoord), -((float) vec32.zCoord));
		} else
		{
			par1Tessellator.setNormal((float) vec32.xCoord, (float) vec32.yCoord, (float) vec32.zCoord);
		}

		for (int i = 0; i < 4; ++i)
		{
			final PositionTextureVertex positiontexturevertex = vertexPositions[i];
			par1Tessellator.addVertexWithUV((float) positiontexturevertex.vector3D.xCoord * par2, (float) positiontexturevertex.vector3D.yCoord * par2, (float) positiontexturevertex.vector3D.zCoord * par2, icon.getMinU(), icon.getMaxV());
		}

		par1Tessellator.draw();
	}
}
