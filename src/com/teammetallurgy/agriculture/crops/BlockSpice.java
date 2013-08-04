package com.teammetallurgy.agriculture.crops;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.Random;

import com.teammetallurgy.agriculture.AgricultureItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockSpice extends BlockFlower
{
	private float growthRate = 1.0f;
	private Icon[] iconArray;

    public BlockSpice(int par1)
	{
		super(par1);
	}
    
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
    	int id = par1World.getBlockId(par2, par3-1, par4);
    	return id == Block.tilledField.blockID;
    }

    @Override
    public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
    {
    	return true;
    }

    public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer) 
    {
    	int meta = par1World.getBlockMetadata(x, y, z);
    	if(meta > 0)
    	{
    		this.dropBlockAsItem_do(par1World, x, y, z, AgricultureItems.cinnamon.getItemStack());
    		par1World.setBlock(x, y, z, blockID, meta - 1, 2);
    	}
    }    
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
    	/*
    	float temp = growthRate;
    	this.growthRate = 100;
    	updateTick(world, x, y, z, new Random());
    	this.growthRate = temp;
    	*/
    	
    	return false;
    }
    
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            int l = par1World.getBlockMetadata(par2, par3, par4);
            if (l < 6)
            {
                if (par5Random.nextInt((int)(25.0F / growthRate) + 1) == 0)
                {
                    ++l;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
        if (par2 < 0 || par2 > 7)
        {
            par2 = 7;
        }

        return this.iconArray[par2];
    }

    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[8];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = par1IconRegister.registerIcon(this.func_111023_E() + "_stage_" + i);
        }
    }
}
