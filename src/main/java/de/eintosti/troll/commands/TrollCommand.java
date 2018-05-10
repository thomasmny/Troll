package de.eintosti.troll.commands;

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
    private final String mPrefix = Utils.getInstance().mPrefix;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;

            if (Utils.getInstance().isAllowed(player)) {
                getTrollItem(player);
                player.sendMessage(mPrefix + "§7Du hast das §dTroll Menü §7Item erhalten");
            } else {
                player.sendMessage(mPrefix + "§7Du hast hierfür §dkeine Rechte§7!");
            }
        }
        return true;
    }

    private ItemStack getTrollItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.RAW_FISH, 1, (byte) 3);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName("§dTroll Menü öffnen §7(Rechtsklick)");
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
        return itemStack;
    }
}
