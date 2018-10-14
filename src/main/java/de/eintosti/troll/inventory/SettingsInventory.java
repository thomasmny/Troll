package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author einTosti
 */
public class SettingsInventory {
    private Troll plugin;
    private InventoryManager inventoryManager;
    private TrollManager trollManager;

    public SettingsInventory(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.trollManager = plugin.getTrollManager();
    }

    private Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("settings_guiName"));
        fillWithGlass(inventory);

        inventoryManager.addItemStack(inventory, 9, Material.STONE, 0, plugin.getString("settings_blockInteractions"));
        inventoryManager.addItemStack(inventory, 10, Material.TNT, 0, plugin.getString("settings_blockDamage"));
        inventoryManager.addItemStack(inventory, 11, Material.SANDSTONE, 0, plugin.getString("settings_placeBlocks"));
        inventoryManager.addItemStack(inventory, 12, Material.DIAMOND_BOOTS, 0, plugin.getString("settings_fallDamage"));
        inventoryManager.addItemStack(inventory, 13, Material.COOKED_BEEF, 0, plugin.getString("settings_hunger"));
        inventoryManager.addItemStack(inventory, 14, Material.PAPER, 0, plugin.getString("settings_chat"));

        addInteractionItem(inventory);
        addBlockDamageItem(inventory);
        addPlaceBlocksItem(inventory);
        addFallDamageItem(inventory);
        addHungerItem(inventory);
        addChatItem(inventory);

        inventoryManager.addSkull(inventory, 16, plugin.getString("settings_gamemode"), player.getName());
        inventoryManager.addItemStack(inventory, 17, Material.RAW_FISH, 3, plugin.getString("settings_permissions"));

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private void addInteractionItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getInteractions()) {
            displayName = "§a✔";
            id = 10;
        }
       inventoryManager.addItemStack(inv, 18, Material.INK_SACK, id, displayName);
    }

    private void addBlockDamageItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getBlockDamage()) {
            displayName = "§a✔";
            id = 10;
        }
        inventoryManager.addItemStack(inv, 19, Material.INK_SACK, id, displayName);
    }

    private void addPlaceBlocksItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getPlaceBlocks()) {
            displayName = "§a✔";
            id = 10;
        }
        inventoryManager.addItemStack(inv, 20, Material.INK_SACK, id, displayName);
    }

    private void addFallDamageItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getFallDamage()) {
            displayName = "§a✔";
            id = 10;
        }
        inventoryManager.addItemStack(inv, 21, Material.INK_SACK, id, displayName);
    }

    private void addHungerItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getHunger()) {
            displayName = "§a✔";
            id = 10;
        }
        inventoryManager.addItemStack(inv, 22, Material.INK_SACK, id, displayName);
    }

    private void addChatItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getChat()) {
            displayName = "§a✔";
            id = 10;
        }
        inventoryManager.addItemStack(inv, 23, Material.INK_SACK, id, displayName);
    }

    private void fillWithGlass(Inventory inv) {
        for (int i = 0; i <= 8; i++) {
            inventoryManager.addGlassPane(inv, i);
        }
        inventoryManager.addGlassPane(inv, 15);
        for (int i = 24; i <= 26; i++) {
            inventoryManager.addGlassPane(inv, i);
        }
    }
}
