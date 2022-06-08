package eu.ethernity.uhcrun.commands.setup;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SetCenterCommand implements ICommand {

    @Override
    public String getLabel() {
        return "setcenter";
    }

    @Override
    public String getUsage() {
        return "/uhcrun setcenter";
    }

    @Override
    public String getPermission() {
        return "uhcrun.admin.setcenter";
    }

    @Override
    public String getDescription() {
        return "Sets center of a map";
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
        plugin.getMapManager().getMap(player.getWorld()).setCenter(player.getLocation());
        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("set-center")));
        return false;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        return null;
    }
}