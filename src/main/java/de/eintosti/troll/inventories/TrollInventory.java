package de.eintosti.troll.inventories;

import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author einTosti
 */
public class TrollInventory {
    private static TrollInventory instance;

    public static synchronized TrollInventory getInstance() {
        if (instance == null) {
            instance = new TrollInventory();
        }
        return instance;
    }

    private Inventory getTrollInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.getInstance().getString("main_guiName"));
        fillGuiWithGlass(inv);

        Utils.getInstance().addLoreItemStack(inv, 3, Material.DIAMOND_SWORD, 0, Utils.getInstance().getString("main_killPlayers"), "§7", Utils.getInstance().getString("main_killPlayers_lore"));
        Utils.getInstance().addLoreItemStack(inv, 4, Material.BLAZE_POWDER, 0, Utils.getInstance().getString("main_effectItem"), "§7", Utils.getInstance().getString("main_effectItem_lore"));
        Utils.getInstance().addLoreItemStack(inv, 5, Material.ENDER_PEARL, 0, Utils.getInstance().getString("main_playerTeleport"), "§7", Utils.getInstance().getString("main_playerTeleport_lore"));

        addGamemodeItem(player, inv);
        addKnockbackItem(player, inv);
        addVanishItem(player, inv);

        Utils.getInstance().addLoreItemStack(inv, 15, Material.IRON_AXE, 0, Utils.getInstance().getString("main_thorHammer"), "§7", Utils.getInstance().getString("main_thorHammer_lore"));
        Utils.getInstance().addLoreItemStack(inv, 16, Material.TNT, 0, Utils.getInstance().getString("main_tntRain"), "§7", Utils.getInstance().getString("main_tntRain_lore"));
        Utils.getInstance().addLoreItemStack(inv, 17, Material.FIREBALL, 0, Utils.getInstance().getString("main_judgementDay"), "§7",Utils.getInstance().getString("main_judgementDay_lore"));

        Utils.getInstance().addLoreItemStack(inv, 22, Material.NETHER_STAR, 0, Utils.getInstance().getString("main_settings"), "§7", Utils.getInstance().getString("main_settings_lore"));

        return inv;
    }

    private void addGamemodeItem(Player player, Inventory inv) {
        String displayName = Utils.getInstance().getString("main_gamemodeItem_disabled");

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            displayName = Utils.getInstance().getString("main_gamemodeItem_enabled");
        }
        Utils.getInstance().addLoreItemStack(inv, 9, Material.GRASS, 0, displayName, "§7", Utils.getInstance().getString("main_gamemodeItem_lore"));
    }

    private void addVanishItem(Player player, Inventory inv) {
        String displayName = Utils.getInstance().getString("main_vanishItem_disabled");

        if (Utils.getInstance().mVanishedPlayers.contains(player.getUniqueId())) {
            displayName = Utils.getInstance().getString("main_vanishItem_enabled");
        }
        Utils.getInstance().addLoreItemStack(inv, 11, Material.QUARTZ, 0, displayName, "§7", Utils.getInstance().getString("main_vanishItem_lore"));
    }

    private void addKnockbackItem(Player player, Inventory inv) {
        String displayName = Utils.getInstance().getString("main_knockbackItem_disabled");

        if (Utils.getInstance().mKnockbackPlayers.contains(player.getUniqueId())) {
            displayName = Utils.getInstance().getString("main_knockbackItem_enabled");
        }
        Utils.getInstance().addLoreItemStack(inv, 10, Material.FEATHER, 0, displayName, "§7", Utils.getInstance().getString("main_knockbackItem_lore"));
    }

    private void fillGuiWithGlass(Inventory inv) {
        for (int i = 0; i <= 2; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        for (int i = 6; i <= 8; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        for (int i = 12; i <= 14; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        for (int i = 18; i <= 21; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        for (int i = 23; i <= 26; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
    }

    public void openInventory(Player player) {
        Inventory inv = getTrollInventory(player);
        player.openInventory(inv);
    }
}
