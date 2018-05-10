package de.eintosti.troll.listeners;

import de.eintosti.troll.inventories.GamemodeInventory;
import de.eintosti.troll.inventories.PermissionInventory;
import de.eintosti.troll.inventories.SettingsInventory;
import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
    private final String mPrefix = Utils.getInstance().mPrefix;

    @EventHandler
    public void onTrollInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().getName().equals("§5Troll Menü")) {
            return;
        }
        event.setCancelled(true);

        ItemStack itemStack = event.getCurrentItem();
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) {
            return;
        }
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
            player.sendMessage(mPrefix + "§7Alle Spieler wurden §dgetötet§7.");
        }

        //Manage effects
        if (itemType == Material.BLAZE_POWDER) {
            if (Utils.getInstance().isAllowed(player)) {
                getEffectsItem(player);
                player.closeInventory();
                player.sendMessage(mPrefix + "§7Du hast das §dEffekt §7Item erhalten.");
            }
        }

        //Teleport all players
        if (itemType == Material.ENDER_PEARL) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (Utils.getInstance().isAllowed(player)) {
                    pl.teleport(player);
                }
            }
            player.sendMessage(mPrefix + "§7Alle Spieler wurden zu dir §dteleportiert§7.");
        }

        //Manage player gamemode
        if (itemType == Material.GRASS) {
            GameMode gameMode = GameMode.SURVIVAL;
            String string = "aus dem Kreativmodus §centfernt";

            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                gameMode = GameMode.CREATIVE;
                string = "in den Kreativmodus §agesetzt§7";
            }

            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                player.setGameMode(gameMode);
                player.sendMessage(mPrefix + "§7Du wurdest " + string + "§7.");
            }
        }

        //Knockback
        if (itemType == Material.FEATHER) {
            if (Utils.getInstance().isAllowed(player)) {
                if (!Utils.getInstance().mKnockbackPlayers.contains(player.getUniqueId())) {
                    Utils.getInstance().mKnockbackPlayers.add(player.getUniqueId());
                    player.sendMessage(mPrefix + "§7Verstärkter Rückstoß wurde §aaktiviert§7.");
                } else {
                    Utils.getInstance().mKnockbackPlayers.remove(player.getUniqueId());
                    player.sendMessage(mPrefix + "§7Verstärkter Rückstoß wurde §cdeaktiviert§7.");
                }
                player.closeInventory();
            }
        }

        //Unsichtbarkeit
        if (itemType == Material.QUARTZ) {
            boolean enabled = false;

            if (Utils.getInstance().isAllowed(player)) {
                if (!Utils.getInstance().mVanishedPlayers.contains(player.getUniqueId())) {
                    Utils.getInstance().mVanishedPlayers.add(player.getUniqueId());
                    enabled = true;
                    player.sendMessage(mPrefix + "§7Du bist nun für alle Spieler §aunsichtbar§7.");
                } else {
                    Utils.getInstance().mVanishedPlayers.remove(player.getUniqueId());
                    player.sendMessage(mPrefix + "§7Du bist nun für alle Spieler §csichtbar§7.");
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

        //dThor
        if (itemType == Material.IRON_AXE) {
            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                getLightningItem(player);
                player.sendMessage(mPrefix + "§7Du hast §dThor's Hammer §7erhalten.");
            }
        }

        //TNT-Regen
        if (itemType == Material.TNT) {
            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                getTntItem(player);
                player.sendMessage(mPrefix + "§7Du hast das §dTNT-Regen §7Item erhalten.");
            }
        }

        //Judgement-Day
        if (itemType == Material.FIREBALL) {
            if (Utils.getInstance().isAllowed(player)) {
                player.closeInventory();
                getFireballItem(player);
                player.sendMessage(mPrefix + "§7Du hast das §dJudgement-Day §7Item erhalten.");
            }
        }

        //Erweiterte Einstellungen
        if (itemType == Material.NETHER_STAR) {
            if (Utils.getInstance().isAllowed(player)) {
                SettingsInventory.getInstance().openInventory(player);
            }
        }
    }

    @EventHandler
    public void onSettingsInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getName().equals("§5Erweiterte Einstellungen")) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        ItemStack itemStack = event.getCurrentItem();
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) {
            return;
        }

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

        if (!event.getInventory().getName().equals("§5Gamemode")) {
            return;
        }

        event.setCancelled(true);
        ItemStack itemStack = event.getCurrentItem();
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) {
            return;
        }
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
        if (!event.getInventory().getName().equals("§5Rechtesystem")) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        ItemStack selectedItemStack = event.getCurrentItem();
        if ((selectedItemStack == null) || (selectedItemStack.getType() == Material.AIR) || (selectedItemStack.getType() != Material.INK_SACK) || (!selectedItemStack.hasItemMeta())) {
            return;
        }

        if (selectedItemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) selectedItemStack.getItemMeta();
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

        if (Utils.getInstance().isAllowed(player)) {
            Inventory inv = PermissionInventory.getInstance().getInventory(player);
            int skullSlot = event.getSlot() - 9;

            if (skullSlot >= 10 && skullSlot <= 16) {
                ItemStack itemStack = inv.getItem(skullSlot);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                Player skullOwner = Bukkit.getServer().getPlayer(skullMeta.getOwner());

                if (!Utils.getInstance().mTrollEnabledPlayers.contains(skullOwner.getName())) {
                    Utils.getInstance().mTrollEnabledPlayers.add(skullOwner.getName());

                    skullOwner.sendMessage(mPrefix + "§7Du hast nun §aRechte §7auf das §dTrollmenü§7.");
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!pl.equals(skullOwner) && Utils.getInstance().isAllowed(pl)) {
                            pl.sendMessage(mPrefix + "§7" + skullOwner.getName() + " §7hat nun §aRechte §7auf das §dTrollmenü§7.");
                        }
                    }
                } else {
                    Utils.getInstance().mTrollEnabledPlayers.remove(skullOwner.getName());

                    skullOwner.sendMessage(mPrefix + "§7Du hast nun §ckeine Rechte §7mehr auf das §dTrollmenü§7.");
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!pl.equals(skullOwner) && Utils.getInstance().isAllowed(pl)) {
                            pl.sendMessage(mPrefix + "§7" + skullOwner.getName() + " §7hat nun §ckeine Rechte §7mehr auf das §dTrollmenü§7.");
                        }
                    }
                }
                inv = PermissionInventory.getInstance().getInventory(player);
                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onEffectInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().getName().equals("§5Effekt Menü")) {
            return;
        }
        event.setCancelled(true);
        ItemStack itemStack = event.getCurrentItem();
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) {
            return;
        }
        if (Utils.getInstance().isAllowed(player)) {
            switch (event.getSlot()) {
                case 0:
                    addPotionEffekt(player, PotionEffectType.BLINDNESS, "Blindheit");
                    break;
                case 1:
                    addPotionEffekt(player, PotionEffectType.CONFUSION, "Verwirrung");
                    break;
                case 2:
                    addPotionEffekt(player, PotionEffectType.DAMAGE_RESISTANCE, "Resistenz");
                    break;
                case 3:
                    addPotionEffekt(player, PotionEffectType.FAST_DIGGING, "Eile");
                    break;
                case 4:
                    addPotionEffekt(player, PotionEffectType.FIRE_RESISTANCE, "Feuerresistenz");
                    break;
                case 5:
                    addPotionEffekt(player, PotionEffectType.HUNGER, "Hunger");
                    break;
                case 6:
                    addPotionEffekt(player, PotionEffectType.INCREASE_DAMAGE, "Stärke");
                    break;
                case 7:
                    addPotionEffekt(player, PotionEffectType.INVISIBILITY, "Unsichtbarkeit");
                    break;
                case 8:
                    addPotionEffekt(player, PotionEffectType.JUMP, "Sprungkraft");
                    break;
                case 9:
                    addPotionEffekt(player, PotionEffectType.NIGHT_VISION, "Nachtsicht");
                    break;
                case 10:
                    addPotionEffekt(player, PotionEffectType.POISON, "Vergiftung");
                    break;
                case 11:
                    addPotionEffekt(player, PotionEffectType.REGENERATION, "Regeneration");
                    break;
                case 12:
                    addPotionEffekt(player, PotionEffectType.SLOW, "Langsamkeit");
                    break;
                case 13:
                    addPotionEffekt(player, PotionEffectType.SLOW_DIGGING, "Abbaulähmung");
                    break;
                case 14:
                    addPotionEffekt(player, PotionEffectType.SPEED, "Schnelligkeit");
                    break;
                case 15:
                    addPotionEffekt(player, PotionEffectType.WATER_BREATHING, "Unterwasseratmung");
                    break;
                case 16:
                    addPotionEffekt(player, PotionEffectType.WEAKNESS, "Schwäche");
                    break;
                case 20:
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!(pl.equals(player))) {
                            if (Utils.getInstance().isAllowed(player)) {
                                pl.setFireTicks(200);
                            }
                        }
                    }
                    player.sendMessage(mPrefix + "§7Alle Mitspieler wurden §dangezündet§7.");
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

        meta.setDisplayName("§dEffekt Menü öffnen §7(Rechtsklick)");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void getLightningItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName("§dThor's Hammer §7(Rechtsklick)");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void getTntItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.TNT);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName("§dTNT-Regen herbeirufen §7(Rechtsklick)");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void getFireballItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.FIREBALL);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName("§dJudgement-Day herbeirufen §7(Rechtsklick)");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    private void addPotionEffekt(Player player, PotionEffectType potion, String potionName) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.equals(player))) {
                if (Utils.getInstance().isAllowed(player)) {
                    pl.addPotionEffect(new PotionEffect(potion, 400, 1));
                }
            }
        }
        player.sendMessage(mPrefix + "§d" + potionName + " §7wurde auf alle Spieler gewirkt.");
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
        player.sendMessage(mPrefix + "§7Die §dStatuseffekte §7aller Mitspieler wurden §centfernt§7.");
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
