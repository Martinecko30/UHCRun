package eu.ethernity.uhcrun.inventory;

import eu.ethernity.uhcrun.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.LinkedList;
import java.util.List;

public class SetupInventory {

    private Map map;
    private Inventory inv;

    public SetupInventory(Map map) {
        inv = Bukkit.createInventory(null, 9, map.getName());

        initializeItems();
    }

    private void initializeItems() {
        List<String> lore = new LinkedList<>();
        lore.add("lore");
        inv.setItem(4 ,createGuiItem(Material.STAINED_GLASS_PANE, (short) 13, "Add start", 1, lore));
        lore.clear();
        lore.add("lore 2");
        inv.setItem(6, createGuiItem(Material.STAINED_GLASS_PANE, (short) 14, "Remove start", 1, lore));
        lore.clear();
        lore.add("Nastavi stred mapy na tvojej po");
        inv.setItem(6, createGuiItem(Material.STAINED_GLASS_PANE, (short) 14, "Remove start", 1, lore));
    }

    protected ItemStack createGuiItem(final Material material, short color, final String name, int amount,  final List<String > lore) {
        final ItemStack item = new ItemStack(material, amount, color);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createGuiItem(final Material material, final String name, int amount,  final List<String > lore) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public Inventory getInv() {
        return inv;
    }
}
