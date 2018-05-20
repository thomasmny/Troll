package de.eintosti.troll.listeners;

import de.eintosti.troll.inventories.GamemodeInventory;
import de.eintosti.troll.inventories.PermissionInventory;
import de.eintosti.troll.inventories.SettingsInventory;
import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author einTosti
 */
public class InventoryClick implements Listener {

    @EventHandler
    public void onTrollInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(Utils.getInstance().getString("main_guiName")))
            return;
        if (!Utils.getInstance().isAllowed(player))
            return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta()))
            return;
        event.setCancelled(true);
        Material itemType = itemStack.getType();

        //Kill all players
        if (itemType == Material.DIAMOND_SWORD) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!(pl.equals(player))) {
                    if (Utils.getInstance().isAllowed(player)) {
                        pl.setHealth(0.0);
                    }
                }
            }
            player.closeInventory();
            player.sendMessage(Utils.getInstance().getString("players_killed"));
        }

        //Manage effects
        if (itemType == Material.BLAZE_POWDER) {
            if (Utils.getInstance().isAllowed(player)) {
                getEffectsItem(player);
                player.closeInventory();
                player.sendMessage(Utils.getInstance().getString("received_effectItem"));
            }
        }

        //Teleport all players
        if (itemType == Material.ENDER_PEARL) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (Utils.getInstance().isAllowed(player)) {
                    pl.teleport(player);
                }
            }
            player.sendMessage(Utils.getInstance().getString("players_teleported"));
        }

        //Manage player gamemode
        if (itemType == Material.GRASS) {
            GameMode gameMode = GameMode.SURVIVAL;
            String string = Utils.getInstance().getString("creative_disabled");

            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                gameMode = GameMode.CREATIVE;
                string = Utils.getInstance().getString("creative_enabled");
            }

            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                player.setGameMode(gameMode);
                player.sendMessage(string);
            }
        }

        //Knockback
        if (itemType == Material.FEATHER) {
            if (Utils.getInstance().isAllowed(player)) {
                if (!Utils.getInstance().mKnockbackPlayers.contains(player.getUniqueId())) {
                    Utils.getInstance().mKnockbackPlayers.add(player.getUniqueId());
                    player.sendMessage(Utils.getInstance().getString("knockback_enabled"));
                } else {
                    Utils.getInstance().mKnockbackPlayers.remove(player.getUniqueId());
                    player.sendMessage(Utils.getInstance().getString("knockback_disabled"));
                }
                player.closeInventory();
            }
        }

        //Vanish
        if (itemType == Material.QUARTZ) {
            boolean enabled = false;

            if (Utils.getInstance().isAllowed(player)) {
                if (!Utils.getInstance().mVanishedPlayers.contains(player.getUniqueId())) {
                    Utils.getInstance().mVanishedPlayers.add(player.getUniqueId());
                    enabled = true;
                    player.sendMessage(Utils.getInstance().getString("vanish_enabled"));
                } else {
                    Utils.getInstance().mVanishedPlayers.remove(player.getUniqueId());
                    player.sendMessage(Utils.getInstance().getString("vanish_disabled"));
                }
                player.closeInventory();

                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (enabled) {
                        pl.hidePlayer(player);
                    } else {
                        pl.showPlayer(player);
                    }
                }
            }
        }

        //Thor
        if (itemType == Material.IRON_AXE) {
            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                getLightningItem(player);
                player.sendMessage(Utils.getInstance().getString("received_thorHammer"));
            }
        }

        //TNT-Rain
        if (itemType == Material.TNT) {
            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                getTntItem(player);
                player.sendMessage(Utils.getInstance().getString("received_tntRain"));
            }
        }

        //Judgement-Day
        if (itemType == Material.FIREBALL) {
            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                getFireballItem(player);
                player.sendMessage(Utils.getInstance().getString("received_judgementDay"));
            }
        }

        //Settings
        if (itemType == Material.NETHER_STAR) {
            if (Utils.getInstance().isAllowed(player)) {
                SettingsInventory.getInstance().openInventory(player);
            }
        }
    }

    @EventHandler
    public void onSettingsInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(Utils.getInstance().getString("settings_guiName")))
            return;
        if (!Utils.getInstance().isAllowed(player))
            return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta()))
            return;
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 18:
                if (Utils.getInstance().mInteractions) {
                    Utils.getInstance().mInteractions = false;
                } else {
                    Utils.getInstance().mInteractions = true;
                }
                SettingsInventory.getInstance().openInventory(player);
                break;
            case 19:
                if (Utils.getInstance().mBlockDamage) {
                    Utils.getInstance().mBlockDamage = false;
                } else {
                    Utils.getInstance().mBlockDamage = true;
                }
                SettingsInventory.getInstance().openInventory(player);
                break;
            case 20:
                if (Utils.getInstance().mPlaceBlocks) {
                    Utils.getInstance().mPlaceBlocks = false;
                } else {
                    Utils.getInstance().mPlaceBlocks = true;
                }
                SettingsInventory.getInstance().openInventory(player);
                break;
            case 21:
                if (Utils.getInstance().mFallDamage) {
                    Utils.getInstance().mFallDamage = false;
                } else {
                    Utils.getInstance().mFallDamage = true;
                }
                SettingsInventory.getInstance().openInventory(player);
                break;
            case 22:
                if (Utils.getInstance().mHunger) {
                    Utils.getInstance().mHunger = false;
                } else {
                    Utils.getInstance().mHunger = true;
                }
                SettingsInventory.getInstance().openInventory(player);
                break;
            case 23:
                if (Utils.getInstance().mChat) {
                    Utils.getInstance().mChat = false;
                } else {
                    Utils.getInstance().mChat = true;
                }
                SettingsInventory.getInstance().openInventory(player);
                break;
            case 16:
                if (Utils.getInstance().isAllowed(player)) {
                    if (GamemodeInventory.getInstance().getInvIntex(player) == null) {
                        GamemodeInventory.getInstance().setInvIndex(player, 0);
                    }
                    player.openInventory(GamemodeInventory.getInstance().getInventory(player));
                }
                break;
            case 17:
                if (Utils.getInstance().isAllowed(player)) {
                    if (PermissionInventory.getInstance().getInvIntex(player) == null) {
                        PermissionInventory.getInstance().setInvIndex(player, 0);
                    }
                    player.openInventory(PermissionInventory.getInstance().getInventory(player));
                }
                break;
        }
    }

    @EventHandler
    public void onGamemodeInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(Utils.getInstance().getString("gamemode_guiName")))
            return;
        if (!Utils.getInstance().isAllowed(player))
            return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta()))
            return;
        event.setCancelled(true);

        if (Utils.getInstance().isAllowed(player)) {
            if (itemStack.getType() == Material.SKULL_ITEM) {
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                Player skullOwner = Bukkit.getServer().getPlayer(skullMeta.getOwner());

                switch (event.getSlot()) {
                    case 18:
                        if (skullMeta.getOwner().equals("MHF_ArrowLeft")) {
                            goBackGamemode(player);
                            break;
                        }
                    case 26:
                        if (skullMeta.getOwner().equals("MHF_ArrowRight")) {
                            goForwardGamemode(player);
                            break;
                        }
                        break;
                }

                if (event.getSlot() >= 9 && event.getSlot() <= 17) {
                    if (skullOwner.getGameMode() == GameMode.CREATIVE) {
                        skullOwner.setGameMode(GameMode.SURVIVAL);
                    } else if (skullOwner.getGameMode() == GameMode.SURVIVAL) {
                        skullOwner.setGameMode(GameMode.ADVENTURE);
                    } else if (skullOwner.getGameMode() == GameMode.ADVENTURE) {
                        skullOwner.setGameMode(GameMode.CREATIVE);
                    }
                    player.openInventory(GamemodeInventory.getInstance().getInventory(player));
                }
            }
        }
    }

    @EventHandler
    public void onPermissionInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(Utils.getInstance().getString("permissions_guiName")))
            return;
        if (!Utils.getInstance().isAllowed(player))
            return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta()))
            return;
        event.setCancelled(true);

        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            switch (event.getSlot()) {
                case 9:
                    if (skullMeta.getOwner().equals("MHF_ArrowLeft")) {
                        goBackPerms(player);
                        break;
                    }
                case 17:
                    if (skullMeta.getOwner().equals("MHF_ArrowRight")) {
                        goForwardPerms(player);
                        break;
                    }
                    break;
            }
        }

        Inventory inv = PermissionInventory.getInstance().getInventory(player);
        int skullSlot = event.getSlot() - 9;

        if (skullSlot >= 10 && skullSlot <= 16) {
            ItemStack skullItem = inv.getItem(skullSlot);
            SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
            Player skullOwner = Bukkit.getServer().getPlayer(skullMeta.getOwner());

            if (!Utils.getInstance().mTrollEnabledPlayers.contains(skullOwner.getName())) {
                Utils.getInstance().mTrollEnabledPlayers.add(skullOwner.getName());

                skullOwner.sendMessage(Utils.getInstance().getString("permissions_receive_player"));
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (!pl.equals(skullOwner) && Utils.getInstance().isAllowed(pl)) {
                        pl.sendMessage(Utils.getInstance().getString("permissions_receive_all").replace("%player%", skullOwner.getName()));
                    }
                }
            } else {
                Utils.getInstance().mTrollEnabledPlayers.remove(skullOwner.getName());

                skullOwner.sendMessage(Utils.getInstance().getString("permissions_remove_player"));
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (!pl.equals(skullOwner) && Utils.getInstance().isAllowed(pl)) {
                        pl.sendMessage(Utils.getInstance().getString("permissions_remove_all").replace("%player%", skullOwner.getName()));
                    }
                }
            }
            inv = PermissionInventory.getInstance().getInventory(player);
            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onEffectInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(Utils.getInstance().getString("effect_guiName")))
            return;
        if (!Utils.getInstance().isAllowed(player))
            return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta()))
            return;
        event.setCancelled(true);

        if (Utils.getInstance().isAllowed(player)) {
            switch (event.getSlot()) {
                case 0:
                    addPotionEffekt(player, PotionEffectType.BLINDNESS,  Utils.getInstance().getString("effect_blindness"));
                    break;
                case 1:
                    addPotionEffekt(player, PotionEffectType.CONFUSION,  Utils.getInstance().getString("effect_nausea"));
                    break;
                case 2:
                    addPotionEffekt(player, PotionEffectType.DAMAGE_RESISTANCE,  Utils.getInstance().getString("effect_resistance"));
                    break;
                case 3:
                    addPotionEffekt(player, PotionEffectType.FAST_DIGGING,  Utils.getInstance().getString("effect_haste"));
                    break;
                case 4:
                    addPotionEffekt(player, PotionEffectType.FIRE_RESISTANCE,  Utils.getInstance().getString("effect_fireResistance"));
                    break;
                case 5:
                    addPotionEffekt(player, PotionEffectType.HUNGER,  Utils.getInstance().getString("effect_hunger"));
                    break;
                case 6:
                    addPotionEffekt(player, PotionEffectType.INCREASE_DAMAGE,  Utils.getInstance().getString("effect_strength"));
                    break;
                case 7:
                    addPotionEffekt(player, PotionEffectType.INVISIBILITY,  Utils.getInstance().getString("effect_invisibility"));
                    break;
                case 8:
                    addPotionEffekt(player, PotionEffectType.JUMP,  Utils.getInstance().getString("effect_jumpBoost"));
                    break;
                case 9:
                    addPotionEffekt(player, PotionEffectType.NIGHT_VISION,  Utils.getInstance().getString("effect_nightVision"));
                    break;
                case 10:
                    addPotionEffekt(player, PotionEffectType.POISON,  Utils.getInstance().getString("effect_poison"));
                    break;
                case 11:
                    addPotionEffekt(player, PotionEffectType.REGENERATION,  Utils.getInstance().getString("effect_regeneration"));
                    break;
                case 12:
                    addPotionEffekt(player, PotionEffectType.SLOW,  Utils.getInstance().getString("effect_slowness"));
                    break;
                case 13:
                    addPotionEffekt(player, PotionEffectType.SLOW_DIGGING,  Utils.getInstance().getString("effect_miningFatigue"));
                    break;
                case 14:
                    addPotionEffekt(player, PotionEffectType.SPEED,  Utils.getInstance().getString("effect_speed"));
                    break;
                case 15:
                    addPotionEffekt(player, PotionEffectType.WATER_BREATHING,  Utils.getInstance().getString("effect_waterBreathing"));
                    break;
                case 16:
                    addPotionEffekt(player, PotionEffectType.WEAKNESS, Utils.getInstance().getString("effect_weakness"));
                    break;
                case 20:
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!(pl.equals(player))) {
                            if (Utils.getInstance().isAllowed(player)) {
                                pl.setFireTicks(200);
                            }
                        }
                    }
                    player.sendMessage(Utils.getInstance().getString("players_burn"));
                    break;
                case 24:
                    removeAllEffects(player);
                    break;
            }
        }
    }

    private void getEffectsItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(Utils.getInstance().getString("effect_item"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void getLightningItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(Utils.getInstance().getString("thorHammer_item"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void getTntItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.TNT);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(Utils.getInstance().getString("tntRain_item"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void getFireballItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.FIREBALL);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(Utils.getInstance().getString("judgementDay_item"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void addPotionEffekt(Player player, PotionEffectType potion, String potionName) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.equals(player))) {
                if (Utils.getInstance().isAllowed(player)) {
                    pl.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE, 1));
                }
            }
        }
        player.sendMessage(Utils.getInstance().getString("players_receiveEffects").replace("%effect%", potionName));
    }

    private void removeAllEffects(Player player) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.equals(player))) {
                if (Utils.getInstance().isAllowed(player)) {
                    pl.removePotionEffect(PotionEffectType.BLINDNESS);
                    pl.removePotionEffect(PotionEffectType.CONFUSION);
                    pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
                    pl.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    pl.removePotionEffect(PotionEffectType.HUNGER);
                    pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    pl.removePotionEffect(PotionEffectType.INVISIBILITY);
                    pl.removePotionEffect(PotionEffectType.JUMP);
                    pl.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    pl.removePotionEffect(PotionEffectType.POISON);
                    pl.removePotionEffect(PotionEffectType.REGENERATION);
                    pl.removePotionEffect(PotionEffectType.SLOW);
                    pl.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                    pl.removePotionEffect(PotionEffectType.SPEED);
                    pl.removePotionEffect(PotionEffectType.WATER_BREATHING);
                    pl.removePotionEffect(PotionEffectType.WEAKNESS);
                    pl.setFireTicks(0);
                }
            }
        }
        player.sendMessage(Utils.getInstance().getString("players_removeEffects"));
    }

    private void goBackGamemode(Player player) {
        GamemodeInventory.getInstance().decrementInv(player);
        player.openInventory(GamemodeInventory.getInstance().getInventory(player));
    }

    private void goForwardGamemode(Player player) {
        GamemodeInventory.getInstance().incrementInv(player);
        player.openInventory(GamemodeInventory.getInstance().getInventory(player));
    }

    private void goBackPerms(Player player) {
        PermissionInventory.getInstance().decrementInv(player);
        player.openInventory(PermissionInventory.getInstance().getInventory(player));
    }

    private void goForwardPerms(Player player) {
        PermissionInventory.getInstance().incrementInv(player);
        player.openInventory(PermissionInventory.getInstance().getInventory(player));
    }
}
