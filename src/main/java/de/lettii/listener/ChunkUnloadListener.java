package de.lettii.listener;

import de.lettii.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkUnloadListener implements Listener {
    private static final String CONFIG_NAME = "chunks.yml";
    private final ConfigManager CONFIG;

    public ChunkUnloadListener() {
        super();
        CONFIG = new ConfigManager(CONFIG_NAME);
    }


    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
//        Bukkit.getConsoleSender().sendMessage("X: " + event.getChunk().getX()+ " Z: " + event.getChunk().getZ());
    }
}
