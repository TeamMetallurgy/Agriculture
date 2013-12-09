package com.teammetallurgy.agriculture.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenSpice extends WorldGenerator implements IWorldGenerator {
    /** The ID of the plant block used in this plant generator. */
    private final int plantBlockId;

    public WorldGenSpice(final int par1)
    {
        plantBlockId = par1;
    }

    @Override
    public void generate(final Random randomGenerator, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider)
    {

        if (randomGenerator.nextFloat() > 0.2)
        {
            for (int j = 0; j < 5; ++j)
            {
                int k = chunkX * 16 + randomGenerator.nextInt(16) + 8;
                int l = randomGenerator.nextInt(128);
                int i1 = chunkZ * 16 + randomGenerator.nextInt(16) + 8;
                this.generate(world, randomGenerator, k, l, i1);

                if (randomGenerator.nextInt(4) == 0)
                {
                    k = chunkX * 16 + randomGenerator.nextInt(16) + 8;
                    l = randomGenerator.nextInt(128);
                    i1 = chunkZ * 16 + randomGenerator.nextInt(16) + 8;
                    this.generate(world, randomGenerator, k, l, i1);
                }
            }
        }
    }

    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5)
    {
        for (int l = 0; l < 64; ++l)
        {
            final int i1 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            final int j1 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int k1 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(i1, j1, k1) && (!par1World.provider.hasNoSky || j1 < 127) && par1World.getBlockId(i1, j1 - 1, k1) == Block.grass.blockID)
            {
                par1World.setBlock(i1, j1, k1, plantBlockId, 0, 2);
            }
        }

        return true;
    }
}
