package eu.ethernity.uhcrun.players.team;

import eu.ethernity.uhcrun.players.party.Party;
import eu.ethernity.uhcrun.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;


public class Team {
    private int size;
    private final Set<Player> players = new HashSet<>();

    public Team(int size) {
        this.size = size;
    }

    public Team(int size, Player... players) {
        this.size = size;
        this.players.addAll(Arrays.asList(players));
    }

    public Team(int size, Party party) {
        this.size = size;
        this.players.addAll(party.getPlayers());
    }

    public void addPlayer(Player player) {
        if(players.size() >= size) return;

        players.add(player);
    }

    public boolean canAddPlayer() {
        return players.size() >= size;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setSize() {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
