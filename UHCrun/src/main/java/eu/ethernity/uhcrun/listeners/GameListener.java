package eu.ethernity.uhcrun.listeners;

import eu.ethernity.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class GameListener implements Listener {

    private UHCRun plugin;

    public GameListener(UHCRun plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(!plugin.getGameManager().isInGame(event.getEntity()))
            return;

        event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        if(!plugin.getGameManager().isInGame((Player) event.getWhoClicked()))
            return;

        Material type = event.getCurrentItem().getType();
        if(type.name().endsWith("_AXE") || type.name().endsWith("_SPADE") || type.name().endsWith("_PICKAXE")) {
            ItemStack item = new ItemStack(event.getCurrentItem().getType(), 1);
            item.addEnchantment(Enchantment.DURABILITY, 3);
            item.addEnchantment(Enchantment.DIG_SPEED, 3);
            event.setCurrentItem(item);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!plugin.getGameManager().isInGame(event.getPlayer()))
            return;

        Random random = new Random();

        if(event.getBlock().getType() == Material.IRON_ORE) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
        }

        if(event.getBlock().getType() == Material.GOLD_ORE) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
        }

        if(event.getBlock().getType() == Material.COAL_ORE) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.TORCH, 1));
        }

        if(event.getBlock().getType() == Material.LAPIS_ORE) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.BOOK, random.nextInt(2)+1));
        }

        if(event.getBlock().getType() == Material.EMERALD_ORE) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(random.nextInt(event.getExpToDrop())+event.getExpToDrop());
        }

        if(event.getBlock().getType() == Material.LOG) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.WOOD, 4));
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        if(!plugin.getGameManager().isInGame(event.getPlayer()))
            return;

        Player player = event.getPlayer();

        if(event.getItem().getType() == Material.GOLDEN_APPLE) {

            player.setHealth(player.getHealth() + (player.getMaxHealth() - player.getHealth()));
        }
    }

    @EventHandler
    public void onPlayerRegen(EntityRegainHealthEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;
        if(!plugin.getGameManager().isInGame((Player) event.getEntity()))
            return;

        if(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if(!plugin.getGameManager().isInGame((Player) event.getPlayer()))
            return;

        plugin.getGameManager().getGame(event.getPlayer()).removePlayer(event.getPlayer());
    }
}