package sixpack.xCheat.checks.combat.reach;

import java.util.AbstractMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.latencyU;
import sixpack.xCheat.util.mathU;
import sixpack.xCheat.util.playerU;
import sixpack.xCheat.util.timeU;



public class Reach3 extends Check{
	
    public Map<Player, Integer> count;
    public Map<Player, Map.Entry<Double, Double>> offsets;
    
    private purge anticheat = purge.getInstance();
    
	public Reach3() {
		super("reach3", "Reach (3)", CheckType.COMBAT, true, false, 10);
	}
	
	public Reach3(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("reach3", name, type, enabled, punishable, maxV);
		offsets = new WeakHashMap<>();
        count = new WeakHashMap<>();
	}
	
	
	 @EventHandler
	  public void onMove(PlayerMoveEvent event) {
	        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) return;
	        double OffsetXZ = mathU.offset(mathU.getHorizontalVector(event.getFrom().toVector()),
	        		mathU.getHorizontalVector(event.getTo().toVector()));
	        double horizontal = Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2.0)
	                + Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2.0));
	        offsets.put(event.getPlayer(),
	                new AbstractMap.SimpleEntry<>(OffsetXZ, horizontal));
	  }

	 @EventHandler(priority = EventPriority.HIGH)
	    public void onDamage(EntityDamageByEntityEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
		 if (!(e.getDamager() instanceof Player)
	                || !(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
	        Player damager = (Player) e.getDamager();
	        Player player = (Player) e.getEntity();
	        double Reach = mathU.trim(2, playerU.getEyeLocation(damager).distance(player.getEyeLocation()) - 0.32);
	        double Reach2 = mathU.trim(2, playerU.getEyeLocation(damager).distance(player.getEyeLocation()) - 0.32);

	        double Difference;

	        if (damager.getAllowFlight()
	                || player.getAllowFlight()) return;

	        if (!count.containsKey(damager)) {
	            count.put(damager, 0);
	        }

	        int Count = count.get(damager);
	        long Time = System.currentTimeMillis();
	        double MaxReach = 3.1;
	        double YawDifference = Math.abs(damager.getEyeLocation().getYaw() - player.getEyeLocation().getYaw());
	        double speedToVelocityDif = 0;
	        double offsets = 0.0D;

	        double lastHorizontal = 0.0D;
	        if (this.offsets.containsKey(damager)) {
	            offsets = (this.offsets.get(damager)).getKey();
	            lastHorizontal = (this.offsets.get(damager)).getValue();
	        }
	        if (latencyU.getLag(damager) > 92 || latencyU.getLag(player) > 92) return;
	        speedToVelocityDif = Math.abs(offsets - player.getVelocity().length());
	        MaxReach += (YawDifference * 0.001);
	        MaxReach += lastHorizontal * 1.5;
	        MaxReach += speedToVelocityDif * 0.08;
	        if (damager.getLocation().getY() > player.getLocation().getY()) {
	            Difference = damager.getLocation().getY() - player.getLocation().getY();
	            MaxReach += Difference / 2.5;
	        } else if (player.getLocation().getY() > damager.getLocation().getY()) {
	            Difference = player.getLocation().getY() - damager.getLocation().getY();
	            MaxReach += Difference / 2.5;
	        }
	        MaxReach += damager.getWalkSpeed() <= 0.2 ? 0 : damager.getWalkSpeed() - 0.2;

	        int PingD = playerU.getPing(damager);
	        int PingP = playerU.getPing(player);
	        MaxReach += ((PingD + PingP) / 2) * 0.0024;
	        if (PingD > 400) {
	            MaxReach += 1.0D;
	        }
	        if (timeU.elapsed(Time, 10000)) {
	            count.remove(damager);
	            Time = System.currentTimeMillis();
	        }
	        if (Reach > MaxReach) {
	            count.put(damager, Count + 1);
	        } else {
	            if (Count >= -2) {
	                count.put(damager, Count - 1);
	            }
	        }
	        if (Reach2 > 6) {
	            e.setCancelled(true);
	        }
	        if (Count >= 2 && Reach > MaxReach && Reach < 20.0) {
	            count.remove(damager);
	            if (latencyU.getLag(player) < 115) {
	            	String diff = Reach + ">" + mathU.trim(2, MaxReach);
	                flag(damager, diff);
	            }

	            return;
	        }
	    } 
	
}
