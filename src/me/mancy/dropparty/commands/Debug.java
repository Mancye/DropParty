package me.mancy.dropparty.commands;

import me.mancy.dropparty.managers.LocationManager;
import me.mancy.dropparty.utility.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Debug {

    public static void debugCommand(Player p) {
        int amtValidShown = 0, amtAllShown = 0;
        for (Location loc : LocationManager.getValidLocations()) {
            MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + " Valid Drop Location #" + amtValidShown + ": X = " + loc.getBlockX() + ", Y = " + loc.getBlockY() + ", Z = " + loc.getBlockZ());
            amtValidShown++;
        }

        for (Location loc : LocationManager.getAllLocations()) {
            MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + " All Drop Location #" + amtAllShown + ": X = " + loc.getBlockX() + ", Y = " + loc.getBlockY() + ", Z = " + loc.getBlockZ());
            amtAllShown++;
        }

    }

}
