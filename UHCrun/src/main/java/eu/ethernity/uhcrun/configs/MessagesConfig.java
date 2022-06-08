package eu.ethernity.uhcrun.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessagesConfig {
    private static JavaPlugin plugin;
    private static GameConfig instance;
    public static FileConfiguration config = null;

    public MessagesConfig(JavaPlugin plugin) {
        MessagesConfig.plugin = plugin;
        init();
    }

    private static void init() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            try {
                plugin.saveResource("messages.yml", false);
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