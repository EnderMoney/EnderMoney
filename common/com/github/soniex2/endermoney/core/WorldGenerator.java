package com.github.soniex2.endermoney.core;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// new WorldGenMinable(EnderMoney.ore.blockID, 8);
		int startX, startZ;
		switch (world.provider.dimensionId) {
			case -1:
				// Nether gen. Do nothing.
				break;
			case 1:
				startX = chunkX * 16;
				startZ = chunkZ * 16;
				WorldGenMinable ender = new WorldGenMinable(EnderMoney.ore.blockID, 1, 8,
						Block.whiteStone.blockID);
				generateOre(startX, startZ, world, random, 20, ender, 0, 64);
				break;
			default:
				startX = chunkX * 16;
				startZ = chunkZ * 16;
				WorldGenMinable iron = new WorldGenMinable(EnderMoney.ore.blockID, 8);
				generateOre(startX, startZ, world, random, 20, iron, 0, 64);
		}
	}

	private void generateOre(int chunk_X, int chunk_Z, World world, Random rand, int amount,
			net.minecraft.world.gen.feature.WorldGenerator gen, int minY, int maxY) {
		for (int l = 0; l < amount; ++l) {
			int i1 = chunk_X + rand.nextInt(16);
			int j1 = rand.nextInt(maxY - minY) + minY;
			int k1 = chunk_Z + rand.nextInt(16);
			gen.generate(world, rand, i1, j1, k1);
		}
	}

}
