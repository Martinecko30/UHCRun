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

public class JoinCommand implements CommandExecutor {

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

        plugin.getGameManager().joinGame((Player) sender);
        sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("joined-game")));
        return true;
    }
}
