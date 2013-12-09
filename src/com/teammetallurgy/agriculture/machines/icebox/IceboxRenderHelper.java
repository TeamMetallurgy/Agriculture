package com.teammetallurgy.agriculture.machines.icebox;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import com.teammetallurgy.agriculture.AgricultureBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class IceboxRenderHelper implements ISimpleBlockRenderingHandler
{

	@Override
	public int getRenderId()
	{
		return AgricultureBlocks.icebox.getRenderType();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		final TileEntityIcebox tileentity = new TileEntityIcebox();
		TileEntityRenderer.instance.renderTileEntityAt(tileentity, 0D, 0D, 0D, 0F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

}
