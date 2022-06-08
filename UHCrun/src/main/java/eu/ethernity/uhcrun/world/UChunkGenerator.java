package eu.ethernity.uhcrun.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UChunkGenerator extends ChunkGenerator {

    private void setBlock(short[][] result, int x, int y, int z, short blkid)
    {
        if (result[y >> 4] == null) {
            result[y >> 4] = new short[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }

    @Override
    public short[][] generateExtBlockSections(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biomes)
    {
        short[][] result = new short[16][];


        for (int x = 0; x < 16; x += 4)
        {
            for (int z = 0; z < 16; z += 4)
            {
                for (int y = 0; y < 192; y += 4)
                {
                    int id = 54;
                    setBlock(result, x, y, z, (short) 54);
                }
            }
        }

        return result;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world)
    {
        return Arrays.asList(new BlockPopulator[] {});
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random)
    {
        return new Location(world, 0, 100, 0);
    }
}