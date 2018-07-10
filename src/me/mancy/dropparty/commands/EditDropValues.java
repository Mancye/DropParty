package me.mancy.dropparty.commands;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.managers.DropPartyManager;
import me.mancy.dropparty.menus.Drops;
import me.mancy.dropparty.utility.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EditDropValues {

    private static Main plugin;

    public EditDropValues(Main main) {
        EditDropValues.plugin = main;
    }

    public static void editHeight(Player p, double height) {
        DropPartyManager.heightToDrop = height;
        plugin.dropLocsConfig.set("height", height);
        plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
        MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "Drop Height Set To: " + ChatColor.GREEN + DropPartyManager.heightToDrop);
    }

    public static void editRadius(Player p, double radius) {
        DropPartyManager.radiusToDrop = radius;
        plugin.dropLocsConfig.set("radius", radius);
        plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
        MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "Drop Radius Set To: " + ChatColor.GREEN + DropPartyManager.radiusToDrop);
    }

    public static void setCountdown(Player p, int countdown) {
        plugin.dropLocsConfig.set("countdowntime", Drops.countdownTime);
        plugin.saveCustomYml(plugin.dropLocsConfig, plugin.dropLocsFile);
        MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "Drop Countdown Time Set To: " + ChatColor.GREEN + Drops.countdownTime + ChatColor.GRAY + " Seconds");
    }

}
