package com.teammetallurgy.agriculture.crops;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrop extends BlockFlower {

    private static final int FULLYGROWN = 6;
	private ItemStack drop;
    private static final float growthRate = 0.5f;

    private IIcon[] iconArray;

    public BlockCrop(final int par1, final ItemStack drop)
    {
        super(par1);
        this.drop = drop;
    }

    public BlockCrop(final int par1, final Material par2Material, final ItemStack drop)
    {
        super(par1);
        this.drop = drop;
    }

    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4)
    {  
        return par1World.getBlock(par2, par3 - 1, par4) == Blocks.farmland;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
    {
    	return true;
    }
    @Override
    public int damageDropped(final int par1)
    {
        return drop.getItemDamage();
    }

    public boolean fertilize(final World par1World, final int par2, final int par3, final int par4)
    {
        final int meta = par1World.getBlockMetadata(par2, par3, par4);

        if (meta >= FULLYGROWN) { return false; }

        int l = meta + MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);

        if (l >= FULLYGROWN)
        {
            l = FULLYGROWN;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int par1, int par2)
    {
        if (par2 < 0 || par2 > 7)
        {
            par2 = 7;
        }

        return iconArray[par2];
    }

    @Override
    public Item getItemDropped(final int par1, final Random par2Random, final int par3)
    {
        return drop.getItem();
    }

    @Override
    public int quantityDropped(final Random par1Random)
    {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister)
    {
        iconArray = new IIcon[8];

        for (int i = 0; i < iconArray.length; ++i)
        {
            iconArray[i] = par1IconRegister.registerIcon(getTextureName() + "_stage_" + i);
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
    	
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	ItemStack equippedItem = player.getCurrentEquippedItem();
    	
    	if(equippedItem != null && equippedItem.getItem().equals(Items.dye))
    	{
    		return false;
    	}
    	
    	if (meta > 0 && !player.isSneaking())
    	{
    		if(world.isRemote)
    		{
    			return true;
    		}
    		
    		world.setBlockMetadataWithNotify(x, y, z, --meta, 2);
    		ArrayList<ItemStack> dropped = getDrops(world, x, y, z, meta, 0);
    		
    		for(ItemStack stack : dropped)
    		{
    			dropBlockAsItem(world, x, y, z, stack);
    		}
    		
    		return true;
    	}
    	
    	return false;
    }
    
    public BlockCrop setDrop(ItemStack drop)
    {
    	this.drop = drop.copy();
    	return this;
    }

    @Override
    public void updateTick(final World world, final int x, final int y, final int z, final Random random)
    {
        super.updateTick(world, x, y, z, random);

        if (world.getBlockLightValue(x, y + 1, z) >= 9)
        {
            int l = world.getBlockMetadata(x, y, z);
            if (l < FULLYGROWN)
            {
                if (random.nextInt((int) (25.0F / growthRate) + 1) == 0)
                {
                    ++l;
                    world.setBlockMetadataWithNotify(x, y, z, l, 2);
                }
            }
        }
    }

}