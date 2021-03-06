package sixpack.xCheat.util.packets.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
public class PacketPlayerEvent extends Event {
	    private Player Player;
	    private double x;
	    private double y;
	    private double z;
	    private float pitch;
	    private float yaw;
	    private static final HandlerList handlers = new HandlerList();
	    private PacketPlayerType type;

	    public PacketPlayerEvent(Player Player2, double x, double y, double z, float yaw, float pitch, PacketPlayerType type) {
	        this.Player = Player2;
	        this.x = x;
	        this.y = y;
	        this.z = z;
	        this.pitch = pitch;
	        this.yaw = yaw;
	        this.type = type;
	    }

	    public Player getPlayer() {
	        return this.Player;
	    }

	    public double getX() {
	        return this.x;
	    }

	    public double getY() {
	        return this.y;
	    }

	    public double getZ() {
	        return this.z;
	    }

	    public float getYaw() {
	        return this.yaw;
	    }

	    public float getPitch() {
	        return this.pitch;
	    }

	    public PacketPlayerType getType() {
	        return this.type;
	    }

	    public HandlerList getHandlers() {
	        return handlers;
	    }

	    public static HandlerList getHandlerList() {
	        return handlers;
	    }
}
