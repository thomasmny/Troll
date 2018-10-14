package de.eintosti.troll.listener;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.TrollManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author einTosti
 */
public class PlayerChat implements Listener {
    private Troll plugin;
    private TrollManager trollManager;

    public PlayerChat(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!trollManager.getChat()) {
            event.setCancelled(true);
            player.sendMessage(plugin.getString("chat_disabled"));
        }
    }
}
