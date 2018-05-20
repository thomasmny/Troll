package de.eintosti.troll.commands;

import de.eintosti.troll.misc.Messages;
import de.eintosti.troll.misc.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author einTosti
 */
public class TrollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;

            if (!Utils.getInstance().isAllowed(player)) {
                player.sendMessage(Utils.getInstance().getString("no_permissions"));
                return true;
            }
            getTrollItem(player);
            player.sendMessage(Utils.getInstance().getString("received_trollItem"));
        }
        return true;
    }

    private ItemStack getTrollItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.RAW_FISH, 1, (byte) 3);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(Utils.getInstance().getString("troll_item"));
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
        return itemStack;
    }
}
