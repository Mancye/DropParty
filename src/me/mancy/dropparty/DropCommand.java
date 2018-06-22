package me.mancy.dropparty;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DropCommand implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		
		if (label.equalsIgnoreCase("drop")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("dropparty.drop") || p.hasPermission("dropparty.*")) {
				p.openInventory(DropGUI.getDropGUI());
				}
			} else if (args.length == 1) {
				
			} else if (args.length == 2) {
				
			}
		}
		
		return false;
	}

}
