package de.eintosti.troll.inventory;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.util.external.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author einTosti
 */
public class GamemodeInventory {
    private Troll plugin;
    private InventoryManager inventoryManager;

    private Map<UUID, Integer> invIndex;
    private Inventory[] inventories;

    public GamemodeInventory(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.invIndex = new HashMap<>();
    }

    private Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getString("gamemode_guiName"));
        fillGuiWithGlass(inventory, player);
        return inventory;
    }

    private void addItems(Player player) {
        int columnSkull = 9, maxColumnSkull = 17;
        int numPlayers = Bukkit.getOnlinePlayers().size();
        int numInventories = (numPlayers % 9 == 0 ? numPlayers : numPlayers + 1);
        int index = 0;

        Inventory inventory = createInventory(player);

        inventories = new Inventory[numInventories];
        inventories[index] = inventory;

        for (Player pl : Bukkit.getOnlinePlayers()) {
            addGamemodeItem(pl, inventory, columnSkull++);
            if (columnSkull > maxColumnSkull) {
                columnSkull = 9;
                inventory = createInventory(player);
                inventories[++index] = inventory;
            }
        }
    }

    public Inventory getInventory(Player player) {
        addItems(player);
        if (getInvIntex(player) == null)
            setInvIndex(player, 0);
        return inventories[invIndex.get(player.getUniqueId())];
    }

    @SuppressWarnings("deprecation")
    private void addGamemodeItem(Player player, Inventory inv, int position) {
        ItemStack itemStack = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setDisplayName("§d" + player.getName());
        skullMeta.setLore(Arrays.asList(plugin.getString("gamemode_lore").replace("%gamemode%", getPlayerGamemode(player))));
        skullMeta.setOwner(player.getName());

        itemStack.setItemMeta(skullMeta);
        inv.setItem(position, itemStack);
    }

    private void fillGuiWithGlass(Inventory inv, Player player) {
        int numOfPages = (Bukkit.getOnlinePlayers().size() / 9) + (Bukkit.getOnlinePlayers().size() % 9 == 0 ? 0 : 1);

        if (numOfPages > 1 && invIndex.get(player.getUniqueId()) > 0) {
            inventoryManager.addSkull(inv, 9, plugin.getString("gamemode_arrowLeft"), "MHF_ArrowLeft");
        } else {
            inventoryManager.addGlassPane(inv, 18);
        }
        if (numOfPages > 1 && invIndex.get(player.getUniqueId()) < (numOfPages - 1)) {
            inventoryManager.addSkull(inv, 17, plugin.getString("gamemode_arrowRight"), "MHF_ArrowRight");
        } else {
            inventoryManager.addGlassPane(inv, 26);
        }

        for (int i = 0; i <= 8; i++)
            inventoryManager.addGlassPane(inv, i);
        for (int i = 19; i <= 25; i++)
            inventoryManager.addGlassPane(inv, i);
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
