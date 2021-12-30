package com.eintosti.troll.listener;

import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author einTosti
 */
public class BlockBreakListener implements Listener {

    private final TrollManager trollManager;

    public BlockBreakListener(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!trollManager.isInteractions()) {
            event.setCancelled(true);
        }
    }
}
