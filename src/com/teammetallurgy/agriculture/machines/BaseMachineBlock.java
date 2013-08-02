package com.teammetallurgy.agriculture.machines;

import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BaseMachineBlock extends BlockContainer
{
	protected int renderID;
	
	public BaseMachineBlock(int par1, Material par2Material)
	{
		super(par1, par2Material);
		renderID = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return renderID;
	}

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (stack.hasDisplayName())
        {
            ((TileEntityFurnace)world.getBlockTileEntity(x, y, z)).setGuiDisplayName(stack.getDisplayName());
        }
    }
	
	@Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }
    
    private void setDefaultDirection(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
            int blockIDLeft = world.getBlockId(x, y, z - 1);
            int blockIDRight = world.getBlockId(x, y, z + 1);
            int blockIDBack = world.getBlockId(x - 1, y, z);
            int blockIDFront = world.getBlockId(x + 1, y, z);
            byte direction = 3;

            if (Block.opaqueCubeLookup[blockIDLeft] && !Block.opaqueCubeLookup[blockIDRight])
            {
                direction = 3;
            }

            if (Block.opaqueCubeLookup[blockIDRight] && !Block.opaqueCubeLookup[blockIDLeft])
            {
                direction = 2;
            }

            if (Block.opaqueCubeLookup[blockIDBack] && !Block.opaqueCubeLookup[blockIDFront])
            {
                direction = 5;
            }

            if (Block.opaqueCubeLookup[blockIDFront] && !Block.opaqueCubeLookup[blockIDBack])
            {
                direction = 4;
            }
            
            world.setBlockMetadataWithNotify(x, y, z, direction, 2);

            /*
            TileEntity tileentity = world.getBlockTileEntity(x, y, z);
            if(tileentity instanceof BaseMachineTileEntity)
            {
            	System.out.println("direction " + direction);
            	((BaseMachineTileEntity) tileentity).setDirection(direction);
            }
            */
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return new BaseMachineTileEntity();
	}
}
