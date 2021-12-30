package com.eintosti.troll.listener;

import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * @author einTosti
 */
public class FoodLevelChangeListener implements Listener {

    private final TrollManager trollManager;

    public FoodLevelChangeListener(Troll plugin) {
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!trollManager.isHunger()) {
            event.setCancelled(true);
        }
    }
}
