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

    private Inventory getEffectInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§5Effekt Menü");

        Utils.getInstance().addSimpleItemStack(inv, 0, Material.EYE_OF_ENDER, 0, "§dBlindheit §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 1, Material.COMPASS, 0, "§dVerwirrung §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 2, Material.DIAMOND_CHESTPLATE, 0, "§dResistenz §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 3, Material.DIAMOND_PICKAXE, 0, "§dEile §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 4, Material.LAVA_BUCKET, 0, "§dFeuerresistenz §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 5, Material.ROTTEN_FLESH, 0, "§dHunger §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 6, Material.DIAMOND_SWORD, 0, "§dStärke §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 7, Material.GLASS, 0, "§dUnsichtbarkeit §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 8, Material.RABBIT_FOOT, 0, "§dSprungkraft §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 9, Material.GOLDEN_CARROT, 0, "§dNachtsicht §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 10, Material.FERMENTED_SPIDER_EYE, 0, "§dVergifung §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 11, Material.GOLDEN_APPLE, 0, "§dRegeneration §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 12, Material.SOUL_SAND, 0, "§dLangsamkeit §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 13, Material.WOOD_PICKAXE, 0, "§dAbbaulähmung §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 14, Material.SUGAR, 0, "§dSchnelligkeit §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 15, Material.WATER_BUCKET, 0, "§dUnterwasseratmung §7auf alle Spieler wirken");
        Utils.getInstance().addSimpleItemStack(inv, 16, Material.WOOD_SWORD, 0, "§dSchwäche §7auf alle Spieler wirken");

        Utils.getInstance().addSimpleItemStack(inv, 20, Material.FLINT_AND_STEEL, 0, "§7Alle Spieler §2anzünden");
        Utils.getInstance().addSimpleItemStack(inv, 24, Material.MILK_BUCKET, 0, "§7Statuseffekte aller Spieler §2entfernen");

        return inv;
    }

    public void openInventory(Player player) {
        Inventory inv = getEffectInventory(player);
        player.openInventory(inv);
    }
}
