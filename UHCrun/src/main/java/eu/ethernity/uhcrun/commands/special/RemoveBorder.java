package eu.ethernity.uhcrun.commands.special;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveBorder implements ICommand {

    @Override
    public String getLabel() {
        return "removeborder";
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/uhcrun removeborder";
    }

    @Override
    public String getPermission() {
        return "uhcrun.admin.removeborder";
    }

    @Override
    public String getDescription() {
        return "Removes border from world you're in";
    }

    @Override
    public Integer getMinArgs() {
        return 1;
    }

    @Override
    public Integer getMaxArgs() {
        return 1;
    }

    @Override
    public boolean perform(UHCRun plugin, CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.getWorld().getWorldBorder().reset();
        return true;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        return null;
    }
}