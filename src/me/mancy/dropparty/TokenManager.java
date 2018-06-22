package me.mancy.dropparty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class TokenManager implements Listener, Runnable {

	private Main plugin;
	
	public static Map<UUID, Integer> tokens = new HashMap<UUID, Integer>();
	
	public TokenManager(Main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	private void setDefaultBalance(PlayerJoinEvent event) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				Player p = event.getPlayer();
				
				if (!tokens.containsKey(p.getUniqueId())) {
				  setTokens(p, 0);
				}
				
			}
			
		}, 20L);
	}
	
	public static int getTokens(Player p) {
		if (tokens.containsKey(p.getUniqueId())) {
			return tokens.get(p.getUniqueId());
		}
		return -1;
	}
	
	public static void addTokens(Player p, int amount) {
		if (tokens.containsKey(p.getUniqueId())) {
			tokens.put(p.getUniqueId(), getTokens(p) + amount);
		}
	}
	
	public static void setTokens(Player p, int amount) {
		if (tokens.containsKey(p.getUniqueId())) {
			tokens.remove(p.getUniqueId());
			tokens.put(p.getUniqueId(), amount);
		}
	}
	
	public static void subtractTokens(Player p, int amount) {
		if (tokens.containsKey(p.getUniqueId())) {
			tokens.remove(p.getUniqueId());
			tokens.put(p.getUniqueId(), getTokens(p) - amount);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
