package de.eintosti.troll.listeners;

import de.eintosti.troll.misc.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author einTosti
 */
public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!Utils.getInstance().mPlaceBlocks) {
            event.setCancelled(true);
        }
    }
}
