package RolePlayingGame_v2;
/*[(0: Sword), (1: Axe), (2: Mace), (3: Cross-Bow), (4: SledgeHammer), (5: Dagger) (6: Gauntlet) (7: Misc)]*/

public class Weapon {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables
	
		//All the Weapons in the Game
			//Common Weapons
				 String[] allcommonweaponnames = new String[] {"Wooden Sword", "Wooden Bow", "Sock-Gauntlet"};
				 int[] allcommonweapondamage = new int[] {10, 20, 5};
				 int[] allcommonweapontypes = new int[] {0, 3, 6};
				 int[] allcommonweaponattacksperturn = new int[] {2, 1, 1};
				 int[] allcommonweaponaccuracy = new int[] {75, 80, 100};
				
			//Uncommon Weapons
				 String[] alluncommonweaponnames = new String[] {"Sledge-Gunner"};
				 int[] alluncommonweapondamage = new int[] {30};
				 int[] alluncommonweapontypes = new int[] {4};
				 int[] alluncommonweaponattacksperturn = new int[] {1};
				 int[] alluncommonweaponaccuracy = new int[] {25};
				 
			//Rare Weapons
				 String[] allrareweaponnames = new String[] {"Steel Dagger"};
				 int[] allrareweapondamage = new int[] {10};
				 int[] allrareweapontypes = new int[] {5};
				 int[] allrareweaponattacksperturn = new int[] {5};
				 int[] allrareweaponaccuracy = new int[] {50};
				 
			//Epic Weapons
				 String[] allepicweaponnames = new String[] {"Plasma Axe", "Dragonbone Sledgehammer", "Lazer Crossbow"};
				 int[] allepicweapondamage = new int[] {20, 50, 100};
				 int[] allepicweapontypes = new int[] {1,4, 3};
				 int[] allepicweaponattacksperturn = new int[] {3, 1, 1};
				 int[] allepicweaponaccuracy = new int[] {90,50,65};
				
			//Legendary Weapons
				 String[] alllegendaryweaponnames = new String[] {"One-Shot"};
				 int[] alllegendaryweapondamage = new int[] {999};
				 int[] alllegendaryweapontypes = new int[] {3};
				 int[] alllegendaryweaponattacksperturn = new int[] {1};
				 int[] alllegendaryweaponaccuracy = new int[] {100};
				 String[] allredtext = new String[] {"One shot is all that I need"};
				
			//All WeaponEffects
				 String[] allweaponeffects = new String[] {"", "Grim ", "Precise " , "Rustler's ", "Destructive ", "Infinite ", "Critting ", "Burning ", "Freezing ", "Electric ", "Melting "};
				 String[] allweaponelements = new String[] {"", "Fire", "Electricity", "Ice", "Corrosive"};
				
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	//Variables (part 2)
				
		//Weapon Attributes
			String weaponname = "";
			/*(name of the weapon)*/
			
			String weaponrarity = "";
			/*(Common, Uncommon, Rare, Epic, Legendary)*/
			
			int weapondamage = 0;
			/*(how much damage it does to enemies)*/
			
			int weapontype = 0;
			/*[(0: Sword), (1: Axe), (2: Mace), (3: Cross-Bow), (4: SledgeHammer), (5: Dagger)]*/
			/*[used to show right icon in view weapon]*/
			
			String weaponeffect = "";
			/*(Grim, Destructive, Freezing, Infinite)*/
			/*[depends on effect]*/
			
			String weaponelement = "";
			/* Fire, Ice, etc */
			
			int attacksperturn = 0;
			/*(times it attacks per turn)*/
			
			int accuracy = 0;
			/*(chance to hit enemy per attack)*/
			
			String redtext = "";
			/*(special text on weapons that's usually a reference to pop culture)*/
			
			int id = 0;
			/*(index of array depending on rarity)*/
			
