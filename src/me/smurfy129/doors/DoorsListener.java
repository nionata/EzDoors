package me.smurfy129.doors;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;


public class DoorsListener implements Listener {

	String[] doors;

	static SettingsManager configW = SettingsManager.getDoors();

	public void setUpWalls() {
		if(configW.contains("Doors.")) {
			Set<String> namesSet = configW.getConfigurationSection("Doors.").getKeys(false);
			int numWalls = namesSet.size();
			doors = namesSet.toArray(new String[numWalls]);
		}
	}


	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;
		final Player player = e.getPlayer();	
		int counter = Main.plugin.EDoors.positionArray.size();
		if(counter == 0) return;
		for(int i = 1; i <= counter; i++ ) {
			CuboidSelection cs = Main.plugin.EDoors.positionArray.get(i);
			if(cs.contains(e.getTo()) && !cs.contains(e.getFrom())) {
				Main.plugin.getServer().broadcastMessage("1");
				if(!(Main.plugin.EDoors.wallsInUse.contains(i))) {
					Main.plugin.getServer().broadcastMessage("2");
					Main.plugin.EDoors.setWall(player, Material.AIR, i);
					Main.plugin.getServer().broadcastMessage("3");
					Main.plugin.EDoors.wallsInUse.add(i);
					Main.plugin.getServer().broadcastMessage("4");
				}
			}
		}
	}
}
