package de.eintosti.troll.manager;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

/**
 * @author einTosti
 */
public class InventoryManager {
    public ItemStack getItemStack(Material material, int id, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material, 1, (byte) id);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.values());

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack addItemStack(Inventory inv, int position, Material material, int id, String displayName, String... lore) {
        ItemStack itemStack = getItemStack(material, id, displayName, lore);
        inv.setItem(position, itemStack);
        return itemStack;
    }

    public void addGlassPane(Inventory inv, int position) {
        inv.setItem(position, getItemStack(Material.STAINED_GLASS_PANE, 15, " "));
    }

    @SuppressWarnings("deprecation")
    public ItemStack getSkull(String displayName, String skullOwner, String... lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwner(skullOwner);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(Arrays.asList(lore));
        skull.setItemMeta(skullMeta);

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public void addSkull(Inventory inv, int position, String displayName, String skullOwner, String... lore) {
        inv.setItem(position, getSkull(displayName, skullOwner, lore));
    }
}
