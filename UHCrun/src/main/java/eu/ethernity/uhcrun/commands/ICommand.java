package eu.ethernity.uhcrun.commands;

import eu.ethernity.uhcrun.UHCRun;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface ICommand {

    String getLabel();

    String getUsage();

    String getPermission();

    String getDescription();

    Integer getMinArgs();

    Integer getMaxArgs();

    boolean perform(UHCRun plugin, CommandSender sender, String[] args);

    List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args);
}
