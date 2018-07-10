package me.mancy.dropparty.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help {

    public static void helpCommand(Player p) {
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
        p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /dtokens" + ChatColor.GRAY + " To view your tokens");

        if (p.hasPermission("dropparty.edittokens") || p.hasPermission("dropparty.*")) {
            p.sendMessage(helpPrefix + ChatColor.GREEN + ChatColor.ITALIC.toString() + " /dtokens (player name) (add/remove/set) (tier) (amount)" + ChatColor.GRAY + " To modify a player's tokens");
        }
    }

}
