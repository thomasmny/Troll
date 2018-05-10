package de.eintosti.troll.listeners;

import de.eintosti.troll.misc.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * @author einTosti
 */
public class FoodLevelChange implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!Utils.getInstance().mHunger) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }
}
