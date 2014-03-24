package com.teammetallurgy.agriculture.modstuff;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class MFRHarvest implements IFactoryFertilizable, IFactoryHarvestable {

    private final int plantId;

    public MFRHarvest(final int plantId)
    {
        this.plantId = plantId;
    }

    @Override
    public boolean breakBlock()
    {
        return true;
    }

    @Override
    public boolean canBeHarvested(final World world, final Map<String, Boolean> harvesterSettings, final int x, final int y, final int z)
    {
        final boolean flag = world.getBlockMetadata(x, y, z) >= 6;

        return flag;
    }

    @Override
    public boolean canFertilizeBlock(final World world, final int x, final int y, final int z, final FertilizerType fertilizerType)
    {
        return world.getBlockMetadata(x, y, z) < 6;
    }

    @Override
    public boolean fertilize(final World world, final Random rand, final int x, final int y, final int z, final FertilizerType fertilizerType)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        return world.setBlockMetadataWithNotify(x, y, z, ++metadata, 2);
    }

    @Override
    public List<ItemStack> getDrops(final World world, final Random rand, final Map<String, Boolean> harvesterSettings, final int x, final int y, final int z)
    {
        return Block.blocksList[plantId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    }

    @Override
    public int getFertilizableBlockId()
    {
        return plantId;
    }

    @Override
    public HarvestType getHarvestType()
    {
        return HarvestType.Normal;
    }

    @Override
    public int getPlantId()
    {
        return plantId;
    }

    @Override
    public void postHarvest(final World world, final int x, final int y, final int z)
    {

    }

    @Override
    public void preHarvest(final World world, final int x, final int y, final int z)
    {

    }
}
