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

        if (args.length <= getMinArgs()) {
            if (plugin.getGameManager().isInGame(player)) {
                Game game = plugin.getGameManager().getGame(player);
                game.startGame();
                player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("started-game").replace("<game>", game.getName())));
                return true;
            } else {
                player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("invalid-game")));
                return false;
            }
        }

        if (plugin.getGameManager().existGame(args[1])) {
            if (plugin.getGameManager().getGame(args[1]).getPlayers().size() >= 1) {
                plugin.getGameManager().getGame(args[1]).startGame();
                player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("started-game").replace("<game>", args[1])));
            }
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("not-enough-players").replace("<game>", args[1])));
        } else {
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("invalid-game")));
        }
        return false;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (args.length == 2) {
            plugin.getGameManager().clearGames();
            for (Game game : plugin.getGameManager().getGames())
                if (game.getPlayers().size() >= 1)
                    tabComplete.add(game.getName());
        }
        return tabComplete;
    }
}