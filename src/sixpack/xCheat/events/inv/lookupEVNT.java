package sixpack.xCheat.events.inv;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;



import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.player.bannedPlayer;
import sixpack.xCheat.player.userManager;
import sixpack.xCheat.util.c;
import sixpack.xCheat.util.playerU;

public class lookupEVNT implements Listener{

	private purge plugin = purge.getInstance();
	private userManager uMan;
	private bannedPlayer bP = new bannedPlayer();
	
	@EventHandler
	public void onInventoryCLICK(InventoryClickEvent e) {	

		if(e.getInventory().getName().length() <13) {
			return;
		}
		
		
		if (e.getInventory().getName().substring(0,13).equalsIgnoreCase(c.f("&cLookup » &f"))) {
			
			
			
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				if(e.getCurrentItem().hasItemMeta()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(c.f("&c&lBANNED"))) {
						String target = e.getInventory().getName().substring(13);
						World w = Bukkit.getWorld(bP.getWorld(target));
						double x = bP.getLastLocation(target, "x"); 
						double y = bP.getLastLocation(target, "y"); 
						double z = bP.getLastLocation(target, "z");
						float yaw = (float) bP.getLastLocation(target, "yaw");
						float pitch = (float) bP.getLastLocation(target, "pitch");
						p.teleport(new Location(w,x,y,z,yaw,pitch));
					
						p.sendMessage(c.f("&7Teleporting to " + target + " last known location."));

					}
			
				}
			}
			
		}
	}
	
	
	
	public void openInv(Player p, Player target) {
		
		uMan = plugin.userMangerHashMap.get(target);
		Inventory inv = Bukkit.createInventory(null, 54, c.f("&cLookup » &f" + 	uMan.getName()));
		
		List<Check> checks = plugin.getCheckManager().checks;
		int count = 0;
		//Player Checks
		for(Check check : checks) {
			ItemStack checkItem = new ItemStack(Material.BARRIER, 1);
			if(check.type == CheckType.COMBAT) {
				 checkItem = new ItemStack(Material.DIAMOND_SWORD, 1);
			}
			if(check.type == CheckType.MOVEMENT) {
				 checkItem = new ItemStack(Material.FEATHER, 1);
				
			}
			if(check.type == CheckType.OTHER) {
				 checkItem = new ItemStack(Material.REDSTONE, 1);
			}
			
			ItemMeta checkMeta = checkItem.getItemMeta();
			checkMeta.setDisplayName(c.f("&c" + check.name));
			List<String> checkLore = new ArrayList<String>();
			checkLore.add(c.f("&8&m------------------------"));
			checkLore.add(c.f("&f» " + check.type.toString() + " Hack"));
			checkLore.add(c.f("&f» Violations set off: &7" + uMan.getViolations(check.name)));
			checkLore.add(c.f("&8&m------------------------"));		
			checkMeta.setLore(checkLore);
			checkItem.setItemMeta(checkMeta);
			inv.setItem(count, checkItem);
			count++;
		}
	
		
		
		// Total player counts
		List<String> tlore = new ArrayList<String>();
			// Total combat	
			ItemStack combatItem = new ItemStack(Material.DIAMOND_SWORD, 1);		
			ItemMeta combatMeta = combatItem.getItemMeta();
			combatMeta.setDisplayName(c.f("&c&lCombat"));
			tlore.add(c.f("&8&m------------------------"));
			tlore.add(c.f("&f» Total Combat"));
			tlore.add(c.f("&f   Violations set off: &7" + uMan.getViolations("Combat")));
			tlore.add(c.f("&8&m------------------------"));		
			combatMeta.setLore(tlore);
			combatItem.setItemMeta(combatMeta);
			inv.setItem(40, combatItem);
			tlore.clear();
			//Movement
			ItemStack movementItem = new ItemStack(Material.FEATHER, 1);		
			ItemMeta movementMeta = movementItem.getItemMeta();
			movementMeta.setDisplayName(c.f("&c&lMovement"));
			List<String> movementLore = new ArrayList<String>();
			tlore.add(c.f("&8&m------------------------"));
			tlore.add(c.f("&f» Total Movement"));
			tlore.add(c.f("&f   Violations set off: &7" + uMan.getViolations("Movement")));
			tlore.add(c.f("&8&m------------------------"));		
			movementMeta.setLore(tlore);
			movementItem.setItemMeta(movementMeta);
			inv.setItem(48, movementItem);
			tlore.clear();
			//Other
			ItemStack otherItem = new ItemStack(Material.REDSTONE, 1);		
			ItemMeta otherMeta = otherItem.getItemMeta();
			otherMeta.setDisplayName(c.f("&c&lOther"));
			tlore.add(c.f("&8&m------------------------"));
			tlore.add(c.f("&f» Total Other"));
			tlore.add(c.f("&f   Violations set off: &7" + uMan.getViolations("Other")));
			tlore.add(c.f("&8&m------------------------"));		
			otherMeta.setLore(tlore);
			otherItem.setItemMeta(otherMeta);
			inv.setItem(50, otherItem);
			tlore.clear();	
			//Total
			int tempTV = uMan.getViolations("Combat") + uMan.getViolations("Movement") 
			+ uMan.getViolations("Other"); 		
			ItemStack totalItem = new ItemStack(Material.EMERALD_BLOCK, 1);		
			ItemMeta totalMeta = otherItem.getItemMeta();
			totalMeta.setDisplayName(c.f("&c&lTotal"));
			tlore.add(c.f("&8&m------------------------"));
			tlore.add(c.f("&f» Total"));
			tlore.add(c.f("&f   Violations set off: &7" + tempTV));
			tlore.add(c.f("&8&m------------------------"));		
			totalMeta.setLore(tlore);
			totalItem.setItemMeta(totalMeta);
			inv.setItem(49, totalItem);
			tlore.clear();
			
			
			
			
		p.openInventory(inv);	
	}
	
	
	public void openInvBanned(Player p, String target) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		if(!bP.dateStored(op.getUniqueId())){
			return;
		}
		
		
		Inventory inv = Bukkit.createInventory(null, 54, c.f("&cLookup » &f" + 	target));
		
		List<Check> checks = plugin.getCheckManager().checks;
		int count = 0;
		//Player Checks
		for(Check check : checks) {
			ItemStack checkItem = new ItemStack(Material.BARRIER, 1);
			if(check.type == CheckType.COMBAT) {
				 checkItem = new ItemStack(Material.DIAMOND_SWORD, 1);
			}
			if(check.type == CheckType.MOVEMENT) {
				 checkItem = new ItemStack(Material.FEATHER, 1);
				
			}
			if(check.type == CheckType.OTHER) {
				 checkItem = new ItemStack(Material.REDSTONE, 1);
			}
			ItemMeta checkMeta = checkItem.getItemMeta();
			checkMeta.setDisplayName(c.f("&c" + check.name));
			List<String> checkLore = new ArrayList<String>();
			checkLore.add(c.f("&8&m------------------------"));
			checkLore.add(c.f("&f» " + check.type.toString() + " Hack"));
			checkLore.add(c.f("&f» Violations set off: &7" + bP.getViolations(target, check.id)));
			checkLore.add(c.f("&8&m------------------------"));		
			checkMeta.setLore(checkLore);
			checkItem.setItemMeta(checkMeta);
			inv.setItem(count, checkItem);
			count++;
		}

		DecimalFormat format = new DecimalFormat("0.00");
		// Players head 
			ItemStack playerInfo = new ItemStack(Material.SKULL_ITEM,1);
			SkullMeta pSkull = (SkullMeta) playerInfo.getItemMeta();
			pSkull.setOwner(op.getName());
			pSkull.setDisplayName(c.f("&c&lBANNED"));
			List<String> lore = new ArrayList<>();
			lore.add(c.f("&c&lLast Location: "));
			lore.add(c.f("&f * X: " + format.format(bP.getLastLocation(target, "x")) 
			+ " Y: " + format.format(bP.getLastLocation(target, "y")) 
			+ " Z: " + format.format(bP.getLastLocation(target, "z"))));
			
			lore.add(c.f("&f * Ping: " + bP.getPing(target)));
			lore.add(c.f("&f * TPS: " + format.format(bP.getTPS(target, 0)) + "/" + format.format(bP.getTPS(target, 1))+ "/" + format.format(bP.getTPS(target, 2))));
			lore.add(c.f("&c"));
			lore.add(c.f(" &7&oClick to telport to last location."));
			pSkull.setLore(lore);
			playerInfo.setItemMeta(pSkull);
			inv.setItem(49, playerInfo);
			
			
			
		p.openInventory(inv);	
	}
	
	
}
