package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import de.eintosti.troll.util.external.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

        inventoryManager.addItemStack(inventory, 3, XMaterial.DIAMOND_SWORD, plugin.getString("main_killPlayers"), plugin.getString("main_killPlayers_lore"));
        inventoryManager.addItemStack(inventory, 4, XMaterial.BLAZE_POWDER, plugin.getString("main_effectItem"), plugin.getString("main_effectItem_lore"));
        inventoryManager.addItemStack(inventory, 5, XMaterial.ENDER_PEARL, plugin.getString("main_playerTeleport"), plugin.getString("main_playerTeleport_lore"));

        addGamemodeItem(player, inventory);
        addKnockbackItem(player, inventory);
        addVanishItem(player, inventory);

        inventoryManager.addItemStack(inventory, 15, XMaterial.IRON_AXE, plugin.getString("main_thorHammer"), plugin.getString("main_thorHammer_lore"));
        inventoryManager.addItemStack(inventory, 16, XMaterial.TNT, plugin.getString("main_tntRain"), plugin.getString("main_tntRain_lore"));
        inventoryManager.addItemStack(inventory, 17, XMaterial.FIRE_CHARGE, plugin.getString("main_judgementDay"), plugin.getString("main_judgementDay_lore"));

        inventoryManager.addItemStack(inventory, 22, XMaterial.NETHER_STAR, plugin.getString("main_settings"), plugin.getString("main_settings_lore"));

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private void addGamemodeItem(Player player, Inventory inv) {
        String displayName = plugin.getString("main_gamemodeItem_disabled");
        boolean enchanted = false;

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            displayName = plugin.getString("main_gamemodeItem_enabled");
            enchanted = true;
        }
        inventoryManager.addEnchantedItemStack(inv, 9, XMaterial.GRASS_BLOCK, displayName, enchanted, plugin.getString("main_gamemodeItem_lore"));
    }

    private void addVanishItem(Player player, Inventory inv) {
        String displayName = plugin.getString("main_vanishItem_disabled");
        boolean enchanted = false;

        if (trollManager.getVanishedPlayers().contains(player.getUniqueId())) {
            displayName = plugin.getString("main_vanishItem_enabled");
            enchanted = true;
        }
        inventoryManager.addEnchantedItemStack(inv, 11, XMaterial.QUARTZ, displayName, enchanted, plugin.getString("main_vanishItem_lore"));
    }

    private void addKnockbackItem(Player player, Inventory inv) {
        String displayName = plugin.getString("main_knockbackItem_disabled");
        boolean enchanted = false;

        if (trollManager.getKnockbackPlayers().contains(player.getUniqueId())) {
            displayName = plugin.getString("main_knockbackItem_enabled");
            enchanted = true;
        }
        inventoryManager.addEnchantedItemStack(inv, 10, XMaterial.FEATHER, displayName, enchanted, plugin.getString("main_knockbackItem_lore"));
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
