package me.mancy.dropparty;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	File tokensFile = new File(this.getDataFolder()+"/tokens.yml");
	FileConfiguration tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);
	
	public static File dropLocsFile = new File(this.getDataFolder()+"/droplocations.yml");
	public static FileConfiguration dropLocsConfig = YamlConfiguration.loadConfiguration(dropLocsFile);
	
	@Override
	public void onEnable() {
		this.getCommand("drop").setExecutor(new DropCommand());
		new DropGUI(this);
		
		loadTokens();
		
		System.out.println(ChatColor.GREEN + "DropPartyAlpha Enabled!");
	}
	
	@Override
	public void onDisable() {
		saveTokens();
		System.out.println(ChatColor.RED + "DropPartyAlpha Disabled!");
	}
	
	public void saveTokens() {
		 List<String> tokens = getConfig().getStringList("tokens");
			
			for (UUID player : TokenManager.tokens.keySet()) {
				
				if (!(tokens.contains(player.toString()))) {
					tokens.add(player.toString() + ":" +  TokenManager.tokens.get(player));
				} else {
					tokens.remove(player.toString());
					tokens.add(player.toString() + ":" + TokenManager.tokens.get(player));
				}
				
			}
			tokensConfig.set("levels", tokens);
			saveCustomYml(tokensConfig, tokensFile);
	}
	 
	public void loadTokens() {
		List<String> tokens = getConfig().getStringList("tokens");
		
		for (String amount : tokens) {
			
			String[] words = amount.split(":");
			TokenManager.tokens.put(UUID.fromString(words[0]), Integer.parseInt(words[1]));
			
			
		}
		tokensConfig.set("tokens", tokens);
		saveCustomYml(tokensConfig, tokensFile);
	}
	
	private void loadLocations() {
		DropParty.numDropLocs = dropLocsConfig.getInt("Number Of Drop Locations");
		
		for (int x = 0; x <= DropParty.numDropLocs; x++) {
			
			
			double xCoord = dropLocsConfig.getDouble("Drop Locations." + x + ". X");
			double yCoord = dropLocsConfig.getDouble("Drop Locations." + x + ". Y");
			double zCoord = dropLocsConfig.getDouble("Drop Locations." + x + ". Z");
			String worldName = dropLocsConfig.getString("Drop Locations." + x + ". World");
			
			Location loc = new Location(Bukkit.getServer().getWorld(worldName), zCoord, zCoord, zCoord);
			DropParty.dropLocations.put(x, loc);
		}
		
	}
	


	
	 public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		 try {
			 ymlConfig.save(ymlFile);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	
}
