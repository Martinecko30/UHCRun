package eu.ethernity.uhcrun.commands.player;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.map.Map;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JoinCommand implements CommandExecutor, TabCompleter {

    private final UHCRun plugin;

    public JoinCommand(UHCRun plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("no-permission")));
            return false;
        }
        if (!sender.hasPermission("uhcrun.join")) {
            sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("no-permission")));
            return false;
        }

        Player player = (Player) sender;

        if (args.length <= 0) {
            plugin.getGameManager().joinRandomGame(player);
        } else if (args.length == 1) {
            plugin.getGameManager().joinGame(player, args[0]);
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        plugin.getMapManager().startAvailableLevels();
        for(Map map : plugin.getMapManager().getMaps()) {
            tabComplete.add(map.getName());
        }
        return tabComplete;
    }
}
