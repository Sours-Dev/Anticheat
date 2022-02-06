package sixpack.xCheat.util;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class mathU {

	 public static float getHorizontalDistance(Location from, Location to) {
	        Location fromClone = from.clone();
	        Location toClone = to.clone();
	        fromClone.setY(0);
	        toClone.setY(0);
	        return (float) fromClone.distance(toClone);
	    }
		
	    
	    public static float yawTo180F(float flub) {
	        if ((flub %= 360.0f) >= 180.0f) {
	            flub -= 360.0f;
	        }
	        if (flub < -180.0f) {
	            flub += 360.0f;
	        }
	        return flub;
	    }
	   
	    public static double trim(int degree, double d) {
	        String format = "#.#";
	        for (int i = 1; i < degree; ++i) {
	            format = String.valueOf(format) + "#";
	        }
	        DecimalFormat twoDForm = new DecimalFormat(format);
	        return Double.valueOf(twoDForm.format(d).replaceAll(",", "."));
	    }
	    
	   public static double offset2d(Entity a, Entity b) {
	        return mathU.offset2d(a.getLocation().toVector(), b.getLocation().toVector());
	    }

	    public static double offset2d(Location a, Location b) {
	        return mathU.offset2d(a.toVector(), b.toVector());
	    }

	    public static double offset2d(Vector a, Vector b) {
	        a.setY(0);
	        b.setY(0);
	        return a.subtract(b).length();
	    }

	    public static double offset(Entity a, Entity b) {
	        return mathU.offset(a.getLocation().toVector(), b.getLocation().toVector());
	    }

	    public static double offset(Location a, Location b) {
	        return mathU.offset(a.toVector(), b.toVector());
	    }

	    public static double offset(Vector a, Vector b) {
	        return a.subtract(b).length();
	    }
	    
	    public static Vector getHorizontalVector(Vector v) {
	        v.setY(0);
	        return v;
	    }

	    public static Vector getVerticalVector(Vector v) {
	        v.setX(0);
	        v.setZ(0);
	        return v;
	    }
	    
	    public static float[] getRotations(Location one, Location two) {
	        double diffX = two.getX() - one.getX();
	        double diffZ = two.getZ() - one.getZ();
	        double diffY = two.getY() + 2.0 - 0.4 - (one.getY() + 2.0);
	        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
	        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
	        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
	        return new float[]{yaw, pitch};
	    }

	    public static double[] getOffsetFromEntity(Player player, LivingEntity entity) {
	        double yawOffset = Math.abs(mathU.yawTo180F(player.getEyeLocation().getYaw()) - mathU.yawTo180F(mathU.getRotations(player.getLocation(), entity.getLocation())[0]));
	        double pitchOffset = Math.abs(Math.abs(player.getEyeLocation().getPitch()) - Math.abs(mathU.getRotations(player.getLocation(), entity.getLocation())[1]));
	        return new double[]{yawOffset, pitchOffset};
	    }
	
}
