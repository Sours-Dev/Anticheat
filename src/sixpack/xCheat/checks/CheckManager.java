package sixpack.xCheat.checks;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import sixpack.xCheat.checks.combat.Autoclicker1;
import sixpack.xCheat.checks.combat.Criticals;
import sixpack.xCheat.checks.combat.DoubleClick;
import sixpack.xCheat.checks.combat.killaura.Killaura1;
import sixpack.xCheat.checks.combat.killaura.Killaura2;
import sixpack.xCheat.checks.combat.killaura.Killaura3;
import sixpack.xCheat.checks.combat.killaura.Killaura4;
import sixpack.xCheat.checks.combat.killaura.Killaura5;
import sixpack.xCheat.checks.combat.killaura.Killaura6;
import sixpack.xCheat.checks.combat.reach.Reach1;
import sixpack.xCheat.checks.combat.reach.Reach2;
import sixpack.xCheat.checks.combat.reach.Reach3;
import sixpack.xCheat.checks.movement.Ascension;
import sixpack.xCheat.checks.movement.Fly1;
import sixpack.xCheat.checks.movement.Fly2;
import sixpack.xCheat.checks.movement.Jesus;
import sixpack.xCheat.checks.movement.NoFall;
import sixpack.xCheat.checks.movement.Phase;
import sixpack.xCheat.checks.movement.Speed1;
import sixpack.xCheat.checks.movement.VClip;
import sixpack.xCheat.checks.other.TabComplete;
import sixpack.xCheat.checks.other.VapeCracked;
import sixpack.xCheat.util.c;


public class CheckManager {

	
	public  List<Check> checks;
	
	private static CheckManager instance;
	private configChecks configChecks = new configChecks();
	
