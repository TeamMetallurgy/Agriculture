package com.teammetallurgy.agriculture.modstuff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class MFRPlants implements IFactoryPlantable
{
    private int seedId;
    private HashMap<Integer, Integer> plants;
    
    public MFRPlants(int id, HashMap<Integer, Integer> plants)
    {
        this.seedId = id;
        this.plants = plants;
    }

    @Override
    public int getSeedId()
    {
        return seedId;
    }

    @Override
    public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
    {
        return plants.get(stack.getItemDamage());
    }

    @Override
    public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
    {
        return 0;
    }

    @Override
    public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
    {
        int groundId = world.getBlockId(x, y - 1, z);
        if(!world.isAirBlock(x, y, z))
        {
            return false;
        }
        
        int plantId = plants.get(stack.getItemDamage());
        
        return (Block.blocksList[plantId].canPlaceBlockAt(world, x, y, z) && Block.blocksList[plantId].canBlockStay(world, x, y, z)) ||
                (Block.blocksList[plantId] instanceof IPlantable && Block.blocksList[groundId] != null &&
                Block.blocksList[groundId].canSustainPlant(world, x, y, z, ForgeDirection.UP, ((IPlantable)Block.blocksList[plantId])));
    }

    @Override
    public void prePlant(World world, int x, int y, int z, ItemStack stack)
    {
        
    }

    @Override
    public void postPlant(World world, int x, int y, int z, ItemStack stack)
    {
        
    }

    

}
