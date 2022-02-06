package sixpack.xCheat.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.NumberConversions;


import net.minecraft.server.v1_8_R3.EntityPlayer;

public class playerU {

	public static int getPing(Player p) { 
		CraftPlayer cp = (CraftPlayer) p; 
		EntityPlayer ep = cp.getHandle(); 
		return ep.ping; 
	}
	
	
    public static boolean slabsNear(Location loc) {
        boolean nearBlocks = false;
        for (Block bl2 : blockU.getSurrounding(loc.getBlock(), true)) {
            if (!bl2.getType().equals((Object)Material.STEP) && !bl2.getType().equals((Object)Material.DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_STEP)) continue;
            nearBlocks = true;
            break;
        }
        for (Block bl2 : blockU.getSurrounding(loc.getBlock(), false)) {
            if (!bl2.getType().equals((Object)Material.STEP) && !bl2.getType().equals((Object)Material.DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_STEP)) continue;
            nearBlocks = true;
            break;
        }
        if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.STEP, Material.DOUBLE_STEP, Material.WOOD_DOUBLE_STEP, Material.WOOD_STEP})) {
            nearBlocks = true;
        }
        return nearBlocks;
    }

    public static boolean isInWater(Player p) {
		  if (!(p.getLocation().getBlock().isLiquid() || p.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid() || p.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid())) {
	            return false;
	        }
	        return true;
	}
	
  public static boolean isInWeb(Player p) {
      if (p.getLocation().getBlock().getType() != Material.WEB && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.WEB && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() != Material.WEB) {
          return false;
      }
      return true;
  }
  
  public static double fixXAxis(double x) {
      double touchedX = x;
      double rem = touchedX - (double)Math.round(touchedX) + 0.01;
      if (rem < 0.3) {
          touchedX = NumberConversions.floor((double)x) - 1;
      }
      return touchedX;
  }


  
  public static double getHealth(Player p) {
  	return p.getHealthScale();
  }
  
  public static boolean isBlock(Block block, Material[] materials) {
      Material type = block.getType();
      Material[] arrmaterial = materials;
      int n = arrmaterial.length;
      int n2 = 0;
      while (n2 < n) {
          Material m = arrmaterial[n2];
          if (m == type) {
              return true;
          }
          ++n2;
      }
      return false;
  }
  
  
  
  public static Location getEyeLocation(Player player) {
      Location eye = player.getLocation();
      eye.setY(eye.getY() + player.getEyeHeight());
      return eye;
  }
  
  public static boolean canStandWithin(Block block) {
      boolean solid;
      boolean isSand = block.getType() == Material.SAND;
      boolean isGravel = block.getType() == Material.GRAVEL;
      boolean bl = solid = block.getType().isSolid() && !block.getType().name().toLowerCase().contains("door") && !block.getType().name().toLowerCase().contains("fence") && !block.getType().name().toLowerCase().contains("bars") && !block.getType().name().toLowerCase().contains("sign");
      if (!(isSand || isGravel || solid)) {
          return true;
      }
      return false;
  }
  
  public static boolean isOnGround(Player player) {
      if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
          return true;
      }
      Location a = player.getLocation().clone();
      a.setY(a.getY() - 0.5);
      if (a.getBlock().getType() != Material.AIR) {
          return true;
      }
      a = player.getLocation().clone();
      a.setY(a.getY() + 0.5);
      if (a.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
          return true;
      }
      if (isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER})) {
          return true;
      }
      return false;
  }
  

  
  public static boolean isFullyInWater(Location player) {
      double touchedX = fixXAxis(player.getX());
      if (new Location(player.getWorld(), touchedX, player.getY(), (double)player.getBlockZ()).getBlock().isLiquid() && new Location(player.getWorld(), touchedX, (double)Math.round(player.getY()), (double)player.getBlockZ()).getBlock().isLiquid()) {
          return true;
      }
      return false;
  }
  
  public static boolean isHoveringOverWater(Location player, int blocks) {
      int i = player.getBlockY();
      while (i > player.getBlockY() - blocks) {
          Block newloc = new Location(player.getWorld(), (double)player.getBlockX(), (double)i, (double)player.getBlockZ()).getBlock();
          if (newloc.getType() != Material.AIR) {
              return newloc.isLiquid();
          }
          --i;
      }
      return false;
  }

  public static boolean isHoveringOverWater(Location player) {
      return isHoveringOverWater(player, 25);
  }
  
  public static boolean cantStandAtWater(Block block) {
      boolean sw;
      Block otherBlock = block.getRelative(BlockFace.DOWN);
      boolean isHover = block.getType() == Material.AIR;
      boolean n = otherBlock.getRelative(BlockFace.NORTH).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER;
      boolean s = otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.WATER || otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.STATIONARY_WATER;
      boolean e = otherBlock.getRelative(BlockFace.EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.EAST).getType() == Material.STATIONARY_WATER;
      boolean w = otherBlock.getRelative(BlockFace.WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.WEST).getType() == Material.STATIONARY_WATER;
      boolean ne = otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.STATIONARY_WATER;
      boolean nw = otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.STATIONARY_WATER;
      boolean se = otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER;
      boolean bl = sw = otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.STATIONARY_WATER;
      if (n && s && e && w && ne && nw && se && sw && isHover) {
          return true;
      }
      return false;
  }

  
  public static boolean blocksNear(Player player) {
      return blocksNear(player.getLocation());
  }
  
	public static boolean isDescending(Player p) {
		return p.getVelocity().getY() < 0.0;
	}
  
  public static boolean blocksNear(Location loc) {
      boolean nearBlocks = false;
      for (Block block2 : blockU.getSurrounding(loc.getBlock(), true)) {
          if (block2.getType() == Material.AIR) continue;
          nearBlocks = true;
          break;
      }
      for (Block block2 : blockU.getSurrounding(loc.getBlock(), false)) {
          if (block2.getType() == Material.AIR) continue;
          nearBlocks = true;
          break;
      }
      Location a = loc;
      a.setY(a.getY() - 0.5);
      if (a.getBlock().getType() != Material.AIR) {
          nearBlocks = true;
      }
      if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER})) {
          nearBlocks = true;
      }
      return nearBlocks;
  }
    

    
    public static boolean isOnGroundVanilla(Player player) {
        return player.isOnGround() && player.getLocation().getBlock().getLocation().subtract(0, 1, 0).getBlock().getType().isSolid();
    }
    
    

    
    public static boolean isInLiquid(Player p) {
    	Object box = reflectionU.getBoundingBox(p);
    	
    	
        double minX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "a"), box);
        double minY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "b"), box);
        double minZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "c"), box);
        double maxX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "d"), box);
        double maxY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "e"), box);
        double maxZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "f"), box);
    	
        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(p.getWorld(), x, y, z).getBlock();

                    if(blockU.isLiquid(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
        
    }
    // does not work
    public static boolean isInStairs(Player player) {

        Object box = reflectionU.modifyBoundingBox(reflectionU.getBoundingBox(player), 0, -0.5,0,0,0,0);

        double minX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "a"), box);
        double minY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "b"), box);
        double minZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "c"), box);
        double maxX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "d"), box);
        double maxY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "e"), box);
        double maxZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(blockU.isStair(block)
                            || blockU.isSlab(block)
                            || block.getType().equals(Material.SKULL)
                            || block.getType().equals(Material.CAKE_BLOCK)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isOnClimbable(Player player) {
        return blockU.isClimbableBlock(player.getLocation().getBlock())
                || blockU.isClimbableBlock(player.getLocation().add(0, 1,0).getBlock());
    }

    public static float getPotionEffectLevel(Player player, PotionEffectType pet) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            if (!pe.getType().getName().equals(pet.getName())) continue;
            return pe.getAmplifier() + 1;
        }
        return 0;
    }

    public static boolean isOnSlime(Player player) {
        Object box = reflectionU.modifyBoundingBox(reflectionU.getBoundingBox(player), 0, -0.4f,0,0,0,0);

        double minX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "a"), box);
        double minY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "b"), box);
        double minZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "c"), box);
        double maxX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "d"), box);
        double maxY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "e"), box);
        double maxZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(block.getType().toString().contains("SLIME")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean inUnderBlock(Player player) {
        Object box = reflectionU.modifyBoundingBox(reflectionU.getBoundingBox(player), 0, 0,0,0,1,0);

        double minX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "a"), box);
        double minY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "b"), box);
        double minZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "c"), box);
        double maxX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "d"), box);
        double maxY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "e"), box);
        double maxZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(block.getType().isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isOnIce(Player player) {
        Object box = reflectionU.modifyBoundingBox(reflectionU.getBoundingBox(player), 0, -0.5,0,0,0,0);

        double minX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "a"), box);
        double minY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "b"), box);
        double minZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "c"), box);
        double maxX = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "d"), box);
        double maxY = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "e"), box);
        double maxZ = (double) reflectionU.getInvokedField(reflectionU.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(block.getType().equals(Material.ICE)
                            || block.getType().equals(Material.PACKED_ICE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	  
}
