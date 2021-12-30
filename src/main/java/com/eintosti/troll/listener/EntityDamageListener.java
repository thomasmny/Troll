package com.eintosti.troll.listener;

import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

/**
 * @author einTosti
 */
public class EntityDamageListener implements Listener {

    private final TrollManager trollManager;

    public EntityDamageListener(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        cancelFallDamage(event);
        increaseKnockBack(event);
    }

    private void cancelFallDamage(EntityDamageEvent event) {
        if (trollManager.isFallDamage() || event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        event.setCancelled(true);
    }

    private void increaseKnockBack(EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
        Entity attacker = entityEvent.getDamager();
        if (!(attacker instanceof Player)) {
            return;
        }

        Entity attacked = event.getEntity();
        if (trollManager.isKnockBackPlayer(attacker.getUniqueId())) {
            Vector locationVector = attacked.getLocation().toVector();
            attacked.setVelocity(attacked.getVelocity().add(locationVector.subtract(locationVector).normalize().multiply(trollManager.getKnockBack())));
        }
    }
}
