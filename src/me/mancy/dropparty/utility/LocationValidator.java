package me.mancy.dropparty.utility;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class LocationValidator implements Listener {

    private static Main plugin;

    public LocationValidator(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static Block getHighestBlock(Location loc) {
        int y = 0;
        if (loc == null) return null;
        while (loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + y, loc.getBlockZ())).getType().name().contains("STAINED_GLASS") ||
                loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + y, loc.getBlockZ())).getType().equals(Material.BEACON)) {
                y++;
        }
        return loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + y, loc.getBlockZ()));
    }

    public static void validateLocations() {
        LocationManager.getValidLocations().clear();
        for (Location loc : LocationManager.getAllLocations()) {
            if (loc != null) {
                if (!getHighestBlock(loc).getType().name().contains("STAINED_GLASS")) {
                    if (getHighestBlock(loc).getType() != Material.BEACON) {
                        if (getHighestBlock(loc).getType() != Material.AIR) {
                            if (!(LocationManager.getValidLocations().contains(loc))) {
                                LocationManager.getValidLocations().add(loc);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void locationValidated(BlockPlaceEvent event) {
        if (!(event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*"))) return;
        if (event.getBlock().getType().name().contains("STAINED_GLASS")) return;
        boolean willValidate = false;
        for (Location loc : LocationManager.getAllLocations()) {
            if (event.getBlock().getLocation().getBlockX() == loc.getBlockX() && event.getBlock().getLocation().getBlockZ() == loc.getBlockZ()) {
            if (!(LocationManager.getValidLocations().contains(loc))) {
                willValidate = true;
                } else {
                    MessageUtil.sendMessageWithPrefix(event.getPlayer(), ChatColor.RED + "Drop location was already validated!");
                    return;
                }
            }
        }
        if (willValidate) {
            LocationManager.getAllLocations().clear();
            plugin.loadLocations();
            validateLocations();
            MessageUtil.sendMessageWithPrefix(event.getPlayer(), ChatColor.GREEN + "Drop location was validated successfully!");
        }
    }
    @EventHandler
    private void locationUnvalidated(BlockBreakEvent event) {

        if (event.getPlayer().hasPermission("dropparty.editlocation") || event.getPlayer().hasPermission("dropparty.*")) {
            if (!event.getBlock().getType().name().contains("STAINED_GLASS")){
                boolean willValidate = false;
                for (Location loc : LocationManager.getValidLocations()) {
                    if (event.getBlock().getLocation().getBlockX() == loc.getBlockX() && event.getBlock().getLocation().getBlockZ() == loc.getBlockZ()) {
                       if (!event.getBlock().getType().name().contains("STAINED_GLASS") && !event.getBlock().getType().equals(Material.BEACON) && !event.getBlock().getType().equals(Material.AIR)) {
                           willValidate = true;
                       }

                    }
                }
                if (willValidate) {
                    event.getBlock().breakNaturally();
                    LocationManager.getAllLocations().clear();
                    LocationManager.getValidLocations().clear();
                    plugin.loadLocations();
                    validateLocations();
                    MessageUtil.sendMessageWithPrefix(event.getPlayer(),ChatColor.RED + "Location unvalidated, you must place a block above the beacon");
                }

            }
        }
    }

}
