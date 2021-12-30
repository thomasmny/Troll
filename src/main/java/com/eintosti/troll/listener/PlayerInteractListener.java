package com.eintosti.troll.listener;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

/**
 * @author einTosti
 */
public class PlayerInteractListener implements Listener {

    private final Troll plugin;
    private final TrollManager trollManager;

    public PlayerInteractListener(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean isValidClick(Action action, ItemStack itemStack, Player player, XMaterial material, String itemName) {
        if ((action == Action.PHYSICAL) || (itemStack == null) || (itemStack.getType() == Material.AIR)) {
            return false;
        }

        if (itemStack.getType() != material.parseMaterial()) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !itemMeta.getDisplayName().equals(itemName)) {
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
    public void onTrollInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isValidClick(event.getAction(), event.getItem(), player, XMaterial.PUFFERFISH, plugin.getString("troll_item"))) {
            return;
        }

        event.setCancelled(true);
        plugin.getTrollInventory().openInventory(player);
        XSound.BLOCK_CHEST_OPEN.play(player);
    }

    @EventHandler
    public void onEffectInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isValidClick(event.getAction(), event.getItem(), player, XMaterial.BLAZE_POWDER, plugin.getString("effect_item"))) {
            return;
        }

        event.setCancelled(true);
        plugin.getEffectInventory().openInventory(player);
        XSound.BLOCK_CHEST_OPEN.play(player);
    }

    @EventHandler
    public void onThorHammerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isValidClick(event.getAction(), event.getItem(), player, XMaterial.IRON_AXE, plugin.getString("thorHammer_item"))) {
            return;
        }

        event.setCancelled(true);
        player.getWorld().strikeLightning(player.getTargetBlock(null, 25).getLocation());
    }

    @EventHandler
    public void onJudgementDayInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isValidClick(event.getAction(), event.getItem(), player, XMaterial.FIRE_CHARGE, plugin.getString("judgementDay_item"))) {
            return;
        }

        ActionBar.sendActionBar(player, plugin.getString("called_judgementDay"));
        XSound.ENTITY_GHAST_SCREAM.play(player);

        for (int s = 0; s <= 30; s++) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> summonFireball(player), s * 20L);
        }
    }

    private void summonFireball(Player player) {
        Random randomNum = new Random();
        int max = 60;

        Location playerLocation = player.getLocation();
        int x = randomNum.nextInt(max) * (randomNum.nextBoolean() ? -1 : 1);
        int y = randomNum.nextInt(max);
        int z = randomNum.nextInt(max) * (randomNum.nextBoolean() ? -1 : 1);

        Location target = playerLocation.clone().add(0, -2, 0);
        Location from = facing(playerLocation.add(x, y, z), target);

        World world = from.getWorld();
        if (world == null) {
            return;
        }

        world.spawn(from, Fireball.class);
    }

    private Location facing(Location location, Location facing) {
        location = location.clone();
        double dX = facing.getX() - location.getX();
        double dY = facing.getY() - location.getY();
        double dZ = facing.getZ() - location.getZ();

        if (dX != 0) {
            if (dX < 0) {
                location.setYaw((float) (1.5 * Math.PI));
            } else {
                location.setYaw((float) (0.5 * Math.PI));
            }
            location.setYaw(location.getYaw() - (float) Math.atan(dZ / dX));
        } else if (dZ < 0) {
            location.setYaw((float) Math.PI);
        }
        double dXZ = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2));

        location.setPitch((float) -Math.atan(dY / dXZ));
        location.setYaw(-location.getYaw() * 180f / (float) Math.PI);
        location.setPitch(location.getPitch() * 180f / (float) Math.PI);

        return location;
    }

    @EventHandler
    public void onTntRainInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isValidClick(event.getAction(), event.getItem(), player, XMaterial.TNT, plugin.getString("tntRain_item"))) {
            return;
        }

        ActionBar.sendActionBar(player, plugin.getString("called_tntRain"));
        XSound.ENTITY_FISHING_BOBBER_RETRIEVE.play(player);

        for (Location location : getLocations(player)) {
            location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        }
    }

    private Location[] getLocations(Player player) {
        Location[] locations = new Location[9];

        int i = 0;

        for (int x = -4; x < 4; x += 4) {
            for (int z = -4; z < 4; z += 4) {
                locations[i] = new Location(player.getWorld(), x, player.getLocation().getY() + 5, z);
                i++;
            }
        }

        return locations;
    }
}
