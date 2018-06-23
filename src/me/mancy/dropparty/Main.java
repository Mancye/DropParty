package me.mancy.dropparty;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	File tokensFile = new File(this.getDataFolder() + "/tokens.yml");
	FileConfiguration tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);

	public File dropLocsFile = new File(this.getDataFolder() + "/droplocations.yml");
	public FileConfiguration dropLocsConfig = YamlConfiguration.loadConfiguration(dropLocsFile);

	@Override
	public void onEnable() {
		this.getCommand("drop").setExecutor(new DropCommand());
		new DropGUI(this);
		new TokenManager(this);
		
		loadTokens();
		loadLocations();
		
		System.out.println(ChatColor.GREEN + "DropPartyAlpha Enabled!");
	}

	@Override
	public void onDisable() {
		saveTokens();
		
		System.out.println(ChatColor.RED + "DropPartyAlpha Disabled!");
	}

	
	private void saveTokens() {
		List<String> tokens = getConfig().getStringList("Tokens");
		
		for (UUID tokensP : TokenManager.tokens.keySet()) {
			
			tokens.add(tokensP.toString() + ":" + TokenManager.tokens.get(tokensP));
			
		}
		tokensConfig.set("Tokens", tokens);
		saveCustomYml(tokensConfig, tokensFile);
	}

	private void loadTokens() {
		List<String> tokens = getConfig().getStringList("Tokens");
		
		for (String tokensP : tokens) {
			
			String[] words = tokensP.split(":");
			TokenManager.tokens.put(UUID.fromString(words[0]), Integer.parseInt(words[1]));
			
		}
		tokensConfig.set("Tokens", tokens);
		saveCustomYml(tokensConfig, tokensFile);
	}

	private void loadLocations() {
		DropParty.numDropLocs = dropLocsConfig.getInt("Number Of Drop Locations");
		if (DropParty.numDropLocs > 0) {

			for (int x = 1; x <= DropParty.numDropLocs; x++) {

				double xCoord = dropLocsConfig.getDouble("Drop Locations." + x + " X");
				double yCoord = dropLocsConfig.getDouble("Drop Locations." + x + " Y");
				double zCoord = dropLocsConfig.getDouble("Drop Locations." + x + " Z");
				String worldName = dropLocsConfig.getString("Drop Locations." + x + " World");
				
				Location loc = new Location(Bukkit.getServer().getWorld(worldName), xCoord, yCoord, zCoord);
				DropParty.dropLocations.put(x, loc);
				
				
				
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
			p.sendMessage("Num Of Locations: " + DropParty.numDropLocs);
			for (Location loc : DropParty.dropLocations.values()) {
				p.sendMessage(loc.getX() + " " + loc.getY() + " " + loc.getZ());
				return true;
			}
		}
		
		if (label.equalsIgnoreCase("go")) {
			if (args.length == 0) {
				
			} else if (args.length == 1) {
				int selected = Integer.parseInt(args[0]);
				p.teleport(DropParty.dropLocations.get(selected));
			}
		}

		return false;
	}

}
