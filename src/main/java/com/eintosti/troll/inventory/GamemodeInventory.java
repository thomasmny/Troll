package com.eintosti.troll.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.eintosti.troll.Troll;
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
public class GamemodeInventory {

    private final Troll plugin;

    private final Map<UUID, Integer> invIndex;
    private Inventory[] inventories;

    private int numPlayers;

    public GamemodeInventory(Troll plugin) {
        this.plugin = plugin;
        this.invIndex = new HashMap<>();
    }

    private Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("gamemode_guiName"));
        fillGuiWithGlass(inventory, player);
        return inventory;
    }

    private void addItems(Player player) {
        int columnSkull = 9, maxColumnSkull = 17;
        this.numPlayers = Bukkit.getOnlinePlayers().size();
        int numInventories = (numPlayers % 9 == 0 ? numPlayers : numPlayers + 1);
        int index = 0;

        Inventory inventory = createInventory(player);

        inventories = new Inventory[numInventories];
        inventories[index] = inventory;

        for (Player pl : Bukkit.getOnlinePlayers()) {
            inventory.setItem(columnSkull++, new ItemBuilder(XMaterial.PLAYER_HEAD)
                    .name("&d" + pl.getName())
                    .skull(pl.getName())
                    .lore(plugin.getString("gamemode_lore").replace("%gamemode%", getPlayerGamemode(pl)))
                    .build()
            );

            if (columnSkull > maxColumnSkull) {
                columnSkull = 9;
                inventory = createInventory(player);
                inventories[++index] = inventory;
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
        addItems(player);
        if (getInvIndex(player) == null) {
            setInvIndex(player, 0);
        }
        return inventories[invIndex.get(player.getUniqueId())];
    }

    private void fillGuiWithGlass(Inventory inventory, Player player) {
        final int numOfPages = (Bukkit.getOnlinePlayers().size() / 9) + (Bukkit.getOnlinePlayers().size() % 9 == 0 ? 0 : 1);
        final ItemStack fillerGlass = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&0").build();

        if (numOfPages > 1 && getInvIndex(player) > 0) {
            addSkull(inventory, 18, "MHF_ArrowLeft", plugin.getString("permissions_arrowLeft"));
        } else {
            inventory.setItem(18, fillerGlass);
        }
        if (numOfPages > 1 && getInvIndex(player) < (numOfPages - 1)) {
            addSkull(inventory, 26, "MHF_ArrowRight", plugin.getString("permissions_arrowRight"));
        } else {
            inventory.setItem(26, fillerGlass);
        }

        for (int i = 0; i <= 8; i++) {
            inventory.setItem(i, fillerGlass);
        }

        for (int i = 19; i <= 25; i++) {
            inventory.setItem(i, fillerGlass);
        }
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

    private String getPlayerGamemode(Player player) {
        switch (player.getGameMode()) {
            case CREATIVE:
                return plugin.getString("gamemode_lore_creative");
            case SURVIVAL:
                return plugin.getString("gamemode_lore_survival");
            case ADVENTURE:
                return plugin.getString("gamemode_lore_adventure");
            case SPECTATOR:
                return plugin.getString("gamemode_lore_spectator");
        }
        return "-";
    }
}
