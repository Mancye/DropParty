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

public class DropGUI implements Listener {

	public static Inventory getDropGUI() {
		Inventory dropGUI = Bukkit.createInventory(null, 27, ChatColor.RED + "Drop Menu");
		
		ItemStack tierOne = new ItemStack(Material.COAL_ORE);
		ItemMeta tierOneMeta = tierOne.getItemMeta();
		List<String> tierOneLore = new ArrayList<String>();
		tierOneMeta.setDisplayName(ChatColor.DARK_GRAY + "Tier 1");
		tierOneLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier One Drop Party!");
		tierOneMeta.setLore(tierOneLore);
		tierOne.setItemMeta(tierOneMeta);
		dropGUI.setItem(10, tierOne);
		
		ItemStack tierTwo = new ItemStack(Material.IRON_BLOCK);
		ItemMeta tierTwoMeta = tierTwo.getItemMeta();
		List<String> tierTwoLore = new ArrayList<String>();
		tierTwoMeta.setDisplayName(ChatColor.GRAY + "Tier 2");
		tierTwoLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Two Drop Party!");
		tierTwoMeta.setLore(tierTwoLore);
		tierTwo.setItemMeta(tierTwoMeta);
		dropGUI.setItem(12, tierTwo);
		
		ItemStack tierThree = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta tierThreeMeta = tierThree.getItemMeta();
		List<String> tierThreeLore = new ArrayList<String>();
		tierThreeMeta.setDisplayName(ChatColor.GOLD + "Tier 3");
		tierThreeLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Three Drop Party!");
		tierThreeMeta.setLore(tierThreeLore);
		tierThree.setItemMeta(tierThreeMeta);
		dropGUI.setItem(14, tierThree);
		
		ItemStack tierFour = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta tierFourMeta = tierFour.getItemMeta();
		List<String> tierFourLore = new ArrayList<String>();
		tierFourMeta.setDisplayName(ChatColor.AQUA + "Tier 4");
		tierFourLore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Click To Start A Tier Four Drop Party!");
		tierFourMeta.setLore(tierFourLore);
		tierFour.setItemMeta(tierFourMeta);
		dropGUI.setItem(16, tierFour);
		
		return dropGUI;
	}
	
	@EventHandler
	private void handleClicks(InventoryClickEvent event) {
		
		if (event.getInventory().getName().contains("Drop Menu")) {
			
			if (event.getWhoClicked() instanceof Player) {
				
				Player p = (Player) event.getWhoClicked();
				
				int slot = event.getSlot();
				
				switch (slot) {
				
				case 10:
					
				
				
				}
				
			}
			
			event.setCancelled(true);
		} 
		
	}
	
	
}
