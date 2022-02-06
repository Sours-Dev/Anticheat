package sixpack.xCheat.checks.movement;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.player.DataPlayer;
import sixpack.xCheat.util.mathU;
import sixpack.xCheat.util.playerU;



public class Speed1 extends Check{

	public Speed1() {
        super("speed1", "Speed (1)" , CheckType.MOVEMENT, true, false, 20);
    }

	public Speed1(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
        super("speed1", name, type, enabled, punishable, maxV);
    }
	

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
    	
    	
    	if(!enabled) {
    		return;
    	}
    	
    	
    	
        DataPlayer data = purge.getInstance().getDataManager().getDataPlayer(e.getPlayer());
        Player p = e.getPlayer();
        
        if (data == null) {
            return;
        }


        if(!playerU.isOnGround(p)) {
        	return;
        }
        
        if(p.isFlying()) {
        	return;
        }
        
        
        if(p.getWalkSpeed() > 0.20000001) {
        	return;
        }
        

        
        
        if(!(p.getGameMode() == GameMode.SURVIVAL)) {
        	return;
        }
        
        float xDelta = mathU.getHorizontalDistance(e.getFrom(), e.getTo());
        float threshold = (float) (data.airTicks > 0 ? data.airTicks > 6 ? 0.318 : 0.338 : data.groundTicks > 5 ? 0.287 : 0.32);

        threshold += playerU.getPotionEffectLevel(data.p, PotionEffectType.SPEED) * 0.10;
        threshold += data.onIce ? data.underBlock ? 0.5f : .2f : 0f;
        threshold += data.underBlock ? 0.4f : 0f;
        threshold += data.onStairSlab ? 0.2f : 0f;

        if (xDelta > threshold) {
            if ((data.speedThreshold += 2) > 40) {
            	// xDelta > threshold
                flag(data.p, null);
            }
        } else if (data.speedThreshold > 0) {
            data.speedThreshold--;
        }

     

    }
	
}
