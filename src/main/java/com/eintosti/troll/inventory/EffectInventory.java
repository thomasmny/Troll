package com.eintosti.troll.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.eintosti.troll.Troll;
import com.eintosti.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author einTosti
 */
public class EffectInventory {

    private final Troll plugin;

    public EffectInventory(Troll plugin) {
        this.plugin = plugin;
    }

    private Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("effect_guiName"));
        fillWithGlass(inventory);

        addItemStack(inventory, 0, XMaterial.ENDER_EYE, plugin.getString("effect_blindness"));
        addItemStack(inventory, 1, XMaterial.COMPASS, plugin.getString("effect_nausea"));
        addItemStack(inventory, 2, XMaterial.DIAMOND_CHESTPLATE, plugin.getString("effect_resistance"));
        addItemStack(inventory, 3, XMaterial.DIAMOND_PICKAXE, plugin.getString("effect_haste"));
        addItemStack(inventory, 4, XMaterial.LAVA_BUCKET, plugin.getString("effect_fireResistance"));
        addItemStack(inventory, 5, XMaterial.ROTTEN_FLESH, plugin.getString("effect_hunger"));
        addItemStack(inventory, 6, XMaterial.DIAMOND_SWORD, plugin.getString("effect_strength"));
        addItemStack(inventory, 7, XMaterial.GLASS, plugin.getString("effect_invisibility"));
        addItemStack(inventory, 8, XMaterial.RABBIT_FOOT, plugin.getString("effect_jumpBoost"));
        addItemStack(inventory, 9, XMaterial.GOLDEN_CARROT, plugin.getString("effect_nightVision"));
        addItemStack(inventory, 10, XMaterial.FERMENTED_SPIDER_EYE, plugin.getString("effect_poison"));
        addItemStack(inventory, 11, XMaterial.GOLDEN_APPLE, plugin.getString("effect_regeneration"));
        addItemStack(inventory, 12, XMaterial.SOUL_SAND, plugin.getString("effect_slowness"));
        addItemStack(inventory, 13, XMaterial.WOODEN_PICKAXE, plugin.getString("effect_miningFatigue"));
        addItemStack(inventory, 14, XMaterial.SUGAR, plugin.getString("effect_speed"));
        addItemStack(inventory, 15, XMaterial.WATER_BUCKET, plugin.getString("effect_waterBreathing"));
        addItemStack(inventory, 16, XMaterial.WOODEN_SWORD, plugin.getString("effect_weakness"));

        inventory.setItem(20, new ItemBuilder(XMaterial.FLINT_AND_STEEL).name(plugin.getString("effect_burn")).build());
        inventory.setItem(24, new ItemBuilder(XMaterial.MILK_BUCKET).name(plugin.getString("effect_removeEffects")).build());

        return inventory;
    }

    private void addItemStack(Inventory inventory, int position, XMaterial material, String effect) {
        inventory.setItem(position, new ItemBuilder(material)
                .name(plugin.getString("effect_itemName").replace("%effect%", effect))
                .build()
        );
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory());
    }

    private void fillWithGlass(Inventory inventory) {
        final ItemStack fillerGlass = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&0").build();

        for (int i = 17; i <= 19; i++) {
            inventory.setItem(i, fillerGlass);
        }

        for (int i = 21; i <= 23; i++) {
            inventory.setItem(i, fillerGlass);
        }

        for (int i = 25; i <= 26; i++) {
            inventory.setItem(i, fillerGlass);
        }
    }
}
