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
                MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "A drop location is already set here!");
                return;
            }
            plugin.dropLocsConfig.set("Drop Locations." + (allLocations.size() + 1) + " X", p.getLocation().getBlockX());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + (allLocations.size() + 1) + " Y", p.getLocation().getBlockY());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + (allLocations.size() + 1) + " Z", p.getLocation().getBlockZ());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + (allLocations.size() + 1) + " World", p.getWorld().getName());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Amount Drops", allLocations.size());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            allLocations.add(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
            MessageUtil.sendMessageWithPrefix(p, ChatColor.GREEN + "Drop Location #" + allLocations.size() + " Set At Current Location!");
            MessageUtil.sendMessageWithPrefix( p, ChatColor.RED + "Before this location can be used you MUST place a block on top of the beacon!");
        } else {
            MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Must be standing on a beacon to set a location!");
        }
    }

    public static void removeLocation(Player p, int selectedIndex) {
        if (selectedIndex > allLocations.size()) {
            if (p != null) {
                MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Location #" + selectedIndex + " doesn't exist");
            }
            return;
        }

        for (int x = 1; x <= allLocations.size(); x++) {
            plugin.dropLocsConfig.set("Drop Locations." + x + " X", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + x + " Y", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + x + " Z", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + x + " World", null);
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
        }
        allLocations.remove(selectedIndex - 1);
        if (validLocations.size() >= selectedIndex) {
            validLocations.remove(selectedIndex - 1);
        }

        for (int x = 0; x < allLocations.size(); x++) {
            plugin.dropLocsConfig.set("Drop Locations." + (x + 1) + " X", LocationManager.getAllLocations().get(x).getBlockX());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + (x + 1) + " Y", LocationManager.getAllLocations().get(x).getBlockY());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + (x + 1) + " Z", LocationManager.getAllLocations().get(x).getBlockZ());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
            plugin.dropLocsConfig.set("Drop Locations." + (x + 1) + " World", LocationManager.getAllLocations().get(x).getWorld().getName());
            plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
        }
        plugin.dropLocsConfig.set("Amount Drops", allLocations.size());
        plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
        if (p != null) {
            MessageUtil.sendMessageWithPrefix(p,ChatColor.GREEN + "Successfully Removed Drop Location #" + selectedIndex);
        }

    }

}
