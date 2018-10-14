package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author einTosti
 */
public class EffectInventory {
    private Troll plugin;
    private InventoryManager inventoryManager;

    public EffectInventory(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
    }

    private Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("effect_guiName"));
        fillWithGlass(inventory);

        addItemStack(inventory, 0, Material.EYE_OF_ENDER, 0, plugin.getString("effect_blindness"));
        addItemStack(inventory, 1, Material.COMPASS, 0, plugin.getString("effect_nausea"));
        addItemStack(inventory, 2, Material.DIAMOND_CHESTPLATE, 0, plugin.getString("effect_resistance"));
        addItemStack(inventory, 3, Material.DIAMOND_PICKAXE, 0, plugin.getString("effect_haste"));
        addItemStack(inventory, 4, Material.LAVA_BUCKET, 0, plugin.getString("effect_fireResistance"));
        addItemStack(inventory, 5, Material.ROTTEN_FLESH, 0, plugin.getString("effect_hunger"));
        addItemStack(inventory, 6, Material.DIAMOND_SWORD, 0, plugin.getString("effect_strength"));
        addItemStack(inventory, 7, Material.GLASS, 0, plugin.getString("effect_invisibility"));
        addItemStack(inventory, 8, Material.RABBIT_FOOT, 0, plugin.getString("effect_jumpBoost"));
        addItemStack(inventory, 9, Material.GOLDEN_CARROT, 0, plugin.getString("effect_nightVision"));
        addItemStack(inventory, 10, Material.FERMENTED_SPIDER_EYE, 0, plugin.getString("effect_poison"));
        addItemStack(inventory, 11, Material.GOLDEN_APPLE, 0, plugin.getString("effect_regeneration"));
        addItemStack(inventory, 12, Material.SOUL_SAND, 0, plugin.getString("effect_slowness"));
        addItemStack(inventory, 13, Material.WOOD_PICKAXE, 0, plugin.getString("effect_miningFatigue"));
        addItemStack(inventory, 14, Material.SUGAR, 0, plugin.getString("effect_speed"));
        addItemStack(inventory, 15, Material.WATER_BUCKET, 0, plugin.getString("effect_waterBreathing"));
        addItemStack(inventory, 16, Material.WOOD_SWORD, 0, plugin.getString("effect_weakness"));

        inventoryManager.addItemStack(inventory, 20, Material.FLINT_AND_STEEL, 0, "§d" + plugin.getString("effect_burn"));
        inventoryManager.addItemStack(inventory, 24, Material.MILK_BUCKET, 0, "§d" + plugin.getString("effect_removeEffects"));

        return inventory;
    }

    private void addItemStack(Inventory inventory, int position, Material material, int id, String effect) {
        inventoryManager.addItemStack(inventory, position, material, id, plugin.getString("effect_itemName").replace("%effect%", effect));
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory());
    }

    private void fillWithGlass(Inventory inventory) {
        for (int i = 17; i <= 19; i++) {
            inventoryManager.addGlassPane(inventory, i);
        }
        for (int i = 21; i <= 23; i++) {
            inventoryManager.addGlassPane(inventory, i);
        }
        for (int i = 25; i <= 26; i++) {
            inventoryManager.addGlassPane(inventory, i);
        }
    }
}
