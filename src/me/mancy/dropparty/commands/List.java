package me.mancy.dropparty.commands;

import me.mancy.dropparty.managers.LocationManager;
import me.mancy.dropparty.utility.MessageUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class List {

    public static void listLocations(Player p) {
        MessageUtil.sendMessageWithPrefix(p,ChatColor.RED + "All Drop Locations");
        for (int x = 0; x < LocationManager.getAllLocations().size(); x++) {
            TextComponent message = new TextComponent( ChatColor.DARK_GRAY + "-" + ChatColor.GRAY +  " Location #" + (x + 1));
            Location loc = LocationManager.getAllLocations().get(x);
            loc.setY(loc.getWorld().getHighestBlockYAt(loc));
            message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ()));
            message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click To Go To Location #" + (x + 1)).color(net.md_5.bungee.api.ChatColor.RED).create() ) );
            p.spigot().sendMessage(message);
        }
        p.sendMessage(" ");
        MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Valid Drop Locations ");
        for (int x = 0; x < LocationManager.getValidLocations().size(); x++) {
            TextComponent message = new TextComponent(ChatColor.DARK_GRAY + "-" + ChatColor.GRAY +  " Location #" + (x + 1));
            Location loc = LocationManager.getValidLocations().get(x);
            loc.setY(loc.getWorld().getHighestBlockYAt(loc));
            message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ()));
            message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click To Go To Location #" + (x + 1)).color(net.md_5.bungee.api.ChatColor.RED).create() ) );
            p.spigot().sendMessage(message);
        }
    }

}