	public CheckManager() {
		instance = this;
		checks = new ArrayList<>();
		init();
	}
	
	
	public void init() {
		//Other
		if(!checkConfig("tab")) {addCheck(new TabComplete());}
		if(!checkConfig("vape")) {addCheck(new VapeCracked());}
		
		//Combat
		if(!checkConfig("reach1")) { addCheck(new Reach1());}
		if(!checkConfig("reach2")) { addCheck(new Reach2());}
		if(!checkConfig("reach3")) { addCheck(new Reach3());}
		if(!checkConfig("doubleclick")) { addCheck(new DoubleClick());}
		if(!checkConfig("criticals")) { addCheck(new Criticals());}
		if(!checkConfig("autoclicker1")) {addCheck(new Autoclicker1());}
		
		if(!checkConfig("killaura1")) { addCheck(new Killaura1());}
		if(!checkConfig("killaura2")) { addCheck(new Killaura2());}
		if(!checkConfig("killaura3")) { addCheck(new Killaura3());}
		if(!checkConfig("killaura4")) { addCheck(new Killaura4());}
		if(!checkConfig("killaura5")) { addCheck(new Killaura5());}
		if(!checkConfig("killaura6")) { addCheck(new Killaura6());}		
		
		//Movement
		if(!checkConfig("speed1")) {addCheck(new Speed1());}
		if(!checkConfig("fly1")) {addCheck(new Fly1());}
		if(!checkConfig("fly1")) {addCheck(new Fly2());}
		if(!checkConfig("ascension1")) {addCheck(new Ascension());}
		if(!checkConfig("phase")) {addCheck(new Phase());}
		if(!checkConfig("vclip")) {addCheck(new VClip());}
		if(!checkConfig("nofall")) {addCheck(new NoFall());}
		if(!checkConfig("jesus")) {addCheck(new Jesus());}
		addChecksConfig();

		checks.clear();
		String name;
		CheckType type;
		boolean enabled;
		boolean punishable;
		int maxV;
		
		configChecks.setupchecks();
		//Other
		//Tab Complete
		name = configChecks.getchecks().getString("tab.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("tab.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("tab.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("tab.punishable"));
		maxV = configChecks.getchecks().getInt("tab.maxVio");
		addCheck(new TabComplete(name,type,enabled,punishable,maxV));
		//Vape
		name = configChecks.getchecks().getString("vape.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("vape.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("vape.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("vape.punishable"));
		maxV = configChecks.getchecks().getInt("vape.maxVio");
		addCheck(new VapeCracked(name,type,enabled,punishable,maxV));
		
		
		//Combat
		//Reach 1
		name = configChecks.getchecks().getString("reach1.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("reach1.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("reach1.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("reach1.punishable"));
		maxV = configChecks.getchecks().getInt("reach1.maxVio");
		addCheck(new Reach1(name,type,enabled,punishable,maxV));
		
		// Reach 2
		name = configChecks.getchecks().getString("reach2.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("reach2.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("reach2.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("reach2.punishable"));
		maxV = configChecks.getchecks().getInt("reach2.maxVio");
		addCheck(new Reach2(name,type,enabled,punishable,maxV));
		
		//Reach 3
		
		name = configChecks.getchecks().getString("reach3.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("reach3.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("reach3.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("reach3.punishable"));
		maxV = configChecks.getchecks().getInt("reach3.maxVio");
		addCheck(new Reach3(name,type,enabled,punishable,maxV));
		
		//Crictcals
		
		name = configChecks.getchecks().getString("criticals.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("criticals.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("criticals.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("criticals.punishable"));
		maxV = configChecks.getchecks().getInt("criticals.maxVio");
		addCheck(new Criticals(name,type,enabled,punishable,maxV));
		
		
		
		//KillAura 1
		name = configChecks.getchecks().getString("killaura1.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("killaura1.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura1.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura1.punishable"));
		maxV = configChecks.getchecks().getInt("killaura1.maxVio");
		addCheck(new Killaura1(name,type,enabled,punishable,maxV));
		
		//KillAura 2
		name = configChecks.getchecks().getString("killaura2.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("killaura2.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura2.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura2.punishable"));
		maxV = configChecks.getchecks().getInt("killaura2.maxVio");
		addCheck(new Killaura2(name,type,enabled,punishable,maxV));
		
		//KillAura 3
		name = configChecks.getchecks().getString("killaura3.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("killaura3.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura3.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura3.punishable"));
		maxV = configChecks.getchecks().getInt("killaura3.maxVio");
		addCheck(new Killaura3(name,type,enabled,punishable,maxV));
	
		//KillAura 4
		name = configChecks.getchecks().getString("killaura4.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("killaura4.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura4.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura4.punishable"));
		maxV = configChecks.getchecks().getInt("killaura4.maxVio");
		addCheck(new Killaura4(name,type,enabled,punishable,maxV));
		
		
		//KillAura 5
		name = configChecks.getchecks().getString("killaura5.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("killaura5.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura5.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura5.punishable"));
		maxV = configChecks.getchecks().getInt("killaura5.maxVio");
		addCheck(new Killaura5(name,type,enabled,punishable,maxV));
		
		
		//KillAura 6
		name = configChecks.getchecks().getString("killaura6.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("killaura6.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura6.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("killaura6.punishable"));
		maxV = configChecks.getchecks().getInt("killaura6.maxVio");
		addCheck(new Killaura6(name,type,enabled,punishable,maxV));
		
		
		//AutoCLicker 1
		name = configChecks.getchecks().getString("autoclicker1.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("autoclicker1.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("autoclicker1.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("autoclicker1.punishable"));
		maxV = configChecks.getchecks().getInt("autoclicker1.maxVio");
		addCheck(new Autoclicker1(name,type,enabled,punishable,maxV));
		
		//DoubleClick
		name = configChecks.getchecks().getString("doubleclick.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("doubleclick.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("doubleclick.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("doubleclick.punishable"));
		maxV = configChecks.getchecks().getInt("doubleclick.maxVio");
		addCheck(new DoubleClick(name,type,enabled,punishable,maxV));
		
		//Movement
		//Speed
		name = configChecks.getchecks().getString("speed1.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("speed1.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("speed1.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("speed1.punishable"));
		maxV = configChecks.getchecks().getInt("speed1.maxVio");
		addCheck(new Speed1(name,type,enabled,punishable,maxV));
		
		//Fly 1
		name = configChecks.getchecks().getString("fly1.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("fly1.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("fly1.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("fly1.punishable"));
		maxV = configChecks.getchecks().getInt("fly1.maxVio");
		addCheck(new Fly1(name,type,enabled,punishable,maxV));
		
		//Fly 2
		name = configChecks.getchecks().getString("fly2.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("fly2.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("fly2.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("fly2.punishable"));
		maxV = configChecks.getchecks().getInt("fly2.maxVio");
		addCheck(new Fly2(name,type,enabled,punishable,maxV));
		
		//Ascension
		name = configChecks.getchecks().getString("ascension1.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("ascension1.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("ascension1.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("ascension1.punishable"));
		maxV = configChecks.getchecks().getInt("ascension1.maxVio");
		addCheck(new Ascension(name,type,enabled,punishable,maxV));
		
		//Phase
		name = configChecks.getchecks().getString("phase.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("phase.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("phase.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("phase.punishable"));
		maxV = configChecks.getchecks().getInt("phase.maxVio");
		addCheck(new Phase(name,type,enabled,punishable,maxV));
		
		//VCLIP
		name = configChecks.getchecks().getString("vclip.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("vclip.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("vclip.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("vclip.punishable"));
		maxV = configChecks.getchecks().getInt("vclip.maxVio");
		addCheck(new VClip(name,type,enabled,punishable,maxV));
		

		
		//jesus
		name = configChecks.getchecks().getString("jesus.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("jesus.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("jesus.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("jesus.punishable"));
		maxV = configChecks.getchecks().getInt("jesus.maxVio");
		addCheck(new Jesus(name,type,enabled,punishable,maxV));
		
		//NoFall
		name = configChecks.getchecks().getString("nofall.name");
		type = CheckType.valueOf(configChecks.getchecks().getString("nofall.type"));
		enabled = Boolean.valueOf(configChecks.getchecks().getBoolean("nofall.enabled"));
		punishable = Boolean.valueOf(configChecks.getchecks().getBoolean("nofall.punishable"));
		maxV = configChecks.getchecks().getInt("nofall.maxVio");
		addCheck(new NoFall(name,type,enabled,punishable,maxV));
		
	}
	
	
	public void addCheck(Check check) {
		checks.add(check);
	}
	
	public void removeCHeck(Check check) {
		checks.remove(check);
	}

	public Check getCheck(String name) {
		for(Check check : checks) {
			if(check.name.equalsIgnoreCase(name)) {
				return check;
			}
		}
		return null;
	}
	
	public CheckManager getInstance() {
		return instance;
	}
	
	public List<String> getChecks(){
		List<String> temp = new ArrayList<String>();
		
		for(Check check : checks) {
			temp.add(check.name);
		}
		return temp;
	}
	
	public void saveCheck(Check check) {
		configChecks.setupchecks();

		configChecks.getchecks().set(check.id + ".name", check.name);
		configChecks.getchecks().set(check.id + ".type", check.type.toString());
		configChecks.getchecks().set(check.id + ".enabled", check.enabled);
		configChecks.getchecks().set(check.id + ".punishable", check.punishable);
		configChecks.getchecks().set(check.id + ".maxVio", check.maxV);
		configChecks.savechecks();
		Bukkit.getConsoleSender().sendMessage(c.f("&a[Purge] &aSaved check " + check.id + " to config."));

			
		
	}
	
	private boolean checkConfig(String id) {
		configChecks.setupchecks();
		if(configChecks.getchecks().contains(id)) {
			return true;
		}
		return false;
	}
	private void addChecksConfig() {
		configChecks.setupchecks();
		
		Check check;
		for(int i = 0; i<checks.size(); i++) {
			check = checks.get(i);
			if(!configChecks.getchecks().contains(check.id)) {
				configChecks.getchecks().set(check.id + ".name", check.name);
				configChecks.getchecks().set(check.id + ".type", check.type.toString());
				configChecks.getchecks().set(check.id + ".enabled", check.enabled);
				configChecks.getchecks().set(check.id + ".punishable", check.punishable);
				configChecks.getchecks().set(check.id + ".maxVio", check.maxV);
				Bukkit.getConsoleSender().sendMessage(c.f("&a[Purge] &aAdded check " + check.id + " to config."));
				configChecks.savechecks();
			}
		}
		

		
	}
	
}
