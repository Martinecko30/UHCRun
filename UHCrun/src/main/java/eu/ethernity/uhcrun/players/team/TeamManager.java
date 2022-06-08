package eu.ethernity.uhcrun.players.team;

import eu.ethernity.uhcrun.utils.Logger;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {
    private final Set<Team> teams = new HashSet<>();
    private int teamSize;

    public TeamManager(int teamSize) {
        this.teamSize = teamSize;
    }

    public void addOrCreateTeam(Player player) {
        for(Team team : teams)
            if(team.canAddPlayer()) {
                team.addPlayer(player);
                return;
            }

        Team team = new Team(teamSize);
        team.addPlayer(player);
        teams.add(team);
    }

    public void addToTeam(Player player, Team team) {
        if(!teams.contains(team)) return;

        team.addPlayer(player);
        teams.add(team);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void remove(Player player) {
        getTeam(player).removePlayer(player);
    }

    public Team getTeam(Player player) {
        for(Team team : teams)
            if(team.getPlayers().contains(player))
                return team;
        return null;
    }

    public Player getPlayer(String name) {
        for(Team team : teams)
            for(Player player : team.getPlayers())
                if(player.getName().equals(name))
                    return player;
        return null;
    }

    public Set<Player> getPlayers() {
        Set<Player> players = new HashSet<>();
        for(Team team : teams) {
            players.addAll(team.getPlayers());
        }

        return players;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int size) {
        this.teamSize = size;
    }

    public Set<Team> getTeams() {
        return teams;
    }
}
