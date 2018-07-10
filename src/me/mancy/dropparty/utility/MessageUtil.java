package me.mancy.dropparty.utility;

import me.mancy.dropparty.managers.TokenManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {

    private static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD.toString() + "P" + ChatColor.RED + ChatColor.BOLD.toString() + "A" + ChatColor.DARK_GRAY + ":" + ChatColor.GRAY + "Events" + ChatColor.DARK_GRAY + "]";

    public static String getPrefix() { return prefix; }

    public static void sendMessageWithPrefix(Player p, String message) {
        if (p == null) return;
        p.sendMessage(prefix + " " + message);
    }

    public static void sendNoPermissionMessage(Player p) {
        if (p == null) return;
        p.sendMessage(prefix + ChatColor.RED + " Sorry, you do not have permission to do this.");
    }

    public static void sendInvalidArgsMessage(Player p) {
        if (p == null) return;
        p.sendMessage(prefix + ChatColor.GRAY + " Invalid Arguments, Use " + ChatColor.GREEN + "/drops help" + ChatColor.GRAY + " To View Available Commands");
    }

    public static void sendUnableToEditMessage(Player p) {
        if (p == null) return;
        p.sendMessage(prefix + ChatColor.RED + " Please wait until the current drop party has finished before editing");

    }

    public static void sendInsufficientTokensMessage(Player p, int tier) {
        MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "You do not have enough " + ChatColor.GREEN + "tier " + tier + ChatColor.GRAY + " tokens for this! Your tokens: " + ChatColor.RED + TokenManager.getTokens(p, 4));

    }

    public static void sendAlreadyActivePartyMessage(Player p) {
        p.sendMessage(prefix + ChatColor.GRAY + " There is already an active drop party, please wait for it to finish");
    }

}
