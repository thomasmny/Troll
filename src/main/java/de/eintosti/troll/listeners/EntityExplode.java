package de.eintosti.troll.listeners;

import de.eintosti.troll.misc.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * @author einTosti
 */
public class EntityExplode implements Listener {

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (Utils.getInstance().mBlockDamage) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (Utils.getInstance().mBlockDamage) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }
}
