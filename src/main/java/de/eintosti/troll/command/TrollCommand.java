package de.eintosti.troll.command;

import de.eintosti.troll.Troll;
import de.eintosti.troll.manager.InventoryManager;
import de.eintosti.troll.manager.TrollManager;
import de.eintosti.troll.util.external.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author einTosti
 */
public class TrollCommand implements CommandExecutor {
    private Troll plugin;
    private InventoryManager inventoryManager;
    private TrollManager trollManager;

    public TrollCommand(Troll plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.trollManager = plugin.getTrollManager();
        Bukkit.getPluginCommand("Troll").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!trollManager.isAllowed(player)) {
            player.sendMessage(plugin.getString("no_permissions"));
            return true;
        }
        player.getInventory().addItem(inventoryManager.getItemStack(XMaterial.PUFFERFISH, plugin.getString("troll_item")));
        player.sendMessage(plugin.getString("received_trollItem"));
        return true;
    }
}
