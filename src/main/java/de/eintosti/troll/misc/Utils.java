package de.eintosti.troll.misc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author einTosti
 */
public class Utils {
    public String mPrefix = "§5Troll> ";

    public Boolean mInteractions = true;
    public Boolean mBlockDamage = true;
    public Boolean mPlaceBlocks = true;
    public Boolean mFallDamage = true;
    public Boolean mHunger = true;
    public Boolean mChat = true;

    public ArrayList<String> mTrollEnabledPlayers = new ArrayList<>();
    public ArrayList<UUID> mVanishedPlayers = new ArrayList<>();
    public ArrayList<UUID> mKnockbackPlayers = new ArrayList<>();

    private static Utils instance;
    public static synchronized Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public boolean isAllowed(Player player) {
        boolean allowed = false;

        if (player.hasPermission("troll.use") || Utils.getInstance().mTrollEnabledPlayers.contains(player.getName()))
            allowed = true;
        return allowed;
    }

    /* GUI Items */
    public void addSimpleItemStack(Inventory inv, int position, Material material, int id, String displayName) {
        ItemStack itemStack = new ItemStack(material, 1, (byte) id);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
        inv.setItem(position, itemStack);
    }

    public void addLoreItemStack(Inventory inv, int position, Material material, int id, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material, 1, (byte) id);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        itemStack.setItemMeta(meta);
        inv.setItem(position, itemStack);
    }

    public void addSkull(Inventory inv, int position, String displayName, String skullOwner) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwner(skullOwner);
        skullMeta.setDisplayName(displayName);
        skull.setItemMeta(skullMeta);

        inv.setItem(position, skull);
    }

    public void addGlassPane(Inventory inv, int position) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);

        inv.setItem(position, itemStack);
    }
}
