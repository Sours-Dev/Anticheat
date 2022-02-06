package sixpack.xCheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.player.DataManager;
import sixpack.xCheat.player.DataPlayer;
import sixpack.xCheat.player.userManager;
import sixpack.xCheat.util.playerU;



public class Fly1 extends Check{

	public Fly1() {
		super("fly1", "Fly (1)", CheckType.MOVEMENT, true, false, 10);
	}
	
	public Fly1(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
		super("fly1", name, type, enabled, punishable, maxV);
	}
	
	private Map<UUID, Long> flyTicks = new HashMap<UUID, Long>();
	
	
	    @EventHandler(priority = EventPriority.MONITOR)
	    public void onMove(PlayerMoveEvent e) {
	    	
	    	if(!enabled) {
	    		return;
	    	}
	    
	    	
	    	Player p = e.getPlayer();
	    		
	    	 DataPlayer data = purge.getInstance().getDataManager().getDataPlayer(e.getPlayer());
	
	        if(data == null
	                || e.getPlayer().getAllowFlight()
	                || e.getPlayer().getVehicle() != null
	                || data.inLiquid
	                || data.onClimbable
	                || playerU.isInWater(p)
	                || System.currentTimeMillis() - data.lastVelocityTaken < 200L) return;

	        

	        
	        float deltaY = (float) (e.getTo().getY() - e.getFrom().getY());

	        if(data.airTicks > 2 && !playerU.slabsNear(p.getLocation()) && !playerU.blocksNear(p) && deltaY > data.lastDeltaY) {
	            if(data.flyThreshold++ > 2) {
	            	if(playerU.blocksNear(e.getPlayer())) {
	            		return;
	            	}
	                flag(e.getPlayer(), null);
	            }
	        } else data.flyThreshold-= data.flyThreshold > 0 ? 0.1f : 0;

	        float accel = (deltaY - data.lastDeltaY);

	        if(data.airTicks > 1 && Math.abs(accel) < 0.01) {
	            if(data.flyThreshold++ > 3) {
	            	if(playerU.blocksNear(e.getPlayer())) {
	            		return;
	            	}
	            	
	                flag(e.getPlayer(), null);
	            }
	        } else data.flyThreshold-= data.flyThreshold > 0 ? 0.25f : 0;
	        data.lastAccel = accel;
	        data.lastDeltaY = deltaY;
	    }
	 
}
