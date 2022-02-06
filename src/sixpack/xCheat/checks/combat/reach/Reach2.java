package sixpack.xCheat.checks.combat.reach;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.util.mathU;
import sixpack.xCheat.util.playerU;
;

public class Reach2 extends Check{

	public Reach2() {
		super("reach2","Reach (2)", CheckType.COMBAT, true, false, 10);
	}
	
	public Reach2(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("reach2", name, type, enabled, punishable, maxV);
	}
	
	 @EventHandler
	  public void onATTACK(EntityDamageByEntityEvent e) {
	    	if(!enabled) {
	    		return;
	    	}
	        if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
	                || !(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;

	        Player player = (Player) e.getDamager();
	        Player damaged = (Player) e.getEntity();


	        double YawDifference = Math.abs(180 - Math.abs(damaged.getLocation().getYaw() - player.getLocation().getYaw()));
	        double Difference = playerU.getEyeLocation(player).distance(damaged.getEyeLocation()) - 0.35;

	        int Ping = playerU.getPing(player);
	        double MaxReach = 4.0 + damaged.getVelocity().length();

	        if (player.isSprinting()) {
	            MaxReach += 0.2;
	        }

	        if (player.getLocation().getY() > damaged.getLocation().getY()) {
	            Difference = player.getLocation().getY() - player.getLocation().getY();
	            MaxReach += Difference / 2.5;
	        } else if (player.getLocation().getY() > player.getLocation().getY()) {
	            Difference = player.getLocation().getY() - player.getLocation().getY();
	            MaxReach += Difference / 2.5;
	        }
	        for (PotionEffect effect : player.getActivePotionEffects()) {
	            if (effect.getType().equals(PotionEffectType.SPEED)) {
	                MaxReach += 0.2D * (effect.getAmplifier() + 1);
	            }
	        }

	        double velocity = player.getVelocity().length() + damaged.getVelocity().length();

	        MaxReach += velocity * 1.5;
	        MaxReach += Ping < 250 ? Ping * 0.00212 : Ping * 0.031;
	        MaxReach += YawDifference * 0.008;

	        double ChanceVal = Math.round(Math.abs((Difference - MaxReach) * 100));

	        if (ChanceVal > 100) {
	            ChanceVal = 100;
	        }

	        if (MaxReach < Difference) {
	        	String c = "Possible";
	        	
	         
	            if (ChanceVal >= 60) {
	               c = "Definitely";
	            }
	            String notes = "Experminental [" + mathU.trim(2, Difference) + " > " + mathU.trim(2, MaxReach) + ", Ping: " + Ping + ", C: " +  c;
	            String diff = Difference + ">" + mathU.trim(2, MaxReach);
	            
	            flag(player, diff);
	        }
	    }
}
