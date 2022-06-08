package eu.ethernity.uhcrun.listeners;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.inventory.SetupInventory;
import eu.ethernity.uhcrun.map.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class SetupInventoryListener implements Listener {

    private UHCRun plugin;

    public SetupInventoryListener(UHCRun plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!plugin.getMapManager().checkInv(e.getInventory())) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || !clickedItem.getType().isBlock()) return;

        final Player p = (Player) e.getWhoClicked();
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (plugin.getMapManager().checkInv(e.getInventory()))
            e.setCancelled(true);
    }
}
