package me.mancy.dropparty.commands;

import me.mancy.dropparty.managers.DropPartyManager;
import me.mancy.dropparty.managers.LocationManager;
import me.mancy.dropparty.menus.EditItems;
import me.mancy.dropparty.menus.Drops;
import me.mancy.dropparty.menus.EditChances;
import me.mancy.dropparty.utility.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Base implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		if (label.equalsIgnoreCase("drops")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("dropparty.drops") || p.hasPermission("dropparty.*")) {
					Drops.fillDropGUI();
					Drops.dropgui.updateDisplayWithTokens(p);
					p.openInventory(Drops.dropGUI);
					return true;
				} else {
					MessageUtil.sendNoPermissionMessage(p);
					return true;
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("loc") || args[0].equalsIgnoreCase("location")) {
					if (p.hasPermission("dropparty.editlocation") || p.hasPermission("dropparty.*")) {
						if (DropPartyManager.isActiveDropParty()) {
							MessageUtil.sendUnableToEditMessage(p);
							return false;
						}
						 if (args[1].equalsIgnoreCase("remove")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								LocationManager.removeLocation(p, selectedIndex);
							}
						} else {
							MessageUtil.sendInvalidArgsMessage(p);
							return false;
						}
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return false;
					}
				} else if (args[0].equalsIgnoreCase("setcost")) {
					if (p.hasPermission("dropparty.setcost") || p.hasPermission("droparty.*")) {
						if ((Integer.parseInt(args[1]) >= 1 && Integer.parseInt(args[1]) <= 4)) {
							if (Integer.parseInt(args[2]) >= 0) {
								int tier = Integer.parseInt(args[1]);
								int cost = Integer.parseInt(args[2]);
								EditCost.setCost(p, tier, cost);

							} else {
								MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Cost can't be less than 0");
								return false;
							}
						} else {
							MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Invalid tier, select 1 - 4");
							return false;
						}
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return false;
					}
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("edititems")) {
					if (p.hasPermission("dropparty.edititems") || p.hasPermission("dropparty.*")) {
						if (DropPartyManager.isActiveDropParty()) {
							MessageUtil.sendUnableToEditMessage(p);							return false;
						}
						p.openInventory(EditItems.editItems.openCategoriesMenu());
						return true;
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("editchances")) {
				    if (p.hasPermission("dropparty.editchances") || p.hasPermission("dropparty.*")) {
						if (DropPartyManager.isActiveDropParty()) {
							MessageUtil.sendUnableToEditMessage(p);
							return false;
						}
				        p.openInventory(EditChances.getDropChancesMenu());
				        return true;
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
					Help.helpCommand(p);
					return true;
				} else if (args[0].equalsIgnoreCase("list")) {
					if (p.hasPermission("dropparty.listlocations") || p.hasPermission("dropparty.*")) {
						List.listLocations(p);
						return true;
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return false;
					}
				} else if (args[0].equalsIgnoreCase("debug")){
					if (p.hasPermission("dropparty.debug") || p.hasPermission("dropparty.*")) {
						Debug.debugCommand(p);
						return true;
					}
				} else {
					MessageUtil.sendInvalidArgsMessage(p);
					return false;
				}
				
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("editheight")) {
					if (p.hasPermission("dropparty.editheight") || p.hasPermission("dropparty.*")){
						if (DropPartyManager.isActiveDropParty()) {
							MessageUtil.sendUnableToEditMessage(p);
							return false;
						}
						if (Double.parseDouble(args[1]) >= 0) {
							Double height = Double.parseDouble(args[1]);
							EditDropValues.editHeight(p, height);
							return true;
						} else {
							MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "Please Enter A Height Greater Than Or Equal To 0");
							return false;
						}
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return false;
					}
				} else if (args[0].equalsIgnoreCase("editradius")) {
					if (p.hasPermission("dropparty.editradius") || p.hasPermission("dropparty.*")){
						if (DropPartyManager.isActiveDropParty()) {
							MessageUtil.sendUnableToEditMessage(p);
							return false;
						}
						if (Double.parseDouble(args[1]) >= 0) {
							Double radius = Double.parseDouble(args[1]);
							EditDropValues.editRadius(p, radius);
							return true;
						} else {
							MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "Please Enter A Radius Greater Than Or Equal To 0");
							return false;
						}
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return false;
					}
				} else if (args[0].equalsIgnoreCase("setcountdown")) {
					if (p.hasPermission("dropparty.setcountdown") || p.hasPermission("dropparty.*")) {
						if (DropPartyManager.isActiveDropParty()) {
							MessageUtil.sendMessageWithPrefix(p,ChatColor.RED + "Please wait until the current drop party has finished before editing");
							return false;
						}
						if (Integer.parseInt(args[1]) >= 0) {
							Drops.countdownTime = Integer.parseInt(args[1]);
							EditDropValues.setCountdown(p, Drops.countdownTime);
							return true;
						} else {
							MessageUtil.sendMessageWithPrefix(p,ChatColor.RED + "Countdown must at least 0");
							return false;
						}
					} else {
						MessageUtil.sendNoPermissionMessage(p);
						return false;
					}
				} else if (args[0].equalsIgnoreCase("loc")) {
					if (args[1].equalsIgnoreCase("add")) {
						if (p.hasPermission("dropparty.editlocation") || p.hasPermission("dropparty.*")) {
							if (DropPartyManager.isActiveDropParty()) {
								MessageUtil.sendUnableToEditMessage(p);
								return false;
							}
							LocationManager.addLocation(p);
						} else {
							MessageUtil.sendNoPermissionMessage(p);
							return false;
						}
					}
				} else {
					MessageUtil.sendInvalidArgsMessage(p);
					return false;
				}
			} else {
				MessageUtil.sendInvalidArgsMessage(p);
				return false;
			}
		}

		return false;
	}

}
