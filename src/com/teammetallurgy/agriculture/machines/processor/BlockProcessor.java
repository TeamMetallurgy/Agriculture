package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockProcessor extends BaseMachineBlock
{	
	public BlockProcessor(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityProcessor();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
	{

		if(player.isSneaking()) {
			return false;
		}
		
		if(!world.isRemote)
		{
			if(side == 1 || yOffset > 0.76) {
				player.openGui(Agriculture.instance, GUIIds.PROCESSOR, world, x, y, z);
				return true;
			}
			
			return true;
		}
		
		return true;
		
	}
}
