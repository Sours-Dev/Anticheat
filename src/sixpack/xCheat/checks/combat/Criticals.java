package sixpack.xCheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.playerU;
import sixpack.xCheat.util.timeU;




public class Criticals extends Check{
	// not work
	
	public Criticals() {
		super("criticals", "Criticals", CheckType.COMBAT, true, false, 10);
	}
	
	public Criticals(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("criticals", name, type, enabled, punishable, maxV);
	}
	
	private Map<UUID, Map.Entry<Integer, Long>> CritTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	private Map<UUID, Double> FallDistance = new HashMap<UUID, Double>();
	

	 @EventHandler
	    public void onDamage(EntityDamageByEntityEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
	        if (!(e.getDamager() instanceof Player)) {
	            return;
	        }
	        if (!e.getCause().equals((Object)EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
	            return;
	        }
	        Player p = (Player)e.getDamager();
	        if (p.getAllowFlight()) {
	            return;
	        }

	        if (playerU.slabsNear(p.getLocation())) {
	            return;
	        }
	        Location pL = p.getLocation().clone();
	        pL.add(0.0, p.getEyeHeight() + 1.0, 0.0);
	        if (playerU.blocksNear(pL)) {
	            return;
	        }
	        int Count = 0;
	        long Time = System.currentTimeMillis();
	        if (this.CritTicks.containsKey(p.getUniqueId())) {
	            Count = this.CritTicks.get(p.getUniqueId()).getKey();
	            Time = this.CritTicks.get(p.getUniqueId()).getValue();
	        }
	        if (!this.FallDistance.containsKey(p.getUniqueId())) {
	            return;
	        }
	        double realFallDistance = this.FallDistance.get(p.getUniqueId());
	        Count = (double)p.getFallDistance() > 0.0 && !p.isOnGround() && realFallDistance == 0.0 ? ++Count : 0;
	        if (this.CritTicks.containsKey(p.getUniqueId()) && timeU.elapsed(Time, 10000)) {
	            Count = 0;
	            Time = timeU.nowlong();
	        }
	        if (Count >= 2) {
	            Count = 0;
	            flag(p, null);
	        }	   
	        this.CritTicks.put(p.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	    }
	 
		@EventHandler
	    public void Move(PlayerMoveEvent e) {
	        Player p2 = e.getPlayer();
	        double Falling = 0.0;
	        if (!p2.isOnGround() && e.getFrom().getY() > e.getTo().getY()) {
	            if (this.FallDistance.containsKey(p2.getUniqueId())) {
	                Falling = this.FallDistance.get(p2.getUniqueId());
	            }
	            Falling += e.getFrom().getY() - e.getTo().getY();
	        }
	        this.FallDistance.put(p2.getUniqueId(), Falling);
	    }
	
}
