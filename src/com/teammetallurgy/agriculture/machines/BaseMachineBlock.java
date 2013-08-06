package com.teammetallurgy.agriculture.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class BaseMachineBlock extends BlockContainer
{
	private static final Random random = new Random();

	protected int renderID;

	public BaseMachineBlock(int par1, Material par2Material)
	{
		super(par1, par2Material);
		renderID = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{

		final TileEntity tileEntity = par1World.getBlockTileEntity(par2, par3, par4);

		if (tileEntity != null)
		{

			if (tileEntity instanceof BaseMachineTileEntity)
			{
				breakEntityWithDrops(((BaseMachineTileEntity) tileEntity).getInventoryCounter(), tileEntity);
			}

			if (tileEntity instanceof TileEntityCounter)
			{
				breakEntityWithDrops(((TileEntityCounter) tileEntity).getCabinet(), tileEntity);
			}

			if (tileEntity instanceof TileEntityProcessor)
			{
				breakEntityWithDrops(((TileEntityProcessor) tileEntity).getInventory(), tileEntity);
				breakEntityWithDrops(((FuelMachineTileEntity) tileEntity).getFuelInventory(), tileEntity);
			}

			if (tileEntity instanceof TileEntityOven)
			{
				breakEntityWithDrops(((TileEntityOven) tileEntity).getInventoryOven(), tileEntity);
			}

			if (tileEntity instanceof TileEntityFrier)
			{
				breakEntityWithDrops(((TileEntityFrier) tileEntity).getInventory(), tileEntity);
			}

			if (tileEntity instanceof TileEntityBrewer)
			{
				breakEntityWithDrops(((TileEntityBrewer) tileEntity).getInventory(), tileEntity);
			}

			if (tileEntity instanceof TileEntityIcebox)
			{
				breakEntityWithDrops(((TileEntityIcebox) tileEntity).getInventory(), tileEntity);
			}

		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	private void breakEntityWithDrops(IInventory inventory, TileEntity tileEntity)
	{
		if (inventory != null)
		{
			for (int j1 = 0; j1 < inventory.getSizeInventory(); ++j1)
			{
				final ItemStack itemstack = inventory.getStackInSlot(j1);

				if (itemstack != null)
				{
					final float f = random.nextFloat() * 0.8F + 0.1F;
					final float f1 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (final float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; tileEntity.worldObj.spawnEntityInWorld(entityitem))
					{
						int k1 = random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
						{
							k1 = itemstack.stackSize;
						}

						itemstack.stackSize -= k1;
						entityitem = new EntityItem(tileEntity.worldObj, tileEntity.xCoord + f, tileEntity.yCoord + f1, tileEntity.zCoord + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
						final float f3 = 0.05F;
						entityitem.motionX = (float) random.nextGaussian() * f3;
						entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}

			tileEntity.worldObj.func_96440_m(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, blockID);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new BaseMachineTileEntity();
	}

	@Override
	public int getRenderType()
	{
		return renderID;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		final int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

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
			((TileEntityFurnace) world.getBlockTileEntity(x, y, z)).setGuiDisplayName(stack.getDisplayName());
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	private void setDefaultDirection(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			final int blockIDLeft = world.getBlockId(x, y, z - 1);
			final int blockIDRight = world.getBlockId(x, y, z + 1);
			final int blockIDBack = world.getBlockId(x - 1, y, z);
			final int blockIDFront = world.getBlockId(x + 1, y, z);
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
			 * TileEntity tileentity = world.getBlockTileEntity(x, y, z);
			 * if(tileentity instanceof BaseMachineTileEntity) {
			 * System.out.println("direction " + direction);
			 * ((BaseMachineTileEntity) tileentity).setDirection(direction); }
			 */
		}
	}
}
