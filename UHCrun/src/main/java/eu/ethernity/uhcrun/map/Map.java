package eu.ethernity.uhcrun.map;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.game.Game;
import eu.ethernity.uhcrun.inventory.SetupInventory;
import eu.ethernity.uhcrun.utils.Logger;
import eu.ethernity.uhcrun.world.CavePopulator;
import eu.ethernity.uhcrun.world.OrePopulator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class Map {
    private final String name;
    private World world;
    private Location center;
    private final SetupInventory setupInventory;
    private final UHCRun plugin;
    private Game game = null;
    private int teamSize = 4;

    private boolean loadedMap = false;

    public Map(UHCRun plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.setupInventory = new SetupInventory(this);
        createWorld();
    }

    public void setGame(Game game) {
        this.game = game;
        plugin.getGameManager().addGame(game);
    }

    public void save() {
        //TODO: save
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

        for(int i = 0; i < chunks.length; i++) {
            chunks[i].unload(false);
        }

        world.getPopulators().add(new CavePopulator());
        world.getPopulators().add(new OrePopulator());

        for(int i = 0; i < chunks.length; i++) {
            chunks[i].load();
        }
        loadedMap = true;
    }

    public void destroyWorld() {
        if(world == null || loadedMap == false)
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
    public SetupInventory getInventory() {
        return setupInventory;
    }
}
