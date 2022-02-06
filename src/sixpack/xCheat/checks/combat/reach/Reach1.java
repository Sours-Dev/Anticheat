package sixpack.xCheat.checks.combat.reach;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.latencyU;
import sixpack.xCheat.util.mathU;
import sixpack.xCheat.util.playerU;
import sixpack.xCheat.util.timeU;
import sixpack.xCheat.util.packets.events.PacketUseEntityEvent;


public class Reach1 extends Check{
    private Map<Player, Map.Entry<Double, Double>> offsets;
    private Map<Player, Long> reachTicks;
    private ArrayList<Player> projectileHit;
    private purge anticheat = purge.getInstance();
	
	public Reach1() {
		super("reach1", "Reach (1)", CheckType.COMBAT, true, false, 10);
	}
	
	public Reach1(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("reach1", name, type, enabled, punishable, maxV);
	    this.offsets = new HashMap<>();
        this.reachTicks = new HashMap<>();
        this.projectileHit = new ArrayList<>();
	}

	
	 @EventHandler
	    public void onMove(PlayerMoveEvent event) {
	        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) return;
	        double OffsetXZ = mathU.offset(mathU.getHorizontalVector(event.getFrom().toVector()),
	        		mathU.getHorizontalVector(event.getTo().toVector()));
	        double horizontal = Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2.0)
	                + Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2.0));
	        this.offsets.put(event.getPlayer(),
	                new AbstractMap.SimpleEntry<>(OffsetXZ, horizontal));
	    }

	    @EventHandler
	    public void onDmg(EntityDamageByEntityEvent e) {
	        if (!(e.getDamager() instanceof Player)
	                || e.getCause() != DamageCause.PROJECTILE) return;

	        Player player = (Player) e.getDamager();

	        this.projectileHit.add(player);
	    }

	    @EventHandler(priority = EventPriority.MONITOR)
	    public void onLogout(PlayerQuitEvent e) {
	        offsets.remove(e.getPlayer());
	        reachTicks.remove(e.getPlayer());
	        projectileHit.remove(e.getPlayer());
	    }

	    @EventHandler
	    public void onDamage(PacketUseEntityEvent e) {

	    	if(!enabled) {
	    		return;
	    	}
	    	
	        if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
	                || !(e.getAttacked() instanceof Player)
	                || e.getAttacker().getAllowFlight() || !(e.getAttacker() instanceof Player)) return;

	        Player damager = e.getAttacker();
	        Player player = (Player) e.getAttacked();
	        double ydist = Math.abs(damager.getEyeLocation().getY() - player.getEyeLocation().getY());
	        double Reach = mathU.trim(2,
	                (playerU.getEyeLocation(damager).distance(player.getEyeLocation()) - ydist) - 0.32);
	        int PingD = playerU.getPing(damager);
	        int PingP = playerU.getPing(player);

	        long attacktimeU = System.currentTimeMillis();
	        if (this.reachTicks.containsKey(damager)) {
	            attacktimeU = reachTicks.get(damager);
	        }

	        double yawdif = Math.abs(180 - Math.abs(damager.getLocation().getYaw() - player.getLocation().getYaw()));
	        if (latencyU.getLag(damager) > 92 || latencyU.getLag(player) > 92) return;
	        double offsetsp = 0.0D;
	        double lastHorizontal = 0.0D;
	        double offsetsd = 0.0D;
	        if (this.offsets.containsKey(damager)) {
	            offsetsd = (this.offsets.get(damager)).getKey();
	            lastHorizontal = (this.offsets.get(damager)).getValue();
	        }
	        if (this.offsets.containsKey(player)) {
	            offsetsp = (this.offsets.get(player)).getKey();
	            lastHorizontal = (this.offsets.get(player)).getValue();
	        }
	        Reach -= mathU.trim(2, offsetsd);
	        Reach -= mathU.trim(2, offsetsp);
	        double maxReach2 = 3.1;
	        if (yawdif > 90) {
	            maxReach2 += 0.38;
	        }
	        maxReach2 += lastHorizontal * 0.87;

	        maxReach2 += ((PingD + PingP) / 2) * 0.0024;
	        if (Reach > maxReach2 && timeU.elapsed(attacktimeU, 1100) && !projectileHit.contains(player)) {
	            String c = "Possible";
	            if ((Reach - maxReach2) > 0.4) {
	                c = "Definitely";
	            }
	            String diff = Reach + ">" + mathU.trim(2, maxReach2);
	            
	            flag(damager, diff);
	        
	        }
	        reachTicks.put(damager, timeU.nowlong());
	        projectileHit.remove(player);
	    }
}
