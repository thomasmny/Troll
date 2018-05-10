package de.eintosti.troll.inventories;

import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author einTosti
 */
public class SettingsInventory {
    private static SettingsInventory instance;

    public static synchronized SettingsInventory getInstance() {
        if (instance == null) {
            instance = new SettingsInventory();
        }
        return instance;
    }

    private Inventory getSettingsInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§5Erweiterte Einstellungen");
        fillSettingsGlass(inv);

        Utils.getInstance().addSimpleItemStack(inv, 9, Material.STONE, 0, "§dBlock Inteaktionen");
        Utils.getInstance().addSimpleItemStack(inv, 10, Material.TNT, 0, "§dBlockschaden");
        Utils.getInstance().addSimpleItemStack(inv, 11, Material.SANDSTONE, 0, "§dBlöcke setzen");
        Utils.getInstance().addSimpleItemStack(inv, 12, Material.DIAMOND_BOOTS, 0, "§dFallschaden");
        Utils.getInstance().addSimpleItemStack(inv, 13, Material.COOKED_BEEF, 0, "§dHunger");
        Utils.getInstance().addSimpleItemStack(inv, 14, Material.PAPER, 0, "§dChat");

        addInteractionItem(inv);
        addBlockDamageItem(inv);
        addPlaceBlocksItem(inv);
        addFallDamageItem(inv);
        addHungerItem(inv);
        addChatItem(inv);

        Utils.getInstance().addSkull(inv, 16, "§dGamemode Menü", player.getName());
        Utils.getInstance().addSimpleItemStack(inv, 17, Material.COOKIE, 0, "§dRechtesystem");

        return inv;
    }

    public void openInventory(Player player) {
        Inventory inv = getSettingsInventory(player);
        player.openInventory(inv);
    }

    /* Settings-Items */
    private void addInteractionItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mInteractions) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, 18, Material.INK_SACK, id, displayName);
    }

    private void addBlockDamageItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mBlockDamage) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, 19, Material.INK_SACK, id, displayName);
    }

    private void addPlaceBlocksItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mPlaceBlocks) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, 20, Material.INK_SACK, id, displayName);
    }

    private void addFallDamageItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mFallDamage) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, 21, Material.INK_SACK, id, displayName);
    }

    private void addHungerItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mHunger) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, 22, Material.INK_SACK, id, displayName);
    }

    private void addChatItem(Inventory inv) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mChat) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, 23, Material.INK_SACK, id, displayName);
    }

    /* Glass Panes */
    private void fillSettingsGlass(Inventory inv) {
        for (int i = 0; i <= 8; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
        Utils.getInstance().addGlassPane(inv, 15);
        for (int i = 24; i <= 26; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
    }
}
