package eu.ethernity.uhcrun.commands.game;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import eu.ethernity.uhcrun.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GamesCommand implements ICommand {
    @Override
    public String getLabel() {
        return "games";
    }

    @Override
    public String getUsage() {
        return "/uhcrun games";
    }

    @Override
    public String getPermission() {
        return "uhcrun.admin.games";
    }

    @Override
    public String getDescription() {
        return "Shows all games";
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
        for(Game game : plugin.getGameManager().getGames())
            player.sendMessage(game.getName());

        return true;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        return null;
    }
}
