package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;
import com.teammetallurgy.agriculture.recipes.BrewerRecipes;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class BlockBrewer extends BaseMachineBlock
{
	public BlockBrewer(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xFace, float yFace, float zFace)
	{

		if (player.isSneaking())
		{
			return false;
		}

		if (!world.isRemote)
		{
			int blockMetadata = world.getBlockMetadata(x, y, z);
			final int front = blockMetadata % 2;

			if (side == 1)
			{
				player.openGui(Agriculture.instance, GUIIds.BREWER, world, x, y, z);
				return true;
			}

			if (side == blockMetadata)
			{

			}
			return true;
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityBrewer();
	}
}
