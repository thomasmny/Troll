package de.eintosti.troll.manager;

import de.eintosti.troll.util.external.XMaterial;
import org.bukkit.enchantments.Enchantment;
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
    @SuppressWarnings("deprecation")
    public ItemStack getItemStack(XMaterial material, String displayName, String... lore) {
        ItemStack itemStack = material.parseItem(true);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        itemMeta.addItemFlags(ItemFlag.values());

        itemStack.setAmount(1);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack addItemStack(Inventory inventory, int position, XMaterial material, String displayName, String... lore) {
        ItemStack itemStack = getItemStack(material, displayName, lore);
        inventory.setItem(position, itemStack);
        return itemStack;
    }

    public ItemStack addEnchantedItemStack(Inventory inventory, int position, XMaterial material, String displayName, boolean enchanted, String... lore) {
        ItemStack itemStack = getItemStack(material, displayName, lore);
        if (enchanted) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);
            itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        inventory.setItem(position, itemStack);
        return itemStack;
    }

    public void addGlassPane(Inventory inventory, int position) {
        inventory.setItem(position, getItemStack(XMaterial.BLACK_STAINED_GLASS_PANE, " "));
    }

    @SuppressWarnings("deprecation")
    public ItemStack getSkull(String displayName, String skullOwner, String... lore) {
        ItemStack itemStack = XMaterial.PLAYER_HEAD.parseItem(true);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(skullOwner);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(Arrays.asList(lore));

        itemStack.setAmount(1);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public void addSkull(Inventory inventory, int position, String displayName, String skullOwner, String... lore) {
        inventory.setItem(position, getSkull(displayName, skullOwner, lore));
    }
}
