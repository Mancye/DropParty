package me.mancy.dropparty.commands;

import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.menus.Drops;
import me.mancy.dropparty.utility.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EditCost {

    private static Main plugin;

    public  EditCost(Main main) {
        EditCost.plugin = main;
    }

    public static void setCost(Player p, int tier, int cost) {
        switch (tier) {
            case 1: {
                Drops.tierOneCost = cost;
                plugin.tokensConfig.set("tierOneCost", cost);
                plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
                Drops.fillDropGUI();
                break;
            }
            case 2: {
                Drops.tierTwoCost = cost;
                plugin.tokensConfig.set("tierTwoCost", cost);
                plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
                Drops.fillDropGUI();
                break;
            }
            case 3: {
                Drops.tierThreeCost = cost;
                plugin.tokensConfig.set("tierThreeCost", cost);
                plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
                Drops.fillDropGUI();
                break;
            }
            case 4: {
                Drops.tierFourCost = cost;
                plugin.tokensConfig.set("tierFourCost", cost);
                plugin.saveCustomYml(plugin.tokensConfig, plugin.tokensFile);
                Drops.fillDropGUI();
                break;
            }
            default: {
                MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "Invalid tier, select 1 - 4");
                break;
            }
        }
        MessageUtil.sendMessageWithPrefix(p,  ChatColor.GRAY + "Set " + ChatColor.GREEN + "Tier " + tier + ChatColor.GRAY + " Cost To " + cost + " Tokens");
    }

}
