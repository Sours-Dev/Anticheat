package sixpack.xCheat.checks.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;



public class Phase extends Check{

	public Phase() {
        super("phase", "Phase" , CheckType.MOVEMENT, true, false, 20);
    }

	public Phase(String name, CheckType type, boolean enabled, boolean punishable, int maxV) {
        super("phase", name, type, enabled, punishable, maxV);
    }
	  public static List<Material> blocked = new ArrayList<Material>();
	  public Map<Player, Long> lastlog = new HashMap<Player, Long>();
	
	  static {
	        blocked.add(Material.ACTIVATOR_RAIL);
	        blocked.add(Material.AIR);
	        blocked.add(Material.ANVIL);
	        blocked.add(Material.BED_BLOCK);
	        blocked.add(Material.BIRCH_WOOD_STAIRS);
	        blocked.add(Material.BREWING_STAND);
	        blocked.add(Material.BOAT);
	        blocked.add(Material.BRICK_STAIRS);
	        blocked.add(Material.BROWN_MUSHROOM);
	        blocked.add(Material.CAKE_BLOCK);
	        blocked.add(Material.CARPET);
	        blocked.add(Material.CAULDRON);
	        blocked.add(Material.COBBLESTONE_STAIRS);
	        blocked.add(Material.COBBLE_WALL);
	        blocked.add(Material.DARK_OAK_STAIRS);
	        blocked.add(Material.DIODE);
	        blocked.add(Material.DIODE_BLOCK_ON);
	        blocked.add(Material.DIODE_BLOCK_OFF);
	        blocked.add(Material.DEAD_BUSH);
	        blocked.add(Material.DETECTOR_RAIL);
	        blocked.add(Material.DOUBLE_PLANT);
	        blocked.add(Material.DOUBLE_STEP);
	        blocked.add(Material.DRAGON_EGG);
	        blocked.add(Material.FENCE_GATE);
	        blocked.add(Material.FENCE);
	        blocked.add(Material.PAINTING);
	        blocked.add(Material.FLOWER_POT);
	        blocked.add(Material.GOLD_PLATE);
	        blocked.add(Material.HOPPER);
	        blocked.add(Material.STONE_PLATE);
	        blocked.add(Material.IRON_PLATE);
	        blocked.add(Material.HUGE_MUSHROOM_1);
	        blocked.add(Material.HUGE_MUSHROOM_2);
	        blocked.add(Material.IRON_DOOR_BLOCK);
	        blocked.add(Material.IRON_DOOR);
	        blocked.add(Material.IRON_FENCE);
	        blocked.add(Material.IRON_PLATE);
	        blocked.add(Material.ITEM_FRAME);
	        blocked.add(Material.JUKEBOX);
	        blocked.add(Material.JUNGLE_WOOD_STAIRS);
	        blocked.add(Material.LADDER);
	        blocked.add(Material.LEVER);
	        blocked.add(Material.LONG_GRASS);
	        blocked.add(Material.NETHER_FENCE);
	        blocked.add(Material.NETHER_STALK);
	        blocked.add(Material.NETHER_WARTS);
	        blocked.add(Material.MELON_STEM);
	        blocked.add(Material.PUMPKIN_STEM);
	        blocked.add(Material.QUARTZ_STAIRS);
	        blocked.add(Material.RAILS);
	        blocked.add(Material.RED_MUSHROOM);
	        blocked.add(Material.RED_ROSE);
	        blocked.add(Material.SAPLING);
	        blocked.add(Material.SEEDS);
	        blocked.add(Material.SIGN);
	        blocked.add(Material.SIGN_POST);
	        blocked.add(Material.SKULL);
	        blocked.add(Material.SMOOTH_STAIRS);
	        blocked.add(Material.NETHER_BRICK_STAIRS);
	        blocked.add(Material.SPRUCE_WOOD_STAIRS);
	        blocked.add(Material.STAINED_GLASS_PANE);
	        blocked.add(Material.REDSTONE_COMPARATOR);
	        blocked.add(Material.REDSTONE_COMPARATOR_OFF);
	        blocked.add(Material.REDSTONE_COMPARATOR_ON);
	        blocked.add(Material.REDSTONE_LAMP_OFF);
	        blocked.add(Material.REDSTONE_LAMP_ON);
	        blocked.add(Material.REDSTONE_TORCH_OFF);
	        blocked.add(Material.REDSTONE_TORCH_ON);
	        blocked.add(Material.REDSTONE_WIRE);
	        blocked.add(Material.SANDSTONE_STAIRS);
	        blocked.add(Material.STEP);
	        blocked.add(Material.ACACIA_STAIRS);
	        blocked.add(Material.ENCHANTMENT_TABLE);
	        blocked.add(Material.SUGAR_CANE);
	        blocked.add(Material.SUGAR_CANE_BLOCK);
	        blocked.add(Material.SOUL_SAND);
	        blocked.add(Material.TORCH);
	        blocked.add(Material.TRAP_DOOR);
	        blocked.add(Material.TRIPWIRE);
	        blocked.add(Material.TRIPWIRE_HOOK);
	        blocked.add(Material.WALL_SIGN);
	        blocked.add(Material.VINE);
	        blocked.add(Material.WATER_LILY);
	        blocked.add(Material.WEB);
	        blocked.add(Material.WOOD_DOOR);
	        blocked.add(Material.WOOD_DOUBLE_STEP);
	        blocked.add(Material.WOOD_PLATE);
	        blocked.add(Material.WOOD_STAIRS);
	        blocked.add(Material.WOOD_STEP);
	        blocked.add(Material.HOPPER);
	        blocked.add(Material.WOODEN_DOOR);
	        blocked.add(Material.YELLOW_FLOWER);
	        blocked.add(Material.LAVA);
	        blocked.add(Material.WATER);
	        blocked.add(Material.STATIONARY_WATER);
	        blocked.add(Material.STATIONARY_LAVA);
	        blocked.add(Material.CACTUS);
	        blocked.add(Material.CHEST);
	        blocked.add(Material.PISTON_BASE);
	        blocked.add(Material.PISTON_MOVING_PIECE);
	        blocked.add(Material.PISTON_EXTENSION);
	        blocked.add(Material.PISTON_STICKY_BASE);
	        blocked.add(Material.TRAPPED_CHEST);
	        blocked.add(Material.SNOW);
	        blocked.add(Material.ENDER_CHEST);
	        blocked.add(Material.THIN_GLASS);
	    }
	  
