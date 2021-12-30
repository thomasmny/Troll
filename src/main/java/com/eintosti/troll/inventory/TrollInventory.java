package com.eintosti.troll.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import com.eintosti.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author einTosti
 */
public class TrollInventory {

    private final Troll plugin;
    private final TrollManager trollManager;

    public TrollInventory(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();
    }

    private Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("main_guiName"));
        fillWithGlass(inventory);

        inventory.setItem(3, new ItemBuilder(XMaterial.DIAMOND_SWORD).name(plugin.getString("main_killPlayers")).lore(plugin.getString("main_killPlayers_lore")).build());
        inventory.setItem(4, new ItemBuilder(XMaterial.BLAZE_POWDER).name(plugin.getString("main_effectItem")).lore(plugin.getString("main_effectItem_lore")).build());
        inventory.setItem(5, new ItemBuilder(XMaterial.ENDER_PEARL).name(plugin.getString("main_playerTeleport")).lore(plugin.getString("main_playerTeleport_lore")).build());

        addGamemodeItem(player, inventory);
        addKnockBackItem(player, inventory);
        addVanishItem(player, inventory);

        inventory.setItem(15, new ItemBuilder(XMaterial.IRON_AXE).name(plugin.getString("main_thorHammer")).lore(plugin.getString("main_thorHammer_lore")).build());
        inventory.setItem(16, new ItemBuilder(XMaterial.TNT).name(plugin.getString("main_tntRain")).lore(plugin.getString("main_tntRain_lore")).build());
        inventory.setItem(17, new ItemBuilder(XMaterial.FIRE_CHARGE).name(plugin.getString("main_judgementDay")).lore(plugin.getString("main_judgementDay_lore")).build());

        inventory.setItem(22, new ItemBuilder(XMaterial.NETHER_STAR).name(plugin.getString("main_settings")).lore(plugin.getString("main_settings_lore")).build());

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private void addGamemodeItem(Player player, Inventory inventory) {
        String displayName = plugin.getString("main_gamemodeItem_disabled");
        Enchantment enchantment = null;

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            displayName = plugin.getString("main_gamemodeItem_enabled");
            enchantment = Enchantment.DURABILITY;
        }

        inventory.setItem(9, new ItemBuilder(XMaterial.GRASS_BLOCK)
                .name(displayName)
                .enchant(enchantment)
                .flags()
                .lore(plugin.getString("main_gamemodeItem_lore"))
                .build()
        );
    }

    private void addKnockBackItem(Player player, Inventory inventory) {
        String displayName = plugin.getString("main_knockbackItem_disabled");
        Enchantment enchantment = null;

        if (trollManager.isKnockBackPlayer(player.getUniqueId())) {
            displayName = plugin.getString("main_knockbackItem_enabled");
            enchantment = Enchantment.DURABILITY;
        }

        inventory.setItem(10, new ItemBuilder(XMaterial.FEATHER)
                .name(displayName)
                .enchant(enchantment)
                .flags()
                .lore(plugin.getString("main_knockbackItem_lore"))
                .build()
        );
    }

    private void addVanishItem(Player player, Inventory inventory) {
        String displayName = plugin.getString("main_vanishItem_disabled");
        Enchantment enchantment = null;

        if (trollManager.isVanishedPlayer(player.getUniqueId())) {
            displayName = plugin.getString("main_vanishItem_enabled");
            enchantment = Enchantment.DURABILITY;
        }

        inventory.setItem(11, new ItemBuilder(XMaterial.QUARTZ)
                .name(displayName)
                .enchant(enchantment)
                .flags()
                .lore(plugin.getString("main_vanishItem_lore"))
                .build()
        );
    }

    private void fillWithGlass(Inventory inventory) {
        final ItemStack fillerGlass = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&0").build();
        final int[] slots = new int[]{0, 1, 2, 6, 7, 8, 12, 13, 14, 18, 19, 20, 21, 23, 24, 25, 26};

        for (int slot : slots) {
            inventory.setItem(slot, fillerGlass);
        }
    }
}