			int itemlevel = 1;
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		//Constructor 1
			public Weapon(String weaponname, String weaponrarity, 
			int weapondamage, int weapontype, int effect, int element, 
			int attacksperturn, String redtext, int id , int accuracy) {
				
							this.weaponname = weaponname;
							this.weaponrarity = weaponrarity;
							this.weapondamage = weapondamage;
							this.weapontype = weapontype;
							this.attacksperturn = attacksperturn;
							this.redtext = redtext;
							this.weaponeffect = allweaponeffects[effect];
							this.id = id;
							this.accuracy = accuracy;
							this.weaponelement = allweaponelements[element];
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		//Constructor 2
			public Weapon(String rarity, int id, String effect, String element, int lvl) {
				this.redtext = "";
				this.id = id;
				this.weaponeffect = effect;
				this.weaponelement = element;
				this.itemlevel = lvl;
				
				if (rarity.contains("ncommon")) {
					this.weaponname = alluncommonweaponnames[id];
					this.weaponrarity = "Uncommon";
					this.weapondamage = (int)(alluncommonweapondamage[id] * Math.pow(1.13, lvl - 1));
					this.weapontype = alluncommonweapontypes[id];
					this.attacksperturn = alluncommonweaponattacksperturn[id];
					this.accuracy = alluncommonweaponaccuracy[id];
					
				}else if(rarity.contains("ommon")) {
					this.weaponname = allcommonweaponnames[id];
					this.weaponrarity = "Common";
					this.weapondamage = (int)(allcommonweapondamage[id] * Math.pow(1.13, lvl - 1));
					this.weapontype = allcommonweapontypes[id];
					this.attacksperturn = allcommonweaponattacksperturn[id];
					this.accuracy = allcommonweaponaccuracy[id];
					
				}else if (rarity.contains("are")) {
					this.weaponname = allrareweaponnames[id];
					this.weaponrarity = "Rare";
					this.weapondamage = (int)(allrareweapondamage[id] * Math.pow(1.13, lvl - 1));
					this.weapontype = allrareweapontypes[id];
					this.attacksperturn = allrareweaponattacksperturn[id];
					this.accuracy = allrareweaponaccuracy[id];
					
				}else if (rarity.contains("pic")) {
					this.weaponname = allepicweaponnames[id];
					this.weaponrarity = "Epic";
					this.weapondamage = (int)(allepicweapondamage[id] * Math.pow(1.13, lvl - 1));
					this.weapontype = allepicweapontypes[id];
					this.attacksperturn = allepicweaponattacksperturn[id];
					this.accuracy = allepicweaponaccuracy[id];
					
				}else if (rarity.contains("egendary")) {
					this.weaponname = alllegendaryweaponnames[id];
					this.weaponrarity = "Legendary";
					this.weapondamage = (int)(alllegendaryweapondamage[id] * Math.pow(1.13, lvl - 1));
					this.weapontype = alllegendaryweapontypes[id];
					this.attacksperturn = alllegendaryweaponattacksperturn[id];
					this.accuracy = alllegendaryweaponaccuracy[id];
					this.redtext = allredtext[id];
					
				}
				
				
				if(weaponeffect.contains("Precise")) {
					if(accuracy * 2 < 100) {
						accuracy*=2;	
					}else {
						accuracy = 100;
					}
					
				}else if (weaponeffect.contains("Destructive")) {
					weapondamage*=2;
					
				}else if (weaponeffect.contains("Rustler's")) {
					attacksperturn*=2;
					
				}else if (weaponeffect.contains("Burning")) {
					if(weaponelement != "" && !weaponelement.contains("Fire")) {
						weaponelement = weaponelement + " & Fire";
					}else {
						if(weaponelement.length() < 1) {
							weaponelement = "Fire";
						}
					}
					
				}else if (weaponeffect.contains("Freezing")) {
					if(weaponelement != "" && !weaponelement.contains("Ice")) {
						weaponelement = weaponelement + " & Ice";
					}else {
						if(weaponelement.length() < 1) {
							weaponelement = "Ice";
						}
					}
					
				}else if (weaponeffect.contains("Electric")) {
					if(weaponelement != "" && !weaponelement.contains("Electricity")) {
						weaponelement = weaponelement + " & Electricity";
					}else {
						if(weaponelement.length() < 1) {
							weaponelement = "Electricity";
						}
					}
					
				}else if (weaponeffect.contains("Melting")) {
					if(weaponelement != "" && !weaponelement.contains("Corrosive")) {
						weaponelement = weaponelement + " & Corrosive";
					}else {
						if(weaponelement.length() < 1) {
							weaponelement = "Corrosive";
						}
					}
					
				}
				
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			
}
