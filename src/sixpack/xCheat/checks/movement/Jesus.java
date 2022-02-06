package sixpack.xCheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.playerU;


public class Jesus extends Check{

	public Jesus() {
        super("jesus", "Jesus" , CheckType.MOVEMENT, true, false, 20);
    }

	public Jesus(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
        super("jesus", name, type, enabled, punishable, maxV);
    }
	
	 private Map<UUID, Long> jesusTicks = new HashMap<UUID, Long>();
	
	 
	  @EventHandler
	    public void CheckJesus(PlayerMoveEvent e) {
	        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) {
	            return;
	        }
	        long Time = System.currentTimeMillis();
	        Player p = e.getPlayer();
	        if (p.getAllowFlight()) {
	            return;
	        }
	        if (!p.getNearbyEntities(1.0, 1.0, 1.0).isEmpty()) {
	            return;
	        }
	        if (this.jesusTicks.containsKey(p.getUniqueId())) {
	            Time = this.jesusTicks.get(p.getUniqueId());
	        }
	        long MS = System.currentTimeMillis() - Time;
	        if (playerU.cantStandAtWater(p.getWorld().getBlockAt(p.getLocation())) && playerU.isHoveringOverWater(p.getLocation()) && !playerU.isFullyInWater(p.getLocation())) {
	           
	            if (MS > 500) {
	                flag(p, "Experimental");
	                Time = System.currentTimeMillis();
	            }
	        } else {
	            Time = System.currentTimeMillis();
	        }
	        this.jesusTicks.put(p.getUniqueId(), Time);
	    }
	 
}
