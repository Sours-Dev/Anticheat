package sixpack.xCheat.player;

import java.awt.List;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;



import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import sixpack.xCheat.util.mathU;


public class userManager {



	private String name;
	private UUID uuid;
	private HashMap<String,Integer> Violation;
	private boolean alerts;

	
	public userManager(String name, UUID uuid, HashMap<String,Integer> violations, boolean alerts) {
		this.setName(name);
		this.setUuid(uuid);
		this.setViolation(violations);
		this.setAlerts(alerts);


	}
	
	


	public boolean isAlerts() {
		return alerts;
	}


	public void setAlerts(boolean alerts) {
		this.alerts = alerts;
	}


	public int getViolations(String name) {
		int vio = Violation.get(name);
		return vio;
	}


	public void setViolations(String name, int vio) {
		Violation.put(name, vio);
	}


	public HashMap<String, Integer> getViolation() {
		return Violation;
	}


	public void setViolation(HashMap<String, Integer> violation) {
		Violation = violation;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}


	

}
