package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
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

/**
 * @author einTosti
 */
public class InventoryClick implements Listener {
    private Troll plugin;
    private InventoryManager inventoryManager;
    private TrollManager trollManager;

    public InventoryClick(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTrollInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(plugin.getString("main_guiName"))) return;
        if (!trollManager.isAllowed(player)) return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) return;

        event.setCancelled(true);
        Material itemType = itemStack.getType();

        switch (itemType) {
            case DIAMOND_SWORD:
                if (trollManager.isAllowed(player)) {
                    Bukkit.getOnlinePlayers().forEach(pl -> {
                        if (!(pl.equals(player))) {
                            pl.setHealth(0.0);
                        }
                    });
                    player.closeInventory();
                    player.sendMessage(plugin.getString("players_killed"));
                }
                break;
            case BLAZE_POWDER:
                if (trollManager.isAllowed(player)) {
                    player.getInventory().addItem(inventoryManager.getItemStack(Material.BLAZE_POWDER, 0, plugin.getString("effect_item")));
                    player.closeInventory();
                    player.sendMessage(plugin.getString("received_effectItem"));
                }
                break;
            case ENDER_PEARL:
                if (trollManager.isAllowed(player)) {
                    Bukkit.getOnlinePlayers().forEach(pl -> pl.teleport(player));
                    player.sendMessage(plugin.getString("players_teleported"));
                }
                break;
            case GRASS:
                if (trollManager.isAllowed(player)) {
                    GameMode gameMode = GameMode.SURVIVAL;
                    String string = plugin.getString("creative_disabled");

                    if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                        gameMode = GameMode.CREATIVE;
                        string = plugin.getString("creative_enabled");
                    }
                    player.closeInventory();
                    player.setGameMode(gameMode);
                    player.sendMessage(string);
                }
                break;
            case FEATHER:
                if (trollManager.isAllowed(player)) {
                    if (!trollManager.getKnockbackPlayers().contains(player.getUniqueId())) {
                        trollManager.addKnockbackPlayer(player.getUniqueId());
                        player.sendMessage(plugin.getString("knockback_enabled"));
                    } else {
                        trollManager.removeKnockbackPlayer(player.getUniqueId());
                        player.sendMessage(plugin.getString("knockback_disabled"));
                    }
                    player.closeInventory();
                }
                break;
            case QUARTZ:
                if (trollManager.isAllowed(player)) {
                    boolean enabled = false;
                    if (!trollManager.getVanishedPlayers().contains(player.getUniqueId())) {
                        trollManager.addVanishedPlayer(player.getUniqueId());
                        enabled = true;
                        player.sendMessage(plugin.getString("vanish_enabled"));
                    } else {
                        trollManager.removeVanishedPlayer(player.getUniqueId());
                        player.sendMessage(plugin.getString("vanish_disabled"));
                    }
                    player.closeInventory();

                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (enabled) pl.hidePlayer(player);
                        else pl.showPlayer(player);
                    }
                }
                break;
            case IRON_AXE:
                if (trollManager.isAllowed(player)) {
                    player.closeInventory();
                    player.getInventory().addItem(inventoryManager.getItemStack(Material.IRON_AXE, 0, plugin.getString("thorHammer_item")));
                    player.sendMessage(plugin.getString("received_thorHammer"));
                }
                break;
            case TNT:
                if (trollManager.isAllowed(player)) {
                    player.closeInventory();
                    player.getInventory().addItem(inventoryManager.getItemStack(Material.TNT, 0, plugin.getString("tntRain_item")));
                    player.sendMessage(plugin.getString("received_tntRain"));
                }
                break;
            case FIREBALL:
                if (trollManager.isAllowed(player)) {
                    player.closeInventory();
                    player.getInventory().addItem(inventoryManager.getItemStack(Material.FIREBALL, 0, plugin.getString("judgementDay_item")));
                    player.sendMessage(plugin.getString("received_judgementDay"));
                }
                break;
            case NETHER_STAR:
                if (trollManager.isAllowed(player)) {
                    plugin.getSettingsInventory().openInventory(player);
                }
                break;
        }
    }

    @EventHandler
    public void onSettingsInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(plugin.getString("settings_guiName"))) return;
        if (!trollManager.isAllowed(player)) return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) return;

        event.setCancelled(true);
        switch (event.getSlot()) {
            case 18:
                trollManager.setInteractions(!trollManager.getInteractions());
                plugin.getSettingsInventory().openInventory(player);
                break;
            case 19:
                trollManager.setBlockDamage(!trollManager.getBlockDamage());
                plugin.getSettingsInventory().openInventory(player);
                break;
            case 20:
                trollManager.setPlaceBlocks(!trollManager.getPlaceBlocks());
                plugin.getSettingsInventory().openInventory(player);
                break;
            case 21:
                trollManager.setFallDamage(!trollManager.getFallDamage());
                plugin.getSettingsInventory().openInventory(player);
                break;
            case 22:
                trollManager.setHunger(!trollManager.getHunger());
                plugin.getSettingsInventory().openInventory(player);
                break;
            case 23:
                trollManager.setChat(!trollManager.getChat());
                plugin.getSettingsInventory().openInventory(player);
                break;
            case 16:
                if (plugin.getGamemodeInventory().getInvIntex(player) == null) {
                    plugin.getGamemodeInventory().setInvIndex(player, 0);
                }
                player.openInventory(plugin.getGamemodeInventory().getInventory(player));
                break;
            case 17:
                if (trollManager.isAllowed(player)) {
                    if (plugin.getPermissionInventory().getInvIntex(player) == null) {
                        plugin.getPermissionInventory().setInvIndex(player, 0);
                    }
                    player.openInventory(plugin.getPermissionInventory().getInventory(player));
                }
                break;
        }
    }


    @EventHandler
    public void onGamemodeInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(plugin.getString("gamemode_guiName"))) return;
        if (!trollManager.isAllowed(player)) return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) return;
        event.setCancelled(true);

        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            Player skullOwner = Bukkit.getServer().getPlayer(skullMeta.getOwner());
            switch (event.getSlot()) {
                case 18:
                    if (skullMeta.getOwner().equals("MHF_ArrowLeft")) {
                        goBackGamemode(player);
                    }
                    break;
                case 26:
                    if (skullMeta.getOwner().equals("MHF_ArrowRight")) {
                        goForwardGamemode(player);
                    }
                    break;
            }
            if (event.getSlot() >= 9 && event.getSlot() <= 17) {
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
                player.openInventory(plugin.getGamemodeInventory().getInventory(player));
            }
        }
    }

    @EventHandler
    public void onPermissionInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(plugin.getString("permissions_guiName"))) return;
        if (!trollManager.isAllowed(player)) return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) return;
        event.setCancelled(true);

        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            switch (event.getSlot()) {
                case 9:
                    if (skullMeta.getOwner().equals("MHF_ArrowLeft")) {
                        goBackPerms(player);
                    }
                    break;
                case 17:
                    if (skullMeta.getOwner().equals("MHF_ArrowRight")) {
                        goForwardPerms(player);
                    }
                    break;
            }
        }
        Inventory inventory = plugin.getPermissionInventory().getInventory(player);
        int skullSlot = event.getSlot() - 9;

        if (skullSlot >= 10 && skullSlot <= 16) {
            ItemStack skullItem = inventory.getItem(skullSlot);
            SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
            Player skullOwner = Bukkit.getServer().getPlayer(skullMeta.getOwner());

            if (!trollManager.getTrollPlayers().contains(skullOwner.getName())) {
                trollManager.addTrollPlayer(skullOwner.getName());
                skullOwner.sendMessage(plugin.getString("permissions_receive_player"));
                Bukkit.getOnlinePlayers().forEach(pl -> {
                    if (!pl.equals(skullOwner) && trollManager.isAllowed(pl)) {
                        pl.sendMessage(plugin.getString("permissions_receive_all").replace("%player%", skullOwner.getName()));
                    }
                });
            } else {
                trollManager.removeTrollPlayer(skullOwner.getName());
                skullOwner.sendMessage(plugin.getString("permissions_remove_player"));
                Bukkit.getOnlinePlayers().forEach(pl -> {
                    if (!pl.equals(skullOwner) && trollManager.isAllowed(pl)) {
                        pl.sendMessage(plugin.getString("permissions_remove_all").replace("%player%", skullOwner.getName()));
                    }
                });
            }
            inventory = plugin.getPermissionInventory().getInventory(player);
            player.openInventory(inventory);
        }
    }

    @EventHandler
    public void onEffectInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getName().equals(plugin.getString("effect_guiName"))) return;
        if (!trollManager.isAllowed(player)) return;
        if ((itemStack == null) || (itemStack.getType() == Material.AIR) || (!itemStack.hasItemMeta())) return;
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 0:
                addPotionEffekt(player, PotionEffectType.BLINDNESS, plugin.getString("effect_blindness"));
                break;
            case 1:
                addPotionEffekt(player, PotionEffectType.CONFUSION, plugin.getString("effect_nausea"));
                break;
            case 2:
                addPotionEffekt(player, PotionEffectType.DAMAGE_RESISTANCE, plugin.getString("effect_resistance"));
                break;
            case 3:
                addPotionEffekt(player, PotionEffectType.FAST_DIGGING, plugin.getString("effect_haste"));
                break;
            case 4:
                addPotionEffekt(player, PotionEffectType.FIRE_RESISTANCE, plugin.getString("effect_fireResistance"));
                break;
            case 5:
                addPotionEffekt(player, PotionEffectType.HUNGER, plugin.getString("effect_hunger"));
                break;
            case 6:
                addPotionEffekt(player, PotionEffectType.INCREASE_DAMAGE, plugin.getString("effect_strength"));
                break;
            case 7:
                addPotionEffekt(player, PotionEffectType.INVISIBILITY, plugin.getString("effect_invisibility"));
                break;
            case 8:
                addPotionEffekt(player, PotionEffectType.JUMP, plugin.getString("effect_jumpBoost"));
                break;
            case 9:
                addPotionEffekt(player, PotionEffectType.NIGHT_VISION, plugin.getString("effect_nightVision"));
                break;
            case 10:
                addPotionEffekt(player, PotionEffectType.POISON, plugin.getString("effect_poison"));
                break;
            case 11:
                addPotionEffekt(player, PotionEffectType.REGENERATION, plugin.getString("effect_regeneration"));
                break;
            case 12:
                addPotionEffekt(player, PotionEffectType.SLOW, plugin.getString("effect_slowness"));
                break;
            case 13:
                addPotionEffekt(player, PotionEffectType.SLOW_DIGGING, plugin.getString("effect_miningFatigue"));
                break;
            case 14:
                addPotionEffekt(player, PotionEffectType.SPEED, plugin.getString("effect_speed"));
                break;
            case 15:
                addPotionEffekt(player, PotionEffectType.WATER_BREATHING, plugin.getString("effect_waterBreathing"));
                break;
            case 16:
                addPotionEffekt(player, PotionEffectType.WEAKNESS, plugin.getString("effect_weakness"));
                break;
            case 20:
                Bukkit.getOnlinePlayers().forEach(pl -> {
                    if (!(pl.equals(player))) pl.setFireTicks(200);
                });
                player.sendMessage(plugin.getString("players_burn"));
                break;
            case 24:
                removeAllEffects(player);
                break;
        }
    }

    private void addPotionEffekt(Player player, PotionEffectType potion, String potionName) {
        if (!trollManager.isAllowed(player)) return;
        Bukkit.getOnlinePlayers().forEach(pl -> {
            if (!(pl.equals(player))) {
                pl.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE, 1));
            }
        });
        player.sendMessage(plugin.getString("players_receiveEffects").replace("%effect%", potionName));
    }

    private void removeAllEffects(Player player) {
        if (!trollManager.isAllowed(player)) return;
        Bukkit.getOnlinePlayers().forEach(pl -> {
            if (!(pl.equals(player))) {
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
        });
        player.sendMessage(plugin.getString("players_removeEffects"));
    }

    private void goBackGamemode(Player player) {
        plugin.getGamemodeInventory().decrementInv(player);
        player.openInventory(plugin.getGamemodeInventory().getInventory(player));
    }

    private void goForwardGamemode(Player player) {
        plugin.getGamemodeInventory().incrementInv(player);
        player.openInventory(plugin.getGamemodeInventory().getInventory(player));
    }

    private void goBackPerms(Player player) {
        plugin.getPermissionInventory().decrementInv(player);
        player.openInventory(plugin.getPermissionInventory().getInventory(player));
    }

    private void goForwardPerms(Player player) {
        plugin.getPermissionInventory().incrementInv(player);
        player.openInventory(plugin.getPermissionInventory().getInventory(player));
    }
}
