package me.smurfy129.doors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;


public class EzDoors {

	Main plugin;

	public EzDoors(Main plugin) {
		this.plugin = plugin;
	}
	
	static SettingsManager config = SettingsManager.getDoors();

	private Selection s;

	ArrayList<Player> playersWall = new ArrayList<Player>();

	public WorldEditPlugin getWorldEdit() {
		Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

		if(p instanceof WorldEditPlugin) return (WorldEditPlugin) p;
		else return null;
	}

	public void setOffsCommand(Player player, String[] args) {
		String name = args[1];
		if(!(SettingsManager.getDoors().contains("Doors." + name))) {
			player.sendMessage(ChatColor.RED + "That door name does not exist!");
			return;
		}

		getWorldEdit().getSelection(player);
		s = getWorldEdit().getSelection(player);

		if(s.getLength() < 2) {
			player.sendMessage(ChatColor.RED + "Before setting the setoffs you have to select two points!");
			return;
		}

		Location pos1 = s.getMaximumPoint();
		Location pos2 = s.getMinimumPoint();

		config.set("Doors." + name + ".cuboid.1.x:", pos1.getX());
		config.set("Doors." + name + ".cuboid.1.y:", pos1.getY());
		config.set("Doors." + name + ".cuboid.1.z:", pos1.getZ());

		config.set("Doors." + name + ".cuboid.2.x:", pos2.getX());
		config.set("Doors." + name + ".cuboid.2.y:", pos2.getY());
		config.set("Doors." + name + ".cuboid.2.z:", pos2.getZ());

		player.sendMessage(ChatColor.GREEN + "Cuboid for door " + name + " have been saved!");
	}

	public void setWallCommand(Player player, String[] args) {	
		String name = args[1];
		if(!(SettingsManager.getDoors().contains("Doors." + name))) {
			player.sendMessage(ChatColor.RED + "That door name does not exist!");
			return;
		}

		getWorldEdit().getSelection(player);
		s = getWorldEdit().getSelection(player);

		if(s.getLength() < 2) {
			player.sendMessage(ChatColor.RED + "Before setting up the door you have to select two points!");
			return;
		}

		Location pos1 = s.getMaximumPoint();
		Location pos2 = s.getMinimumPoint();

		config.set("Doors." + name + ".door.1.x:", pos1.getX());
		config.set("Doors." + name + ".door.1.y:", pos1.getY());
		config.set("Doors." + name + ".door.1.z:", pos1.getZ());

		config.set("Doors." + name + ".door.2.x:", pos2.getX());
		config.set("Doors." + name + ".door.2.y:", pos2.getY());
		config.set("Doors." + name + ".door.2.z:", pos2.getZ());

		player.sendMessage(ChatColor.GREEN + "Door positions for " + name + " have been saved succesfully!");
	}

	public void newCommand(Player player, String[] args) {
		String name = args[1];

		if((SettingsManager.getDoors().contains("Doors." + name))) {
			player.sendMessage(ChatColor.RED + "That door already exists!");
			return;
		} else {
			config.set("Doors." + name + ".cuboid.1.x:", 0);
			config.set("Doors." + name + ".cuboid.1.y:", 0);
			config.set("Doors." + name + ".cuboid.1.z:", 0);

			config.set("Doors." + name + ".cuboid.2.x:", 0);
			config.set("Doors." + name + ".cuboid.2.y:", 0);
			config.set("Doors." + name + ".cuboid.2.z:", 0);

			config.set("Doors." + name + ".door.1.x:", 0);
			config.set("Doors." + name + ".door.1.y:", 0);
			config.set("Doors." + name + ".door.1.z:", 0);

			config.set("Doors." + name + ".door.2.x:", 0);
			config.set("Doors." + name + ".door.2.y:", 0);
			config.set("Doors." + name + ".door.2.z:", 0);

			player.sendMessage(ChatColor.GREEN + "Door " + name + " was created succesfully!");
		}
	}

	public HashMap<Integer, CuboidSelection> positionArray = new HashMap<Integer, CuboidSelection>();

	public void postionArray() {
		getWorldEdit();
		if(!(SettingsManager.getDoors().contains("Doors."))) return;
		Set<String> namesSet = config.getConfigurationSection("Doors.").getKeys(false);
		int numWalls = namesSet.size();
		String[] names = namesSet.toArray(new String[numWalls]);
		if(numWalls == 0) return;
		for(int i = 0; i < numWalls; i++) {
			int x1 = config.getInt("Doors." + names[i] + ".cuboid" + ".1" + ".x:");
			int y1 = config.getInt("Doors." + names[i] + ".cuboid" + ".1" + ".y:");
			int z1 = config.getInt("Doors." + names[i] + ".cuboid" + ".1" + ".z:");

			int x2 = config.getInt("Doors." + names[i] + ".cuboid" + ".2" + ".x:");
			int y2 = config.getInt("Doors." + names[i] + ".cuboid" + ".2" + ".y:");
			int z2 = config.getInt("Doors." + names[i] + ".cuboid" + ".2" + ".z:");

			Location pos1 = new Location(Bukkit.getWorld("world"), x1, y1, z1);
			Location pos2 = new Location(Bukkit.getWorld("world"), x2, y2, z2);

			CuboidSelection cs = new CuboidSelection(Bukkit.getWorld("world"), pos1, pos2);
			positionArray.put(i + 1, cs);
		}
		
			Main.plugin.getServer().broadcastMessage("Position array: " + positionArray.size());
	}
	
	public ArrayList<Integer> wallsInUse = new ArrayList<Integer>();
	
	int counter = 0;

	public void setWall(final Player player, Material mat, final int wallIndex) {
		Set<String> namesSet = config.getConfigurationSection("Doors.").getKeys(false);
		int numWalls = namesSet.size();
		String[] doors = namesSet.toArray(new String[numWalls]);
		String door = doors[wallIndex];
		int x1 = config.getInt("Doors." + door + ".door.1.x:");
		int y1 = config.getInt("Doors." + door + ".door.1.y:");
		int z1 = config.getInt("Doors." + door + ".door.1.z:");
		int x2 = config.getInt("Doors." + door + ".door.2.x:");
		int y2 = config.getInt("Doors." + door + ".door.2.y:");
		int z2 = config.getInt("Doors." + door + ".door.2.z:");
		Location corner1 = new Location(player.getWorld(), x1, y1, z1);
		Location corner2 = new Location(player.getWorld(), x2, y2, z2);
		final Material oldMaterial = corner1.getBlock().getType();
		for (int x = Math.min(corner1.getBlockX(), corner2.getBlockX()); x <= Math.max(corner1.getBlockX(), corner2.getBlockX()); x++) {
			for (int y = Math.min(corner1.getBlockY(), corner2.getBlockY()); y <= Math.max(corner1.getBlockY(), corner2.getBlockY()); y++) {
				for (int z = Math.min(corner1.getBlockZ(), corner2.getBlockZ()); z <= Math.max(corner1.getBlockZ(), corner2.getBlockZ()); z++) {
					Location l = new Location(corner1.getWorld(), x, y, z);
					l.getBlock().setType(mat);
				}
			}
		}
		
		if(counter == 1) {
			counter = 0;
			return;
		}
		
		Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				setWall(player, oldMaterial, wallIndex);
			}
		}, 20L);
		counter++;
		
		int i = wallsInUse.indexOf(wallIndex + 1);
		wallsInUse.remove(i);
		Bukkit.broadcastMessage(wallsInUse.size() + "");
	}
}
