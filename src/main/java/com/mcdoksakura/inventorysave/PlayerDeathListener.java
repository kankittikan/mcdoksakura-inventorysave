package com.mcdoksakura.inventorysave;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final InventorySavePlugin plugin;

    public PlayerDeathListener(InventorySavePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        plugin.getInventoryManager().saveInventory(player);
        player.sendMessage("§aYour inventory has been backed up! Use /invrestore [1-3] to restore it.");
    }
} 