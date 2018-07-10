package me.mancy.dropparty.managers;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.utility.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private static Main plugin;
    private static List<Location> allLocations = new ArrayList<>();
    private static List<Location> validLocations = new ArrayList<>();

    public LocationManager(Main main) {
        LocationManager.plugin = main;
    }

    public static List<Location> getAllLocations() {
        return allLocations;
    }

    public static List<Location> getValidLocations() {
        return validLocations;
    }

    public static void addLocation(Player p) {
        if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.BEACON) {
            if (allLocations.contains(p.getLocation())) {
                MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + " A drop location is already set here!");
                return;
            }

            allLocations.add(p.getLocation());
            plugin.dropLocsConfig.set("Drop Locations." + allLocations.size() + " X", p.getLocation().getX());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + allLocations.size() + " Y", p.getLocation().getY());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + allLocations.size() + " Z", p.getLocation().getZ());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + allLocations.size() + " World", p.getWorld().getName());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);

            MessageUtil.sendMessageWithPrefix(p, ChatColor.GREEN + "Drop Location #" + allLocations.size() + " Set At Current Location!");
            MessageUtil.sendMessageWithPrefix( p, ChatColor.RED + "Before this location can be used you MUST place a block on top of the beacon!");
        } else {
            MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Must be standing on a beacon to set a location!");
            return;
        }
    }

    public static void removeLocation(Player p, int selectedIndex) {
        if (selectedIndex > allLocations.size()) {
            MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Location #" + selectedIndex + " doesn't exist");
            return;
        }

        if (selectedIndex <= allLocations.size()) {
            plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " X", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " Y", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " Z", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + selectedIndex + " World", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            allLocations.remove(selectedIndex - 1);
            if (validLocations.size() >= selectedIndex) {
                validLocations.remove(selectedIndex - 1);
            }
            MessageUtil.sendMessageWithPrefix(p,ChatColor.GREEN + "Successfully Removed Drop Location #" + selectedIndex);
        } else {
            MessageUtil.sendMessageWithPrefix(p,ChatColor.RED + "Selected Location Does Not Exist!" );
            return;
        }
    }

}
