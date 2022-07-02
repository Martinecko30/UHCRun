package eu.ethernity.uhcrun.commands.game;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.game.Game;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StartGameCommand implements ICommand {
    @Override
    public String getLabel() {
        return "start";
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/waterrun start [game]";
    }

    @Override
    public String getPermission() {
        return "waterrun.admin.start";
    }

    @Override
    public String getDescription() {
        return "Force starts a game";
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

        if(args.length > 1 && args[1].equalsIgnoreCase("force")) {
            plugin.getGameManager().startGame();
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("started-game").replace("<game>", plugin.getGameManager().getGame().getName())));
            return true;
        }

        if(plugin.getGameManager().getGame().getPlayers().size() < 10) { //TODO: variable
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("not-enough-players")));
            return false;
        }

        plugin.getGameManager().startGame();
        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("started-game").replace("<game>", plugin.getGameManager().getGame().getName())));
        return true;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        return null;
    }
}