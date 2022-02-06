package sixpack.xCheat.player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;


import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckManager;
import sixpack.xCheat.util.c;
import sixpack.xCheat.util.playerU;
import sixpack.xCheat.util.reflectionU;




public class userEVNT implements Listener{


	
	private purge plugin = purge.getInstance();

	
	public void setUp(Player p) {
		String name = p.getName();
		UUID uuid = p.getUniqueId();
		HashMap<String, Integer> vio = new HashMap<>();
		
		List<String> checks = plugin.getCheckManager().getChecks();
		for(String check : checks) {
			vio.put(check, 0);
		}
		vio.put("Combat", 0);
		vio.put("Movement", 0);
		vio.put("Other", 0);
		
		plugin.userMangerHashMap.put(p, new userManager(name, uuid, vio,false));
		
		Bukkit.getConsoleSender().sendMessage(c.f("&a[Purge] &f" + name + " has been loaded"));
	}

	@EventHandler
	public void joinEVNT(PlayerJoinEvent e) {
		setUp(e.getPlayer());
		plugin.getDataManager().add(e.getPlayer());
		
		bannedPlayer bP = new bannedPlayer();
				
		if(bP.dateStored(e.getPlayer().getUniqueId())) {
			bP.removeBanInfo(e.getPlayer());
		}
		
	}
	@EventHandler
	public void leaveEVNT(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		plugin.userMangerHashMap.remove(p);
		plugin.getDataManager().remove(e.getPlayer());
	}
	
	
	
	 @EventHandler(priority = EventPriority.HIGH)
	    public void onMove(PlayerMoveEvent event) {
	        Player player = event.getPlayer();
	        if (event.getFrom().getX() != event.getTo().getX()
	                || event.getFrom().getY() != event.getTo().getY()
	                || event.getFrom().getZ() != event.getTo().getZ()) {
	            DataPlayer data = purge.getInstance().getDataManager().getDataPlayer(player);

	            if (data != null) {
	                data.onGround = playerU.isOnGround(player);
	                data.onStairSlab = playerU.isInStairs(player);
	                data.inLiquid = playerU.isInLiquid(player);
	                data.onIce = playerU.isOnIce(player);
	                data.onClimbable = playerU.isOnClimbable(player);
	                data.underBlock = playerU.inUnderBlock(player);
	                data.onSlime = playerU.isOnSlime(player);

	                data.boundingBox = reflectionU.getBoundingBox(event.getPlayer());
	                data.nearGround = reflectionU.getCollidingBlocks(event.getPlayer(), reflectionU
	                        .modifyBoundingBox(data.boundingBox, 0, -1,0,0,0,0))
	                        .size() > 0;

	                if(event.getPlayer().isOnGround()) {
	                    data.groundTicks++;
	                    data.airTicks = 0;
	                } else {
	                    data.airTicks++;
	                    data.groundTicks = 0;
	                }

	                data.reduceVelocity();

	                data.iceTicks = Math.max(0, data.onIce ? Math.min(60, data.iceTicks + 3) : data.iceTicks - 1);
	                data.liquidTicks = Math.max(0, data.inLiquid ? Math.min(40, data.liquidTicks + 1)  : data.liquidTicks - 1);
	                data.blockTicks = Math.max(0, data.underBlock ? Math.min(60, data.blockTicks + 3)  : data.blockTicks - 1);
	                data.slimeTicks = Math.max(0, data.onSlime ? Math.min(data.slimeTicks + 8, 60) : data.slimeTicks - 1);
	            }
	        }
	    }

	    @EventHandler
	    public void onVelocity(PlayerVelocityEvent event) {
	        DataPlayer data = purge.getInstance().getDataManager().getDataPlayer(event.getPlayer());

	        if(data == null) {
	            return;
	        }
	        if(event.getVelocity().getY() > -0.078 || event.getVelocity().getY() < -0.08) {
	            data.lastVelocityTaken = System.currentTimeMillis();
	        }

	        data.velXTicks = (int) Math.round(event.getVelocity().getX() * 100);
	        data.velXTicks = (int) Math.round(event.getVelocity().getY() * 100);
	        data.velXTicks = (int) Math.round(event.getVelocity().getZ() * 100);
	    }
	
}
