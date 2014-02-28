package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BlockAgriculture;

public class BlockProcessor extends BlockAgriculture
{

    public BlockProcessor(final int id)
    {
        super(id);
    }

    @Override
    public TileEntity createNewTileEntity(final World world)
    {
        return new TileEntityProcessor();
    }

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        final int blockMetadata = world.getBlockMetadata(x, y, z);
        if (side == 1 || yOffset > 0.76)
        {
            player.openGui(Agriculture.instance, GUIIds.PROCESSOR, world, x, y, z);
        }
    }
}
