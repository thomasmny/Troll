package com.eintosti.troll.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import com.eintosti.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author einTosti
 */
public class SettingsInventory {

    private final Troll plugin;
    private final TrollManager trollManager;

    public SettingsInventory(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();
    }

    private Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("settings_guiName"));
        fillWithGlass(inventory);

        inventory.setItem(9, new ItemBuilder(XMaterial.STONE).name(plugin.getString("settings_blockInteractions")).build());
        inventory.setItem(10, new ItemBuilder(XMaterial.TNT).name(plugin.getString("settings_blockDamage")).build());
        inventory.setItem(11, new ItemBuilder(XMaterial.SANDSTONE).name(plugin.getString("settings_placeBlocks")).build());
        inventory.setItem(12, new ItemBuilder(XMaterial.DIAMOND_BOOTS).name(plugin.getString("settings_fallDamage")).build());
        inventory.setItem(13, new ItemBuilder(XMaterial.COOKED_BEEF).name(plugin.getString("settings_hunger")).build());
        inventory.setItem(14, new ItemBuilder(XMaterial.PAPER).name(plugin.getString("settings_chat")).build());

        addSettingsItem(inventory, trollManager.isInteractions(), 18);
        addSettingsItem(inventory, trollManager.isBlockDamage(), 19);
        addSettingsItem(inventory, trollManager.isPlaceBlocks(), 20);
        addSettingsItem(inventory, trollManager.isFallDamage(), 21);
        addSettingsItem(inventory, trollManager.isHunger(), 22);
        addSettingsItem(inventory, trollManager.isChat(), 23);

        inventory.setItem(16, new ItemBuilder(XMaterial.PLAYER_HEAD).name(plugin.getString("settings_gamemode")).skull(player.getName()).build());
        inventory.setItem(17, new ItemBuilder(XMaterial.PUFFERFISH).name(plugin.getString("settings_permissions")).build());

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private void addSettingsItem(Inventory inventory, boolean enabled, int position) {
        String displayName = "§7✘";
        XMaterial material = XMaterial.GRAY_DYE;

        if (enabled) {
            displayName = "§a✔";
            material = XMaterial.LIME_DYE;
        }

        inventory.setItem(position, new ItemBuilder(material).name(displayName).build());
    }

    private void fillWithGlass(Inventory inventory) {
        final ItemStack fillerGlass = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&0").build();

        for (int i = 0; i <= 8; i++) {
            inventory.setItem(i, fillerGlass);
        }

        inventory.setItem(15, fillerGlass);

        for (int i = 24; i <= 26; i++) {
            inventory.setItem(i, fillerGlass);
        }
    }
}
