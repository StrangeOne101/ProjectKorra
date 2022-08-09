package com.projectkorra.projectkorra.configuration;

import com.projectkorra.projectkorra.ProjectKorra;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PerWorldConfig extends Config {

    private static Map<String, ConfigurationSection> worldConfigs = new HashMap<>();

    private File folder;

    /**
     * Creates a new {@link Config} with the file being the configuration file.
     *
     * @param file The file to create/load
     */
    public PerWorldConfig(File file) {
        this(file, "WorldConfigs");
    }

    /**
     * Creates a new {@link Config} with the file being the configuration file.
     *
     * @param file The file to create/load
     * @param worldsConfigFolder The folder containing all the per world config files
     */
    public PerWorldConfig(File file, String worldsConfigFolder) {
        super(file);

        this.folder = new File(file.getParentFile(), worldsConfigFolder);

        Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, this::loadWorldConfigs, 1L);
    }

    /**
     * Loads all PerWorldConfigs from the config
     */
    protected void loadWorldConfigs() {
        worldConfigs.clear();

        if (this.folder.exists() && this.folder.isDirectory()) {
            for (File file : this.folder.listFiles()) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".yml")) {
                    String worldName = file.getName().substring(0, file.getName().length() - 4);

                    World worldObject = Bukkit.getWorld(worldName);
                    if (worldObject == null) {
                        ProjectKorra.log.severe("Could not find world " + worldName + " for per world config settings. Skipping...");
                        continue;
                    }

                    YamlConfiguration worldConfig = YamlConfiguration.loadConfiguration(file);

                    ConfigurationSection clonedConfig = clone(get());
                    ConfigurationSection newSection = merge(clonedConfig, worldConfig);
                    worldConfigs.put(worldName.toLowerCase(), newSection);

                }
            }
        }
    }

    /**
     * Clones a {@link ConfigurationSection} and returns a new copy of it
     * @param section The section to clone
     * @return A new copy of the section
     */
    protected ConfigurationSection clone(ConfigurationSection section) {
        return merge(new YamlConfiguration(), section);
    }

    /**
     * Merges two {@link ConfigurationSection}s together.
     * @param base The main section that the other section will be merged into
     * @param other The section to merge into the base section
     * @return The base section
     */
    protected ConfigurationSection merge(ConfigurationSection base, ConfigurationSection other) {
        for (String key : other.getKeys(true)) {
            if (key.toLowerCase().startsWith("perworldconfig")) continue;

            Object object = other.get(key);
            if (object instanceof ConfigurationSection) continue; //Skip sections; we are only merging values as sections will delete existing things

            base.set(key, object);
        }
        return base;
    }

    /**
     * @return The folder housing all the per world configs
     */
    public File getWorldConfigsFolder() {
        return folder;
    }

    /**
     * Gets the config for the specified world.
     * @param world The world to get the config for.
     * @return The config for the specified world.
     */
    public ConfigurationSection get(String world) {
        if (worldConfigs.containsKey(world.toLowerCase())) {
            return worldConfigs.get(world.toLowerCase());
        }
        return get();
    }

    /**
     * Gets the config for the specified world.
     * @param world The world to get the config for.
     * @return The config for the specified world.
     */
    public ConfigurationSection get(World world) {
        return world == null ? get() : get(world.getName());
    }
}
