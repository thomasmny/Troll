package de.eintosti.troll.inventories;

import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author einTosti
 */
public class EffectInventory {
    private static EffectInventory instance;

    public static synchronized EffectInventory getInstance() {
        if (instance == null) {
            instance = new EffectInventory();
        }
        return instance;
    }

    private Inventory getEffectInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.getInstance().getString("effect_guiName"));
        fillGuiWithGlass(inv);

        addEffectItemStack(inv, 0, Material.EYE_OF_ENDER, 0, Utils.getInstance().getString("effect_blindness"));
        addEffectItemStack(inv, 1, Material.COMPASS, 0, Utils.getInstance().getString("effect_nausea"));
        addEffectItemStack(inv, 2, Material.DIAMOND_CHESTPLATE, 0, Utils.getInstance().getString("effect_resistance"));
        addEffectItemStack(inv, 3, Material.DIAMOND_PICKAXE, 0, Utils.getInstance().getString("effect_haste"));
        addEffectItemStack(inv, 4, Material.LAVA_BUCKET, 0, Utils.getInstance().getString("effect_fireResistance"));
        addEffectItemStack(inv, 5, Material.ROTTEN_FLESH, 0, Utils.getInstance().getString("effect_hunger"));
        addEffectItemStack(inv, 6, Material.DIAMOND_SWORD, 0, Utils.getInstance().getString("effect_strength"));
        addEffectItemStack(inv, 7, Material.GLASS, 0, Utils.getInstance().getString("effect_invisibility"));
        addEffectItemStack(inv, 8, Material.RABBIT_FOOT, 0, Utils.getInstance().getString("effect_jumpBoost"));
        addEffectItemStack(inv, 9, Material.GOLDEN_CARROT, 0, Utils.getInstance().getString("effect_nightVision"));
        addEffectItemStack(inv, 10, Material.FERMENTED_SPIDER_EYE, 0, Utils.getInstance().getString("effect_poison"));
        addEffectItemStack(inv, 11, Material.GOLDEN_APPLE, 0, Utils.getInstance().getString("effect_regeneration"));
        addEffectItemStack(inv, 12, Material.SOUL_SAND, 0, Utils.getInstance().getString("effect_slowness"));
        addEffectItemStack(inv, 13, Material.WOOD_PICKAXE, 0, Utils.getInstance().getString("effect_miningFatigue"));
        addEffectItemStack(inv, 14, Material.SUGAR, 0, Utils.getInstance().getString("effect_speed"));
        addEffectItemStack(inv, 15, Material.WATER_BUCKET, 0, Utils.getInstance().getString("effect_waterBreathing"));
        addEffectItemStack(inv, 16, Material.WOOD_SWORD, 0, Utils.getInstance().getString("effect_weakness"));

        Utils.getInstance().addSimpleItemStack(inv, 20, Material.FLINT_AND_STEEL, 0, "§d" + Utils.getInstance().getString("effect_burn"));
        Utils.getInstance().addSimpleItemStack(inv, 24, Material.MILK_BUCKET, 0, "§d" + Utils.getInstance().getString("effect_removeEffects"));

        return inv;
    }

    private void addEffectItemStack(Inventory inv, int position, Material material, int id, String effect) {
        Utils.getInstance().addSimpleItemStack(inv, position, material, id, Utils.getInstance().getString("effect_itemName").replace("%effect%", effect));
    }

    public void openInventory(Player player) {
        Inventory inv = getEffectInventory();
        player.openInventory(inv);
    }

    private void fillGuiWithGlass(Inventory inv) {
        for (int i = 17; i <= 19; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        for (int i = 21; i <= 23; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        for (int i = 25; i <= 26; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
    }
}
