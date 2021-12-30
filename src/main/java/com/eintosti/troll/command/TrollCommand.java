package com.eintosti.troll.command;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.eintosti.troll.Troll;
import com.eintosti.troll.manager.TrollManager;
import com.eintosti.troll.util.ItemBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author einTosti
 */
public class TrollCommand implements CommandExecutor {

    private final Troll plugin;
    private final TrollManager trollManager;

    public TrollCommand(Troll plugin) {
        this.plugin = plugin;
        this.trollManager = plugin.getTrollManager();
        plugin.getCommand("troll").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (!trollManager.isAllowed(player)) {
            player.sendMessage(plugin.getString("no_permissions"));
            return true;
        }

        player.getInventory().addItem(new ItemBuilder(XMaterial.PUFFERFISH).name(plugin.getString("troll_item")).build());
        XSound.ENTITY_PLAYER_LEVELUP.play(player);
        player.sendMessage(plugin.getString("received_trollItem"));
        return true;
    }
}
