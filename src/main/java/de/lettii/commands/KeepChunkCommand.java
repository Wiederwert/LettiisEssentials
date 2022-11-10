package de.lettii.commands;

import de.lettii.utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class KeepChunkCommand implements CommandExecutor {

    public static final String PERMISSION_LABEL = "de.letti.keep-chunk";
    public static final String LABEL = "keepchunk";
    private static final String CONFIG_NAME = "chunks.yml";
    private final ConfigManager CONFIG;

    public KeepChunkCommand() {
        super();
        CONFIG = new ConfigManager(CONFIG_NAME);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can access this command!");
            return false;
        }

        if (label.equalsIgnoreCase(LABEL) && sender.hasPermission(PERMISSION_LABEL)) {
            Player player = (Player) sender;
            int numberOfLocations = CONFIG.getInt("numberOfLocations");
            numberOfLocations++;

            Map<String, Object> chunkInfo = new HashMap<>();
            chunkInfo.put("id", new Integer(numberOfLocations));
            chunkInfo.put("X", new Integer(player.getChunk().getX()));
            chunkInfo.put("Z", new Integer(player.getChunk().getZ()));
            chunkInfo.put("world", player.getWorld().getName());

            CONFIG.set(Integer.toString(numberOfLocations), chunkInfo);
            return true;
        } else {
            sender.sendMessage("No permissions for using this command.");
            return false;
        }
    }
}
