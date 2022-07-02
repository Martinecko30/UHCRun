package eu.ethernity.uhcrun.world.populators;

import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import org.bukkit.util.noise.PerlinNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class CavesPopulator extends BlockPopulator {

    private static boolean isGenerating = false;
    private double noiseScale = 2.8;

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (isGenerating)
            return;

        int rx = 4 + random.nextInt(8);
        int rz = 4 + random.nextInt(8);
        int maxY = world.getHighestBlockYAt(rx, rz);
        if (maxY < 16) {
            maxY = 32;
        }

        isGenerating = true;
        int ry = random.nextInt(maxY);
        ArrayList<Block> cave = perlinCave(world, random, chunk.getBlock(rx, ry, rz));
        createCave(world, random, cave);
        cave = perlinWorm(world, random, chunk.getBlock(rx, ry, rz));
        createCave(world, random, cave);
        isGenerating = false;
    }

    private ArrayList<Block> perlinWorm(World world, Random random, Block block) {
        ArrayList<Block> worm = new ArrayList<>();

        int blockX = block.getX();
        int blockY = block.getY();
        int blockZ = block.getZ();

        PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(random);
        while(world.getBlockAt(blockX, blockY, blockZ).getType() != Material.AIR) {
            if (worm.size() > 6000) {
                break;
            }

            if(random.nextInt(800) == 1)
                break;

            blockX = blockX + (random.nextInt(3) - 1);
            blockY = blockY + (random.nextInt(3) - 1);
            blockZ = blockZ + (random.nextInt(3) - 1);

            if(blockY <= 4)
                continue;

            if(perlin(perlinNoiseGenerator, blockX, blockY, blockZ) <= 0) {
                worm.add(world.getBlockAt(blockX, blockY, blockZ));
            }
        }
        return worm;
    }

    private ArrayList<Block> perlinCave(World world, Random random, Block block) {
        ArrayList<Block> worm = new ArrayList<>();

        int blockX = block.getX();
        int blockY = block.getY();
        int blockZ = block.getZ();

        PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(random);
        while(world.getBlockAt(blockX, blockY, blockZ).getType() != Material.AIR) {
            if (worm.size() > 6000) {
                break;
            }

            if(random.nextInt(1000) == 1)
                break;

            blockX = blockX + (random.nextInt(3) - 1);
            if(random.nextDouble(1) < 0.6)
                blockY = blockY + (random.nextInt(3) - 1);
            blockZ = blockZ + (random.nextInt(3) - 1);

            if(blockY <= 4)
                continue;

            if(perlin(perlinNoiseGenerator, blockX, blockY, blockZ) <= 0) {
                worm.add(world.getBlockAt(blockX, blockY, blockZ));
            }
        }
        return worm;
    }

    private double spline(double t, double p0, double p1, double p2, double p3) {

        return 0.5 * ((2 * p1) + (-p0 + p2) * t
                + (2 * p0 - 5 * p1 + 4 * p2 - p3) * (t * t) + (-p0 + 3 * p1 - 3
                * p2 + p3)
                * (t * t * t));
    }

    private void createCave(World world, Random random, ArrayList<Block> worm) {
        /*
        for(Block block : worm) {
            List<Block> surBlocks = getSurBlocks(world, block, random);
            for(Block surBlock : surBlocks)
                if(isSolid(surBlock) && !worm.contains(surBlock))
                    surBlock.setType(Material.AIR);

            if(isSolid(block))
                block.setType(Material.AIR);
        }
         */

        for(Block block : worm)
            if(isSolid(block))
                block.setType(Material.LAPIS_BLOCK);

        double t = 0.0;
        while (t <= 1.0) {
            System.out.println(spline(t, 5, 10, 20, 10));
            t += 0.1;
        }
        /*
        for(Block block : worm) {
            Vector center = new BlockVector(block.getX(), block.getY(), block.getZ());
            if (isSolid(block)){
                int radius = 1 + random.nextInt(2); // TODO: 3
                for (int x = -radius; x <= radius; x++)
                    for (int y = -radius; y <= radius; y++)
                        for (int z = -radius; z <= radius; z++) {
                            Vector position = center.clone().add(new Vector(x, y, z));

                            if (center.distance(position) <= radius + 0.5)
                                if (canPlaceBlock(world, position.getBlockX(), position.getBlockY(), position.getBlockZ()))
                                    world.getBlockAt(position.toLocation(world)).setType(Material.LAPIS_BLOCK, true);
                        }
            }


            /*
            List<Block> surBlocks = getSurrBlocks(world, block, random);
            for(Block surBlock : surBlocks)
                if(isSolid(surBlock) && !worm.contains(surBlock))
                    surBlock.setType(Material.LAPIS_BLOCK);
        }
        */
    }

    private boolean isAbleToSpawn(Random random) {
        return random.nextDouble(1) < 0.80;
    }

    private ArrayList<Block> getSurrBlocks(World world, Block block, Random random) {
        ArrayList<Block> surBlocks = new ArrayList<>();

        int radius = 1 + random.nextInt(1);
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    if(isAbleToSpawn(random))
                        surBlocks.add(world.getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z));

        return surBlocks;
    }

    @Deprecated
    private ArrayList<Block> getSurBlocks(World world, Block block, Random random) {
        ArrayList<Block> surBlocks = new ArrayList<>();

        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()-1, block.getY()-1, block.getZ()-1));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY()-1, block.getZ()-1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()+1, block.getY()-1, block.getZ()-1));

        surBlocks.add(world.getBlockAt(block.getX()-1, block.getY()-1, block.getZ()));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY()-1, block.getZ()));
        surBlocks.add(world.getBlockAt(block.getX()+1, block.getY()-1, block.getZ()));

        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()-1, block.getY()-1, block.getZ()+1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX(), block.getY()-1, block.getZ()+1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()+1, block.getY()-1, block.getZ()+1));

        //======================================================================================

        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()-1, block.getY(), block.getZ()-1));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY(), block.getZ()-1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()+1, block.getY(), block.getZ()-1));

        surBlocks.add(world.getBlockAt(block.getX()-1, block.getY(), block.getZ()));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY(), block.getZ()));
        surBlocks.add(world.getBlockAt(block.getX()+1, block.getY(), block.getZ()));

        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()-1, block.getY(), block.getZ()+1));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY(), block.getZ()+1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()+1, block.getY(), block.getZ()+1));

        //======================================================================================

        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()-1, block.getY()+1, block.getZ()-1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX(), block.getY()+1, block.getZ()-1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()+1, block.getY()+1, block.getZ()-1));

        surBlocks.add(world.getBlockAt(block.getX()-1, block.getY()+1, block.getZ()));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY()+1, block.getZ()));
        surBlocks.add(world.getBlockAt(block.getX()+1, block.getY()+1, block.getZ()));

        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()-1, block.getY()+1, block.getZ()+1));
        surBlocks.add(world.getBlockAt(block.getX(), block.getY()+1, block.getZ()+1));
        if(isAbleToSpawn(random))
            surBlocks.add(world.getBlockAt(block.getX()+1, block.getY()+1, block.getZ()+1));

        return surBlocks;
    }

    private double perlin3D(PerlinNoiseGenerator perlinNoise, double x, double y, double z) {
        double ab = perlinNoise.noise(x, y);
        double bc = perlinNoise.noise(y, z);
        double ac = perlinNoise.noise(x, z);

        double ba = perlinNoise.noise(y, x);
        double cb = perlinNoise.noise(z, y);
        double ca = perlinNoise.noise(z, x);

        double abc = ab + bc + ac + ba + cb + ca;
        Bukkit.getLogger().log(Level.WARNING, abc/6 + " " + perlinNoise.noise(x, y, z)); // TODO: remove
        return abc / 6;
    }

    private double perlin(PerlinNoiseGenerator perlinNoise, double x, double y, double z) {
        return perlinNoise.noise(x, y, z, 2, 1, 1);
    }

    private boolean canPlaceBlock(World world, int x, int y, int z) {
        return isSolid(world.getBlockAt(x, y, z));
    }

    private boolean isSolid(Block block) {
        switch (block.getType()) {
            case GRASS:
            case AIR:
            case WATER:
            case STATIONARY_WATER:
            case LAVA:
            case STATIONARY_LAVA:
                return false;
            default:
                return true;
        }
    }
}
