package com.eintosti.troll.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import com.eintosti.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author einTosti
 */
public class PermissionInventory {

    private final Troll plugin;
    private final TrollManager trollManager;

    private final Map<UUID, Integer> invIndex;
    private Inventory[] inventories;

    public PermissionInventory(Troll plugin) {
        this.plugin = plugin;
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

        if (getInvIndex(player) == 0) {
            String playerName = player.getName();
            addSkull(inventory, 9, playerName, "&d" + playerName);
        }

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!pl.equals(player) && !pl.hasPermission("troll.use")) {
                addSkull(inventory, columnSkull++, pl.getName(), "&5" + pl.getName());
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

    private void addSkull(Inventory inventory, int position, String ownerName, String displayName) {
        inventory.setItem(position, new ItemBuilder(XMaterial.PLAYER_HEAD)
                .skull(ownerName)
                .name(displayName)
                .build()
        );
    }

    public Inventory getInventory(Player player) {
        addInvItems(player);
        return inventories[getInvIndex(player)];
    }

    private void addSelectedPlayerItem(Player player, Inventory inventory, int position) {
        String displayName = "§7✘";
        XMaterial material = XMaterial.GRAY_DYE;

        if (trollManager.isTrollPlayer(player.getName())) {
            displayName = "§a✔";
            material = XMaterial.LIME_DYE;
        }

        inventory.setItem(position, new ItemBuilder(material).name(displayName).build());
    }

    private void fillWithGlass(Inventory inv, Player player) {
        final int numOfPages = (Bukkit.getOnlinePlayers().size() / 9) + (Bukkit.getOnlinePlayers().size() % 9 == 0 ? 0 : 1);
        final ItemStack fillerGlass = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&0").build();

        for (int i = 0; i <= 8; i++) {
            inv.setItem(i, fillerGlass);
        }

        if (numOfPages > 1 && getInvIndex(player) > 0) {
            addSkull(inv, 9, "MHF_ArrowLeft", plugin.getString("permissions_arrowLeft"));
        }

        if (numOfPages > 1 && getInvIndex(player) < (numOfPages - 1)) {
            addSkull(inv, 9, "MHF_ArrowRight", plugin.getString("permissions_arrowRight"));
        }

        inv.setItem(18, fillerGlass);
        inv.setItem(26, fillerGlass);
    }

    public Integer getInvIndex(Player player) {
        return invIndex.getOrDefault(player.getUniqueId(), 0);
    }

    public void setInvIndex(Player player, int index) {
        invIndex.put(player.getUniqueId(), index);
    }

    public void forward(Player player) {
        UUID playerUUID = player.getUniqueId();
        invIndex.put(playerUUID, invIndex.get(playerUUID) + 1);

        player.openInventory(getInventory(player));
        XSound.BLOCK_CHEST_OPEN.play(player);
    }

    public void back(Player player) {
        UUID playerUUID = player.getUniqueId();
        invIndex.put(playerUUID, invIndex.get(playerUUID) - 1);

        player.openInventory(getInventory(player));
        XSound.BLOCK_CHEST_OPEN.play(player);
    }
}
