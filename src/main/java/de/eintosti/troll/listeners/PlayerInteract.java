package de.eintosti.troll.listeners;

import de.eintosti.troll.Troll;
import de.eintosti.troll.inventories.EffectInventory;
import de.eintosti.troll.inventories.TrollInventory;
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

import java.util.Random;
import java.util.Set;

/**
 * @author einTosti
 */
public class PlayerInteract implements Listener {
    private final String mPrefix = Utils.getInstance().mPrefix;

    @EventHandler
    public void onPlayerInteractCookie(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Action a = event.getAction();
        ItemStack itemStack = event.getItem();
        if ((a == Action.PHYSICAL) || (itemStack == null) || (itemStack.getType() == Material.AIR)) {
            return;
        }

        if (itemStack.getType() == Material.RAW_FISH) {
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta.getDisplayName() != null && itemMeta.getDisplayName().equals("§dTroll Menü öffnen §7(Rechtsklick)")) {
                    if (Utils.getInstance().isAllowed(player)) {
                        event.setCancelled(true);
                        TrollInventory.getInstance().openInventory(player);
                    } else {
                        player.sendMessage(mPrefix + "§7Was dieses Item wohl macht...");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractBlazePowder(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Action a = event.getAction();
        ItemStack is = event.getItem();
        if ((a == Action.PHYSICAL) || (is == null) || (is.getType() == Material.AIR)) {
            return;
        }

        if (is.getType().equals(Material.BLAZE_POWDER)) {
            if (is.hasItemMeta()) {
                if (Utils.getInstance().isAllowed(player)) {
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
        ItemStack is = event.getItem();
        ItemMeta meta = is.getItemMeta();

        if (meta.getDisplayName().equals("§dThor's Hammer §7(Rechtsklick)")) {
            if (Utils.getInstance().isAllowed(player)) {
                player.getWorld().strikeLightning(player.getTargetBlock((Set<Material>) null, 25).getLocation());
            }
        }
    }

    @EventHandler
    public void onPlayerInteractFireball(PlayerInteractEvent event) {
        if (!(isDataValid(event))) {
            return;
        }
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getDisplayName().equals("§dJudgement-Day herbeirufen §7(Rechtsklick)")) {
            Player player = event.getPlayer();

            if (Utils.getInstance().isAllowed(player)) {
                event.setCancelled(true);

                delayFireball(player, 20);
                delayFireball(player, 40);
                delayFireball(player, 60);
                delayFireball(player, 80);
                delayFireball(player, 100);
                delayFireball(player, 120);
                delayFireball(player, 140);
                delayFireball(player, 160);
                delayFireball(player, 180);
                delayFireball(player, 200);
                delayFireball(player, 220);
                delayFireball(player, 240);
                delayFireball(player, 260);
                delayFireball(player, 280);
                delayFireball(player, 300);
            }
        }
    }

    private void summonFireball(Player player) {
        Location target = player.getLocation().add(0, -1, 0);

        int MAX = 60;
        Random randomNum = new Random();

        Location from = lookAt(player.getLocation().add(randomNum.nextInt(MAX) * (randomNum.nextBoolean() ? -1 : 1), randomNum.nextInt(MAX), randomNum.nextInt(MAX) * (randomNum.nextBoolean() ? -1 : 1)), target);

        from.getWorld().spawn(from, Fireball.class);
    }

    private void delayFireball(Player player, int delay) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Troll.plugin, () -> {
            summonFireball(player);
        }, delay);
    }

    @EventHandler
    public void onPlayerInteractTnt(PlayerInteractEvent event) {
        if (!isDataValid(event)) {
            return;
        }
        Player player = event.getPlayer();
        int y = player.getLocation().getBlockY() + 5;

        double x1 = player.getLocation().getBlockX();
        double z1 = player.getLocation().getBlockZ();

        double x2 = player.getLocation().getBlockX() + 4;
        double z2 = player.getLocation().getBlockZ();

        double x3 = player.getLocation().getBlockX();
        double z3 = player.getLocation().getBlockZ() + 4;

        double x4 = player.getLocation().getBlockX() - 4;
        double z4 = player.getLocation().getBlockZ();

        double x5 = player.getLocation().getBlockX();
        double z5 = player.getLocation().getBlockZ() - 4;

        double x6 = player.getLocation().getBlockX() + 4;
        double z6 = player.getLocation().getBlockZ() + 4;

        double x7 = player.getLocation().getBlockX() + 4;
        double z7 = player.getLocation().getBlockZ() - 4;

        double x8 = player.getLocation().getBlockX() - 4;
        double z8 = player.getLocation().getBlockZ() - 4;

        double x9 = player.getLocation().getBlockX() - 4;
        double z9 = player.getLocation().getBlockZ() + 4;

        Location loc1 = new Location(player.getWorld(), x1, y, z1);
        Location loc2 = new Location(player.getWorld(), x2, y, z2);
        Location loc3 = new Location(player.getWorld(), x3, y, z3);
        Location loc4 = new Location(player.getWorld(), x4, y, z4);
        Location loc5 = new Location(player.getWorld(), x5, y, z5);
        Location loc6 = new Location(player.getWorld(), x6, y, z6);
        Location loc7 = new Location(player.getWorld(), x7, y, z7);
        Location loc8 = new Location(player.getWorld(), x8, y, z8);
        Location loc9 = new Location(player.getWorld(), x9, y, z9);

        ItemStack is = event.getItem();
        ItemMeta meta = is.getItemMeta();

        if (meta.getDisplayName().equals("§dTNT-Regen herbeirufen §7(Rechtsklick)")) {
            event.setCancelled(true);
            if (Utils.getInstance().isAllowed(player)) {
                loc1.getWorld().spawnEntity(loc1, EntityType.PRIMED_TNT);
                loc2.getWorld().spawnEntity(loc2, EntityType.PRIMED_TNT);
                loc3.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
                loc4.getWorld().spawnEntity(loc4, EntityType.PRIMED_TNT);
                loc5.getWorld().spawnEntity(loc5, EntityType.PRIMED_TNT);
                loc6.getWorld().spawnEntity(loc6, EntityType.PRIMED_TNT);
                loc7.getWorld().spawnEntity(loc7, EntityType.PRIMED_TNT);
                loc8.getWorld().spawnEntity(loc8, EntityType.PRIMED_TNT);
                loc9.getWorld().spawnEntity(loc9, EntityType.PRIMED_TNT);
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
