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

public class MFRHarvest implements IFactoryFertilizable, IFactoryHarvestable{
    
    private int plantId;
    
    public MFRHarvest(int plantId)
    {
        this.plantId = plantId;
    }

    @Override
    public int getFertilizableBlockId()
    {
        return plantId;
    }

    @Override
    public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
    {
        return world.getBlockMetadata(x, y, z) < 6;
    }

    @Override
    public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        
        return world.setBlockMetadataWithNotify(x, y, z, ++metadata, 2);
    }

    @Override
    public int getPlantId()
    {
        return plantId;
    }

    @Override
    public HarvestType getHarvestType()
    {
        return HarvestType.Normal;
    }

    @Override
    public boolean breakBlock()
    {
        return true;
    }

    @Override
    public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
    {
        boolean flag = world.getBlockMetadata(x, y, z) >= 6;
        
        return flag;
    }

    @Override
    public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
    {
        return Block.blocksList[plantId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    }

    @Override
    public void preHarvest(World world, int x, int y, int z)
    {

    }

    @Override
    public void postHarvest(World world, int x, int y, int z)
    {
  
    }
}
