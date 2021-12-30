package com.eintosti.troll.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author einTosti
 */
public class TrollManager {

    private final List<String> trollPlayers;
    private final List<UUID> vanishedPlayers;
    private final List<UUID> knockBackPlayers;
    private int knockBack;
    private boolean interactions;
    private boolean blockDamage;
    private boolean placeBlocks;
    private boolean fallDamage;
    private boolean hunger;
    private boolean chat;

    public TrollManager() {
        this.knockBack = 15;

        this.interactions = true;
        this.blockDamage = true;
        this.placeBlocks = true;
        this.fallDamage = true;
        this.hunger = true;
        this.chat = true;

        this.trollPlayers = new ArrayList<>();
        this.vanishedPlayers = new ArrayList<>();
        this.knockBackPlayers = new ArrayList<>();
    }

    public boolean isAllowed(Player player) {
        return player.hasPermission("troll.use") || this.trollPlayers.contains(player.getName());
    }

    public int getKnockBack() {
        return knockBack;
    }

    public void setKnockBack(int knockBack) {
        this.knockBack = knockBack;
    }

    public boolean isInteractions() {
        return interactions;
    }

    public void setInteractions(Boolean interactions) {
        this.interactions = interactions;
    }

    public boolean isBlockDamage() {
        return blockDamage;
    }

    public void setBlockDamage(Boolean blockDamage) {
        this.blockDamage = blockDamage;
    }

    public boolean isPlaceBlocks() {
        return placeBlocks;
    }

    public void setPlaceBlocks(Boolean placeBlocks) {
        this.placeBlocks = placeBlocks;
    }

    public boolean isFallDamage() {
        return fallDamage;
    }

    public void setFallDamage(Boolean fallDamage) {
        this.fallDamage = fallDamage;
    }

    public boolean isHunger() {
        return hunger;
    }

    public void setHunger(Boolean hunger) {
        this.hunger = hunger;
    }

    public boolean isChat() {
        return chat;
    }

    public void setChat(Boolean chat) {
        this.chat = chat;
    }

    public List<String> getTrollPlayers() {
        return trollPlayers;
    }

    public boolean isTrollPlayer(String name) {
        return this.trollPlayers.contains(name);
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

    public boolean isVanishedPlayer(UUID uuid) {
        return this.vanishedPlayers.contains(uuid);
    }

    public void addVanishedPlayer(UUID uuid) {
        this.vanishedPlayers.add(uuid);
    }

    public void removeVanishedPlayer(UUID uuid) {
        this.vanishedPlayers.remove(uuid);
    }

    public boolean isKnockBackPlayer(UUID uuid) {
        return this.knockBackPlayers.contains(uuid);
    }

    public void addKnockBackPlayer(UUID uuid) {
        this.knockBackPlayers.add(uuid);
    }

    public void removeKnockBackPlayer(UUID uuid) {
        this.knockBackPlayers.remove(uuid);
    }
}
