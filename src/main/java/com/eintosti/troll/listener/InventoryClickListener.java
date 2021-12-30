package com.eintosti.troll.listener;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.eintosti.troll.Troll;
import com.eintosti.troll.inventory.GamemodeInventory;
import com.eintosti.troll.inventory.PermissionInventory;
import com.eintosti.troll.inventory.SettingsInventory;
import com.eintosti.troll.manager.TrollManager;
import com.eintosti.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * @author einTosti
 */
public class InventoryClickListener implements Listener {

    private final Troll plugin;
    private final TrollManager trollManager;

    private final GamemodeInventory gamemodeInventory;
    private final PermissionInventory permissionInventory;
    private final SettingsInventory settingsInventory;

    public InventoryClickListener(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();

        this.gamemodeInventory = plugin.getGamemodeInventory();
        this.permissionInventory = plugin.getPermissionInventory();
        this.settingsInventory = plugin.getSettingsInventory();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean isValid(InventoryClickEvent event, String inventoryName) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(inventoryName)) {
            return false;
        }

        ItemStack itemStack = event.getCurrentItem();
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) {
            return false;
        }

        if (!trollManager.isAllowed(player)) {
            player.closeInventory();
            player.sendMessage(plugin.getString("no_permissions"));
            XSound.ENTITY_ITEM_BREAK.play(player);
            return false;
        }

        return true;
    }

    @EventHandler
    public void onTrollInventoryClick(InventoryClickEvent event) {
        if (!isValid(event, plugin.getString("main_guiName"))) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        UUID playerUuid = player.getUniqueId();
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 3: {
                Bukkit.getOnlinePlayers().stream()
                        .filter(pl -> !pl.equals(player))
                        .forEach(pl -> pl.setHealth(0.0));
                player.closeInventory();
                player.sendMessage(plugin.getString("players_killed"));
                XSound.ENTITY_LIGHTNING_BOLT_IMPACT.play(player);
                break;
            }

            case 4: {
                player.getInventory().addItem(new ItemBuilder(XMaterial.BLAZE_POWDER).name(plugin.getString("effect_item")).build());
                player.closeInventory();
                player.sendMessage(plugin.getString("received_effectItem"));
                XSound.ENTITY_ITEM_PICKUP.play(player);
                break;
            }

            case 5: {
                Bukkit.getOnlinePlayers().forEach(pl -> pl.teleport(player));
                player.sendMessage(plugin.getString("players_teleported"));
                XSound.ENTITY_ENDERMAN_TELEPORT.play(player);
                break;
            }

            case 9: {
                GameMode gameMode = GameMode.SURVIVAL;
                String string = plugin.getString("creative_disabled");

                if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    gameMode = GameMode.CREATIVE;
                    string = plugin.getString("creative_enabled");
                }

                player.closeInventory();
                player.setGameMode(gameMode);
                player.sendMessage(string);
                XSound.ENTITY_CHICKEN_EGG.play(player);
                break;
            }

            case 10: {
                if (!trollManager.isKnockBackPlayer(playerUuid)) {
                    trollManager.addKnockBackPlayer(playerUuid);
                    player.sendMessage(plugin.getString("knockback_enabled"));
                } else {
                    trollManager.removeKnockBackPlayer(playerUuid);
                    player.sendMessage(plugin.getString("knockback_disabled"));
                }

                player.closeInventory();
                XSound.ENTITY_CHICKEN_EGG.play(player);
                break;
            }

            case 11: {
                boolean enabled = false;
                if (!trollManager.isVanishedPlayer(playerUuid)) {
                    trollManager.addVanishedPlayer(playerUuid);
                    enabled = true;
                    player.sendMessage(plugin.getString("vanish_enabled"));
                } else {
                    trollManager.removeVanishedPlayer(playerUuid);
                    player.sendMessage(plugin.getString("vanish_disabled"));
                }

                player.closeInventory();
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (enabled) {
                        pl.hidePlayer(player);
                    } else {
                        pl.showPlayer(player);
                    }
                }
                XSound.ENTITY_CHICKEN_EGG.play(player);
                break;
            }

            case 15: {
                player.closeInventory();
                player.getInventory().addItem(new ItemBuilder(XMaterial.IRON_AXE).name(plugin.getString("thorHammer_item")).build());
                player.sendMessage(plugin.getString("received_thorHammer"));
                XSound.ENTITY_ITEM_PICKUP.play(player);
                break;
            }

            case 16: {
                player.closeInventory();
                player.getInventory().addItem(new ItemBuilder(XMaterial.TNT).name(plugin.getString("tntRain_item")).build());
                player.sendMessage(plugin.getString("received_tntRain"));
                XSound.ENTITY_ITEM_PICKUP.play(player);
                break;
            }

            case 17: {
                player.closeInventory();
                player.getInventory().addItem(new ItemBuilder(XMaterial.FIRE_CHARGE).name(plugin.getString("judgementDay_item")).build());
                player.sendMessage(plugin.getString("received_judgementDay"));
                XSound.ENTITY_ITEM_PICKUP.play(player);
                break;
            }

            case 22: {
                plugin.getSettingsInventory().openInventory(player);
                XSound.BLOCK_CHEST_OPEN.play(player);
                break;
            }
        }
    }

    @EventHandler
    public void onSettingsInventoryClick(InventoryClickEvent event) {
        if (!isValid(event, plugin.getString("settings_guiName"))) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 18:
                trollManager.setInteractions(!trollManager.isInteractions());
                break;
            case 19:
                trollManager.setBlockDamage(!trollManager.isBlockDamage());
                break;
            case 20:
                trollManager.setPlaceBlocks(!trollManager.isPlaceBlocks());
                break;
            case 21:
                trollManager.setFallDamage(!trollManager.isFallDamage());
                break;
            case 22:
                trollManager.setHunger(!trollManager.isHunger());
                break;
            case 23:
                trollManager.setChat(!trollManager.isChat());
                break;
            case 16:
                player.openInventory(gamemodeInventory.getInventory(player));
                XSound.BLOCK_CHEST_OPEN.play(player);
                return;
            case 17:
                player.openInventory(permissionInventory.getInventory(player));
                XSound.BLOCK_CHEST_OPEN.play(player);
                return;
        }

        settingsInventory.openInventory(player);
        XSound.ENTITY_CHICKEN_EGG.play(player);
    }

    @EventHandler
    public void onGamemodeInventoryClick(InventoryClickEvent event) {
        if (!isValid(event, plugin.getString("gamemode_guiName"))) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        event.setCancelled(true);

        if (itemStack.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) {
            return;
        }

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        String skullOwnerName = skullMeta.getOwner();
        if (skullOwnerName == null) {
            return;
        }

        switch (event.getSlot()) {
            case 18:
                if (skullOwnerName.equalsIgnoreCase("MHF_ArrowLeft")) {
                    gamemodeInventory.back(player);
                }
                break;
            case 26:
                if (skullOwnerName.equalsIgnoreCase("MHF_ArrowRight")) {
                    gamemodeInventory.forward(player);
                }
                break;
        }

        if (event.getSlot() >= 9 && event.getSlot() <= 17) {
            Player skullOwner = Bukkit.getServer().getPlayer(skullOwnerName);
            if (skullOwner == null) {
                player.closeInventory();
                return;
            }

            switch (skullOwner.getGameMode()) {
                case SURVIVAL:
                    skullOwner.setGameMode(GameMode.CREATIVE);
                    break;
                case CREATIVE:
                    skullOwner.setGameMode(GameMode.SURVIVAL);
                    break;
                case ADVENTURE:
                    skullOwner.setGameMode(GameMode.ADVENTURE);
                    break;
            }

            XSound.ENTITY_ITEM_PICKUP.play(player);
            player.openInventory(gamemodeInventory.getInventory(player));
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPermissionInventoryClick(InventoryClickEvent event) {
        if (!isValid(event, plugin.getString("permissions_guiName"))) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        event.setCancelled(true);

        if (itemStack.getType() == XMaterial.PLAYER_HEAD.parseMaterial()) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            String skullOwnerName = skullMeta.getOwner();
            if (skullOwnerName == null) {
                return;
            }

            switch (event.getSlot()) {
                case 9:
                    if (skullOwnerName.equalsIgnoreCase("MHF_ArrowLeft")) {
                        permissionInventory.back(player);
                    }
                    break;
                case 17:
                    if (skullOwnerName.equalsIgnoreCase("MHF_ArrowRight")) {
                        permissionInventory.forward(player);
                    }
                    break;
            }

            XSound.ENTITY_ITEM_PICKUP.play(player);
        }

        Inventory inventory = permissionInventory.getInventory(player);
        int skullSlot = event.getSlot() - 9;

        if (skullSlot >= 10 && skullSlot <= 16) {
            ItemStack skullItem = inventory.getItem(skullSlot);
            SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
            String skullOwnerName = skullMeta.getOwner();
            if (skullOwnerName == null) {
                return;
            }

            Player skullOwner = Bukkit.getServer().getPlayer(skullOwnerName);

            if (!trollManager.isTrollPlayer(skullOwnerName)) {
                trollManager.addTrollPlayer(skullOwnerName);
                if (skullOwner != null) {
                    skullOwner.sendMessage(plugin.getString("permissions_receive_player"));
                }

                Bukkit.getOnlinePlayers().stream()
                        .filter(pl -> !pl.equals(skullOwner))
                        .filter(trollManager::isAllowed)
                        .forEach(pl -> pl.sendMessage(plugin.getString("permissions_receive_all").replace("%player%", skullOwnerName)));
            } else {
                trollManager.removeTrollPlayer(skullOwnerName);
                if (skullOwner != null) {
                    skullOwner.sendMessage(plugin.getString("permissions_remove_player"));
                }

                Bukkit.getOnlinePlayers().stream()
                        .filter(pl -> !pl.equals(skullOwner))
                        .filter(trollManager::isAllowed)
                        .forEach(pl -> pl.sendMessage(plugin.getString("permissions_remove_all").replace("%player%", skullOwnerName)));
            }

            inventory = permissionInventory.getInventory(player);
            player.openInventory(inventory);
            XSound.ENTITY_ITEM_PICKUP.play(player);
        }
    }

    @EventHandler
    public void onEffectInventoryClick(InventoryClickEvent event) {
        if (!isValid(event, plugin.getString("effect_guiName"))) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 0:
                addPotionEffect(player, PotionEffectType.BLINDNESS, plugin.getString("effect_blindness"));
                break;
            case 1:
                addPotionEffect(player, PotionEffectType.CONFUSION, plugin.getString("effect_nausea"));
                break;
            case 2:
                addPotionEffect(player, PotionEffectType.DAMAGE_RESISTANCE, plugin.getString("effect_resistance"));
                break;
            case 3:
                addPotionEffect(player, PotionEffectType.FAST_DIGGING, plugin.getString("effect_haste"));
                break;
            case 4:
                addPotionEffect(player, PotionEffectType.FIRE_RESISTANCE, plugin.getString("effect_fireResistance"));
                break;
            case 5:
                addPotionEffect(player, PotionEffectType.HUNGER, plugin.getString("effect_hunger"));
                break;
            case 6:
                addPotionEffect(player, PotionEffectType.INCREASE_DAMAGE, plugin.getString("effect_strength"));
                break;
            case 7:
                addPotionEffect(player, PotionEffectType.INVISIBILITY, plugin.getString("effect_invisibility"));
                break;
            case 8:
                addPotionEffect(player, PotionEffectType.JUMP, plugin.getString("effect_jumpBoost"));
                break;
            case 9:
                addPotionEffect(player, PotionEffectType.NIGHT_VISION, plugin.getString("effect_nightVision"));
                break;
            case 10:
                addPotionEffect(player, PotionEffectType.POISON, plugin.getString("effect_poison"));
                break;
            case 11:
                addPotionEffect(player, PotionEffectType.REGENERATION, plugin.getString("effect_regeneration"));
                break;
            case 12:
                addPotionEffect(player, PotionEffectType.SLOW, plugin.getString("effect_slowness"));
                break;
            case 13:
                addPotionEffect(player, PotionEffectType.SLOW_DIGGING, plugin.getString("effect_miningFatigue"));
                break;
            case 14:
                addPotionEffect(player, PotionEffectType.SPEED, plugin.getString("effect_speed"));
                break;
            case 15:
                addPotionEffect(player, PotionEffectType.WATER_BREATHING, plugin.getString("effect_waterBreathing"));
                break;
            case 16:
                addPotionEffect(player, PotionEffectType.WEAKNESS, plugin.getString("effect_weakness"));
                break;
            case 20:
                Bukkit.getOnlinePlayers().stream()
                        .filter(pl -> !pl.equals(player))
                        .forEach(pl -> pl.setFireTicks(200));
                player.sendMessage(plugin.getString("players_burn"));
                XSound.ENTITY_ITEM_PICKUP.play(player);
                break;
            case 24:
                removeAllEffects(player);
                break;
        }
    }

    private void addPotionEffect(Player player, PotionEffectType potion, String potionName) {
        if (!trollManager.isAllowed(player)) {
            return;
        }

        Bukkit.getOnlinePlayers().stream()
                .filter(pl -> !pl.equals(player))
                .forEach(pl -> pl.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE, 1)));

        XSound.ENTITY_ITEM_PICKUP.play(player);
        player.sendMessage(plugin.getString("players_receiveEffects").replace("%effect%", potionName));
    }

    private void removeAllEffects(Player player) {
        if (!trollManager.isAllowed(player)) {
            return;
        }

        Bukkit.getOnlinePlayers().stream()
                .filter(pl -> !pl.equals(player))
                .forEach(pl -> {
                    pl.getActivePotionEffects().forEach(potionEffect -> pl.removePotionEffect(potionEffect.getType()));
                    pl.setFireTicks(0);
                });

        XSound.ENTITY_ITEM_PICKUP.play(player);
        player.sendMessage(plugin.getString("players_removeEffects"));
    }
}
