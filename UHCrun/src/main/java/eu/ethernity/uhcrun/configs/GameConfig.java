package eu.ethernity.uhcrun.configs;

import eu.ethernity.uhcrun.UHCRun;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GameConfig {
    private static JavaPlugin plugin;
    private static GameConfig instance;
    public static FileConfiguration config = null;

    public GameConfig(JavaPlugin plugin) {
        GameConfig.plugin = plugin;
        init();
    }

    private static void init() {
        File file = new File(plugin.getDataFolder(), "game.yml");
        if (!file.exists()) {
            try {
                plugin.saveResource("game.yml", false);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return config;
    }
}