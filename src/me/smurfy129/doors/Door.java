package me.smurfy129.doors;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

public class Door {
	
	SettingsManager config = SettingsManager.getDoors();
	
	public enum DoorState {
		OPEN, CLOSED, CHANGING
	}
	
	private String doorName;
	private DoorState state;
	private Location wallCr1;
	private Location wallCr2;
	private CuboidSelection sensor;
	
	protected Door(String doorName) {
		this.doorName = doorName;
		this.state = DoorState.CLOSED;
		this.wallCr1 = getCords(this.doorName, 1);
		this.wallCr2 = getCords(this.doorName, 2);
		
	}
	
	private Location getCords(String doorName, int cornerNum) {
		Set<String> namesSet = config.getConfigurationSection("Doors.").getKeys(false);
		int numWalls = namesSet.size();
		String[] doorsNames = namesSet.toArray(new String[numWalls]);
		String door = doors[wallIndex];
		int x1 = config.getInt("Doors." + door + ".door.1.x:");
		int y1 = config.getInt("Doors." + door + ".door.1.y:");
		int z1 = config.getInt("Doors." + door + ".door.1.z:");
		int x2 = config.getInt("Doors." + door + ".door.2.x:");
		int y2 = config.getInt("Doors." + door + ".door.2.y:");
		int z2 = config.getInt("Doors." + door + ".door.2.z:");
		Location corner = new Location(Bukkit.getWorld("world"), x1, y1, z1);
	}
}
