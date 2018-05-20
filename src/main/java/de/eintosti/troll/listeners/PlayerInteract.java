package de.eintosti.troll.listeners;

import de.eintosti.troll.Troll;
import de.eintosti.troll.inventories.EffectInventory;
import de.eintosti.troll.inventories.TrollInventory;
import de.eintosti.troll.misc.ActionBar;
import de.eintosti.troll.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * @author einTosti
 */
public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteractFish(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if ((action == Action.PHYSICAL) || (itemStack == null) || (itemStack.getType() == Material.AIR)) {
            return;
        }

        if (itemStack.getType() == Material.RAW_FISH) {
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta.getDisplayName() != null && itemMeta.getDisplayName().equals(Utils.getInstance().getString("troll_item"))) {
                    if (!Utils.getInstance().isAllowed(player)) {
                        player.sendMessage(Utils.getInstance().getString("no_permissions"));
                        return;
                    }
                    event.setCancelled(true);
                    TrollInventory.getInstance().openInventory(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractBlazePowder(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if ((action == Action.PHYSICAL) || (itemStack == null) || (itemStack.getType() == Material.AIR)) {
            return;
        }

        if (itemStack.getType() == Material.BLAZE_POWDER) {
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta.getDisplayName() != null && itemMeta.getDisplayName().equals(Utils.getInstance().getString("effect_item"))) {
                    if (!Utils.getInstance().isAllowed(player)) {
                        player.sendMessage(Utils.getInstance().getString("no_permissions"));
                        return;
                    }

                    event.setCancelled(true);
                    EffectInventory.getInstance().openInventory(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAxe(PlayerInteractEvent event) {
        if (!(isDataValid(event))) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals(Utils.getInstance().getString("thorHammer_item"))) {
            if (!Utils.getInstance().isAllowed(player)) {
                player.sendMessage(Utils.getInstance().getString("no_permissions"));
                return;
            }
            player.getWorld().strikeLightning(player.getTargetBlock((Set<Material>) null, 25).getLocation());
        }
    }

    @EventHandler
    public void onPlayerInteractFireball(PlayerInteractEvent event) {
        if (!(isDataValid(event))) {
            return;
        }
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals(Utils.getInstance().getString("judgementDay_item"))) {
            Player player = event.getPlayer();
            event.setCancelled(true);

            if (!Utils.getInstance().isAllowed(player)) {
                player.sendMessage(Utils.getInstance().getString("no_permissions"));
                return;
            }

            ActionBar.sendHotBarMessage(player, Utils.getInstance().getString("called_judgementDay"));
            for (int i = 1; i <= 30; i++) {
                delayFireball(player, i * 20);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractTnt(PlayerInteractEvent event) {
        if (!isDataValid(event)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals(Utils.getInstance().getString("tntRain_item"))) {
            event.setCancelled(true);
            if (!Utils.getInstance().isAllowed(player)) {
                player.sendMessage(Utils.getInstance().getString("no_permissions"));
                return;
            }

            ActionBar.sendHotBarMessage(player, Utils.getInstance().getString("called_tntRain"));
            for (Location location : getLocations(player)) {
                location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            }
        }
    }

    private boolean isDataValid(PlayerInteractEvent event) {
        ItemStack is = event.getItem();
        if ((is == null) || (is.getType() == Material.AIR)) {
            return false;
        }

        ItemMeta meta = is.getItemMeta();
        if (meta == null || meta.getDisplayName() == null) {
            return false;
        }
        return true;
    }

    private void summonFireball(Player player) {
        int max = 60;
        Random randomNum = new Random();

        Location target = player.getLocation().add(0, -2, 0);
        Location from = lookAt(player.getLocation().add(randomNum.nextInt(max) * (randomNum.nextBoolean() ? -1 : 1), randomNum.nextInt(max), randomNum.nextInt(max) * (randomNum.nextBoolean() ? -1 : 1)), target);

        from.getWorld().spawn(from, Fireball.class);
    }

    private void delayFireball(Player player, int delay) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Troll.plugin, () -> {
            summonFireball(player);
        }, delay);
    }

    private Location[] getLocations(Player player) {
        Location[] locations = new Location[9];

        double y = player.getLocation().getY() + 5;
        double x = player.getLocation().getX();
        double z = player.getLocation().getZ();

        locations[0] = new Location(player.getWorld(), x, y, z);
        locations[1] = new Location(player.getWorld(), x + 4, y, z);
        locations[2] = new Location(player.getWorld(), x, y, z + 4);
        locations[3] = new Location(player.getWorld(), x - 4, y, z);
        locations[4] = new Location(player.getWorld(), x, y, z - 4);
        locations[5] = new Location(player.getWorld(), x + 4, y, z + 4);
        locations[6] = new Location(player.getWorld(), x + 4, y, z - 4);
        locations[7] = new Location(player.getWorld(), x - 4, y, z + 4);
        locations[8] = new Location(player.getWorld(), x - 4, y, z - 4);

        return locations;
    }

    private Location lookAt(Location loc, Location lookat) {
        loc = loc.clone();
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        if (dx != 0) {
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw(loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        loc.setPitch((float) -Math.atan(dy / dxz));
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }
}
