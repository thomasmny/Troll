package de.eintosti.troll.util;

import de.eintosti.troll.Troll;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author einTosti
 */
public class Messages {
    private Troll plugin;
    public HashMap<String, String> messageData;

    public Messages(Troll plugin) {
        this.plugin = plugin;
        this.messageData = new HashMap<>();
    }

    public void createMessageFile() {
        File file = new File(plugin.getDataFolder() + File.separator + "messages.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        StringBuilder sb = new StringBuilder();

        addLine(sb, "######################################");
        addLine(sb, "#      Troll Plugin by einTosti      #");
        addLine(sb, "#                                    #");
        addLine(sb, "#            Message File            #");
        addLine(sb, "######################################");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# MESSAGES");
        addLine(sb, "# ---------");
        setMessage(sb, config, "prefix", "&5Troll>");
        setMessage(sb, config, "no_permissions", "&7You do not have &denough permissions&7!");
        addLine(sb, "");
        setMessage(sb, config, "chat_disabled", "%prefix% &7The Chat ist currently &cdisabled&7.");
        addLine(sb, "");
        setMessage(sb, config, "creative_disabled", "%prefix% &7You have been &cremoved &7from creative-mode.");
        setMessage(sb, config, "creative_enabled", "%prefix% &7You have been &aput &7into creative-mode.");
        addLine(sb, "");
        setMessage(sb, config, "knockback_disabled", "%prefix% &7Amplified knockback &cdisabled&7.");
        setMessage(sb, config, "knockback_enabled", "%prefix% &7Amplified knockback &aenabled&7.");
        addLine(sb, "");
        setMessage(sb, config, "vanish_disabled", "%prefix% &7You are now &cvisible &7for all players.");
        setMessage(sb, config, "vanish_enabled", "%prefix% &7You are now &ainvisible &7for all players.");
        addLine(sb, "");
        setMessage(sb, config, "players_killed", "%prefix% &7All players were &dkilled&7.");
        setMessage(sb, config, "players_teleported", "%prefix% &7All players were &dteleportiert&7 to you.");
        setMessage(sb, config, "players_burn", "%prefix% &7All players were &dset on fire&7.");
        setMessage(sb, config, "players_receiveEffects", "%prefix% &7The effect &d%effect% &7was applied to all players.");
        setMessage(sb, config, "players_removeEffects", "%prefix% &7The &deffects &7of all players have been &cremoved&7.");
        addLine(sb, "");
        setMessage(sb, config, "permissions_receive_player", "%prefix% &7You now have &apermission &7for the &dTroll Menu&7.");
        setMessage(sb, config, "permissions_receive_all", "%prefix% &7%player% now has &apermission &7for the &dTroll Menu&7.");
        setMessage(sb, config, "permissions_remove_player", "%prefix% &7You now have &cno permission &7for the &dTroll Menu&7.");
        setMessage(sb, config, "permissions_remove_all", "%prefix% &7%player% now has &cno permission &7for the &dTroll Menu&7.");
        addLine(sb, "");
        setMessage(sb, config, "received_trollItem", "%prefix% &7You have received the &dTroll Menu &7item.");
        setMessage(sb, config, "received_effectItem", "%prefix% &7You have received the &dEffect-Menu &7item.");
        setMessage(sb, config, "received_judgementDay", "%prefix% &7You have received the &dJudgement-Day &7item.");
        setMessage(sb, config, "received_thorHammer", "%prefix% &7You have received &dThors Hammer&7.");
        setMessage(sb, config, "received_tntRain", "%prefix% &7You have received the &dTNT-Rain &7item.");
        addLine(sb, "");
        setMessage(sb, config, "called_judgementDay", "&7You summoned &5Judgement-Day&7!");
        setMessage(sb, config, "called_tntRain", "&7You summoned a &5TNT-Rain&7!");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# ITEMS");
        addLine(sb, "# ---------");
        setMessage(sb, config, "troll_item", "&dTroll Menu &7(Right Click)");
        setMessage(sb, config, "effect_item", "&dEffect Menu &7(Right Click)");
        setMessage(sb, config, "judgementDay_item", "&dSummon Judgement-Day &7(Right Click)");
        setMessage(sb, config, "thorHammer_item", "&dThors Hammer &7(Right Click)");
        setMessage(sb, config, "tntRain_item", "&dSummon a TNT-Rain &7(Right Click)");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# GUIs");
        addLine(sb, "# ---------");
        setMessage(sb, config, "main_guiName", "&5Troll Menu");
        addLine(sb, "");
        setMessage(sb, config, "main_killPlayers", "&dKill all players");
        setMessage(sb, config, "main_killPlayers_lore", "&7&oLets all players die");
        addLine(sb, "");
        setMessage(sb, config, "main_effectItem", "&dReceive Effect-Menu Item");
        setMessage(sb, config, "main_effectItem_lore", "&7&oGives all players potion effects");
        addLine(sb, "");
        setMessage(sb, config, "main_playerTeleport", "&dTeleport players");
        setMessage(sb, config, "main_playerTeleport_lore", "&7&oTeleports all players to you");
        addLine(sb, "");
        setMessage(sb, config, "main_gamemodeItem_disabled", "&aActivate &7Creative-Mode");
        setMessage(sb, config, "main_gamemodeItem_enabled", "&cDeactivate &7Creative-Mode");
        setMessage(sb, config, "main_gamemodeItem_lore", "&7&oPuts you into creative-mode");
        addLine(sb, "");
        setMessage(sb, config, "main_vanishItem_disabled", "&aActivate &7Vanish");
        setMessage(sb, config, "main_vanishItem_enabled", "&cDeactivate &7Vanish");
        setMessage(sb, config, "main_vanishItem_lore", "&7&oHides you from your fellow players");
        addLine(sb, "");
        setMessage(sb, config, "main_knockbackItem_disabled", "&aActivate &7Knockback");
        setMessage(sb, config, "main_knockbackItem_enabled", "&cDeactivate &7Knockback");
        setMessage(sb, config, "main_knockbackItem_lore", "&7&oKnocks your opponents even further back");
        addLine(sb, "");
        setMessage(sb, config, "main_thorHammer", "&dReceive Thors Hammer");
        setMessage(sb, config, "main_thorHammer_lore", "&7&oSummons a huge bolt of lightning");
        addLine(sb, "");
        setMessage(sb, config, "main_tntRain", "&dReceive the TNT-Rain item");
        setMessage(sb, config, "main_tntRain_lore", "&7&oSummons a rain of ignited TNT blocks");
        addLine(sb, "");
        setMessage(sb, config, "main_judgementDay", "&dReceive the Judgement-Day item");
        setMessage(sb, config, "main_judgementDay_lore", "&7&oSummons a storm of fireballs");
        addLine(sb, "");
        setMessage(sb, config, "main_settings", "&dAdvanced Settings");
        setMessage(sb, config, "main_settings_lore", "&7&oOpens the advanced settings");
        addLine(sb, "");
        addLine(sb, "");
        setMessage(sb, config, "settings_guiName", "&5Advanced Settings");
        addLine(sb, "");
        setMessage(sb, config, "settings_blockInteractions", "&dBlock Interactions");
        setMessage(sb, config, "settings_blockDamage", "&dBlock Damage");
        setMessage(sb, config, "settings_placeBlocks", "&dPlace Blockes");
        setMessage(sb, config, "settings_fallDamage", "&dFall Damage");
        setMessage(sb, config, "settings_hunger", "&dHunger");
        setMessage(sb, config, "settings_chat", "&dChat");
        addLine(sb, "");
        setMessage(sb, config, "settings_gamemode", "&dGamemode Menu");
        setMessage(sb, config, "settings_permissions", "&dPermissions");
        addLine(sb, "");
        addLine(sb, "");
        setMessage(sb, config, "permissions_guiName", "&5Permissions");
        addLine(sb, "");
        setMessage(sb, config, "permissions_arrowLeft", "&5« &7Previous Page");
        setMessage(sb, config, "permissions_arrowRight", "&7Next Page &5»");
        addLine(sb, "");
        addLine(sb, "");
        setMessage(sb, config, "gamemode_guiName", "&5Gamemode");
        addLine(sb, "");
        setMessage(sb, config, "gamemode_lore", "&7Gamemode: &e%gamemode%");
        setMessage(sb, config, "gamemode_lore_creative", "Creative");
        setMessage(sb, config, "gamemode_lore_survival", "Survival");
        setMessage(sb, config, "gamemode_lore_adventure", "Adventure");
        setMessage(sb, config, "gamemode_lore_spectator", "Spectator");
        addLine(sb, "");
        setMessage(sb, config, "gamemode_arrowLeft", "&5« &7Previous Page");
        setMessage(sb, config, "gamemode_arrowRight", "&7Next Page &5»");
        addLine(sb, "");
        addLine(sb, "");
        setMessage(sb, config, "effect_guiName", "&5Effect Menu");
        addLine(sb, "");
        setMessage(sb, config, "effect_itemName", "&7Gives all players &d%effect%");
        addLine(sb, "");
        setMessage(sb, config, "effect_blindness", "Blindness");
        setMessage(sb, config, "effect_nausea", "Nausea");
        setMessage(sb, config, "effect_resistance", "Resistance");
        setMessage(sb, config, "effect_haste", "Haste");
        setMessage(sb, config, "effect_fireResistance", "Fire Resistance");
        setMessage(sb, config, "effect_hunger", "Hunger");
        setMessage(sb, config, "effect_strength", "Strength");
        setMessage(sb, config, "effect_invisibility", "Invisibility");
        setMessage(sb, config, "effect_jumpBoost", "Jump Boost");
        setMessage(sb, config, "effect_nightVision", "Night Vision");
        setMessage(sb, config, "effect_poison", "Poison");
        setMessage(sb, config, "effect_regeneration", "Regeneration");
        setMessage(sb, config, "effect_slowness", "Slowness");
        setMessage(sb, config, "effect_miningFatigue", "Mining Fatigue");
        setMessage(sb, config, "effect_speed", "Speed");
        setMessage(sb, config, "effect_waterBreathing", "Water Breathing");
        setMessage(sb, config, "effect_weakness", "Weakness");
        setMessage(sb, config, "effect_burn", "Set all players on fire");
        setMessage(sb, config, "effect_removeEffects", "Remove all effects");

        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String message : config.getConfigurationSection("").getKeys(false)) {
            this.messageData.put(message, config.getString(message));
        }
    }

    private void addLine(StringBuilder stringBuilder, String value) {
        stringBuilder.append(value).append("\n");
    }

    private void setMessage(StringBuilder stringBuilder, FileConfiguration config, String key, String defaultValue) {
        String value = config.getString(key, defaultValue);
        stringBuilder.append(key).append(": '").append(value).append("'").append("\n");
    }
}
