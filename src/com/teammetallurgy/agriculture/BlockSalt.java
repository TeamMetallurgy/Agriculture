package com.teammetallurgy.agriculture;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockSalt extends Block
{

	public BlockSalt(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}
	
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++)
        {
            ret.add(AgricultureItems.salt.getItemStack());
        }
        return ret;
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return AgricultureItems.salt.itemID;
    }

    public int quantityDropped(Random par1Random)
    {
        return (int) (2 + par1Random.nextDouble() * 3);
    }
}
