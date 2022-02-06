package sixpack.xCheat.player;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import sixpack.xCheat.util.pastLoc;

public class DataPlayer {

    public Player p;
    public Object boundingBox;
    public boolean onGround, inLiquid, onStairSlab, onIce, onClimbable, underBlock, onSlime, nearGround;
    public int airTicks, groundTicks, iceTicks, liquidTicks, blockTicks, slimeTicks, velXTicks, velYTicks, velZTicks;
    public long lastVelocityTaken, lastAttack, lastServerKP, ping;
    public LivingEntity lastHitEntity;
    public pastLoc entityPastLocations = new pastLoc();
    /**KillAura**/
    public int killauraAV;
    public long lastFlying;
    
    /**Pattern **/
    public List<Float> patterns = Lists.newArrayList();
    public float lastRange;
    
    /**Fly**/
     public float lastDeltaY, lastAccel;
    
    
    /**
     * Thresholds
     **/
    public int speedThreshold;
    public int reachThreshold;
    public float flyThreshold;
    
	public DataPlayer(Player p) {
		this.p = p;
	}
	
    public boolean isVelocityTaken() {
        return velXTicks > 0 || velYTicks > 0 || velZTicks > 0;
    }

    public void reduceVelocity() {
        velXTicks = Math.max(0, velXTicks - 1);
        velYTicks = Math.max(0,velYTicks - 1);
        velZTicks = Math.max(0, velZTicks - 1);
    }
	
}
