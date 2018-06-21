package me.mancy.dropparty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class TokenManager extends BukkitRunnable implements Listener {

	private Main plugin;
	
	public Map<UUID, Integer> coins = new HashMap<UUID, Integer>();
	
	public TokenManager(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	private void setDefaultBalance(PlayerJoinEvent event) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				Player p = event.getPlayer();
				
				if (!coins.containsKey(p.getUniqueId())) {
					
				}
				
			}
			
		}, 20L);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
