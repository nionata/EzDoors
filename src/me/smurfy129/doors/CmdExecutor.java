package me.smurfy129.doors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//fasd
public class CmdExecutor implements CommandExecutor{

	Main plugin; 

	public CmdExecutor(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(!(sender instanceof Player)) {
			sender.sendMessage("This command can only be used in-game!");
			return false;
		}

		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.sendMessage(ChatColor.AQUA + "  Welcome to the EzDoors commands!");
			player.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.AQUA + "Nicholas Ionata");
			player.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.AQUA + plugin.pdf.getVersion());
			player.sendMessage(ChatColor.GRAY + "------------------------------------");
			player.sendMessage(ChatColor.AQUA + "/doors new <name> " + ChatColor.GRAY + "- Creates a new door");
			player.sendMessage(ChatColor.AQUA + "/doors setwall <name> " + ChatColor.GRAY + "- Sets the door region");
			player.sendMessage(ChatColor.AQUA + "/doors setwall <name> " + ChatColor.GRAY + "- Sets the door trigger region");

			return false;
		}

		if(commandLabel.equalsIgnoreCase("doors")) {
			if(player.hasPermission("doors")) {
				if(args[0].equalsIgnoreCase("setwall")) {

					if(args.length == 2) {
						Main.plugin.EDoors.setWallCommand(player, args);
					} else {
						player.sendMessage(ChatColor.RED + "Wrong arguments. Try /doors setwall <name>");
					}

				} else if(args[0].equalsIgnoreCase("new")) {

					if(args.length == 2) {
						Main.plugin.EDoors.newCommand(player, args);
					} else {
						player.sendMessage(ChatColor.RED + "Wrong arguments. Try /doors new <name>");
					}
				} else if(args[0].equalsIgnoreCase("setcuboid")) {

					if(args.length == 2) {
						Main.plugin.EDoors.setOffsCommand(player, args);
					} else {
						player.sendMessage(ChatColor.RED + "Wrong arguments. Try /doors setcuboid <name>");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Wrong arguments. Try /wall <setwall | new | setcuboid> <name>");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
			}
		}

		return false;
	}

}
