package com.teammetallurgy.agriculture.crops;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

import com.teammetallurgy.agriculture.AgricultureItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStrawberry extends BlockFlower {
	private float growthRate = 0.5f;
	private Icon[] iconArray;

	public BlockStrawberry(int par1) {
		super(par1);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		int id = par1World.getBlockId(par2, par3 - 1, par4);
		return id == Block.tilledField.blockID;
	}

	@Override
	public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
		return true;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = super.getBlockDropped(world, x, y, z, metadata, fortune);
		ret.add(AgricultureItems.strawberry.getItemStack());

		if (metadata >= 6) {
			for (int n = 0; n < 3 + fortune; n++) {
				if (world.rand.nextInt(15) <= metadata) {
					ret.add(AgricultureItems.strawberry.getItemStack());
				}
			}
		}

		return ret;
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.updateTick(par1World, par2, par3, par4, par5Random);

		if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9) {
			int l = par1World.getBlockMetadata(par2, par3, par4);
			if (l < 6) {
				if (par5Random.nextInt((int) (25.0F / growthRate) + 1) == 0) {
					++l;
					par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		if (player.getHeldItem() != null) {
			if (player.getHeldItem().itemID == Item.dyePowder.itemID && player.getHeldItem().getItemDamage() == 15) {
				if (world.getBlockMetadata(x, y, z) < 6) {
					if (!world.isRemote) {
						player.getHeldItem().stackSize--;
						float temp = growthRate;
						this.growthRate = 100;
						updateTick(world, x, y, z, new Random());
						this.growthRate = temp;
						if (!world.isRemote) {
							world.playAuxSFX(2005, x, y, z, 0);

						}
						return true;
					}
				}
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		if (par2 < 0 || par2 > 7) {
			par2 = 7;
		}

		return this.iconArray[par2];
	}

	@SideOnly(Side.CLIENT)
	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister) {
		this.iconArray = new Icon[8];

		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
		}
	}
}
