package de.eintosti.troll.listeners;

import de.eintosti.troll.misc.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author einTosti
 */
public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity attacked = event.getEntity();

        if (!Utils.getInstance().mFallDamage) {
            if (attacked instanceof Player) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    event.setCancelled(true);
                }
            }
        }

        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
            Entity attacker = entityDamageByEntityEvent.getDamager();
            if (attacker instanceof Player) {
                if (Utils.getInstance().mKnockbackPlayers.contains(attacker.getUniqueId())) {
                    attacked.setVelocity(attacked.getVelocity().add(attacked.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(75)));
                }
            }
        }
    }
}
