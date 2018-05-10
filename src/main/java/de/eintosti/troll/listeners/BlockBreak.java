package de.eintosti.troll.listeners;

import de.eintosti.troll.misc.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author einTosti
 */
public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!Utils.getInstance().mInteractions) {
            event.setCancelled(true);
        }
    }
}
