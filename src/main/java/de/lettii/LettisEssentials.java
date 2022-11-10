package de.lettii;

import de.lettii.commands.KeepChunkCommand;
import de.lettii.listener.ChunkUnloadListener;
import de.lettii.listener.CreeperExplosionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LettisEssentials extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveResource("chunks.yml", false);
        register();
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
        manager.registerEvents(new ChunkUnloadListener(), this);

        // register commands
        Bukkit.getPluginCommand("keepchunk").setExecutor(new KeepChunkCommand());
    }
}
