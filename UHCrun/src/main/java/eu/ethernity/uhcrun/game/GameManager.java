package eu.ethernity.uhcrun.game;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.map.Map;
import eu.ethernity.uhcrun.players.party.Party;
import eu.ethernity.uhcrun.players.team.Team;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private Game game = null;

    private final UHCRun plugin;

    public GameManager(UHCRun plugin) {
        this.plugin = plugin;
        this.game = new Game(plugin, new Map(plugin, "game"), 4);
    }

    public void joinGame(Player player) {
        if(game.isStarted()) {
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("game-started")));
            return;
        }

        game.addPlayer(player);
    }

    public void joinGame(Party party) {
        if(game.isStarted()) {
            party.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("game-started")));
            return;
        }

        game.addTeam(party);
    }

    public void leaveGame(Player player) {
        game.removePlayer(player);
    }

    public void leaveGame(Party party) {
        for(Player player : party.getPlayers())
            game.removePlayer(player);
    }

    public boolean isInGame(Player player) {
        return game.isInGame(player);
    }

    public void removePlayers() {
        game.removePlayers();
    }

    public void restartGame() {
        this.game = null;
        this.game = new Game(plugin, new Map(plugin, "game"), 4);
    }

    public void startGame() {
        game.startGame();
    }

    public Game getGame() {
        return game;
    }
}
