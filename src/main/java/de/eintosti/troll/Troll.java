package de.eintosti.troll;

import de.eintosti.troll.commands.TrollCommand;
import de.eintosti.troll.listeners.*;
import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author einTosti
 */
public class Troll extends JavaPlugin {
    public static Troll plugin = null;

    @Override
    public void onEnable() {
        setInstance();

        registerListeners();
        registerExecutors();

        loadConfiguration();
        getConfigValues();

        getLogger().info("Plugin aktiviert");
    }

    @Override
    public void onDisable() {
        setConfigValues();
        saveConfig();

        plugin = null;
        getLogger().info("Plugin deaktiviert");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplode(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChange(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new ItemPickup(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
    }

    private void registerExecutors() {
        Bukkit.getPluginCommand("troll").setExecutor(new TrollCommand());
    }

    private void setInstance() {
        if (plugin == null) {
            plugin = this;
        } else {
            getLogger().warning("Das Plugin ist zweimal geladen worden; Ein Fehler ist aufgetreten!");
        }
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void setConfigValues() {
        getConfig().set("trollPlayers", Utils.getInstance().mTrollEnabledPlayers);

        getConfig().set("settings.interactions", Utils.getInstance().mInteractions);
        getConfig().set("settings.blockDamage", Utils.getInstance().mBlockDamage);
        getConfig().set("settings.placeBlocks", Utils.getInstance().mPlaceBlocks);
        getConfig().set("settings.fallDamage", Utils.getInstance().mFallDamage);
        getConfig().set("settings.hunger", Utils.getInstance().mHunger);
        getConfig().set("settings.chat", Utils.getInstance().mChat);
    }

    private void getConfigValues() {
        //Troll players
        if (getConfig().getList("trollPlayers") != null) {
            List<String> list = getConfig().getStringList("trollPlayers");
            Utils.getInstance().mTrollEnabledPlayers.clear();

            for (String string : list) {
                Utils.getInstance().mTrollEnabledPlayers.add(string);
            }
        }

        //Interactions
        if (getConfig().get("settings.interactions") != null) {
            Utils.getInstance().mInteractions = getConfig().getBoolean("settings.interactions");
        } else {
            Utils.getInstance().mInteractions = true;
        }
        //BlockDamage
        if (getConfig().get("settings.blockDamage") != null) {
            Utils.getInstance().mBlockDamage = getConfig().getBoolean("settings.blockDamage");
        } else {
            Utils.getInstance().mBlockDamage = true;
        }
        //PlaceBlocks
        if (getConfig().get("settings.placeBlocks") != null) {
            Utils.getInstance().mPlaceBlocks = getConfig().getBoolean("settings.placeBlocks");
        } else {
            Utils.getInstance().mPlaceBlocks = true;
        }
        //FallDamage
        if (getConfig().get("settings.fallDamage") != null) {
            Utils.getInstance().mFallDamage = getConfig().getBoolean("settings.fallDamage");
        } else {
            Utils.getInstance().mFallDamage = true;
        }
        //Hunger
        if (getConfig().get("settings.hunger") != null) {
            Utils.getInstance().mHunger = getConfig().getBoolean("settings.hunger");
        } else {
            Utils.getInstance().mHunger = true;
        }
        //Chat
        if (getConfig().get("settings.chat") != null) {
            Utils.getInstance().mChat = getConfig().getBoolean("settings.chat");
        } else {
            Utils.getInstance().mChat = true;
        }
    }
}
