package sixpack.xCheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.latencyU;
import sixpack.xCheat.util.mathU;
import sixpack.xCheat.util.playerU;



public class Fly2 extends Check{

	  public static Map<UUID, Long> flyTicksA;
		public Fly2() {
			super("fly2", "Fly (2)", CheckType.MOVEMENT, true, false, 10);
		}
		
		public Fly2(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
			super("fly2", name, type, enabled, punishable, maxV);
			flyTicksA = new HashMap<>();
		}
	 
	    @EventHandler
	    public void onLog(PlayerQuitEvent e) {
	        Player p = e.getPlayer();
	        UUID uuid = p.getUniqueId();

	        flyTicksA.remove(uuid);
	    }

	    @EventHandler
	    public void CheckFlyB(PlayerMoveEvent event) {
	    	if(!enabled) {
	    		return;
	    	}
	        Player player = event.getPlayer();

	        /** False positive/optimization check **/
	        if (event.isCancelled()
	                || (event.getTo().getX() == event.getFrom().getX()) && (event.getTo().getZ() == event.getFrom().getZ())
	                || player.getAllowFlight()
	                || player.getVehicle() != null
	                || playerU.isInWater(player)
	                || playerU.isInWeb(player)
	                || latencyU.getLag(player) > 92) return;

	        if (playerU.blocksNear(player.getLocation())) {
	            flyTicksA.remove(player.getUniqueId());
	            return;
	        }
	        if (Math.abs(event.getTo().getY() - event.getFrom().getY()) > 0.06) {
	            flyTicksA.remove(player.getUniqueId());
	            return;
	        }

	        long Time = System.currentTimeMillis();
	        if (flyTicksA.containsKey(player.getUniqueId())) {
	            Time = flyTicksA.get(player.getUniqueId());
	        }
	        long MS = System.currentTimeMillis() - Time;
	        if (MS > 200L) {
	        	String notes = "H:" + mathU.trim(1, (double) (MS / 1000)) + " second(s)";
	        	flag(player, null);
	            flyTicksA.remove(player.getUniqueId());
	            return;
	        }
	        flyTicksA.put(player.getUniqueId(), Time);
	    }
	
}
