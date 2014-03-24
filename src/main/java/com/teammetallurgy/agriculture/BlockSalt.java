package com.teammetallurgy.agriculture;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockSalt extends Block {

    public BlockSalt(final int par1, final Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(final World world, final int x, final int y, final int z, final int metadata, final int fortune)
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        final int count = this.quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++)
        {
            ret.add(AgricultureItems.salt.getItemStack());
        }
        return ret;
    }

    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3)
    {
        return AgricultureItems.salt.itemID;
    }

    @Override
    public int quantityDropped(final Random par1Random)
    {
        return (int) (2 + par1Random.nextDouble() * 3);
    }
}
