package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineBlock;

public class BlockBrewer extends BaseMachineBlock {
    public BlockBrewer(final int par1, final Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
    public TileEntity createNewTileEntity(final World world)
    {
        return new TileEntityBrewer();
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float xFace, final float yFace, final float zFace)
    {

        if (player.isSneaking()) { return false; }

        if (!world.isRemote)
        {
            final int blockMetadata = world.getBlockMetadata(x, y, z);
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
}
