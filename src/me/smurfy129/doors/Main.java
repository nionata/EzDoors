package me.smurfy129.doors;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main plugin;
	public Logger myLog = Bukkit.getLogger();
	PluginDescriptionFile pdf = this.getDescription();
	public EzDoors EDoors;
	public DoorsListener DoorsL;

	public void onEnable() {
		
		this.getCommand("doors").setExecutor(new CmdExecutor(this));
		
		plugin = this;
		EDoors = new EzDoors (this);
		
		getServer().getPluginManager().registerEvents(DoorsL = new DoorsListener(), this);
		
		setUpArray();
		SettingsManager.getDoors();
		
		myLog.info(String.format("[%s] has been enabled!", getDescription().getName()));
	}

	public void onDisabel() {
		myLog.info(String.format("[%s] has been disabled!", getDescription().getName()));
	}
	
	public void setUpArray() {
		EDoors.postionArray();
	}
}	