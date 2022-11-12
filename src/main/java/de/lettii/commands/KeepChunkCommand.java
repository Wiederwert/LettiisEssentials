package de.lettii.commands;

import de.lettii.dao.ChunkDAO;
import de.lettii.utils.ChunkLoadEnforcer;
import de.lettii.utils.ConfigManager;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeepChunkCommand implements CommandExecutor, TabCompleter {

    public static final String PERMISSION_LABEL = "de.letti.keep-chunk";
    private final String CONFIG_KEEPCHUNKS_KEY = "keep-chunks";
    private final ChunkLoadEnforcer CHUNK_LOAD_ENFORCER;
    private final ConfigManager CONFIG;
    private final String LABEL;

    public KeepChunkCommand(String label, ConfigManager config, ChunkLoadEnforcer enforcer) {
        super();
        LABEL = label;
        CONFIG = config;
        CHUNK_LOAD_ENFORCER = enforcer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(String.valueOf(CONFIG.getRoot().getCurrentPath()));
        boolean keep = true;
        int x = 0, z = 0;
        World world = null;

        if ((args.length == 1 || args.length > 4)) {
            sender.sendMessage("Invalid command arguments, usage: ", LABEL, " [ChunkPosX ChunkPosZ] [world] [keep]");
            return false;
        }

        if (args.length >= 2) {
            try {
                x = Integer.parseInt(args[0]);
                z = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid arguments for chunk position.");
                return false;
            }
        }

        if (args.length >= 3) {
            world = sender.getServer().getWorld(args[2]);
            if (world == null) {
                sender.sendMessage("World with name '" + args[2] + "' was not found!");
                return false;
            }
        }

        if (args.length == 4) {
            keep = Boolean.parseBoolean(args[3]);
        }

        if (label.equalsIgnoreCase(LABEL) && sender.hasPermission(PERMISSION_LABEL)) {
            Chunk chunk;
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    world = player.getWorld();
                    chunk = player.getChunk();
                    x = chunk.getX();
                    z = chunk.getZ();
                } else {
                    if (world == null) {
                        world = player.getWorld();
                    }
                    chunk = world.getChunkAt(x, z);
                }
            } else {
                if (args.length < 3) {
                    sender.sendMessage("You need to specify the chunk position and the world!");
                    return false;
                }
                chunk = world.getChunkAt(x, z);
            }

            String configChunkPath = getConfigPathForChunk(chunk);
            if (keep) {
                CONFIG.set(configChunkPath, new ChunkDAO(chunk));
                CHUNK_LOAD_ENFORCER.enforceLoadingChunk(chunk);
                sender.sendMessage("Saved chunk[" + x + "," + z + "," + world.getName() + "] for keeping.");
            } else {
                if (CONFIG.contains(configChunkPath)) {
                    CONFIG.set(configChunkPath, null);
                    CHUNK_LOAD_ENFORCER.enforceLoadingChunk(chunk, false);
                    sender.sendMessage("Removed chunk[" + x + "," + z + "," + world.getName() + "] for keeping.");
                } else {
                    sender.sendMessage("chunk[" + x + "," + z + "," + world.getName() + "] was not registered.");
                }
            }
        } else {
            sender.sendMessage("No permissions for using this command.");
            return false;
        }

        CONFIG.saveConfig();
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Chunk chunk = player.getChunk();

            if (args.length == 1) {
                list.add(String.valueOf(chunk.getX()));
            } else if (args.length == 2) {
                list.add(String.valueOf(chunk.getZ()));
            } else if (args.length == 3) {
                World chunk_world = chunk.getWorld();
                list.add(chunk_world.getName());
                for (World world : player.getServer().getWorlds()) {
                    if (world == chunk_world) continue;
                    list.add(world.getName());
                }
            } else if (args.length == 4) {
                list.add(String.valueOf(true));
                list.add(String.valueOf(false));
            }
        } else {
            Server server = sender.getServer();

            if (args.length == 1) {
                list.add(String.valueOf(0));
            } else if (args.length == 2) {
                list.add(String.valueOf(0));
            } else if (args.length == 3) {
                List<String> world_names = server.getWorlds().stream()
                        .map((world) -> world != null ? world.getName() : "")
                        .collect(Collectors.toList());
                list.addAll(world_names);
            } else if (args.length == 4) {
                list.add(String.valueOf(true));
                list.add(String.valueOf(false));
            }
        }
        return list;
    }

    private String getConfigPathForChunk(Chunk chunk) {
        return CONFIG_KEEPCHUNKS_KEY + getConfigSeparator() + chunk.getChunkKey();
    }

    private char getConfigSeparator() {
        char separator = '.';
        Configuration root = CONFIG.getRoot();
        if (root != null) {
            separator = root.options().pathSeparator();
        }
        return separator;
    }
}
