package com.eintosti.troll;

import com.eintosti.troll.command.TrollCommand;
import com.eintosti.troll.inventory.*;
import com.eintosti.troll.listener.*;
import com.eintosti.troll.manager.TrollManager;
import com.eintosti.troll.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author einTosti
 */
public class Troll extends JavaPlugin {

    private EffectInventory effectInventory;
    private GamemodeInventory gamemodeInventory;
    private PermissionInventory permissionInventory;
    private SettingsInventory settingsInventory;
    private TrollInventory trollInventory;

    private TrollManager trollManager;

    private Messages messages;

    @Override
    public void onEnable() {
        initClasses();
        registerCommands();
        registerListeners();

        loadConfiguration();
        getConfigValues();
        messages.createMessageFile();

        Bukkit.getConsoleSender().sendMessage("Troll » Plugin §aenabled§r!");
    }

    @Override
    public void onDisable() {
        setConfigValues();
        saveConfig();

        Bukkit.getConsoleSender().sendMessage("Troll » Plugin §cdisabled§r!");
    }

    private void initClasses() {
        this.trollManager = new TrollManager();

        this.effectInventory = new EffectInventory(this);
        this.gamemodeInventory = new GamemodeInventory(this);
        this.permissionInventory = new PermissionInventory(this);
        this.settingsInventory = new SettingsInventory(this);
        this.trollInventory = new TrollInventory(this);

        this.messages = new Messages(this);
    }

    private void registerCommands() {
        new TrollCommand(this);
    }

    private void registerListeners() {
        new BlockBreakListener(this);
        new BlockPlaceListener(this);
        new EntityDamageListener(this);
        new EntityExplodeListener(this);
        new FoodLevelChangeListener(this);
        new InventoryClickListener(this);
        new ItemPickupListener(this);
        new PlayerChatListener(this);
        new PlayerInteractListener(this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void setConfigValues() {
        getConfig().set("troll-players", trollManager.getTrollPlayers());
        getConfig().set("knockback", trollManager.getKnockBack());
        getConfig().set("settings.interactions", trollManager.isInteractions());
        getConfig().set("settings.block-damage", trollManager.isBlockDamage());
        getConfig().set("settings.place-blocks", trollManager.isPlaceBlocks());
        getConfig().set("settings.fall-damage", trollManager.isFallDamage());
        getConfig().set("settings.hunger", trollManager.isHunger());
        getConfig().set("settings.chat", trollManager.isChat());
    }

    private void getConfigValues() {
        if (getConfig().getList("troll-players") != null) {
            trollManager.clearTrollPlayers();
            getConfig().getStringList("troll-players").forEach(playerName -> trollManager.addTrollPlayer(playerName));
        }

        trollManager.setKnockBack(getConfig().getInt("knockback", 75));
        trollManager.setInteractions(getConfig().getBoolean("settings.interactions", true));
        trollManager.setBlockDamage(getConfig().getBoolean("settings.blockDamage", true));
        trollManager.setPlaceBlocks(getConfig().getBoolean("settings.placeBlocks", true));
        trollManager.setFallDamage(getConfig().getBoolean("settings.fallDamage", true));
        trollManager.setHunger(getConfig().getBoolean("settings.hunger", true));
        trollManager.setChat(getConfig().getBoolean("settings.chat", true));
    }

    public String getString(String key) {
        try {
            String prefix = messages.messageData.get("prefix");
            return ChatColor.translateAlternateColorCodes('&', messages.messageData.get(key).replace("%prefix%", prefix));
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Troll] Could not find message with key: " + key);
            messages.createMessageFile();
            return getString(key);
        }
    }

    public EffectInventory getEffectInventory() {
        return effectInventory;
    }

    public GamemodeInventory getGamemodeInventory() {
        return gamemodeInventory;
    }

    public PermissionInventory getPermissionInventory() {
        return permissionInventory;
    }

    public SettingsInventory getSettingsInventory() {
        return settingsInventory;
    }

    public TrollInventory getTrollInventory() {
        return trollInventory;
    }

    public TrollManager getTrollManager() {
        return trollManager;
    }
}
