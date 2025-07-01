# MCDokSakura Inventory Save Plugin

A Minecraft 1.12.2 plugin that automatically backs up player inventories on death and allows players to restore them.

## Features

- Automatically saves player inventory when they die
- Maintains up to 3 most recent backups per player
- Saves complete inventory including:
  - Inventory contents
  - Armor contents
  - Experience points
  - Experience level
- Simple restore command
- Player-friendly messages

## Requirements

- Minecraft Server 1.12.2 (Spigot/Paper)
- Java 8 or higher

## Installation

1. Download the latest plugin JAR from the releases section
2. Place the JAR file in your server's `plugins` folder
3. Restart your server or load the plugin using a plugin manager

## Usage

### For Players

When you die:
1. Your inventory will be automatically backed up
2. You'll receive a message confirming the backup
3. Use the restore command to get your items back

### Commands

- `/invrestore [1-3]` - Restore a previous inventory backup
  - `1` = Most recent backup
  - `2` = Second most recent backup
  - `3` = Third most recent backup

### Permissions

- `inventorysave.restore` - Allows players to use the `/invrestore` command (given by default)

## Building from Source

1. Clone the repository
2. Make sure you have Maven installed
3. Run `mvn clean package`
4. The compiled JAR will be in the `target` folder

## Support

If you encounter any issues or need help, please create an issue in the GitHub repository.

## License

This project is open source and available under the MIT License. # mcdoksakura-inventorysave
