package me.mancy.dropparty.menus;

import me.mancy.dropparty.main.DropParty;
import me.mancy.dropparty.main.Main;
import me.mancy.dropparty.managers.DropPartyManager;
import me.mancy.dropparty.managers.LocationManager;
import me.mancy.dropparty.managers.TokenManager;
import me.mancy.dropparty.utility.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Drops implements Listener, Runnable {
    private static Main plugin;
    public static Drops dropgui;
    public static Inventory dropGUI;

    public static int countdownTime = 1;

    public static int tierOneCost;
    public static int tierTwoCost;
    public static int tierThreeCost;
    public static int tierFourCost;

    int time;
    int taskID;

    public Drops(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        dropgui = this;
    }


    public static void fillDropGUI() {
        tierOneCost = plugin.tokensConfig.getInt("tierOneCost");
        tierTwoCost = plugin.tokensConfig.getInt("tierTwoCost");
        tierThreeCost = plugin.tokensConfig.getInt("tierThreeCost");
        tierFourCost = plugin.tokensConfig.getInt("tierFourCost");

        dropGUI = Bukkit.createInventory(null, 27, ChatColor.RED + "Drop Menu");
        ItemStack tierOne = new ItemStack(Material.COAL_ORE);
        ItemMeta tierOneMeta = tierOne.getItemMeta();
        List<String> tierOneLore = new ArrayList<>();
        tierOneMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 1");
        tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier One Drop Party!");
        tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierOneCost + " Token(s)");
        tierOneMeta.setLore(tierOneLore);
        tierOne.setItemMeta(tierOneMeta);
        dropGUI.setItem(10, tierOne);

        ItemStack tierTwo = new ItemStack(Material.IRON_BLOCK);
        ItemMeta tierTwoMeta = tierTwo.getItemMeta();
        List<String> tierTwoLore = new ArrayList<>();
        tierTwoMeta.setDisplayName(ChatColor.GRAY + "Tier 2");
        tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Two Drop Party!");
        tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierTwoCost + " Token(s)");
        tierTwoMeta.setLore(tierTwoLore);
        tierTwo.setItemMeta(tierTwoMeta);
        dropGUI.setItem(12, tierTwo);

        ItemStack tierThree = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta tierThreeMeta = tierThree.getItemMeta();
        List<String> tierThreeLore = new ArrayList<>();
        tierThreeMeta.setDisplayName(ChatColor.GOLD + "Tier 3");
        tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Three Drop Party!");
        tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierThreeCost + " Token(s)");
        tierThreeMeta.setLore(tierThreeLore);
        tierThree.setItemMeta(tierThreeMeta);
        dropGUI.setItem(14, tierThree);

        ItemStack tierFour = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta tierFourMeta = tierFour.getItemMeta();
        List<String> tierFourLore = new ArrayList<>();
        tierFourMeta.setDisplayName(ChatColor.AQUA + "Tier 4");
        tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Four Drop Party!");
        tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: " + tierFourCost + " Token(s)");
        tierFourMeta.setLore(tierFourLore);
        tierFour.setItemMeta(tierFourMeta);
        dropGUI.setItem(16, tierFour);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "Exit");
        exit.setItemMeta(exitMeta);
        dropGUI.setItem(18, exit);

    }



    public void updateDisplayWithTokens(Player p) {

        ItemStack tokens = new ItemStack(Material.BOOK);
        ItemMeta tokensMeta = tokens.getItemMeta();
        List<String> tokensLore = new ArrayList<>();
        tokensMeta.setDisplayName(ChatColor.GRAY + "Your Tokens:");
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier One: " + ChatColor.GREEN + TokenManager.getTokens(p, 1));
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier Two: " + ChatColor.GREEN + TokenManager.getTokens(p, 2));
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier Three: " + ChatColor.GREEN + TokenManager.getTokens(p, 3));
        tokensLore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tier Four: " + ChatColor.GREEN + TokenManager.getTokens(p, 4));
        tokensMeta.setLore(tokensLore);
        tokens.setItemMeta(tokensMeta);
        dropGUI.setItem(26, tokens);

    }


    private void setTimer(int amount) {
        time = amount;
    }

    private void startTimer(int dropTier) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (time == 0) {
                    Bukkit.broadcastMessage(MessageUtil.getPrefix() + ChatColor.GREEN + " DROP PARTY HAS BEGUN! Look for activated " + ChatColor.GOLD + "totems" + ChatColor.GREEN + "!");
                    DropParty dp = new DropParty(plugin, dropTier);
                    dp.start();
                    stopTimer();
                    return;
                }
                if (time % 5 == 0 && time <= 20) {
                    Bukkit.broadcastMessage(MessageUtil.getPrefix() + ChatColor.GRAY + " Time Until Drop Party" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + time + " Seconds!");
                }


                time -= 1;
            }
        }, 0L, 20L);
    }

    private void stopTimer() {
        Bukkit.getScheduler().cancelTask(taskID);
    }


    private int getAmount(Inventory inv) {
        int amt = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null) {
                if (!(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Back")))
                    if (!(i.getType().equals(Material.AIR))) {
                        amt++;
                    }
            }
        }
        return amt;
    }


    @EventHandler
    private void handleClicks(InventoryClickEvent event) {

        if (event.getInventory().getName().contains("Drop Menu")) {

            if (event.getWhoClicked() instanceof Player) {

                Player p = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                event.setCancelled(true);
                if (slot != 18 && slot != 26) {

                    if (LocationManager.getValidLocations().size() == 0) {
                        MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "No valid drop locations set");
                        return;
                    }

                    if (getAmount(EditItems.editItems.commonItems) == 0
                            && getAmount(EditItems.editItems.uncommonItems) == 0
                            && getAmount(EditItems.editItems.rareItems) == 0
                            && getAmount(EditItems.editItems.epicItems) == 0
                            && getAmount(EditItems.editItems.legendaryItems) == 0) {
                        MessageUtil.sendMessageWithPrefix(p, ChatColor.RED + "No items to drop");
                        return;

                    }

                }
                switch (slot) {

                    case 10:

                        if (!DropPartyManager.isActiveDropParty()) {
                            if (TokenManager.getTokens(p, 1) >= tierOneCost) {
                                TokenManager.subtractTokens(p, 1, tierOneCost);
                                MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "You have started a" + ChatColor.GREEN + " tier 1 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(1);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 1 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }
                            } else {
                                MessageUtil.sendInsufficientTokensMessage(p, 1);
                            }
                        } else {
                            MessageUtil.sendAlreadyActivePartyMessage(p);
                        }
                        break;
                    case 12:
                        if (!DropPartyManager.isActiveDropParty()) {
                            if (TokenManager.getTokens(p, 2) >= tierTwoCost) {
                                TokenManager.subtractTokens(p, 2, tierTwoCost);
                                MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "You have started a" + ChatColor.GREEN + " tier 2 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(2);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 2 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }
                            } else {
                                MessageUtil.sendInsufficientTokensMessage(p, 2);
                            }
                        } else {
                            MessageUtil.sendAlreadyActivePartyMessage(p);
                        }
                        break;
                    case 14:
                        if (!DropPartyManager.isActiveDropParty()) {
                            if (TokenManager.getTokens(p, 3) >= tierThreeCost) {
                                TokenManager.subtractTokens(p, 3, tierThreeCost);
                                MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "You have started a" + ChatColor.GREEN + " tier 3 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(3);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 3 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }
                            } else {
                                MessageUtil.sendInsufficientTokensMessage(p, 3);
                            }
                        } else {
                            MessageUtil.sendAlreadyActivePartyMessage(p);
                        }
                        break;
                    case 16: {
                        if (!DropPartyManager.isActiveDropParty()) {
                            if (TokenManager.getTokens(p, 4) >= tierFourCost) {
                                TokenManager.subtractTokens(p, 4, tierFourCost);
                                MessageUtil.sendMessageWithPrefix(p,ChatColor.GRAY + "You have started a" + ChatColor.GREEN + " tier 4 " + ChatColor.GRAY + "drop party!");
                                setTimer(countdownTime);
                                startTimer(4);
                                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                                    online.sendMessage("");
                                    online.sendMessage("");
                                    online.sendMessage(ChatColor.GREEN + "          TIER 4 DROP PARTY         ");
                                    online.sendMessage(ChatColor.AQUA + "           BEGINS IN " + countdownTime + " SECONDS");
                                    online.sendMessage("");
                                    online.sendMessage("");
                                }

                            } else {
                                MessageUtil.sendInsufficientTokensMessage(p, 4);
                            }
                        } else {
                            MessageUtil.sendAlreadyActivePartyMessage(p);
                        }
                        break;
                    }

                    case 18: {
                        p.closeInventory();
                        break;
                    }


                }

            }


        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
