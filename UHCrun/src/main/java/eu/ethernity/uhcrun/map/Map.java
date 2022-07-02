package eu.ethernity.uhcrun.map;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.game.Game;
import eu.ethernity.uhcrun.world.populators.CavePopulator;
import eu.ethernity.uhcrun.world.populators.CavesPopulator;
import eu.ethernity.uhcrun.world.populators.OrePopulator;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Map {
    private final String name;
    private World world;
    private Location center;
    private final UHCRun plugin;
    private Game game = null;
    private int teamSize = 4;

    private boolean loadedMap = false;

    public Map(UHCRun plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        createWorld();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    protected void createWorld() {
        loadedMap = false;
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.generateStructures(true);
        worldCreator.generatorSettings();


        world = Bukkit.createWorld(worldCreator);
        center = world.getSpawnLocation(); //new Location(world, 0, 0, world.getHighestBlockYAt(0, 0)+1);

        Chunk[] chunks = world.getLoadedChunks();

        Bukkit.getLogger().log(Level.INFO, "Unloading default chunks!");
        for (Chunk chunk : chunks) {
            chunk.unload(false);
        }

        world.getPopulators().add(new CavesPopulator());
        world.getPopulators().add(new OrePopulator());

        Bukkit.getLogger().log(Level.INFO, "Loading populated chunks!");
        for (Chunk chunk : chunks) {
            chunk.load();
        }
        loadedMap = true;
    }

    public void destroyWorld() {
        if(world == null || !loadedMap)
            return;
        File worldFile = world.getWorldFolder();
        String worldName = world.getName();
        world = null;
        if(Bukkit.unloadWorld(Bukkit.getWorld(worldName), true)) {
            try {
                FileUtils.deleteDirectory(worldFile);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
                if(plugin.getConfig().getBoolean("debug"))
                    e.printStackTrace();
            }
        }
        loadedMap = false;
    }

    public boolean isLoadedMap() {
        return loadedMap;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
    public int getTeamSize() {
        return teamSize;
    }
    public Game getGame() {
        return game;
    }
    public void setCenter(Location center) {
        this.center = center;
    }
    public Location getCenter() {
        return center;
    }
    public World getWorld() {
        return world;
    }
    public String getName() {
        return name;
    }
}
