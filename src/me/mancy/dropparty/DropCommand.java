package me.mancy.dropparty;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropCommand implements CommandExecutor {
	private Main plugin;
	private String prefix;
	public DropCommand(Main main) {
		this.plugin = main;
		prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD.toString() + "P" + ChatColor.RED + ChatColor.BOLD.toString() + "A" + ChatColor.DARK_GRAY + ":" + ChatColor.GRAY + "Events" + ChatColor.DARK_GRAY + "]";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		if (label.equalsIgnoreCase("drops")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("dropparty.drop") || p.hasPermission("dropparty.*")) {
					DropGUI.fillDropGUI();
					DropGUI.dropgui.updateDisplayWithTokens(p);
					p.openInventory(DropGUI.dropgui.dropGUI);
					return true;
				} else {
					p.sendMessage(prefix + ChatColor.GRAY + " Sorry, you don't have permission to do this!");
					return true;
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("loc") || args[0].equalsIgnoreCase("location")) {
					if (p.hasPermission("dropparty.editlocation") || p.hasPermission("dropparty.*")) {

						 if (args[1].equalsIgnoreCase("remove")) {
							if (Integer.parseInt(args[2]) >= 1) {
								int selectedIndex = Integer.parseInt(args[2]);
								if (DropParty.dropParty.dropLocations.get(selectedIndex - 1) != null) {
									DropParty.dropParty.dropLocations.remove(selectedIndex - 1);
									p.sendMessage(prefix + ChatColor.GREEN + " Successfully Removed Drop Location #" + selectedIndex);
								} else {
									p.sendMessage(prefix + ChatColor.RED + " Selected Location Does Not Exist!");
								}
							}
						} else {
							p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drops help" + ChatColor.GRAY + " To View Available Commands");
							return false;
						}
					} else {
						p.sendMessage(prefix + ChatColor.GRAY + " Sorry, you don't have permission to do this!");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("setcost")) {
					if (p.hasPermission("dropparty.setcost") || p.hasPermission("droparty.*")) {
						if ((Integer.parseInt(args[1]) >= 1 && Integer.parseInt(args[1]) <= 4)) {
							if (Integer.parseInt(args[2]) >= 0) {
								int tier = Integer.parseInt(args[1]);
								int cost = Integer.parseInt(args[2]);

								switch (tier) {
									case 1: {
										DropGUI.tierOneCost = cost;
										p.sendMessage(prefix + ChatColor.GRAY + " Set " + ChatColor.GREEN + "Tier 1" + ChatColor.GRAY + " Cost To " + cost + " Tokens");
										plugin.tokensConfig.set("tierOneCost", cost);
										plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
										DropGUI.fillDropGUI();
										break;
									}
									case 2: {
										DropGUI.tierTwoCost = cost;
										p.sendMessage(prefix + ChatColor.GRAY + " Set " + ChatColor.GREEN + "Tier 2" + ChatColor.GRAY + " Cost To " + cost + " Tokens");
										plugin.tokensConfig.set("tierTwoCost", cost);
										plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
										DropGUI.fillDropGUI();
										break;
									}
									case 3: {
										DropGUI.tierThreeCost = cost;
										p.sendMessage(prefix + ChatColor.GRAY + " Set " + ChatColor.GREEN + "Tier 3" + ChatColor.GRAY + " Cost To " + cost + " Tokens");
										plugin.tokensConfig.set("tierThreeCost", cost);
										plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
										DropGUI.fillDropGUI();
										break;
									}
									case 4: {
										DropGUI.tierFourCost = cost;
										p.sendMessage(prefix + ChatColor.GRAY + " Set " + ChatColor.GREEN + "Tier 4" + ChatColor.GRAY + " Cost To " + cost + " Tokens");
										plugin.tokensConfig.set("tierFourCost", cost);
										plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
										DropGUI.fillDropGUI();
										break;
									}
									default: {
										p.sendMessage(prefix + ChatColor.RED + " Invalid Tier, Select 1 - 4");
										break;
									}
								}

							} else {
								p.sendMessage(prefix + ChatColor.GRAY + " Cost can not be less than 0");
								return false;
							}
						} else {
							p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drops help" + ChatColor.GRAY + " To View Available Commands");
							return false;
						}
					} else {
						p.sendMessage(prefix + ChatColor.GRAY + " Sorry, you don't have permission to do this!");
						return false;
					}
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("edititems")) {
					if (p.hasPermission("dropparty.edititems") || p.hasPermission("dropparty.*")) {
						p.openInventory(DropItems.dropItems.openCategoriesMenu());
						return true;
					} else {
						p.sendMessage(prefix + ChatColor.RED + " Sorry, you don't have permission to do this!");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("editchances")) {
				    if (p.hasPermission("dropparty.editchances") || p.hasPermission("dropparty.*")) {
				        p.openInventory(DropGUI.dropgui.getDropChancesMenu());
				        return true;
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
					String helpPrefix = ChatColor.GRAY + "-";
					p.sendMessage(" ");
					p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD.toString() + "P" + ChatColor.RED + ChatColor.BOLD.toString() + "A" + ChatColor.DARK_GRAY + ":" + ChatColor.GRAY + "Events" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + "Drop Party" + ChatColor.GRAY + " help page:");
					p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops" + ChatColor.GRAY + " Opens the main drop menu");
					if (p.hasPermission("dropparty.editchances") || p.hasPermission("dropparty.*")) {
						p.sendMessage( helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops editchances" + ChatColor.GRAY + " To edit item drop chances");
					}
					if (p.hasPermission("dropparty.edititems") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops edititems" + ChatColor.GRAY + " To edit drop item lists");
					}
					if (p.hasPermission("dropparty.editheight") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops editheight (height in blocks)" + ChatColor.GRAY + " To edit drop height");
					}
					if (p.hasPermission("dropparty.editradius") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops editradius (radius in blocks)" + ChatColor.GRAY + " To edit drop radius");
					}
					if (p.hasPermission("dropparty.editlocation") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops loc remove (index)" + ChatColor.GRAY + " To remove a selected drop location");
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + "/drops loc add" + ChatColor.GRAY + " To add a new drop location at your position");
					}
					if (p.hasPermission("dropparty.listlocations") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops list" + ChatColor.GRAY + " To list all current drop locations");
					}
					if (p.hasPermission("droparty.setcountdown") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops setcountdown (time in seconds)" + ChatColor.GRAY + " To set the countdown time until drop parties start");
					}
					if (p.hasPermission("dropparty.setcost") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /drops setcost (tier) (amount)" + ChatColor.GRAY + " To set the cost of drop parties");
					}
					p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /tokens" + ChatColor.GRAY + " To view your tokens");

					if (p.hasPermission("dropparty.edittokens") || p.hasPermission("dropparty.*")) {
						p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /tokens (player name) (add/remove/set) (tier) (amount)" + ChatColor.GRAY + " To modify a player's tokens");
					}
					return true;
				} else if (args[0].equalsIgnoreCase("list")) {
					if (p.hasPermission("dropparty.listlocations") || p.hasPermission("dropparty.*")) {
						for (int x = 0; x < DropParty.dropParty.dropLocations.size(); x++) {
							TextComponent message = new TextComponent( prefix + ChatColor.GRAY +  " Location #" + (x + 1));
							Location loc = DropParty.dropParty.dropLocations.get(x);
							loc.setY(loc.getWorld().getHighestBlockYAt(loc));
							message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ()));
							message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click To Go To Location #" + (x + 1)).color(net.md_5.bungee.api.ChatColor.RED).create() ) );
							p.spigot().sendMessage(message);
						}
						return true;
					} else {
						p.sendMessage(prefix + ChatColor.GRAY + " Sorry, you don't have permission to do this!");
						return false;
					}
				} else {
					p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drops help" + ChatColor.GRAY + " To View Available Commands");
					return false;
				}
				
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("editheight")) {
					if (p.hasPermission("dropparty.editheight") || p.hasPermission("dropparty.*")){
						if (Double.parseDouble(args[1]) >= 0) {
							Double height = Double.parseDouble(args[1]);
							DropParty.dropParty.heightToDrop = height;
							plugin.dropLocsConfig.set("height", height);
							plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
							p.sendMessage(prefix + ChatColor.GRAY + " Drop Height Set To: " + ChatColor.GREEN + DropParty.dropParty.heightToDrop);
							return true;
						} else {
							p.sendMessage(prefix + ChatColor.GRAY + " Please Enter A Height Greater Than Or Equal To 0");
							return false;
						}
					}
				} else if (args[0].equalsIgnoreCase("editradius")) {
					if (p.hasPermission("dropparty.editradius") || p.hasPermission("dropparty.*")){
						if (Double.parseDouble(args[1]) >= 0) {
							Double radius = Double.parseDouble(args[1]);
							DropParty.dropParty.radiusToDrop = radius;
							plugin.dropLocsConfig.set("radius", radius);
							plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
							p.sendMessage(prefix + ChatColor.GRAY + " Drop Radius Set To: " + ChatColor.GREEN + DropParty.dropParty.radiusToDrop);
							return true;
						} else {
							p.sendMessage(prefix + ChatColor.GRAY + " Please Enter A Radius Greater Than Or Equal To 0");
							return false;
						}
					}
				} else if (args[0].equalsIgnoreCase("setcountdown")) {
					if (p.hasPermission("dropparty.setcountdown") || p.hasPermission("dropparty.*")) {
						if (Integer.parseInt(args[1]) >= 0) {
							DropGUI.countdownTime = Integer.parseInt(args[1]);
							plugin.dropLocsConfig.set("countdowntime", DropGUI.dropgui.countdownTime);
							plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
							p.sendMessage(prefix + ChatColor.GRAY + " Drop Countdown Time Set To: " + ChatColor.GREEN + DropGUI.dropgui.countdownTime + ChatColor.GRAY + " Seconds");
						} else {
							p.sendMessage(prefix + ChatColor.RED + " Countdown must at least 0");
							return false;
						}
					}
				} else if (args[0].equalsIgnoreCase("loc")) {
					if (args[1].equalsIgnoreCase("add")) {

						DropParty.dropParty.dropLocations.add(p.getLocation());
						plugin.dropLocsConfig.set("Drop Locations." + DropParty.dropParty.dropLocations.size() + " X", p.getLocation().getX());
						plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
						plugin.dropLocsConfig.set("Drop Locations." + DropParty.dropParty.dropLocations.size() + " Y", p.getLocation().getY());
						plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
						plugin.dropLocsConfig.set("Drop Locations." + DropParty.dropParty.dropLocations.size() + " Z", p.getLocation().getZ());
						plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
						plugin.dropLocsConfig.set("Drop Locations." + DropParty.dropParty.dropLocations.size() + " World", p.getWorld().getName());
						plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
						p.sendMessage(prefix + ChatColor.GREEN + " Drop Location #" + DropParty.dropParty.dropLocations.size() + " Set At Current Location!");
						return true;

					}
				} else {
					p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drops help" + ChatColor.GRAY + " To View Available Commands");
					return false;
				}
			} else {
				p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drops help" + ChatColor.GRAY + " To View Available Commands");
				return false;
			}
		}

		return false;
	}

}
