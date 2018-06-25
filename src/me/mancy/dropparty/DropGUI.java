package me.mancy.dropparty;

import java.util.ArrayList;
import java.util.List;

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

public class DropGUI implements Listener, Runnable {

	private Main plugin;
	public static Inventory dropGUI;

	int time;
	int taskID;
	
	public DropGUI(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		fillDropGUI();
	}

	private void fillDropGUI() {
		dropGUI = Bukkit.createInventory(null, 27, ChatColor.RED + "Drop Menu");

		ItemStack tierOne = new ItemStack(Material.COAL_ORE);
		ItemMeta tierOneMeta = tierOne.getItemMeta();
		List<String> tierOneLore = new ArrayList<String>();
		tierOneMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 1");
		tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier One Drop Party!");
		tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: 1 Token");
		tierOneMeta.setLore(tierOneLore);
		tierOne.setItemMeta(tierOneMeta);
		dropGUI.setItem(10, tierOne);

		ItemStack tierTwo = new ItemStack(Material.IRON_BLOCK);
		ItemMeta tierTwoMeta = tierTwo.getItemMeta();
		List<String> tierTwoLore = new ArrayList<String>();
		tierTwoMeta.setDisplayName(ChatColor.GRAY + "Tier 2");
		tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Two Drop Party!");
		tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: 5 Tokens");
		tierTwoMeta.setLore(tierTwoLore);
		tierTwo.setItemMeta(tierTwoMeta);
		dropGUI.setItem(12, tierTwo);

		ItemStack tierThree = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta tierThreeMeta = tierThree.getItemMeta();
		List<String> tierThreeLore = new ArrayList<String>();
		tierThreeMeta.setDisplayName(ChatColor.GOLD + "Tier 3");
		tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Three Drop Party!");
		tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: 10 Tokens");
		tierThreeMeta.setLore(tierThreeLore);
		tierThree.setItemMeta(tierThreeMeta);
		dropGUI.setItem(14, tierThree);

		ItemStack tierFour = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta tierFourMeta = tierFour.getItemMeta();
		List<String> tierFourLore = new ArrayList<String>();
		tierFourMeta.setDisplayName(ChatColor.AQUA + "Tier 4");
		tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Four Drop Party!");
		tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "COST: 15 Tokens");
		tierFourMeta.setLore(tierFourLore);
		tierFour.setItemMeta(tierFourMeta);
		dropGUI.setItem(16, tierFour);

		

	}

	public static void updateDisplayWithTokens(Player p) {
		
		ItemStack tokens = new ItemStack(Material.BOOK);
		ItemMeta tokensMeta = tokens.getItemMeta();
		List<String> tokensLore = new ArrayList<String>();
		tokensMeta.setDisplayName(ChatColor.GRAY + "Your Tokens:");
		tokensLore.add(ChatColor.GREEN + ChatColor.ITALIC.toString() + TokenManager.getTokens(p));
		tokensMeta.setLore(tokensLore);
		tokens.setItemMeta(tokensMeta);
		dropGUI.setItem(26, tokens);

	}

	
	 public void setTimer(int amount) {
	        time = amount;
	    }
	
	 public void startTimer() {
	        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	        taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	if(time == 0) {
	            		Bukkit.broadcastMessage("          --DROP PARTY--          ");
	                    Bukkit.broadcastMessage(ChatColor.GREEN + "DROP PARTY HAS BEGUN! USE " + ChatColor.ITALIC + "/warp drop");
	                    Bukkit.broadcastMessage("                                   ");
	                    stopTimer();
	                    return;
	                }
	            	 if(time % 5 == 0) {
	                     Bukkit.broadcastMessage(ChatColor.RED + "Timer remaining " + time + " seconds");
	                 }
	            	
	            	 
	            	time -= 1;
	            }
	        }, 0L, 20L);
	    }
	 public void stopTimer() {
	        Bukkit.getScheduler().cancelTask(taskID);
	    }
	
	@EventHandler
	private void handleClicks(InventoryClickEvent event) {

		if (event.getInventory().getName().contains("Drop Menu")) {

			if (event.getWhoClicked() instanceof Player) {

				Player p = (Player) event.getWhoClicked();

				int slot = event.getSlot();
				event.setCancelled(true);
				switch (slot) {

				case 10:
					if (TokenManager.getTokens(p) >= 1) {
						DropParty.startDropParty(1);
						TokenManager.subtractTokens(p, 1);
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You have started a tier 1 drop party!");
						setTimer(900);
						startTimer();
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.sendMessage("");
							online.sendMessage("");
							online.sendMessage(ChatColor.GREEN + "          TIER 1 DROP PARTY         ");
							online.sendMessage(ChatColor.AQUA + "           BEGINS IN 15 MINUTES");
							online.sendMessage("");
							online.sendMessage("");
						}
					} else {
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You do not have enough tokens for this! Your tokens: " + TokenManager.getTokens(p));
					}
				case 12:
					if (TokenManager.getTokens(p) >= 5) {
						DropParty.startDropParty(2);
						TokenManager.subtractTokens(p, 5);
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You have started a tier 2 drop party!");
						setTimer(900);
						startTimer();
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.sendMessage("");
							online.sendMessage("");
							online.sendMessage(ChatColor.GREEN + "          TIER 2 DROP PARTY         ");
							online.sendMessage(ChatColor.AQUA + "           BEGINS IN 15 MINUTES ");
							online.sendMessage("");
							online.sendMessage("");
						}
					} else {
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You do not have enough tokens for this! Your tokens: " + TokenManager.getTokens(p));
					}
				case 14:
					if (TokenManager.getTokens(p) >= 10) {
						DropParty.startDropParty(3);
						TokenManager.subtractTokens(p, 10);
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You have started a tier 3 drop party!");
						setTimer(900);
						startTimer();
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.sendMessage("");
							online.sendMessage("");
							online.sendMessage(ChatColor.GREEN + "          TIER 3 DROP PARTY         ");
							online.sendMessage(ChatColor.AQUA + "           BEGINS IN 15 MINUTES ");
							online.sendMessage("");
							online.sendMessage("");
						}
					} else {
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You do not have enough tokens for this! Your tokens: " + TokenManager.getTokens(p));
					}
				case 16:
					if (TokenManager.getTokens(p) >= 15) {
						DropParty.startDropParty(4);
						TokenManager.subtractTokens(p, 15);
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You have started a tier 4 drop party!");
						setTimer(900);
						startTimer();
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.sendMessage("");
							online.sendMessage("");
							online.sendMessage(ChatColor.GREEN + "          TIER 4 DROP PARTY         ");
							online.sendMessage(ChatColor.AQUA + "           BEGINS IN 15 MINUTES ");
							online.sendMessage("");
							online.sendMessage("");
						}
						
					} else {
						p.sendMessage(ChatColor.AQUA + "[Drop Party] You do not have enough tokens for this! Your tokens: " + TokenManager.getTokens(p));
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
