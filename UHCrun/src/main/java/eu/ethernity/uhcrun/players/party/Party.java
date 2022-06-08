package eu.ethernity.uhcrun.players.party;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Party {

    private final Set<Player> players = new HashSet<>();
    private final String name;

    public Party(Player player) {
        this.name = player.getName();
        players.add(player);
    }

    public Player getLeader() {
        return players.iterator().next();
    }

    public void addToParty(Player player) {
        players.add(player);
    }

    public boolean isInParty(Player player) {
        return players.contains(player);
    }

    public String getName() {
        return name;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
