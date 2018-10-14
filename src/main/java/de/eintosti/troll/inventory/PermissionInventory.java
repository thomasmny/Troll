package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author einTosti
 */
public class PermissionInventory {
    private Troll plugin;
    private InventoryManager inventoryManager;
    private TrollManager trollManager;

    private Map<UUID, Integer> invIndex;
    private Inventory[] inventories;

    public PermissionInventory(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.trollManager = plugin.getTrollManager();
        this.invIndex = new HashMap<>();
    }

    private Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("permissions_guiName"));
        fillWithGlass(inventory, player);
        return inventory;
    }

    private void addInvItems(Player player) {
        int columnSkull = 10, maxColumnSkull = 16;
        int columnDye = 19;

        int numPlayers = Bukkit.getOnlinePlayers().size();
        int numInventories = (numPlayers % 9 == 0 ? numPlayers : numPlayers + 1);

        int index = 0;
        Inventory inventory = createInventory(player);
        inventories = new Inventory[numInventories];
        inventories[index] = inventory;

        if (invIndex.get(player.getUniqueId()) == 0)
            inventoryManager.addSkull(inventory, 9, "§d" + player.getName(), player.getName());

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!pl.equals(player) && !pl.hasPermission("troll.use")) {
                inventoryManager.addSkull(inventory, columnSkull++, "§5" + pl.getName(), pl.getName());
                addSelectedPlayerItem(pl, inventory, columnDye++);

                if (columnSkull > maxColumnSkull) {
                    columnSkull = 10;
                    columnDye = 19;
                    inventory = createInventory(player);
                    inventories[++index] = inventory;
                }
            }
        }
    }

    public Inventory getInventory(Player player) {
        addInvItems(player);
        if (getInvIntex(player) == null) {
            setInvIndex(player, 0);
        }
        return inventories[invIndex.get(player.getUniqueId())];
    }

    private void addSelectedPlayerItem(Player player, Inventory inv, int position) {
        String displayName = "§7✘";
        int id = 8;

        if (trollManager.getTrollPlayers().contains(player.getName())) {
            displayName = "§a✔";
            id = 10;
        }
        inventoryManager.addItemStack(inv, position, Material.INK_SACK, id, displayName);
    }

    private void fillWithGlass(Inventory inv, Player player) {
        int numOfPages = (Bukkit.getOnlinePlayers().size() / 9) + (Bukkit.getOnlinePlayers().size() % 9 == 0 ? 0 : 1);

        for (int i = 0; i <= 8; i++)
            inventoryManager.addGlassPane(inv, i);
        if (numOfPages > 1 && invIndex.get(player.getUniqueId()) > 0)
            inventoryManager.addSkull(inv, 9, plugin.getString("permissions_arrowLeft"), "MHF_ArrowLeft");
        if (numOfPages > 1 && invIndex.get(player.getUniqueId()) < (numOfPages - 1))
            inventoryManager.addSkull(inv, 17, plugin.getString("permissions_arrowRight"), "MHF_ArrowRight");

        inventoryManager.addGlassPane(inv, 18);
        inventoryManager.addGlassPane(inv, 26);
    }

    public Integer getInvIntex(Player player) {
        return invIndex.get(player.getUniqueId());
    }

    public void setInvIndex(Player player, int index) {
        invIndex.put(player.getUniqueId(), index);
    }

    public void incrementInv(Player player) {
        UUID playerUUID = player.getUniqueId();
        invIndex.put(playerUUID, invIndex.get(playerUUID) + 1);
    }

    public void decrementInv(Player player) {
        UUID playerUUID = player.getUniqueId();
        invIndex.put(playerUUID, invIndex.get(playerUUID) - 1);
    }
}
