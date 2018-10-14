package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.inventory.EffectInventory;
import de.eintosti.troll.inventory.TrollInventory;
import de.eintosti.troll.manager.TrollManager;
import de.eintosti.troll.misc.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import java.util.Set;

/**
 * @author einTosti
 */
public class PlayerInteract implements Listener {
    private Troll plugin;
    private TrollManager trollManager;

    public PlayerInteract(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteractFish(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if ((action == Action.PHYSICAL) || (itemStack == null) || (itemStack.getType() == Material.AIR)) return;
        if (itemStack.getType() == Material.RAW_FISH) {
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta.getDisplayName() != null && itemMeta.getDisplayName().equals(plugin.getString("troll_item"))) {
                    if (!trollManager.isAllowed(player)) {
                        player.sendMessage(plugin.getString("no_permissions"));
                        return;
                    }
                    event.setCancelled(true);
                    plugin.getTrollInventory().openInventory(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractBlazePowder(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if ((action == Action.PHYSICAL) || (itemStack == null) || (itemStack.getType() == Material.AIR)) return;
        if (itemStack.getType() == Material.BLAZE_POWDER) {
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta.getDisplayName() != null && itemMeta.getDisplayName().equals(plugin.getString("effect_item"))) {
                    if (!trollManager.isAllowed(player)) {
                        player.sendMessage(plugin.getString("no_permissions"));
                        return;
                    }
                    event.setCancelled(true);
                    plugin.getEffectInventory().openInventory(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAxe(PlayerInteractEvent event) {
        if (!(isDataValid(event))) return;

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals(plugin.getString("thorHammer_item"))) {
            if (!trollManager.isAllowed(player)) {
                player.sendMessage(plugin.getString("no_permissions"));
                return;
            }
            player.getWorld().strikeLightning(player.getTargetBlock((Set<Material>) null, 25).getLocation());
        }
    }

    @EventHandler
    public void onPlayerInteractFireball(PlayerInteractEvent event) {
        if (!(isDataValid(event))) return;

        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals(plugin.getString("judgementDay_item"))) {
            Player player = event.getPlayer();
            event.setCancelled(true);

            if (!trollManager.isAllowed(player)) {
                player.sendMessage(plugin.getString("no_permissions"));
                return;
            }
            ActionBar.sendHotBarMessage(player, plugin.getString("called_judgementDay"));
            for (int i = 0; i <= 30; i++) {
                delayFireball(player, i * 20);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractTnt(PlayerInteractEvent event) {
        if (!isDataValid(event)) return;

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals(plugin.getString("tntRain_item"))) {
            event.setCancelled(true);
            if (!trollManager.isAllowed(player)) {
                player.sendMessage(plugin.getString("no_permissions"));
                return;
            }
            ActionBar.sendHotBarMessage(player, plugin.getString("called_tntRain"));
            for (Location location : getLocations(player)) {
                location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            }
        }
    }

    private boolean isDataValid(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if ((itemStack == null) || (itemStack.getType() == Material.AIR)) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || itemMeta.getDisplayName() == null) {
            return false;
        }
        return true;
    }

    private void summonFireball(Player player) {
        Random randomNum = new Random();
        int max = 60;

        Location target = player.getLocation().add(0, -2, 0);
        Location from = lookAt(player.getLocation().add(randomNum.nextInt(max) * (randomNum.nextBoolean() ? -1 : 1), randomNum.nextInt(max), randomNum.nextInt(max) * (randomNum.nextBoolean() ? -1 : 1)), target);

        from.getWorld().spawn(from, Fireball.class);
    }

    private void delayFireball(Player player, int delay) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> summonFireball(player), delay);
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

    private Location lookAt(Location location, Location lookat) {
        location = location.clone();
        double dX = lookat.getX() - location.getX();
        double dY = lookat.getY() - location.getY();
        double dZ = lookat.getZ() - location.getZ();

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
}
