package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockBrewer extends BaseMachineBlock
{
	public BlockBrewer(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xFace, float yFace, float zFace)
	{

		if (player.isSneaking())
		{
			return false;
		}

		// (>_>) //
		if (!world.isRemote)
		{
			int blockMetadata = world.getBlockMetadata(x, y, z);
			final int front = blockMetadata % 2;

			if (side == 1) 
			{
				player.openGui(Agriculture.instance, GUIIds.BREWER, world, x, y, z);
				return true;
			}

			if (side == blockMetadata)
			{
				/*
				player.openGui(Agriculture.instance, GUIIds.CABINET, world, x, y, z);
				return true;
				*/
			}

			return true;
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityBrewer();
	}
}