	  @EventHandler
	    public void onMove(PlayerMoveEvent e) {
	        double zDist;
	        Player p = e.getPlayer();
	        if (p.getAllowFlight()) {
	            return;
	        }
	        if (p.getVehicle() != null) {
	            return;
	        }
	        if (p.getLocation().getY() < 0.0 || p.getLocation().getY() > (double)p.getWorld().getMaxHeight()) {
	            return;
	        }
	        if (this.lastlog.containsKey((Object)p) && System.currentTimeMillis() - this.lastlog.get((Object)p) < 500) {
	            return;
	        }
	        Location to = e.getTo().clone();
	        Location from = e.getFrom().clone();
	        double xDist = to.getX() - from.getX();
	        int blocks = 0;
	        if (xDist < -0.5 || xDist > 0.5) {
	            int x = (int)Math.round(Math.abs(xDist));
	            int i = 0;
	            while (i < x) {
	                Location l;
	                Location location = l = xDist < -0.5 ? to.clone().add((double)i, 0.0, 0.0) : from.clone().add((double)i, 0.0, 0.0);
	                if (l.getBlock() != null && l.getBlock().getType().isSolid() && l.getBlock().getType().isBlock() && l.getBlock().getType() != Material.AIR) {
	                    if (blocked.contains((Object)l.getBlock().getType())) {
	            	    } else {
	                        ++blocks;
	                    }
	                }
	                ++i;
	            }
	        }
	        if ((zDist = to.getX() - from.getX()) < -0.5 || zDist > 0.5) {
	            int z = (int)Math.round(Math.abs(xDist));
	            int i = 0;
	            while (i < z) {
	                Location l;
	                Location location = l = zDist < -0.5 ? to.clone().add(0.0, 0.0, (double)i) : from.clone().add(0.0, 0.0, (double)i);
	                if (l.getBlock() != null && l.getBlock().getType().isSolid() && l.getBlock().getType().isBlock() && l.getBlock().getType() != Material.AIR) {
	                    if (blocked.contains((Object)l.getBlock().getType())) {
	                    } else {
	                        ++blocks;
	                    }
	                }
	                ++i;
	            }
	        }
	        if (blocks > 0) {
	            flag(p, null);
	            this.lastlog.put(p, System.currentTimeMillis());
	            p.teleport(e.getFrom());
	            return;
	        }
	        if (e.getFrom().getBlock() != null && e.getFrom().getBlock().getType().isSolid() && e.getFrom().getBlock().getType().isBlock() && e.getFrom().getBlock().getType() != Material.AIR) {
	            if (blocked.contains((Object)e.getFrom().getBlock().getType())) {
	            } else if (e.getTo().getBlock() != e.getFrom().getBlock() && e.getTo().getBlock() != null) {	              
	                flag(p, null);
	                p.teleport(e.getFrom());
	                this.lastlog.put(p, System.currentTimeMillis());
	            }
	        }
	    }
	  
}
