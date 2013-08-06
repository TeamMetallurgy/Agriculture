package com.teammetallurgy.agriculture.machines.frier;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

public class BlockFrier extends BaseMachineBlock
{

	public BlockFrier(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityFrier();
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
			final int blockMetadata = world.getBlockMetadata(x, y, z);
			if (side == 1)// (<_<) /// :P Deal with it keith :P
			{
				player.openGui(Agriculture.instance, GUIIds.FRIER, world, x, y, z);
				return true;
			}
			if (side == blockMetadata)
			{
				player.openGui(Agriculture.instance, GUIIds.FUEL, world, x, y, z);
				return true;
			}

			return true;
		}
		return true;
	}
}
