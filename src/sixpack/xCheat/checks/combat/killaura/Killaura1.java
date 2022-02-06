package sixpack.xCheat.checks.combat.killaura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.player.DataPlayer;


public class Killaura1 extends Check{

	public purge AntiCheat = purge.getInstance();
	

	
	  public Killaura1() {
		  super("killaura1", "KillAura (1)", CheckType.COMBAT, true, false, 10);
	    }
	  
		public Killaura1(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
			super("killaura1", name, type, enabled, punishable, maxV);
	    	if(!enabled) {
	    		return;
	    	}
	        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.getInstance(),
	                PacketType.Play.Client.POSITION,
	                PacketType.Play.Client.LOOK,
	                PacketType.Play.Client.POSITION_LOOK,
	                PacketType.Play.Client.FLYING,
	                PacketType.Play.Client.USE_ENTITY) {
	            @Override
	            public void onPacketReceiving(PacketEvent event) {
	                DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());
	                if(event.getPacketType().equals(PacketType.Play.Client.USE_ENTITY)) {
	                    if(System.currentTimeMillis() - data.lastFlying < 5) {
	                        if(data.killauraAV++ > 5) {
	                            flag(data.p, "Late Packets");
	                        }
	                    } else {
	                        data.killauraAV = 0;
	                    }
	                } else {
	                    data.lastFlying = System.currentTimeMillis();
	                }
	            }
	        });
			
		}
	  
}
