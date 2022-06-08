package eu.ethernity.uhcrun.commands.player;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveCommand implements CommandExecutor, TabCompleter {

    private final UHCRun plugin;

    public LeaveCommand(UHCRun plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("no-permission")));
            return false;
        }
        if (!sender.hasPermission("uhcrun.leave")) {
            sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("no-permission")));
            return false;
        }

        Player player = (Player) sender;

        if (plugin.getGameManager().isInGame(player)) {
            plugin.getGameManager().leaveGame(player);
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("left-game")));
            return true;
        }

        sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("not-in-game")));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
