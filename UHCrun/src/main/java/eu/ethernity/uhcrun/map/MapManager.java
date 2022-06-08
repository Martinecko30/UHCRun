package eu.ethernity.uhcrun.map;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.game.Game;
import eu.ethernity.uhcrun.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MapManager {
    private final UHCRun plugin;
    private final List<Map> maps = new ArrayList<>();

    private final int MAX_MAPS;

    public MapManager(UHCRun plugin) {
        this.plugin = plugin;
        MAX_MAPS = plugin.getConfig().getInt("max-games");
        init();
    }

    public void init() {
        for(int i = 0; i < MAX_MAPS; i++) {
            maps.add(new Map(plugin, "UHCRun"+i));
            Logger.info(maps.get(i).getName());
        }
        createMaps();
    }

    private void createMaps() {
        for (Map map : maps)
            if (map.isLoadedMap())
                map.createWorld();
        Logger.info(maps + "createMaps()");
    }

    public Map getMap(String name) {
        for(Map map : maps)
            if(map.getName().equals(name))
                return map;
        return null;
    }

    public void save() {
        for (Map map : getMaps())
            map.save();
    }

    public void destroyMaps() {
        for(Map map : maps)
            map.destroyWorld();
    }
    public Map getMap(World world) {
        for(Map map : maps)
            if(map.getWorld().equals(world))
                return map;
        return null;
    }

    public boolean contains(String name) {
        for(Map map : maps)
            if(map.getName().equals(name))
                return true;
        return false;
    }

    public void startAvailableLevels() {
        if (maps.size() <= 0) {
            Bukkit.getLogger().log(Level.WARNING, "Not enough maps"); // TODO: remove I guess?
            return;
        }
        for (Map map : maps)
            if (map.getGame() == null)
                map.setGame(new Game(plugin, map, map.getTeamSize()));
    }

    public boolean checkInv(Inventory inventory) {
        for(Map map : maps)
            if(map.getInventory().getInv().equals(inventory))
                return true;
        return false;
    }

    public List<Map> getMaps() {
        return maps;
    }
}
