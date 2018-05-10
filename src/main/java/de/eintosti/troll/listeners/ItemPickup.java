package de.eintosti.troll.listeners;

import de.eintosti.troll.misc.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author einTosti
 */
public class ItemPickup implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (!Utils.getInstance().mInteractions) {
            event.setCancelled(true);
        }
    }
}
