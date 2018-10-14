package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author einTosti
 */
public class BlockBreak implements Listener {
    private TrollManager trollManager;

    public BlockBreak(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!trollManager.getInteractions()) {
            event.setCancelled(true);
        }
    }
}
