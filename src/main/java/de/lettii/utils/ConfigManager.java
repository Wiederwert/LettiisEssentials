package de.lettii.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class ConfigManager extends YamlConfiguration {
    private static final String FOLDER = "plugins/LettisEssentials/";

    private String path;

    public ConfigManager(String filename) {
        this.path = FOLDER + filename;

        try {
            load(this.path);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("Cannot load config file " + this.path + " for Letti's Essentials!");
        }
    }

    public void saveConfig() {
        try {
            save(this.path);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Cannt save config file: " + this.path);
        }
    }
}
