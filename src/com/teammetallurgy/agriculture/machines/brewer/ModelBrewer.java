package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;

import com.teammetallurgy.agriculture.ModelSimpleBox;

public class ModelBrewer extends ModelBase
{
	// fields
	ModelRenderer base;
	ModelRenderer base2;
	ModelRenderer top;
	ModelRenderer nozzle;
	ModelRenderer tophatch;
	
	ModelSimpleBox liquid;

	public ModelBrewer()
	{
		textureWidth = 128;
		textureHeight = 64;

		base = new ModelRenderer(this, 0, 36);
		base.addBox(-8F, -8F, -8F, 16, 12, 15);
		base.setRotationPoint(0F, 20F, 1F);
		base.setTextureSize(128, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		top = new ModelRenderer(this, 0, 12);
		top.addBox(-8F, -2F, -8F, 16, 3, 16);
		top.setRotationPoint(0F, 11F, 0F);
		top.setTextureSize(128, 64);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		nozzle = new ModelRenderer(this, 0, 0);
		nozzle.addBox(-1F, 0F, -1F, 2, 3, 1);
		nozzle.setRotationPoint(0F, 21F, -7F);
		nozzle.setTextureSize(128, 64);
		nozzle.mirror = true;
		setRotation(nozzle, 0F, 0F, 0F);
		tophatch = new ModelRenderer(this, 14, 0);
		tophatch.addBox(-5F, -1F, -10F, 10, 1, 10);
		tophatch.setRotationPoint(0F, 9F, 5F);
		tophatch.setTextureSize(128, 64);
		tophatch.mirror = true;
		setRotation(tophatch, 0F, 0F, 0F);
	}
	
	public void setLiquidLevel(float level)
	{
		if(level > 0)
		{
			int height = (int) (level * 12);
			liquid = new ModelSimpleBox(128, 64, 68, 29, -7, 23 - height, -6, 14, height, 13, 0);
		}
		else
		{
			liquid = null;
		}
	}

	public void renderAll()
	{
		base.render(1/16f);
		top.render(1/16f);
		nozzle.render(1/16f);
		tophatch.render(1/16f);
		
		if(liquid != null)
			liquid.render(Tessellator.instance, 1/16f);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		top.render(f5);
		nozzle.render(f5);
		tophatch.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
