package com.teammetallurgy.agriculture.machines.frier;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFrier extends ModelBase
{
	// fields
	ModelRenderer base;
	ModelRenderer frontwall;
	ModelRenderer rightwall;
	ModelRenderer backwall;
	ModelRenderer leftwall;
	ModelRenderer lid;

	public ModelFrier()
	{
		textureWidth = 128;
		textureHeight = 64;

		base = new ModelRenderer(this, 0, 37);
		base.addBox(-8F, -6F, -8F, 16, 11, 16);
		base.setRotationPoint(0F, 19F, 0F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		frontwall = new ModelRenderer(this, 0, 28);
		frontwall.addBox(-8F, -2F, 0F, 16, 4, 4);
		frontwall.setRotationPoint(0F, 11F, -8F);
		frontwall.setTextureSize(64, 32);
		frontwall.mirror = true;
		setRotation(frontwall, 0F, 0F, 0F);
		rightwall = new ModelRenderer(this, 70, 0);
		rightwall.addBox(-8F, -2F, 0F, 4, 4, 8);
		rightwall.setRotationPoint(0F, 11F, -4F);
		rightwall.setTextureSize(64, 32);
		rightwall.mirror = true;
		setRotation(rightwall, 0F, 0F, 0F);
		backwall = new ModelRenderer(this, 0, 19);
		backwall.addBox(-8F, -2F, 0F, 16, 4, 4);
		backwall.setRotationPoint(0F, 11F, 4F);
		backwall.setTextureSize(64, 32);
		backwall.mirror = true;
		setRotation(backwall, 0F, 0F, 0F);
		leftwall = new ModelRenderer(this, 44, 0);
		leftwall.addBox(-8F, -2F, 0F, 4, 4, 8);
		leftwall.setRotationPoint(12F, 11F, -4F);
		leftwall.setTextureSize(64, 32);
		leftwall.mirror = true;
		setRotation(leftwall, 0F, 0F, 0F);
		lid = new ModelRenderer(this, 0, 0);
		lid.addBox(-4F, -1F, -8F, 8, 1, 8);
		lid.setRotationPoint(0F, 9F, 4F);
		lid.setTextureSize(64, 32);
		lid.mirror = true;
		setRotation(lid, -1.570796F, 0F, 0F);
	}
	
	public void renderAll()
	{
		base.render(1/16f);
		frontwall.render(1/16f);
		rightwall.render(1/16f);
		backwall.render(1/16f);
		leftwall.render(1/16f);
		lid.render(1/16f);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		frontwall.render(f5);
		rightwall.render(f5);
		backwall.render(f5);
		leftwall.render(f5);
		lid.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setDoorAngle(float angle)
	{
		lid.rotateAngleX = 1.5f * -angle;
		
	}
}
