package com.teammetallurgy.agriculture.crops;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.ArrayList;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockStrawberry extends BlockFlower 
{
	private float growthRate = 0.5f;
	private Icon[] iconArray;

    public BlockStrawberry(int par1)
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
    
    @Override 
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getBlockDropped(world, x, y, z, metadata, fortune);
        ret.add(AgricultureItems.strawberry.getItemStack());
        
        if (metadata >= 6)
        {
            for (int n = 0; n < 3 + fortune; n++)
            {
                if (world.rand.nextInt(15) <= metadata)
                {
                    ret.add(AgricultureItems.strawberry.getItemStack());
                }
            }
        }

        return ret;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
    	
//    	float temp = growthRate;
//    	this.growthRate = 100;
//    	updateTick(world, x, y, z, new Random());
//    	this.growthRate = temp;
//    	
    	
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
            this.iconArray[i] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }
}
