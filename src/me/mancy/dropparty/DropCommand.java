package me.mancy.dropparty;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropCommand implements CommandExecutor {
	private Main plugin;
	
	public DropCommand(Main main) {
		this.plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		if (label.equalsIgnoreCase("drop")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("dropparty.drop") || p.hasPermission("dropparty.*")) {
					
					DropGUI.dropgui.updateDisplayWithTokens(p);
					p.openInventory(DropGUI.dropgui.dropGUI);
					for (int index : DropParty.dropParty.dropLocations.keySet()) {
						p.sendMessage(ChatColor.AQUA + "[Drop Party] " + "Location #" + index + "");
					}
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do this!");
					return true;
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("loc") || args[0].equalsIgnoreCase("location")) {
					if (p.hasPermission("dropparty.modifylocation") || p.hasPermission("dropparty.*")) {
 
						if (args[1].equalsIgnoreCase("add")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								if (DropParty.dropParty.dropLocations.containsKey(selectedIndex)) {
									p.sendMessage(ChatColor.RED + "There is already a location set for this index! See all drop locations with " + ChatColor.ITALIC +  "/drop locations");
									return true;
								} else {
									
									DropParty.dropParty.dropLocations.put(selectedIndex, p.getLocation());
									plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " X", p.getLocation().getX());
									plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
									plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " Y", p.getLocation().getY());
									plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
									plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " Z", p.getLocation().getZ());
									plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
									plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " World", p.getWorld().getName());
									plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
									DropParty.dropParty.numDropLocs++;
									plugin.dropLocsConfig.set("Number Of Drop Locations", DropParty.dropParty.numDropLocs);
									plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
									p.sendMessage(ChatColor.GREEN + "Drop Location #" + selectedIndex + " Set At Current Location!");
									return true;
								}
							} else {
								p.sendMessage(ChatColor.RED + "Select a drop location index greater than 0 " + ChatColor.ITALIC + "/drop (loc/location) (add/remove) (#)");
								return true;
							}
						} else if (args[1].equalsIgnoreCase("remove")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								if (DropParty.dropParty.dropLocations.get(selectedIndex) != null) {
									DropParty.dropParty.dropLocations.remove(selectedIndex);
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
					if (p.hasPermission("dropparty.edititems") || p.hasPermission("dropparty.*")) {
						p.openInventory(DropItems.dropItems.openCategoriesMenu());
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do this!");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("editchances")) {
				    if (p.hasPermission("dropparty.editchances") || p.hasPermission("dropparty.*")) {
				        p.openInventory(DropGUI.dropgui.getDropChancesMenu());
				        return true;
                    }
                }
				
			}
		}

		return false;
	}

}
