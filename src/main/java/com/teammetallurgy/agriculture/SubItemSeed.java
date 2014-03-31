package com.teammetallurgy.agriculture;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SubItemSeed extends SubItemFood {
    Block plant;

    public SubItemSeed(final int id, final int damage, final int amount)
    {
        super(id, damage, amount);
    }

    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
        {
            final Block soil = par3World.getBlock(par4, par5, par6);
            if (soil != null && plant.canPlaceBlockAt(par3World, par4, par5 + 1, par6) && par3World.isAirBlock(par4, par5 + 1, par6))
            {
                par3World.setBlock(par4, par5 + 1, par6, plant);
                --par1ItemStack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public SubItemSeed setPlantBlock(final Block block)
    {
        plant = block;
        return this;
    }
}
