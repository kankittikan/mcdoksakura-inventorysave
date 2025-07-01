package com.mcdoksakura.inventorysave;

import org.bukkit.plugin.java.JavaPlugin;

public class InventorySavePlugin extends JavaPlugin {
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        // Create data folder if it doesn't exist
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Initialize inventory manager
        inventoryManager = new InventoryManager(this);

        // Register event listener
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);

        // Register command executor
        getCommand("invrestore").setExecutor(new RestoreCommand(this));

        getLogger().info("InventorySave plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("InventorySave plugin has been disabled!");
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
} 