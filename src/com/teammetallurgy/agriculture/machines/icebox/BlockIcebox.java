package com.teammetallurgy.agriculture.machines.icebox;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

public class BlockIcebox extends BaseMachineBlock
{
	public BlockIcebox(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityIcebox();
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
			if (side == 1 || yFace > 0.76f) // (<_<) /// :P Deal with it keith
											// :P
			{
				player.openGui(Agriculture.instance, GUIIds.ICEBOX, world, x, y, z);
				return true;
			}
			if (side == blockMetadata)
			{
				player.openGui(Agriculture.instance, GUIIds.ICEBOX, world, x, y, z);
				return true;
			}

			return true;
		}
		return true;
	}
}
