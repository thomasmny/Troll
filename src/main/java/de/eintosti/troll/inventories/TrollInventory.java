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
        Inventory inv = Bukkit.createInventory(null, 27, "§5Troll Menü");
        fillGuiWithGlass(inv);

        Utils.getInstance().addLoreItemStack(inv, 3, Material.DIAMOND_SWORD, 0, "§7Alle §dSpieler töten", "§7", "§7§oLässt alle Spieler des Servers sterben");
        Utils.getInstance().addLoreItemStack(inv, 4, Material.BLAZE_POWDER, 0, "§dEffekt §7Item bekommen", "§7", "§7§oÖffnet ein Menü, indem du allen Mitspielern", "§7§oStatuseffekte auferlegen kannst");
        Utils.getInstance().addLoreItemStack(inv, 5, Material.ENDER_PEARL, 0, "§7Spieler §dteleportieren", "§7", "§7§oLässt alle Spieler zu dir teleportieren");

        addGamemodeItem(player, inv);
        addKnockbackItem(player, inv);
        addVanishItem(player, inv);

        Utils.getInstance().addLoreItemStack(inv, 15, Material.IRON_AXE, 0, "§dThor's Hammer §7bekommen", "§7", "§7§oLässt an der Stelle, die du", "§7§oanschaust einen Blizt einschlagen");
        Utils.getInstance().addLoreItemStack(inv, 16, Material.TNT, 0, "§dTNT-Regen §7Item bekommen", "§7", "§7§oRuft an der Stelle, die du anschaust einen", "§7§oRegen an gezündeten TNT Blöcken herbei");
        Utils.getInstance().addLoreItemStack(inv, 17, Material.FIREBALL, 0, "§dJudgement-Day §7Item bekommen", "§7", "§7§oLässt an der Stelle, die du anschaust", "§7§oein Sturm aus Feuerkugeln einschlagen");

        Utils.getInstance().addLoreItemStack(inv, 22, Material.NETHER_STAR, 0, "§7Erweiterte §dEinstellungen", "§7", "§7§oÖffnet die erweiterten Einstellungen");

        return inv;
    }

    private void addGamemodeItem(Player player, Inventory inv) {
        String displayName = "§aaktivieren";

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            displayName = "§cdeaktivieren";
        }
        Utils.getInstance().addLoreItemStack(inv, 9, Material.GRASS, 0, "§7Kreativmodus " + displayName, "§7", "§7§oVersetzt dich in den Kreativmodus");
    }

    private void addVanishItem(Player player, Inventory inv) {
        String displayName = "§aaktivieren";

        if (Utils.getInstance().mVanishedPlayers.contains(player.getUniqueId())) {
            displayName = "§cdeaktivieren";
        }
        Utils.getInstance().addLoreItemStack(inv, 11, Material.QUARTZ, 0, "§7Unsichtbarkeit " + displayName, "§7", "§7§oMacht dich für deine Mitspieler unsichtbar");
    }

    private void addKnockbackItem(Player player, Inventory inv) {
        String displayName = "§aaktivieren";

        if (Utils.getInstance().mKnockbackPlayers.contains(player.getUniqueId())) {
            displayName = "§cdeaktivieren";
        }
        Utils.getInstance().addLoreItemStack(inv, 10, Material.FEATHER, 0, "§7Rückstoß " + displayName, "§7", "§7§oEigene Nahkampfangriffe stoßen", "§7§odeine Gegner zurück");
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
