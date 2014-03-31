package com.teammetallurgy.agriculture;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockSalt extends Block {

	@Deprecated
    public BlockSalt(final int par1, final Material par2Material)
    {
        this(par2Material);
    }

    public BlockSalt(Material material)
    {
    	super(material);
	}

    @Override
    public Item getItemDropped(final int par1, final Random par2Random, final int par3)
    {
        return AgricultureItems.salt.getItemStack().getItem();
    }

    @Override
    public int quantityDropped(final Random random)
    {
        return (int) (2 + random.nextDouble() * 3);
    }
}
