package sixpack.xCheat.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.util.c;
import sixpack.xCheat.util.latencyU;
import sixpack.xCheat.util.playerU;

public class bannedPlayer {

	private purge plugin = purge.getInstance();
	private configUser user = new configUser();
	private userManager uMan;
	
	public void addBanInfo(Player p) {
		UUID uuid = p.getUniqueId();
		uMan = plugin.userMangerHashMap.get(p);
		List<Check> checks = plugin.getCheckManager().checks;
		
		user.setupData(uuid);
		user.getData(uuid).set("name", p.getName());
		
		user.getData(uuid).set("ping", playerU.getPing(p));
		user.getData(uuid).set("tps.0", plugin.getServer().spigot().getTPS()[0]);
		user.getData(uuid).set("tps.1", plugin.getServer().spigot().getTPS()[1]);
		user.getData(uuid).set("tps.2", plugin.getServer().spigot().getTPS()[2]);
		user.getData(uuid).set("location.world", p.getLocation().getWorld().getName());
		user.getData(uuid).set("location.x", p.getLocation().getX());
		user.getData(uuid).set("location.y", p.getLocation().getY());
		user.getData(uuid).set("location.z", p.getLocation().getZ());
		user.getData(uuid).set("location.yaw", p.getLocation().getYaw());
		user.getData(uuid).set("location.pitch", p.getLocation().getPitch());
		for(Check check : checks) {
			user.getData(uuid).set("alerts." + check.id , uMan.getViolations(check.name));
		}
		
		user.saveData(uuid);
		Bukkit.broadcastMessage(c.f("&7File for " + p.getName() + " has been created"));
	}
	
	public boolean dateStored(UUID uuid) {
		if(user.dateExists(uuid)) {
			return true;
		}
		return false;
	}

	public void removeBanInfo(Player p) {
		user.deleteData(p.getUniqueId());
		Bukkit.broadcastMessage(c.f("&7File for " + p.getName() + " has been delete"));
	}
	

	
	public Double getTPS (String target, int var){
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		user.setupData(op.getUniqueId());
		return user.getData(op.getUniqueId()).getDouble("tps." + var);		

	}
	
	
	
	public int getPing(String target) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		user.setupData(op.getUniqueId());
		
		return user.getData(op.getUniqueId()).getInt("ping");
	}
	
	public double getLastLocation(String target, String location) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		user.setupData(op.getUniqueId());
		return user.getData(op.getUniqueId()).getDouble("location." + location);
	}
	public String getWorld(String target) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		user.setupData(op.getUniqueId());
		return user.getData(op.getUniqueId()).getString("location.world");
	}
	
	public int getViolations(String target, String id) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		user.setupData(op.getUniqueId());
		return user.getData(op.getUniqueId()).getInt("alerts." + id);
	}
}
