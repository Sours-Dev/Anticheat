package sixpack.xCheat.checks.combat.killaura;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
;


public class Killaura2 extends Check{

	public Killaura2() {
		super("killaura2", "KillAura (2)", CheckType.COMBAT, true, false, 10);
	}
	
	public Killaura2(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("killaura2", name, type, enabled, punishable, maxV);
	}
	
	 private Map<Player, Map.Entry<Integer, Long>> lastAttack = new HashMap<Player, Map.Entry<Integer, Long>>();
	
	   @EventHandler
	    public void Damage(EntityDamageByEntityEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
	    	
	        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
	            return;
	        }
	        if (!(e.getDamager() instanceof Player)) {
	            return;
	        }
	        Player p = (Player)e.getDamager();
	        if (this.lastAttack.containsKey((Object)p)) {
	            Integer entityid = this.lastAttack.get((Object)p).getKey();
	            Long time = this.lastAttack.get((Object)p).getValue();
	            if (entityid.intValue() != e.getEntity().getEntityId() && System.currentTimeMillis() - time < 5) {
	                flag(p, "Muti (Experimental)");	               
	            }
	            this.lastAttack.remove((Object)p);
	        } else {
	            this.lastAttack.put(p, new AbstractMap.SimpleEntry<Integer, Long>(e.getEntity().getEntityId(), System.currentTimeMillis()));
	        }
	    }
	 
}
