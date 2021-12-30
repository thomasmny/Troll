package com.eintosti.troll.listener;

import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author einTosti
 */
public class BlockPlaceListener implements Listener {

    private final TrollManager trollManager;

    public BlockPlaceListener(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!trollManager.isPlaceBlocks()) {
            event.setCancelled(true);
        }
    }
}
