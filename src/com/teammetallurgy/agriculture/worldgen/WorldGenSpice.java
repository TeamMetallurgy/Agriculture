package com.teammetallurgy.agriculture.worldgen;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

public class WorldGenSpice extends WorldGenerator implements IWorldGenerator
{
    /** The ID of the plant block used in this plant generator. */
    private int plantBlockId;

    public WorldGenSpice(int par1)
    {
        this.plantBlockId = par1;
    }


	@Override
	public void generate(Random randomGenerator, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{

		if(randomGenerator.nextFloat() > 0.2)
		{
		    for (int j = 0; j < 5; ++j)
		    {
		        int k = chunkX*16 + randomGenerator.nextInt(16) + 8;
		        int l = randomGenerator.nextInt(128);
		        int i1 = chunkZ*16 + randomGenerator.nextInt(16) + 8;
		        generate(world, randomGenerator, k, l, i1);
		
		        if (randomGenerator.nextInt(4) == 0)
		        {
		            k = chunkX*16 + randomGenerator.nextInt(16) + 8;
		            l = randomGenerator.nextInt(128);
		            i1 = chunkZ*16 + randomGenerator.nextInt(16) + 8;
		            generate(world, randomGenerator, k, l, i1);
		        }
		    }
		}
	}
	
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int l = 0; l < 64; ++l)
        {
            int i1 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int j1 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int k1 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(i1, j1, k1) && (!par1World.provider.hasNoSky || j1 < 127) && par1World.getBlockId(i1, j1-1, k1) == Block.grass.blockID)
            {
                par1World.setBlock(i1, j1, k1, this.plantBlockId, 0, 2);
            }
        }

        return true;
    }
}
