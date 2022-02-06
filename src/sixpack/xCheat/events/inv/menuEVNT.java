package sixpack.xCheat.events.inv;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import sixpack.xCheat.purge;
import sixpack.xCheat.checks.Check;
import sixpack.xCheat.checks.CheckType;
import sixpack.xCheat.player.userManager;
import sixpack.xCheat.util.c;

public class menuEVNT implements Listener{

	private purge plugin = purge.getInstance();
	private userManager uMan;
	
	
	@EventHandler
	public void onInventoryMENU(InventoryClickEvent e) {	
		
		if (e.getInventory().getName().equalsIgnoreCase(c.f("&c&lAnticheat » &fMenu"))) {
			
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				if(e.getCurrentItem().hasItemMeta()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(c.f("&cChecks"))) {
						p.closeInventory();
						openInvChecks(p);
						
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(c.f("&cReload"))) {

					}
				}
			}
			
		}
		

	}
	
	@EventHandler
	public void onInventoryCHECKS(InventoryClickEvent e) {	
		
		if (e.getInventory().getName().equalsIgnoreCase(c.f("&c&lAnticheat » &fChecks"))) {
			
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
				if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
					if(e.getCurrentItem().hasItemMeta()) {
						
						// right click
						if(e.getClick() == ClickType.RIGHT) {
							String check = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
							
							String checkN = "";
							List<Check> checks = plugin.getCheckManager().checks;
							for(Check checkL : checks) {
								if(checkL.name.equalsIgnoreCase(check)) {
									checkN = checkL.name;
								}
							}
						
							Check rCheck = plugin.getCheckManager().getCheck(checkN);
							if(rCheck.punishable) {
								rCheck.punishable = false;
								openInvChecks(p);
							}else {
								rCheck.punishable = true;
								openInvChecks(p);
							}
							plugin.getCheckManager().saveCheck(rCheck);
						}
						
						//left
						if(e.getClick() == ClickType.LEFT) {
							String check = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
							
							String checkN = "";
							List<Check> checks = plugin.getCheckManager().checks;
							for(Check checkL : checks) {
								if(checkL.name.equalsIgnoreCase(check)) {
									checkN = checkL.name;
								}
							}
						
							Check rCheck = plugin.getCheckManager().getCheck(checkN);
							if(rCheck.enabled) {
								rCheck.enabled = false;
								openInvChecks(p);
							}else {
								rCheck.enabled = true;
								openInvChecks(p);
							}
							plugin.getCheckManager().saveCheck(rCheck);
						}
					}
				}
			
			
			}
			
	}
		

	
	
	private void openInvChecks(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, c.f("&c&lAnticheat » &fChecks"));
		
		
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
			checkLore.add(c.f("&7&l &aType » &f" + check.type));
			checkLore.add(c.f("&7&l &aEnabled » &f" + check.enabled));
			checkLore.add(c.f("&7&l &aPunishable » &f" + check.punishable));
			checkLore.add(c.f("&7&l &aMaximum Violation » &f" + check.maxV));
			checkLore.add(c.f("&8&m------------------------"));		
			checkLore.add(c.f("&aLeft Click - &fToggle check &aon&f/&coff"));
			checkLore.add(c.f("&aRight Click - &fToggle punisable &aon&f/&coff"));
			checkMeta.setLore(checkLore);
			checkItem.setItemMeta(checkMeta);
			inv.setItem(count, checkItem);
			count++;
		}
	
		p.openInventory(inv);
	}
	
	public void openInvMenu(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 9, c.f("&c&lAnticheat » &fMenu"));
		
		ItemStack checksItem = new ItemStack(Material.COMMAND, 1);
		ItemMeta checksMeta = checksItem.getItemMeta();
		checksMeta.setDisplayName(c.f("&cChecks"));
		
		List<String> checksLore = new ArrayList<String>();
		checksLore.add(c.f("&8&m------------------------"));
		checksLore.add(c.f("&7&l* &fEdit anticheat checks"));
		checksLore.add(c.f("&8&m------------------------"));
		
		checksMeta.setLore(checksLore);
		checksItem.setItemMeta(checksMeta);
		
		inv.setItem(0, checksItem);
		
		ItemStack reloadItem = new ItemStack(Material.BARRIER, 1);
		ItemMeta reloadMeta = reloadItem.getItemMeta();
		reloadMeta.setDisplayName(c.f("&cReload"));
		
		List<String> reloadLore = new ArrayList<String>();
		reloadLore.add(c.f("&8&m------------------------"));
		reloadLore.add(c.f("&7&l* &fReload anticheat config"));
		reloadLore.add(c.f("&8&m------------------------"));
		
		reloadMeta.setLore(reloadLore);
		reloadItem.setItemMeta(reloadMeta);
		
	//	inv.setItem(8, reloadItem);
		
		
		//stained glass
		
		ItemStack stainedGlassItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta sGMeta = stainedGlassItem.getItemMeta();
		sGMeta.setDisplayName(c.f("&c"));
		stainedGlassItem.setItemMeta(sGMeta);
		
		for(int i = 1; i< 8; i++) {
			inv.setItem(i, stainedGlassItem);
		}
		p.openInventory(inv);
		
	}
}
