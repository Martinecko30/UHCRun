package eu.ethernity.uhcrun.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Logger {
    public static void log(Level level,Object o) {
        Bukkit.getLogger().log(level, String.valueOf(o) + " | " + o.getClass() + " " + o.hashCode());
    }

    public static void error(Object o) {
        log(Level.SEVERE, o);
    }

    public static void warning(Object o) {
        log(Level.WARNING, o);
    }

    public static void info(Object o) {
        log(Level.INFO, o);
    }
}
