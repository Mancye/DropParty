package me.mancy.dropparty;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		if (label.equalsIgnoreCase("drop")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("dropparty.drop") || p.hasPermission("dropparty.*")) {
					p.openInventory(DropGUI.getDropGUI());
					return true;
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("loc") || args[0].equalsIgnoreCase("location")) {
					if (p.hasPermission("dropparty.addlocation") || p.hasPermission("dropparty.*")) {

						if (args[1].equalsIgnoreCase("add")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								if (DropParty.dropLocations.containsKey(selectedIndex)) {
									p.sendMessage(ChatColor.RED + "There is already a location set for this index! See all drop locations with " + ChatColor.ITALIC +  "/drop locations");
									return false;
								} else {
									DropParty.dropLocations.put(selectedIndex, p.getLocation());
									Main.dropLocsConfig.set("Drop Locations." + selectedIndex + ". X", p.getLocation().getX());
									Main.dropLocsConfig.set("Drop Locations." + selectedIndex + ". Y", p.getLocation().getX());
									Main.dropLocsConfig.set("Drop Locations." + selectedIndex + ". Z", p.getLocation().getX());
									Main.dropLocsConfig.set("Drop Location." + selectedIndex + ". World", p.getWorld().getName());
									DropParty.numDropLocs++;
									Main.dropLocsConfig.set("Number Of Drop Locations.", DropParty.numDropLocs);
									p.sendMessage(ChatColor.GREEN + "Drop Location #" + selectedIndex + " Set At Current Location!");
									return true;
								}
							} else {
								p.sendMessage(ChatColor.RED + "Select a drop location index greater than 0 " + ChatColor.ITALIC + "/drop (loc/location) (add/remove) (#)");
								return false;
							}
						}
					}
				}
			}
		}

		return false;
	}

}
