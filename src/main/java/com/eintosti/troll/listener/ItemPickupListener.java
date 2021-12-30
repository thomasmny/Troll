package com.eintosti.troll.listener;

import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author einTosti
 */
public class ItemPickupListener implements Listener {

    private final TrollManager trollManager;

    public ItemPickupListener(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (!trollManager.isInteractions()) {
            event.setCancelled(true);
        }
    }
}
