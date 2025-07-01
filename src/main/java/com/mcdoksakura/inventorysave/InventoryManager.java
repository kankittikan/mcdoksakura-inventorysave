package com.mcdoksakura.inventorysave;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryManager {
    private final InventorySavePlugin plugin;
    private static final int MAX_BACKUPS = 3;

    public InventoryManager(InventorySavePlugin plugin) {
        this.plugin = plugin;
    }

    public void saveInventory(Player player) {
        UUID playerUUID = player.getUniqueId();
        File playerFile = new File(plugin.getDataFolder(), playerUUID + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        // Get existing backups
        List<String> backups = config.getStringList("backups");
        if (backups == null) {
            backups = new ArrayList<>();
        }

        // Create new backup section
        String backupKey = "inventory." + System.currentTimeMillis();
        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        config.set(backupKey + ".inventory", contents);
        config.set(backupKey + ".armor", armorContents);
        config.set(backupKey + ".exp", player.getExp());
        config.set(backupKey + ".level", player.getLevel());

        // Add to backups list
        backups.add(backupKey);

        // Keep only the last MAX_BACKUPS
        while (backups.size() > MAX_BACKUPS) {
            String oldestBackup = backups.remove(0);
            config.set(oldestBackup, null);
        }

        config.set("backups", backups);

        // Save the file
        try {
            config.save(playerFile);
            plugin.getLogger().info("Saved inventory backup for player: " + player.getName());
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save inventory backup for player: " + player.getName());
            e.printStackTrace();
        }
    }

    public boolean restoreInventory(Player player, int backupNumber) {
        UUID playerUUID = player.getUniqueId();
        File playerFile = new File(plugin.getDataFolder(), playerUUID + ".yml");
        
        if (!playerFile.exists()) {
            return false;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        List<String> backups = config.getStringList("backups");

        if (backups == null || backups.isEmpty() || backupNumber < 1 || backupNumber > backups.size()) {
            return false;
        }

        // Get the requested backup (convert to 0-based index)
        String backupKey = backups.get(backups.size() - backupNumber);

        // Restore inventory
        ItemStack[] contents = (ItemStack[]) config.get(backupKey + ".inventory");
        ItemStack[] armorContents = (ItemStack[]) config.get(backupKey + ".armor");
        float exp = (float) config.getDouble(backupKey + ".exp");
        int level = config.getInt(backupKey + ".level");

        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armorContents);
        player.setExp(exp);
        player.setLevel(level);

        return true;
    }

    public int getBackupCount(UUID playerUUID) {
        File playerFile = new File(plugin.getDataFolder(), playerUUID + ".yml");
        if (!playerFile.exists()) {
            return 0;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        List<String> backups = config.getStringList("backups");
        return backups != null ? backups.size() : 0;
    }
} 