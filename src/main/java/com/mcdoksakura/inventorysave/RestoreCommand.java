package com.mcdoksakura.inventorysave;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RestoreCommand implements CommandExecutor {
    private final InventorySavePlugin plugin;

    public RestoreCommand(InventorySavePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("§cUsage: /invrestore [1-3]");
            return true;
        }

        int backupNumber;
        try {
            backupNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cPlease provide a valid number between 1 and 3!");
            return true;
        }

        if (backupNumber < 1 || backupNumber > 3) {
            player.sendMessage("§cPlease provide a number between 1 and 3!");
            return true;
        }

        int availableBackups = plugin.getInventoryManager().getBackupCount(player.getUniqueId());
        if (availableBackups == 0) {
            player.sendMessage("§cYou don't have any inventory backups!");
            return true;
        }

        if (backupNumber > availableBackups) {
            player.sendMessage("§cYou only have " + availableBackups + " backup(s) available!");
            return true;
        }

        if (plugin.getInventoryManager().restoreInventory(player, backupNumber)) {
            player.sendMessage("§aSuccessfully restored inventory from backup #" + backupNumber + "!");
        } else {
            player.sendMessage("§cFailed to restore inventory backup!");
        }

        return true;
    }
} 