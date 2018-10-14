package de.eintosti.troll;

import de.eintosti.troll.command.TrollCommand;
import de.eintosti.troll.inventory.*;
import de.eintosti.troll.listener.*;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import de.eintosti.troll.misc.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author einTosti
 */
public class Troll extends JavaPlugin {
    private EffectInventory effectInventory;
    private GamemodeInventory gamemodeInventory;
    private PermissionInventory permissionInventory;
    private SettingsInventory settingsInventory;
    private TrollInventory trollInventory;

    private InventoryManager inventoryManager;
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

        this.inventoryManager = new InventoryManager();
        this.trollManager = new TrollManager(this);

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
        new BlockBreak(this);
        new BlockPlace(this);
        new EntityDamage(this);
        new EntityExplode(this);
        new FoodLevelChange(this);
        new InventoryClick(this);
        new ItemPickup(this);
        new PlayerChat(this);
        new PlayerInteract(this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void setConfigValues() {
        getConfig().set("trollPlayers", trollManager.getTrollPlayers());
        getConfig().set("settings.interactions", trollManager.getInteractions());
        getConfig().set("settings.blockDamage", trollManager.getBlockDamage());
        getConfig().set("settings.placeBlocks", trollManager.getPlaceBlocks());
        getConfig().set("settings.fallDamage", trollManager.getFallDamage());
        getConfig().set("settings.hunger", trollManager.getHunger());
        getConfig().set("settings.chat", trollManager.getChat());
    }

    private void getConfigValues() {
        if (getConfig().getList("trollPlayers") != null) {
            List<String> list = getConfig().getStringList("trollPlayers");
            trollManager.clearTrollPlayers();
            for (String string : list) {
                trollManager.addTrollPlayer(string);
            }
        }
        trollManager.setInteractions(getConfig().isBoolean("settings.interactions") ? getConfig().getBoolean("settings.interactions") : true);
        trollManager.setBlockDamage(getConfig().isBoolean("settings.blockDamage") ? getConfig().getBoolean("settings.blockDamage") : true);
        trollManager.setPlaceBlocks(getConfig().isBoolean("settings.placeBlocks")? getConfig().getBoolean("settings.placeBlocks") : true);
        trollManager.setFallDamage(getConfig().isBoolean("settings.fallDamage") ? getConfig().getBoolean("settings.fallDamage") : true);
        trollManager.setHunger(getConfig().isBoolean("settings.hunger") ? getConfig().getBoolean("settings.hunger") : true);
        trollManager.setChat(getConfig().isBoolean("settings.chat") ? getConfig().getBoolean("settings.chat") : true);
    }

    public String getString(String string) {
        try {
            return messages.messageData.get(string).replace("&", "§").replace("%prefix%", messages.messageData.get("prefix"));
        } catch (NullPointerException e) {
            messages.createMessageFile();
            return getString(string);
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

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public TrollManager getTrollManager() {
        return trollManager;
    }
}
