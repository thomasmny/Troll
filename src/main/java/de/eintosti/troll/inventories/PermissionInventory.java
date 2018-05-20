package de.eintosti.troll.inventories;

import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author einTosti
 */
public class PermissionInventory {
    private HashMap<UUID, Integer> mInvIndex = new HashMap<>();
    private Inventory[] mInventories;

    private static PermissionInventory instance;
    public static synchronized PermissionInventory getInstance() {
        if (instance == null) {
            instance = new PermissionInventory();
        }
        return instance;
    }

    private Inventory createInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.getInstance().getString("permissions_guiName"));
        fillGuiWithGlass(inv, player);
        return inv;
    }

    private void addInvItems(Player player) {
        int columnSkull = 10, maxColumnSkull = 16;
        int columnDye = 19;

        int numPlayers = Bukkit.getOnlinePlayers().size();
        int numInventories = (numPlayers % 9 == 0 ? numPlayers : numPlayers + 1);

        int index = 0;
        Inventory inv = createInventory(player);
        mInventories = new Inventory[numInventories];
        mInventories[index] = inv;

        if (mInvIndex.get(player.getUniqueId()) == 0)
            Utils.getInstance().addSkull(inv, 9, "§d" + player.getName(), player.getName());

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!pl.equals(player) && !pl.hasPermission("troll.use")) {
                Utils.getInstance().addSkull(inv, columnSkull++, "§5" + pl.getName(), pl.getName());
                addSelectedPlayerItem(pl, inv, columnDye++);

                if (columnSkull > maxColumnSkull) {
                    columnSkull = 10;
                    columnDye = 19;
                    inv = createInventory(player);
                    mInventories[++index] = inv;
                }
            }
        }
    }

    public Inventory getInventory(Player player) {
        addInvItems(player);

        if (getInvIntex(player) == null) {
            setInvIndex(player, 0);
        }
        return mInventories[mInvIndex.get(player.getUniqueId())];
    }

    /* Items */
    private void addSelectedPlayerItem(Player player, Inventory inv, int position) {
        String displayName = "§7✘";
        int id = 8;

        if (Utils.getInstance().mTrollEnabledPlayers.contains(player.getName())) {
            displayName = "§a✔";
            id = 10;
        }
        Utils.getInstance().addSimpleItemStack(inv, position, Material.INK_SACK, id, displayName);
    }

    private void fillGuiWithGlass(Inventory inv, Player player) {
        int numOfPages = (Bukkit.getOnlinePlayers().size() / 9) + (Bukkit.getOnlinePlayers().size() % 9 == 0 ? 0 : 1);

        for (int i = 0; i <= 8; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }

        if (numOfPages > 1 && mInvIndex.get(player.getUniqueId()) > 0)
            Utils.getInstance().addSkull(inv, 9, Utils.getInstance().getString("permissions_arrowLeft"), "MHF_ArrowLeft");
        if (numOfPages > 1 && mInvIndex.get(player.getUniqueId()) < (numOfPages - 1))
            Utils.getInstance().addSkull(inv, 17, Utils.getInstance().getString("permissions_arrowRight"), "MHF_ArrowRight");

        Utils.getInstance().addGlassPane(inv, 18);
        Utils.getInstance().addGlassPane(inv, 26);
    }

    /* Manage Pages */
    public Integer getInvIntex(Player player) {
        return mInvIndex.get(player.getUniqueId());
    }

    public void setInvIndex(Player player, int index) {
        mInvIndex.put(player.getUniqueId(), index);
    }

    public void incrementInv(Player player) {
        UUID playerUUID = player.getUniqueId();
        mInvIndex.put(playerUUID, mInvIndex.get(playerUUID) + 1);
    }

    public void decrementInv(Player player) {
        UUID playerUUID = player.getUniqueId();
        mInvIndex.put(playerUUID, mInvIndex.get(playerUUID) - 1);
    }
}
