package de.eintosti.troll.manager;

import de.eintosti.troll.Troll;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author einTosti
 */
public class TrollManager {
    private int knockback;

    private Boolean interactions;
    private Boolean blockDamage;
    private Boolean placeBlocks;
    private Boolean fallDamage;
    private Boolean hunger;
    private Boolean chat;

    private ArrayList<String> trollPlayers;
    private ArrayList<UUID> vanishedPlayers;
    private ArrayList<UUID> knockbackPlayers;

    public TrollManager() {
        this.knockback = 15;
        this.interactions = true;
        this.blockDamage = true;
        this.placeBlocks = true;
        this.fallDamage = true;
        this.hunger = true;
        this.chat = true;

        this.trollPlayers = new ArrayList<>();
        this.vanishedPlayers = new ArrayList<>();
        this.knockbackPlayers = new ArrayList<>();
    }

    public boolean isAllowed(Player player) {
        boolean allowed = false;
        if (player.hasPermission("troll.use") || this.trollPlayers.contains(player.getName())) {
            allowed = true;
        }
        return allowed;
    }

    public int getKnockback() {
        return knockback;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    public Boolean getInteractions() {
        return interactions;
    }

    public void setInteractions(Boolean interactions) {
        this.interactions = interactions;
    }

    public Boolean getBlockDamage() {
        return blockDamage;
    }

    public void setBlockDamage(Boolean blockDamage) {
        this.blockDamage = blockDamage;
    }

    public Boolean getPlaceBlocks() {
        return placeBlocks;
    }

    public void setPlaceBlocks(Boolean placeBlocks) {
        this.placeBlocks = placeBlocks;
    }

    public Boolean getFallDamage() {
        return fallDamage;
    }

    public void setFallDamage(Boolean fallDamage) {
        this.fallDamage = fallDamage;
    }

    public Boolean getHunger() {
        return hunger;
    }

    public void setHunger(Boolean hunger) {
        this.hunger = hunger;
    }

    public Boolean getChat() {
        return chat;
    }

    public void setChat(Boolean chat) {
        this.chat = chat;
    }

    public ArrayList<String> getTrollPlayers() {
        return trollPlayers;
    }

    public void clearTrollPlayers() {
        this.trollPlayers.clear();
    }

    public void addTrollPlayer(String string) {
        this.trollPlayers.add(string);
    }

    public void removeTrollPlayer(String string) {
        this.trollPlayers.remove(string);
    }

    public ArrayList<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public void addVanishedPlayer(UUID uuid) {
        this.vanishedPlayers.add(uuid);
    }

    public void removeVanishedPlayer(UUID uuid) {
        this.vanishedPlayers.remove(uuid);
    }

    public ArrayList<UUID> getKnockbackPlayers() {
        return knockbackPlayers;
    }

    public void addKnockbackPlayer(UUID uuid) {
        this.knockbackPlayers.add(uuid);
    }

    public void removeKnockbackPlayer(UUID uuid) {
        this.knockbackPlayers.remove(uuid);
    }
}
