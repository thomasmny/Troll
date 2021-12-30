package com.eintosti.troll.listener;

import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * @author einTosti
 */
public class EntityExplodeListener implements Listener {

    private final TrollManager trollManager;

    public EntityExplodeListener(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!trollManager.isBlockDamage()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!trollManager.isBlockDamage()) {
            event.setCancelled(true);
        }
    }
}
