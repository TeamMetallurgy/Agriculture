package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

public class BlockProcessor extends BaseMachineBlock {
    public BlockProcessor(final int par1, final Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
    public TileEntity createNewTileEntity(final World world)
    {
        return new TileEntityProcessor();
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float xOffset, final float yOffset, final float zOffset)
    {

        if (player.isSneaking()) { return false; }

        if (!world.isRemote)
        {
            final int blockMetadata = world.getBlockMetadata(x, y, z);
            if (side == 1 || yOffset > 0.76)
            {
                player.openGui(Agriculture.instance, GUIIds.PROCESSOR, world, x, y, z);
                return true;
            }
            if (side == blockMetadata)
            {
                player.openGui(Agriculture.instance, GUIIds.FUEL, world, x, y, z);
                return true;
            }

            return true;
        }

        return true;

    }
}
