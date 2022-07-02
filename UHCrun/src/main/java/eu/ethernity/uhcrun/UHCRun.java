package eu.ethernity.uhcrun;

import eu.ethernity.uhcrun.commands.Commands;
import eu.ethernity.uhcrun.commands.PartyCommands;
import eu.ethernity.uhcrun.commands.player.JoinCommand;
import eu.ethernity.uhcrun.commands.player.LeaveCommand;
import eu.ethernity.uhcrun.configs.GameConfig;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.game.GameManager;
import eu.ethernity.uhcrun.listeners.GameListener;
import eu.ethernity.uhcrun.players.party.PartyManager;
import eu.ethernity.uhcrun.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class UHCRun extends JavaPlugin {
    private GameManager gameManager;
    private PartyManager partyManager;

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, ChatColor.GOLD+"Starting UHCRun plugin!");
        try {
            saveDefaultConfig();
            new Logger();
            new MessagesConfig(this);
            new GameConfig(this);

            gameManager = new GameManager(this);
            partyManager = new PartyManager(this);

            initCommands();
            initListeners();

            Bukkit.getLogger().log(Level.INFO, ChatColor.GOLD+"Started UHCRun plugin!");
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
            if(getConfig().getBoolean("debug"))
                e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            gameManager.removePlayers();
            gameManager.getGame().getMap().destroyWorld();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
            if(getConfig().getBoolean("debug"))
                e.printStackTrace();
        }
    }

    private void initCommands() {
        getCommand("uhcrun").setExecutor(new Commands(this));
        getCommand("join").setExecutor(new JoinCommand(this));
        getCommand("leave").setExecutor(new LeaveCommand(this));
        getCommand("party").setExecutor(new PartyCommands(this));
    }

    private void initListeners() {
        initListener(new GameListener(this));
    }

    private void initListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }
}

// Made by Martinecko30
// This code is not to be reproduced, rewritten or reused under any circumstances without written approval by me
// If this is broken, I will be seeking legal action
// Rights for use are permitted for Etherity.eu