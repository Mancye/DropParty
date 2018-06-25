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
					
					DropGUI.updateDisplayWithTokens(p);
					p.openInventory(DropGUI.dropGUI);
					for (int index : DropParty.dropLocations.keySet()) {
						p.sendMessage(ChatColor.AQUA + "[Drop Party] " + "Location #" + index + "");
					}
					return true;
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("loc") || args[0].equalsIgnoreCase("location")) {
					if (p.hasPermission("dropparty.modifylocation") || p.hasPermission("dropparty.*")) {
 
						if (args[1].equalsIgnoreCase("add")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								if (DropParty.dropLocations.containsKey(selectedIndex)) {
									p.sendMessage(ChatColor.RED + "There is already a location set for this index! See all drop locations with " + ChatColor.ITALIC +  "/drop locations");
									return false;
								} else {
									
									DropParty.dropLocations.put(selectedIndex, p.getLocation());
									Main.getPlugin(Main.class).dropLocsConfig.set("Drop Locations." + selectedIndex + " X", p.getLocation().getX());
									Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropLocsConfig, Main.getPlugin(Main.class).dropLocsFile);
									Main.getPlugin(Main.class).dropLocsConfig.set("Drop Locations." + selectedIndex + " Y", p.getLocation().getY());
									Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropLocsConfig, Main.getPlugin(Main.class).dropLocsFile);
									Main.getPlugin(Main.class).dropLocsConfig.set("Drop Locations." + selectedIndex + " Z", p.getLocation().getZ());
									Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropLocsConfig, Main.getPlugin(Main.class).dropLocsFile);
									Main.getPlugin(Main.class).dropLocsConfig.set("Drop Locations." + selectedIndex + " World", p.getWorld().getName());
									Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropLocsConfig, Main.getPlugin(Main.class).dropLocsFile);
									DropParty.numDropLocs++;
									Main.getPlugin(Main.class).dropLocsConfig.set("Number Of Drop Locations", DropParty.numDropLocs);
									Main.getPlugin(Main.class).saveCustomYml(Main.getPlugin(Main.class).dropLocsConfig, Main.getPlugin(Main.class).dropLocsFile);
									p.sendMessage(ChatColor.GREEN + "Drop Location #" + selectedIndex + " Set At Current Location!");
									return true;
								}
							} else {
								p.sendMessage(ChatColor.RED + "Select a drop location index greater than 0 " + ChatColor.ITALIC + "/drop (loc/location) (add/remove) (#)");
								return false;
							}
						} else if (args[1].equalsIgnoreCase("remove")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								if (DropParty.dropLocations.get(selectedIndex) != null) {
									DropParty.dropLocations.remove(selectedIndex);
									p.sendMessage(ChatColor.AQUA + "[Drop Party] " + ChatColor.GREEN + "Successfully Removed Drop Location #" + selectedIndex);
								} else {
									p.sendMessage(ChatColor.AQUA + "[Drop Party] " + ChatColor.RED + "Selected Location Does Not Exist!");
								}
							}
						}
					}
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("edititems")) {
					if (p.hasPermission("dropparty.edititems")) {
						p.openInventory(DropItems.openCategoriesMenu());
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do this!");
						return false;
					}
				}
				
			}
		}

		return false;
	}

}
