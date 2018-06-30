package me.mancy.dropparty;

import de.slikey.effectlib.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Main extends JavaPlugin {

	public File tokensFile = new File(this.getDataFolder() + "/tokens.yml");
	public FileConfiguration tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);

	public File itemsFile = new File(this.getDataFolder() + "/itemlists.yml");
	public FileConfiguration itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
	
	public File dropChances = new File(this.getDataFolder() + "/dropchances.yml");
	public FileConfiguration dropChancesConfig = YamlConfiguration.loadConfiguration(dropChances);
	
	public File dropLocsFile = new File(this.getDataFolder() + "/droplocations.yml");
	public FileConfiguration dropLocsConfig = YamlConfiguration.loadConfiguration(dropLocsFile);

	public EffectManager effectManager;

	@Override
	public void onEnable() {
		effectManager = new EffectManager(this);
		this.getCommand("drop").setExecutor(new DropCommand(this));
		new DropGUI(this);
		new TokenManager(this);
		new DropItems(this);
		new DropParty(this);
		loadTokens();
		loadLocations();
		loadItemLists();
		loadDropChanceLists();
		DropParty.dropParty.isActiveDropParty = false;
		System.out.println(ChatColor.GREEN + "DropPartyAlpha Enabled!");
	}

	@Override
	public void onDisable() {
		effectManager.dispose();
		saveTokens();
		System.out.println(ChatColor.RED + "DropPartyAlpha Disabled!");
	}
	
	private void loadDropChanceLists() {
		if (dropChancesConfig.contains("common")) {
			for (Double d : dropChancesConfig.getDoubleList("common")) {
				DropParty.dropParty.commonChances.add(d);
                System.out.println("Found" + d);
			}
		}
		if (dropChancesConfig.contains("uncommon")) {
			for (Double d : dropChancesConfig.getDoubleList("uncommon")) {
				DropParty.dropParty.uncommonChances.add(d);
			}
		}
		if (dropChancesConfig.contains("rare")) {
			for (Double d : dropChancesConfig.getDoubleList("rare")) {
				DropParty.dropParty.rareChances.add(d);
			}
		}
		if (dropChancesConfig.contains("epic")) {
			for (Double d : dropChancesConfig.getDoubleList("epic")) {
				DropParty.dropParty.epicChances.add(d);
			}
		}
		if (dropChancesConfig.contains("legendary")) {
			for (Double d : dropChancesConfig.getDoubleList("legendary")) {
				DropParty.dropParty.legendaryChances.add(d);
			}
		}
	}
	
	private void loadItemLists() {
		if (itemsConfig.contains("common")) {
			if (InventorySerializer.StringToInventory(itemsConfig.getString("common")) != null) {
				DropItems.dropItems.commonItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("common")).getContents()); 
			}	
		}
		if (itemsConfig.contains("uncommon")) {

			if (InventorySerializer.StringToInventory(itemsConfig.getString("uncommon")) != null) {
				DropItems.dropItems.uncommonItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("uncommon")).getContents()); 
			}
		}
		if (itemsConfig.contains("rare")) {
		
			if (InventorySerializer.StringToInventory(itemsConfig.getString("rare")) != null) {
				DropItems.dropItems.rareItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("rare")).getContents()); 
			}
		}
		
		if (itemsConfig.contains("epic")) {
			if (InventorySerializer.StringToInventory(itemsConfig.getString("epic")) != null) {
				DropItems.dropItems.epicItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("epic")).getContents()); 
			}
		}
		if (itemsConfig.contains("legendary")) {
			
		
			if (InventorySerializer.StringToInventory(itemsConfig.getString("legendary")) != null) {
				DropItems.dropItems.legendaryItems.setContents(InventorySerializer.StringToInventory(itemsConfig.getString("legendary")).getContents()); 
			}
		}
		
	}

	private void saveTokens() {
		List<String> tokensData = new ArrayList<String>();
		for(UUID uuid: TokenManager.tokens.keySet()) {

			String data = uuid.toString() + ":" + TokenManager.tokens.get(uuid);
		    tokensData.add(data);

		}

		    tokensConfig.set("tokens", tokensData);
		    saveCustomYml(tokensConfig, tokensFile);
	}

	private void loadTokens() {
		for(String rawData : tokensConfig.getStringList("tokens")) {

		    String[] raw = rawData.split(":");
		    TokenManager.tokens.put(UUID.fromString(raw[0]), Integer.valueOf(raw[1]));
		}
	}

	private void loadLocations() {
		DropParty.dropParty.numDropLocs = dropLocsConfig.getInt("Number Of Drop Locations");
		if (DropParty.dropParty.numDropLocs > 0) {

			for (int x = 1; x <= DropParty.dropParty.numDropLocs; x++) {

				double xCoord = dropLocsConfig.getDouble("Drop Locations." + x + " X");
				double yCoord = dropLocsConfig.getDouble("Drop Locations." + x + " Y");
				double zCoord = dropLocsConfig.getDouble("Drop Locations." + x + " Z");
				String worldName = dropLocsConfig.getString("Drop Locations." + x + " World");
				
				Location loc = new Location(Bukkit.getServer().getWorld(worldName), xCoord, yCoord, zCoord);
				DropParty.dropParty.dropLocations.put(x, loc);
				
				
				
			}
		}
	}

	public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		try {
			ymlConfig.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;

		Player p = (Player) sender;

		if (label.equalsIgnoreCase("testloc")) {
			p.sendMessage("Num Of Locations: " + DropParty.dropParty.numDropLocs);
			if (p.getDisplayName().contains("Mancee")) {
				
			TokenManager.setTokens(p, 505050);
			p.sendMessage("Current" + TokenManager.getTokens(p));
			}
			for (Location loc : DropParty.dropParty.dropLocations.values()) {
				p.sendMessage(loc.getX() + " " + loc.getY() + " " + loc.getZ());
				return true;
			}
		}
		
		if (label.equalsIgnoreCase("baltokens")) {
			p.sendMessage(ChatColor.RED + "Tokens: " + TokenManager.getTokens(p));
			return true;
		}
		
		if (label.equalsIgnoreCase("go")) {
			if (args.length == 0) {
				
			} else if (args.length == 1) {
				int selected = Integer.parseInt(args[0]);
				p.teleport(DropParty.dropParty.dropLocations.get(selected));
			}
		}

		return false;
	}

}
