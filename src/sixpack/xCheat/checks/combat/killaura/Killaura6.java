package sixpack.xCheat.checks.combat.killaura;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.event.entity.EntityDamageEvent;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;

public class Killaura6 extends Check{

	public Killaura6() {
		super("killaura6", "KillAura (6)", CheckType.COMBAT, true, false, 10);
	}
	
	public Killaura6(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("killaura6", name, type, enabled, punishable, maxV);
		   lastAttack = new HashMap<>();
	}
	 public static Map<Player, Map.Entry<Integer, Long>> lastAttack;
	 
	    @EventHandler
	    public void onLog(PlayerQuitEvent e) {
	        lastAttack.remove(e.getPlayer());
	    }

	    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	    public void Damage(EntityDamageByEntityEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
	        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK
	                || !((e.getEntity()) instanceof Player)
	                || !(e.getDamager() instanceof Player)) return;

	        Player player = (Player) e.getDamager();
	        if (lastAttack.containsKey(player)) {
	            Integer entityid = lastAttack.get(player).getKey();
	            Long time = lastAttack.get(player).getValue();
	            if (entityid != e.getEntity().getEntityId() && System.currentTimeMillis() - time < 6L) {
	                flag(player, "MultiAura");
	            }
	            lastAttack.remove(player);
	        } else {
	            lastAttack.put(player, new AbstractMap.SimpleEntry<>(e.getEntity().getEntityId(),
	                    System.currentTimeMillis()));
	        }
	    }
	 
}
