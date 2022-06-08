package eu.ethernity.uhcrun.commands.setup;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.inventory.SetupInventory;
import eu.ethernity.uhcrun.map.Map;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SetupCommand implements ICommand {
    @Override
    public String getLabel() {
        return "setup";
    }

    @Override
    public String getUsage() {
        return "/uhcrun setup [map]";
    }

    @Override
    public String getPermission() {
        return "uhcrun.admin.setup";
    }

    @Override
    public String getDescription() {
        return "Open setup inventory for map";
    }

    @Override
    public Integer getMinArgs() {
        return 1;
    }

    @Override
    public Integer getMaxArgs() {
        return 2;
    }

    @Override
    public boolean perform(UHCRun plugin, CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == getMaxArgs()) {

        }

        if(plugin.getMapManager().getMap(player.getWorld()) != null) {
            SetupInventory inv = plugin.getMapManager().getMap(player.getWorld()).getInventory();
            player.openInventory(inv.getInv());
            return true;
        }

        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("unknown-map")));
        return true;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        for(Map map : plugin.getMapManager().getMaps())
            tabComplete.add(map.getName());
        return tabComplete;
    }
}
