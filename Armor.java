
public class Armor {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	//Variables (pt1)
		//All Common Armor
			 String[] allcommonarmornames = new String[] {"Leather Armor"};
			 int[] allcommonarmormaxhp = new int[] {50};
			
		//All Uncommon Armor
			 String[] alluncommonarmornames = new String[] {"Raider Armor", "Steel Armor"};
			 int[] alluncommonarmormaxhp = new int[] {75, 100};
		
		//All Rare Armor
			 String[] allrarearmornames = new String[] {"Holo-Armor"};
			 int[] allrarearmormaxhp = new int[] {200};
			
		//All Epic Armor
			 String[] allepicarmornames = new String[] {"Dragonscale Armor"};
			 int[] allepicarmormaxhp = new int[] {350};
			
		//All Legendary Armor
			 String[] alllegendaryarmornames = new String[] {"Bloodletter"};
			 int[] alllegendaryarmormaxhp = new int[] {100};
			 String[] allredtext = new String[] {"Blood is the price of victory"};
			
		//All Armor Effects
			 String[] allarmoreffects = new String[] {"", "Flammable ", "Ironclad ", "Insulated ", "Antifreeze ", "Stainless "};
			 String[] allarmorresist = new String[] {"", "Fire", "Electricity", "Ice", "Corrosive"};
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	//Variables (pt2)
		//Armor	Attributes
			int armormaxhp = 0;
			int armordhp = 0;
			String armorrarity = "";
			String redtext = "";
			String armoreffect = "";
			String armorresist = "";
			String armorname = "";
			int id;
			int itemlevel;
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructor 1
		public Armor(String name, String rarity, int armormaxhp, String redtext, int id, 
		int effect, int resist) {
			
			this.armorname = name;
			this.armorrarity = rarity;
			this.armormaxhp = armormaxhp;
			this.armordhp = armormaxhp;
			this.redtext = redtext;
			this.id = id;
			this.armoreffect = allarmoreffects[effect];
			this.armorresist = allarmorresist[resist];
		}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructor 2
		public Armor(String rarity, int id, String armoreffect, String resist, int lvl) {
			this.armoreffect = armoreffect;
			this.armorresist = resist;
			this.redtext = "";
			this.id = id;
			this.itemlevel = lvl;
			
			if (rarity.contains("ncommon")) {
				this.armorname = alluncommonarmornames[id];
				this.armordhp = (int)(alluncommonarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armormaxhp = (int)(alluncommonarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armorrarity = "Uncommon";
			}else if (rarity.contains("ommon")) {
				this.armorname = allcommonarmornames[id];
				this.armordhp = (int)(allcommonarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armormaxhp = (int)(allcommonarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armorrarity = "Common";
			}else if (rarity.contains("are")) {
				this.armorname = allrarearmornames[id];
				this.armordhp = (int)(allrarearmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armormaxhp = (int)(allrarearmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armorrarity = "Rare";
			}else if (rarity.contains("pic")) {
				this.armorname = allepicarmornames[id];
				this.armordhp = (int)(allepicarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armormaxhp = (int)(allepicarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armorrarity = "Epic";
			}else if (rarity.contains("egendary")) {
				this.armorname = alllegendaryarmornames[id];
				this.armordhp = (int)(alllegendaryarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.armormaxhp = (int)(alllegendaryarmormaxhp[id]* (Math.pow(1.13, lvl - 1)));
				this.redtext = allredtext[id];
				this.armorrarity = "Legendary";
			}
			
			if(armoreffect.contains("Ironclad")) {
				armordhp*=2;
				armormaxhp*=2;
				
			}else if(armoreffect.contains("Flammable")) {
				if(armorresist != "" && !armorresist.contains("Fire")) {
					armorresist = armorresist + " & Fire";
				}else {
					armorresist = "Fire";
				}
				
			}else if(armoreffect.contains("Antifreeze")) {
				if(armorresist != "" && !armorresist.contains("Ice")) {
					armorresist = armorresist + " & Ice";
				}else {
					armorresist = "Ice";
				}
				
			}else if(armoreffect.contains("Insulated")) {
				if(armorresist != "" && !armorresist.contains("Electricity")) {
					armorresist = armorresist + " & Electricity";
				}else {
					armorresist = "Electricity";
				}
				
			}else if(armoreffect.contains("Stainless")) {
				if(armorresist != "" && !armorresist.contains("Corrosive")) {
					armorresist = armorresist + " & Corrosive";
				}else {
					armorresist = "Corrosive";
				}
				
			}
			
		}
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
}
