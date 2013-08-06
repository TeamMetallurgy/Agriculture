package com.teammetallurgy.agriculture;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SubItemSeed extends SubItemFood
{
	Block plant;

	public SubItemSeed(int id, int damage, int amount)
	{
		super(id, damage, amount);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		System.out.println("test");
		if (par7 != 1)
		{
			return false;
		} else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
		{
			final int i1 = par3World.getBlockId(par4, par5, par6);
			final Block soil = Block.blocksList[i1];

			System.out.println(plant);
			if (soil != null && plant.canPlaceBlockAt(par3World, par4, par5 + 1, par6) && par3World.isAirBlock(par4, par5 + 1, par6))
			{
				par3World.setBlock(par4, par5 + 1, par6, plant.blockID);
				--par1ItemStack.stackSize;
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public SubItemSeed setPlantBlock(Block block)
	{
		plant = block;
		return this;
	}
}
