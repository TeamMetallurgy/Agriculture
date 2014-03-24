package com.teammetallurgy.agriculture.machines;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderHelper implements ISimpleBlockRenderingHandler {

    private final Block block;
    private final TileEntity tileEntity;

    public RenderHelper(final Block block, final TileEntity tileEntity)
    {
        this.block = block;
        this.tileEntity = tileEntity;
    }

    @Override
    public int getRenderId()
    {
        return block.getRenderType();
    }

    @Override
    public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer)
    {
        TileEntityRenderer.instance.renderTileEntityAt(tileEntity, 0D, 0D, 0D, 0F);
    }

    @Override
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer)
    {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return true;
    }
}
