package com.teammetallurgy.agriculture.crops;

import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class BlockCrop extends BlockCrops
{
    private boolean harvestable;

    public BlockCrop(int id, boolean harvestable)
    {
        super(id);
        this.harvestable = harvestable;
    }
    
    @Override
    protected abstract int getSeedItem();
    
    @Override
    protected abstract int getCropItem();
    
    @Override
    protected abstract boolean canThisPlantGrowOnThisBlockID(int par1);

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        if (harvestable && !world.isRemote)
        {
            int blockMetadata = world.getBlockMetadata(x, y, z);

            if (blockMetadata == 7)
            {
                this.harvestBlock(world, player, x, y, z, blockMetadata);
                world.setBlockMetadataWithNotify(x, y, z, 0, 2);
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }
}
