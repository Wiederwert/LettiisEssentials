package de.lettii;

import de.lettii.commands.KeepChunkCommand;
import de.lettii.listener.CreeperExplosionListener;
import de.lettii.utils.ChunkLoadEnforcer;
import de.lettii.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LettisEssentialsPlugin extends JavaPlugin {

    private static final ChunkLoadEnforcer CHUNK_LOAD_ENFORCER = new ChunkLoadEnforcer();

    private static final String CHUNK_CONFIG_FILE = "chunks.yml";
    private static final ConfigManager CHUNK_CONFIG_MNG = new ConfigManager(CHUNK_CONFIG_FILE);
    private static final String KEEPCHUNK_CMD_LABEL = "keepchunk";

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveResource(CHUNK_CONFIG_FILE, false);
        register();
        init();
        Bukkit.getConsoleSender().sendMessage("Loaded Letti's Essentials Plugin");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("Unloaded Letti's Essentials Plugin");
    }

    private void register() {
        PluginManager manager = Bukkit.getPluginManager();

        // register listeners
        manager.registerEvents(new CreeperExplosionListener(), this);

        // register commands
        Bukkit.getPluginCommand(KEEPCHUNK_CMD_LABEL).setExecutor(new KeepChunkCommand(KEEPCHUNK_CMD_LABEL, CHUNK_CONFIG_MNG, CHUNK_LOAD_ENFORCER));
    }

    private void init() {
        int countOfForceLoadedChunks = CHUNK_LOAD_ENFORCER.enforceLoadChunksFromConfig(CHUNK_CONFIG_MNG);
        Bukkit.getConsoleSender().sendMessage("Force loaded " + countOfForceLoadedChunks + " chunks");
    }
}
