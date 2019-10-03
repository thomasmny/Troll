package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author einTosti
 */
public class ItemPickup implements Listener {
    private TrollManager trollManager;

    public ItemPickup(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (!trollManager.getInteractions()) {
            event.setCancelled(true);
        }
    }
}
