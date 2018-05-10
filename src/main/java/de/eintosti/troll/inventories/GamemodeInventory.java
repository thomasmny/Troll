package de.eintosti.troll.inventories;

import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author einTosti
 */
public class GamemodeInventory {
    private HashMap<UUID, Integer> mInvIndex = new HashMap<>();
    private Inventory[] mInventories;

    private static GamemodeInventory instance;

    public static synchronized GamemodeInventory getInstance() {
        if (instance == null) {
            instance = new GamemodeInventory();
        }
        return instance;
    }

    private Inventory createInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§5Gamemode");
        fillGuiWithGlass(inv, player);
        return inv;
    }

    private void addInvItems(Player player) {
        int columnSkull = 9, maxColumnSkull = 17;
        int numPlayers = Bukkit.getOnlinePlayers().size();
        int numInventories = (numPlayers % 9 == 0 ? numPlayers : numPlayers + 1);
        int index = 0;

        Inventory inv = createInventory(player);

        mInventories = new Inventory[numInventories];
        mInventories[index] = inv;

        for (Player pl : Bukkit.getOnlinePlayers()) {
            addPlayerGamemodeItem(pl, inv, columnSkull++);

            if (columnSkull > maxColumnSkull) {
                columnSkull = 9;
                inv = createInventory(player);
                mInventories[++index] = inv;
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
    private void addPlayerGamemodeItem(Player player, Inventory inv, int position) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

        meta.setDisplayName("§d" + player.getName());
        meta.setLore(Arrays.asList("§7Gamemode: §e" + getPlayerGamemode(player)));
        meta.setOwner(player.getName());

        itemStack.setItemMeta(meta);
        inv.setItem(position, itemStack);
    }

    private void fillGuiWithGlass(Inventory inv, Player player) {
        int numOfPages = (Bukkit.getOnlinePlayers().size() / 9) + (Bukkit.getOnlinePlayers().size() % 9 == 0 ? 0 : 1);

        for (int i = 0; i <= 8; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }

        if (numOfPages > 1 && mInvIndex.get(player.getUniqueId()) > 0) {
            Utils.getInstance().addSkull(inv, 18, "§5« §7Vorherige Seite", "MHF_ArrowLeft");
        } else {
            Utils.getInstance().addGlassPane(inv, 18);
        }
        if (numOfPages > 1 && mInvIndex.get(player.getUniqueId()) < (numOfPages - 1)) {
            Utils.getInstance().addSkull(inv, 26, "§7Nächste Seite §5»", "MHF_ArrowRight");
        } else {
            Utils.getInstance().addGlassPane(inv, 26);
        }

        for (int i = 19; i <= 25; i++) {
            Utils.getInstance().addGlassPane(inv, i);
        }
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

    private String getPlayerGamemode(Player player) {
        String gameMode = "-";

        switch (player.getGameMode()) {
            case CREATIVE:
                gameMode = "Creative";
                break;
            case SURVIVAL:
                gameMode = "Survival";
                break;
            case ADVENTURE:
                gameMode = "Adventure";
                break;
            case SPECTATOR:
                gameMode = "Spectator";
                break;
        }
        return gameMode;
    }
}
