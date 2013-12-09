package com.teammetallurgy.agriculture.machines;

import com.teammetallurgy.agriculture.AgricultureBlocks;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderHelper implements ISimpleBlockRenderingHandler {

    private Block block;
    private TileEntity tileEntity;

    public RenderHelper(Block block, TileEntity tileEntity) {
        this.block = block;
        this.tileEntity = tileEntity;
    }

    @Override
    public int getRenderId()
    {
        return block.getRenderType();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        TileEntityRenderer.instance.renderTileEntityAt(tileEntity, 0D, 0D, 0D, 0F);
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
