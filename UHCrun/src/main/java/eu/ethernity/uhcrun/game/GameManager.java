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
    private final List<Game> games = new ArrayList<>();

    private final UHCRun plugin;

    public GameManager(UHCRun plugin) {
        this.plugin = plugin;
    }

    public void joinRandomGame(Player player) {
        if(!isGameAvailable(player)) return;
        sortGames();
        for (Game game : games)
            if (!game.isStarted()) {
                game.addPlayer(player);
                player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("joined-game")));
                return;
            }
        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("game-started")));
    }

    public void joinGame(Player player, String gameName) {
        if(!isGameAvailable(player)) return;
        if (games.size() >= 1)
            for (int i = 0; i <= games.size(); i++)
                if (games.get(i).getName().equals(gameName)) {
                    games.get(i).addPlayer(player);
                    player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("joined-game")));
                    return;
                }

        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("unavailable-game")));
    }

    /*
    @Deprecated
    public void joinGame(Player player, Map map) {
        if(!isGameAvailable(player)) return;

        for (int i = 0; i <= games.size(); i++)
            if (games.get(i).getName().equals(map.getName())) {
                games.get(i).addPlayer(player);
                player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("joined-game")));
                return;
            }

        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("unavailable-game")));
    }
     */

    public boolean isGameAvailable(Player player) {
        if (plugin.getGameManager().isInGame(player)) {
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("already-in-game")));
            return false;
        }
        plugin.getMapManager().startAvailableLevels();
        if (games.size() <= 0) {
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("not-enough-games")));
            return false;
        }
        return true;
    }

    private boolean isGamePartyAvailable(Party party) {
        Player leader = party.getLeader();
        if (isInGame(leader)) {
            leader.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("already-in-game")));
            return false;
        }
        plugin.getMapManager().startAvailableLevels();
        if (games.size() <= 0) {
            leader.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("not-enough-games")));
            return false;
        }
        sortGames();
        return true;
    }

    public void joinRandomGameWithParty(Party party) {
        if(!isGamePartyAvailable(party)) return;
        for (Game game : games)
            if (!game.isStarted()) {
                game.addTeam(party);
                for(Player player : party.getPlayers())
                    player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("joined-game")));
                return;
            }
        party.getLeader().sendMessage(Utils.tc(MessagesConfig.getConfig().getString("game-started")));
    }

    public void joinGameWithParty(Party party, String gameName) {
        if(!isGamePartyAvailable(party)) return;
        for (Game game : games)
            for (int i = 0; i <= games.size(); i++)
                if (games.get(i).getName().equals(gameName)) {
                    games.get(i).addTeam(party);
                    for(Player player : party.getPlayers())
                        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("joined-game")));
                    return;
                }
        party.getLeader().sendMessage(Utils.tc(MessagesConfig.getConfig().getString("game-started")));
    }

    public void removePlayers() {
        clearGames();
        for(Game game : games)
            game.removePlayers();
    }

    public void leaveGame(Player player) {
        getGame(player).removePlayer(player);
        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("left-game")));
    }

    // Sorts all games by players
    private void sortGames() {
        clearGames();
        int n = games.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (games.get(j).getPlayers().size() > games.get(j + 1).getPlayers().size()) {
                    Game temp = games.get(j);
                    games.set(j, games.get(j + 1));
                    games.set(j + 1, temp);
                }
    }

    public void clearGames() {
        for (int i = 0; i < games.size(); i++)
            if (games.get(i) == null)
                games.remove(i);
        //games.removeAll(Collections.singleton(null));
        //games.removeIf(Objects::isNull);
    }

    public boolean isInGame(Player player) {
        clearGames();
        for(Game game : games)
            if(game.isInGame(player))
                return true;
        return false;
    }

    public Game getGame(Player player) {
        clearGames();
        for(Game game : games)
            if(game.isInGame(player))
                return game;
        return null;
    }

    public Game getGame(String name) {
        clearGames();
        for(Game game : games)
            if(game.getName().equals(name))
                return game;
        return null;
    }

    public boolean existGame(String name) {
        return getGame(name) != null;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public List<Game> getGames() {
        return games;
    }
}
