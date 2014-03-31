package com.teammetallurgy.agriculture.machines.counter;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

public class BlockCounter extends BaseMachineBlock {
    public BlockCounter(final int par1, final Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
    public TileEntity createNewTileEntity(final World world, int meta)
    {
        return new TileEntityCounter();
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
                player.openGui(Agriculture.instance, GUIIds.CABINET, world, x, y, z);
                return true;
            }

            return true;
        }
        return true;
    }
}
