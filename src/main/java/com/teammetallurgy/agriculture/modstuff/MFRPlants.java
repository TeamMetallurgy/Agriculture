//package com.teammetallurgy.agriculture.modstuff;
//
//import java.util.HashMap;
//
//import net.minecraft.block.Block;
//import net.minecraft.item.ItemStack;
//import net.minecraft.world.World;
//import net.minecraftforge.common.ForgeDirection;
//import net.minecraftforge.common.IPlantable;
//import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
//
//public class MFRPlants implements IFactoryPlantable {
//    private final HashMap<Integer, Integer> plants;
//    private final int seedId;
//
//    public MFRPlants(final int id, final HashMap<Integer, Integer> plants)
//    {
//        seedId = id;
//        this.plants = plants;
//    }
//
//    @Override
//    public boolean canBePlantedHere(final World world, final int x, final int y, final int z, final ItemStack stack)
//    {
//        final int groundId = world.getBlockId(x, y - 1, z);
//        if (!world.isAirBlock(x, y, z)) { return false; }
//
//        final int plantId = plants.get(stack.getItemDamage());
//
//        return Block.blocksList[plantId].canPlaceBlockAt(world, x, y, z) && Block.blocksList[plantId].canBlockStay(world, x, y, z) || Block.blocksList[plantId] instanceof IPlantable && Block.blocksList[groundId] != null && Block.blocksList[groundId].canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) Block.blocksList[plantId]);
//    }
//
//    @Override
//    public int getPlantedBlockId(final World world, final int x, final int y, final int z, final ItemStack stack)
//    {
//        return plants.get(stack.getItemDamage());
//    }
//
//    @Override
//    public int getPlantedBlockMetadata(final World world, final int x, final int y, final int z, final ItemStack stack)
//    {
//        return 0;
//    }
//
//    @Override
//    public int getSeedId()
//    {
//        return seedId;
//    }
//
//    @Override
//    public void postPlant(final World world, final int x, final int y, final int z, final ItemStack stack)
//    {
//
//    }
//
//    @Override
//    public void prePlant(final World world, final int x, final int y, final int z, final ItemStack stack)
//    {
//
//    }
//
//}
