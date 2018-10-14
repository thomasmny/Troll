package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author einTosti
 */
public class TrollInventory {
    private Troll plugin;
    private InventoryManager inventoryManager;
    private TrollManager trollManager;

    public TrollInventory(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.trollManager = plugin.getTrollManager();
    }

    private Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("main_guiName"));
        fillWithGlass(inventory);

        inventoryManager.addItemStack(inventory, 3, Material.DIAMOND_SWORD, 0, plugin.getString("main_killPlayers"), "§7", plugin.getString("main_killPlayers_lore"));
        inventoryManager.addItemStack(inventory, 4, Material.BLAZE_POWDER, 0, plugin.getString("main_effectItem"), "§7", plugin.getString("main_effectItem_lore"));
        inventoryManager.addItemStack(inventory, 5, Material.ENDER_PEARL, 0, plugin.getString("main_playerTeleport"), "§7", plugin.getString("main_playerTeleport_lore"));

        addGamemodeItem(player, inventory);
        addKnockbackItem(player, inventory);
        addVanishItem(player, inventory);

        inventoryManager.addItemStack(inventory, 15, Material.IRON_AXE, 0, plugin.getString("main_thorHammer"), "§7", plugin.getString("main_thorHammer_lore"));
        inventoryManager.addItemStack(inventory, 16, Material.TNT, 0, plugin.getString("main_tntRain"), "§7", plugin.getString("main_tntRain_lore"));
        inventoryManager.addItemStack(inventory, 17, Material.FIREBALL, 0, plugin.getString("main_judgementDay"), "§7", plugin.getString("main_judgementDay_lore"));

        inventoryManager.addItemStack(inventory, 22, Material.NETHER_STAR, 0, plugin.getString("main_settings"), "§7", plugin.getString("main_settings_lore"));

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private void addGamemodeItem(Player player, Inventory inv) {
        String displayName = plugin.getString("main_gamemodeItem_disabled");

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            displayName = plugin.getString("main_gamemodeItem_enabled");
        }
        inventoryManager.addItemStack(inv, 9, Material.GRASS, 0, displayName, "§7", plugin.getString("main_gamemodeItem_lore"));
    }

    private void addVanishItem(Player player, Inventory inv) {
        String displayName = plugin.getString("main_vanishItem_disabled");

        if (trollManager.getVanishedPlayers().contains(player.getUniqueId())) {
            displayName = plugin.getString("main_vanishItem_enabled");
        }
        inventoryManager.addItemStack(inv, 11, Material.QUARTZ, 0, displayName, "§7", plugin.getString("main_vanishItem_lore"));
    }

    private void addKnockbackItem(Player player, Inventory inv) {
        String displayName = plugin.getString("main_knockbackItem_disabled");

        if (trollManager.getKnockbackPlayers().contains(player.getUniqueId())) {
            displayName = plugin.getString("main_knockbackItem_enabled");
        }
        inventoryManager.addItemStack(inv, 10, Material.FEATHER, 0, displayName, "§7", plugin.getString("main_knockbackItem_lore"));
    }

    private void fillWithGlass(Inventory inventory) {
        for (int i = 0; i <= 2; i++)
            inventoryManager.addGlassPane(inventory, i);
        for (int i = 6; i <= 8; i++)
            inventoryManager.addGlassPane(inventory, i);
        for (int i = 12; i <= 14; i++)
            inventoryManager.addGlassPane(inventory, i);
        for (int i = 18; i <= 21; i++)
            inventoryManager.addGlassPane(inventory, i);
        for (int i = 23; i <= 26; i++)
            inventoryManager.addGlassPane(inventory, i);
    }
}
