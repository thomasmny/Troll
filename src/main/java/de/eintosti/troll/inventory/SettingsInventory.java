package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import de.eintosti.troll.util.external.XMaterial;
import org.bukkit.Bukkit;
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

        inventoryManager.addItemStack(inventory, 9, XMaterial.STONE, plugin.getString("settings_blockInteractions"));
        inventoryManager.addItemStack(inventory, 10, XMaterial.TNT, plugin.getString("settings_blockDamage"));
        inventoryManager.addItemStack(inventory, 11, XMaterial.SANDSTONE, plugin.getString("settings_placeBlocks"));
        inventoryManager.addItemStack(inventory, 12, XMaterial.DIAMOND_BOOTS, plugin.getString("settings_fallDamage"));
        inventoryManager.addItemStack(inventory, 13, XMaterial.COOKED_BEEF, plugin.getString("settings_hunger"));
        inventoryManager.addItemStack(inventory, 14, XMaterial.PAPER, plugin.getString("settings_chat"));

        addSettingsItem(inventory, trollManager.getInteractions(), 18);
        addSettingsItem(inventory, trollManager.getBlockDamage(), 19);
        addSettingsItem(inventory, trollManager.getPlaceBlocks(), 20);
        addSettingsItem(inventory, trollManager.getFallDamage(), 21);
        addSettingsItem(inventory, trollManager.getHunger(), 22);
        addSettingsItem(inventory, trollManager.getChat(), 23);

        inventoryManager.addSkull(inventory, 16, plugin.getString("settings_gamemode"), player.getName());
        inventoryManager.addItemStack(inventory, 17, XMaterial.PUFFERFISH, plugin.getString("settings_permissions"));

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private void addSettingsItem(Inventory inventory, boolean b, int position) {
        String displayName = "§7✘";
        XMaterial material = XMaterial.GRAY_DYE;

        if (b) {
            displayName = "§a✔";
            material = XMaterial.LIME_DYE;
        }
        inventoryManager.addItemStack(inventory, position, material, displayName);
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
