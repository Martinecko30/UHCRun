package eu.ethernity.uhcrun.inventory;

import eu.ethernity.uhcrun.map.Map;
import eu.ethernity.uhcrun.players.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.LinkedList;
import java.util.List;

public class PartyInventory {

    private final Party party;
    private Inventory inv;

    public PartyInventory(Party party) {
        inv = Bukkit.createInventory(null, 9, party.getName());
        this.party = party;

        initializeItems();
    }

    private void initializeItems() {
        List<String> lore = new LinkedList<>();
        lore.add("Prida hraca do tvojej party");
        inv.setItem(2 ,createGuiItem(Material.STAINED_GLASS_PANE, (short) 13, "Pridat hraca", 1, lore));
        lore.clear();
        lore.add("Odoberie hraca z tvojej party");
        inv.setItem(4, createGuiItem(Material.STAINED_GLASS_PANE, (short) 14, "Odstranit hraca", 1, lore));
        lore.clear();
        lore.add("Vymaze tvoju party");
        inv.setItem(8, createGuiItem(Material.STAINED_GLASS_PANE, (short) 14, "Odstranit party", 1, lore));
    }

    protected ItemStack createHead(String player, final String name, int amount, final List<String> lore) {
        final ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player);
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createGuiItem(final Material material, short color, final String name, int amount, final List<String> lore) {
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
