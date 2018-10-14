package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * @author einTosti
 */
public class EntityExplode implements Listener {
    private TrollManager trollManager;

    public EntityExplode(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!trollManager.getBlockDamage()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!trollManager.getBlockDamage()) {
            event.setCancelled(true);
        }
    }
}
