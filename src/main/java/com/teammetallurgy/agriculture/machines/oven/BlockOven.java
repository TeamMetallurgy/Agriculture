package com.teammetallurgy.agriculture.machines.oven;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockOven extends BaseMachineBlock {
    public BlockOven(final int par1, final Material par2Material)
    {
        super(par1, par2Material);
        renderID = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public TileEntity createNewTileEntity(final World world, int meta)
    {
        return new TileEntityOven();
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float xFace, final float yFace, final float zFace)
    {
        if (player.isSneaking()) { return false; }

        // (>_>) //
        if (!world.isRemote)
        {
            final int blockMetadata = world.getBlockMetadata(x, y, z);
            if (side == 1 || yFace > 0.76f) // (<_<) /// :P Deal with it keith
                                            // :P
            {
                player.openGui(Agriculture.instance, GUIIds.COUNTER, world, x, y, z);
                return true;
            }
            if (side == blockMetadata)
            {
                player.openGui(Agriculture.instance, GUIIds.OVEN, world, x, y, z);
                return true;
            }

            return true;
        }
        return true;
    }
}
