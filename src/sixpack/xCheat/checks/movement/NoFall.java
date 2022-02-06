package sixpack.xCheat.checks.movement;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.playerU;
import sixpack.xCheat.util.timeU;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class NoFall extends Check{

	public NoFall() {
        super("nofall", "NoFall" , CheckType.MOVEMENT, true, false, 20);
    }

	public NoFall(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
        super("nofall", name, type, enabled, punishable, maxV);
    }
	 private Map<UUID, Map.Entry<Long, Integer>> NoFallTicks = new HashMap<UUID, Map.Entry<Long, Integer>>();
	 private Map<UUID, Double> FallDistance = new HashMap<UUID, Double>();
	    
	    
	    
	    @EventHandler
	    public void Move(PlayerMoveEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
	        Player player = e.getPlayer();
	        if (player.getAllowFlight()) {
	            return;
	        }
	        if (player.getGameMode().equals((Object)GameMode.CREATIVE)) {
	            return;
	        }
	        if (player.getVehicle() != null) {
	            return;
	        }
	        if (player.getHealth() <= 0.0) {
	            return;
	        }
	        if (playerU.isOnClimbable(player)) {
	            return;
	        }
	        if (playerU.isInWater(player)) {
	            return;
	        }
	        double Falling = 0.0;
	        if (!playerU.isOnGround(player) && e.getFrom().getY() > e.getTo().getY()) {
	            if (this.FallDistance.containsKey(player.getUniqueId())) {
	                Falling = this.FallDistance.get(player.getUniqueId());
	            }
	            Falling += e.getFrom().getY() - e.getTo().getY();
	        }
	        this.FallDistance.put(player.getUniqueId(), Falling);
	        if (Falling < 3.0) {
	            return;
	        }
	        long Time = System.currentTimeMillis();
	        int Count = 0;
	        if (this.NoFallTicks.containsKey(player.getUniqueId())) {
	            Time = this.NoFallTicks.get(player.getUniqueId()).getKey();
	            Count = this.NoFallTicks.get(player.getUniqueId()).getValue();
	        }
	        if (player.isOnGround() || player.getFallDistance() == 0.0f) {
	            ++Count;
	        } else {

	            Count = 0;
	        }
	        if (this.NoFallTicks.containsKey(player.getUniqueId()) && timeU.elapsed(Time, 10000)) {

	            Count = 0;
	            Time = System.currentTimeMillis();
	        }
	        if (Count >= 3) {

	            Count = 0;
	            this.FallDistance.put(player.getUniqueId(), 0.0);
	            flag(e.getPlayer(), null);
	        }
	        this.NoFallTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Long, Integer>(Time, Count));
	    }

	
}
