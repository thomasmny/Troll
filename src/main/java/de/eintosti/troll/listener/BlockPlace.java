package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author einTosti
 */
public class BlockPlace implements Listener {
    private TrollManager trollManager;

    public BlockPlace(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!trollManager.getPlaceBlocks()) {
            event.setCancelled(true);
        }
    }
}
