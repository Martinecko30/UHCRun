package eu.ethernity.uhcrun.game;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.configs.GameConfig;
import eu.ethernity.uhcrun.game.gamemisc.Border;
import eu.ethernity.uhcrun.map.Map;
import eu.ethernity.uhcrun.players.party.Party;
import eu.ethernity.uhcrun.players.team.Team;
import eu.ethernity.uhcrun.players.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public class Game {
    private final UHCRun plugin;
    private final Map map;
    private Border border;
    private final String name;

    private boolean started = false, canAddPlayers = true;

    private final TeamManager teamManager;
    private static final int MAX_PLAYERS = GameConfig.getConfig().getInt("max-players");
    private static final int MIN_PLAYERS = GameConfig.getConfig().getInt("min-players");
    private static final int MAX_SECONDS_TO_JOIN = GameConfig.getConfig().getInt("max-seconds-to-join");

    private int timer = 0;

    private int task;

    public Game(UHCRun plugin, Map map, int teamSize) {
        this.plugin = plugin;
        this.map = map;
        this.name = map.getName();
        border = new Border(map);
        teamManager = new TeamManager(teamSize);
    }

    public void startCountdown() {
        timer = 10; //TODO: config constant
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            timer--;

            if(timer <= 0) {
                startGame();
                Bukkit.getScheduler().cancelTask(task);
            }
        },0 ,20L);
    }

    public void addTeam(Party party) {
        if(!canAddPlayers)
            return;

        teamManager.addTeam(new Team(teamManager.getTeamSize(), party));
    }

    public void addPlayer(Player player) {
        if(!canAddPlayers)
            return;

        teamManager.addOrCreateTeam(player);

        if(teamManager.getTeams().size() >= MIN_PLAYERS) {
            startCountdown();
        }

        if(started) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 10));
            player.setFallDistance(10000);
        }
    }

    public void removePlayer(Player player) {
        teamManager.remove(player);

        player.teleport(Bukkit.getWorld("world").getSpawnLocation()); //TODO: world

        if(player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

        if(teamManager.getPlayers().size() <= 0)
            stopGame();
    }

    public Player getPlayer(String name) {
        return teamManager.getPlayer(name);
    }

    public void miningPeriod() {
        for(Player player : teamManager.getPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 10));
            player.setFallDistance(10000);
        }
        map.getWorld().setPVP(false);

        timer = 10; //TODO: mining period
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            timer--;
            if(timer <= 0) {
                pvpPeriod();
                Bukkit.getScheduler().cancelTask(task);
            }
        },0 ,20L);
    }

    public boolean isInGame(Player player) {
        return teamManager.getPlayers().contains(player);
    }

    private void pvpPeriod() {
        border.shrink();
        map.getWorld().setPVP(true);
    }
    
    public void startGame() {
        spawnTeams();
        started = true;
        border.start();
    }

    private void spawnTeams() {
        double gameArea = border.getSize();
        int players = teamManager.getPlayers().size();

        double spaceBetweenSpawns = gameArea / (double) players;

        //TODO: spawn players
        for(Player player : teamManager.getPlayers())
            player.teleport(new Location(map.getWorld(), 0, map.getWorld().getHighestBlockYAt(0, 0)+1, 0));
    }

    public void stopGame() {
        removePlayers();
        started = false;
        border.reset();
        border = null;
        map.destroyWorld();
        map.setGame(null);
        plugin.getGameManager().restartGame();
    }

    public void  removePlayers() {
        for(Player player : teamManager.getPlayers())
            player.teleport(new Location(Bukkit.getWorld("world"), 0, Bukkit.getWorld("world").getHighestBlockYAt(0, 0)+1, 0));
    }

    public int getTeamSize() {
        return teamManager.getTeamSize();
    }

    public boolean isStarted() {
        return started;
    }

    public Set<Player> getPlayers() {
        return teamManager.getPlayers();
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }
}
