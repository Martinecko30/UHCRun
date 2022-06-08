package eu.ethernity.uhcrun.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String tc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> tc(List<String> list) {
        List<String> texts = new ArrayList<>();
        for (String text : list) {
            tc(text);
        }
        return texts;
    }
}