package me.smurfy129.doors;

import java.io.File;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {
	
	private static final SettingsManager 
	doors = new SettingsManager("doors");
	
	public static SettingsManager getDoors() {
		return doors;
	}
	
	private File file;
	private FileConfiguration config;
	
	private SettingsManager(String fileName) {
		
		file = new File(Main.plugin.getDataFolder(), fileName + ".yml");
		
		if(!file.exists()) {
			try {
				Main.plugin.saveResource(fileName + ".yml", false);
			} catch(Exception e) {
				Main.plugin.myLog.severe("[EzDoors] " + fileName + ".yml could not be created! Error!");
				e.printStackTrace();
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}	
	
	public boolean contains(String path) {
		return config.contains(path);
	}
	
	public ConfigurationSection getConfigurationSection(String path) {	
		return config.getConfigurationSection(path);
	}
	
	public String getString(String path) {
		return config.getString(path);
	}
	
	public int getInt(String path) {
		return config.getInt(path);
	}
	
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
		Main.plugin.myLog.info("[EzDoors] " + file.getName() + " has been reloaded!");
	}
	
	public void save() {
		try {
			config.save(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
