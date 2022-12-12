
public class Shield {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables
	
		//All the Weapons in the Game
			//Common Weapons
				 String[] allcommonshieldnames = new String[] {"Wooden Shield"};
				 int[] allcommonshieldblock = new int[] {5};
				 int[] allcommonshieldhp = new int[] {50};			
				 int[] allcommonshieldturntorecharge = new int[] {2};
				 int [] allcommonshieldshieldrecharged = new int[] {10};
				
			//Uncommon Weapons
				 String[] alluncommonshieldnames = new String[] {"Steel Shield"};
				 int[] alluncommonshieldblock = new int[] {10};
				 int[] alluncommonshieldhp = new int[] {100};				
				 int[] alluncommonshieldturntorecharge = new int[] {1};
				 int [] alluncommonshieldshieldrecharged = new int[] {35};
				
				
			//Rare Weapons
				 String[] allrareshieldnames = new String[] {"Holo-Shield"};
				 int[] allrareshieldblock = new int[] {30};
				 int[] allrareshieldhp = new int[] {250};			
				 int[] allrareshieldturntorecharge = new int[] {5};
				 int [] allrareshieldshieldrecharged = new int[] {250};
				
				
			//Epic Weapons
				 String[] allepicshieldnames = new String[] {"Dragonscale Shield"};
				 int[] allepicshieldblock = new int[] {50};
				 int[] allepicshieldhp = new int[] {750};				
				 int[] allepicshieldturntorecharge = new int[] {2};
				 int [] allepicshieldshieldrecharged = new int[] {100};
				
				
			//Legendary Weapons
				 String[] alllegendaryshieldnames = new String[] {"Evolution", "Deathless", "Gump"};
				 int[] alllegendaryshieldhp = new int[] {500, 1, 300};
				 int[] alllegendaryshieldblock = new int[] {50, 0, 60};			
				 int[] alllegendaryshieldturntorecharge = new int[] {2,7,4};
				 int [] alllegendaryshieldshieldrecharged = new int[] {0, 1, 100};
				 String[] allredtext = new String[] {"What doesn't kill you makes you", "Not today", "Life is like a box of chocolates"};
				
			//All WeaponEffects
				 String[] allshieldeffects = new String[] {"","Insta-", "Durable ", "Stronger ", "Faster ", "Improved ", "Thorny ", "Singularity ", "Reflecting ", "Absorbing ", "Amplifying "};
				
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	//Variables (part 2)
				
		//Weapon Attributes
			String shieldname = "";
			/*(name of the shield)*/
			
			String shieldrarity = "";
			/*(Common, Uncommon, Rare, Epic, Legendary)*/
			
			int shieldblock = 0;
			/*(percent of enemy damage it blocks)*/
						
			String shieldeffect = "";
			/*()*/
			/*[depends on effect]*/
			
			int shieldmaxhp = 0;
			int shieldcurrenthp = 0;
			/*()*/
			
			int turntorecharge = 0;
			/**/
			
			int shieldrecharged = 0;
			/**/
			
			String redtext = "";
			/*(special text on shields that's usually a reference to pop culture)*/
			
			int id = 0;
			/*(index of array depending on rarity)*/
			
			int itemlevel;
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		//Constructor 1
			public Shield(String shieldname, String shieldrarity, int shieldblock, 
			int shieldeffect, String redtext, int shieldmaxhp, int turntorecharge, 
			int shieldrecharged, int id) {
				
							this.shieldname = shieldname;
							this.shieldrarity = shieldrarity;
							this.shieldblock = shieldblock;
							this.shieldcurrenthp = shieldmaxhp;
							this.shieldmaxhp = shieldmaxhp;
							this.redtext = redtext;
							this.shieldeffect = allshieldeffects[shieldeffect];
							this.turntorecharge = turntorecharge;
							this.shieldrecharged = shieldrecharged;
							this.id = id;
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		//Constructor 2
			public Shield(String rarity, int id, String effect, int lvl) {
				this.redtext = "";
				this.id = id;
				this.shieldeffect = effect;
				this.itemlevel = lvl;
				
				if (rarity.contains("ncommon")) {
					this.shieldname = alluncommonshieldnames[id];
					this.shieldrarity = "Uncommon";
					this.shieldblock = alluncommonshieldblock[id];
					this.shieldcurrenthp = (int)(alluncommonshieldhp[id]* (Math.pow(1.13, lvl - 1)));
					this.shieldmaxhp = (int)(alluncommonshieldhp[id]* (Math.pow(1.13, lvl - 1)));				
					this.turntorecharge = alluncommonshieldturntorecharge[id];
					this.shieldrecharged = (int)(alluncommonshieldshieldrecharged[id]* (Math.pow(1.13, lvl - 1)));
					
				}else if(rarity.contains("ommon")) {
					this.shieldname = allcommonshieldnames[id];
					this.shieldrarity = "Common";
					this.shieldblock = allcommonshieldblock[id];
					this.shieldcurrenthp = (int)(allcommonshieldhp[id]* (Math.pow(1.13, lvl - 1)));
					this.shieldmaxhp = (int)(allcommonshieldhp[id]* (Math.pow(1.13, lvl - 1)));					
					this.turntorecharge = allcommonshieldturntorecharge[id];
					this.shieldrecharged = (int)(allcommonshieldshieldrecharged[id] * (Math.pow(1.13, lvl - 1)));
					
				}else if (rarity.contains("are")) {
					this.shieldname = allrareshieldnames[id];
					this.shieldrarity = "Rare";
					this.shieldblock = allrareshieldblock[id];
					this.shieldcurrenthp = (int)(allrareshieldhp[id]* (Math.pow(1.13, lvl - 1)));
					this.shieldmaxhp = (int)(allrareshieldhp[id]* (Math.pow(1.13, lvl - 1)));				
					this.turntorecharge = allrareshieldturntorecharge[id];
					this.shieldrecharged = (int)(allrareshieldshieldrecharged[id] * (Math.pow(1.13, lvl - 1)));
					
				}else if (rarity.contains("pic")) {
					this.shieldname = allepicshieldnames[id];
					this.shieldrarity = "Epic";
					this.shieldblock = allepicshieldblock[id];
					this.shieldcurrenthp = (int)(allepicshieldhp[id]* (Math.pow(1.13, lvl - 1)));
					this.shieldmaxhp = (int)(allepicshieldhp[id]* (Math.pow(1.13, lvl - 1)));			
					this.turntorecharge = allepicshieldturntorecharge[id];
					this.shieldrecharged = (int)(allepicshieldshieldrecharged[id] * (Math.pow(1.13, lvl - 1)));
					
				}else if (rarity.contains("egendary")) {
					this.shieldname = alllegendaryshieldnames[id];
					this.shieldrarity = "Legendary";
					this.shieldblock = (int)(alllegendaryshieldblock[id]);
					this.shieldcurrenthp = (int)(alllegendaryshieldhp[id]* (Math.pow(1.13, lvl - 1)));;
					this.shieldmaxhp = (int)(alllegendaryshieldhp[id] * (Math.pow(1.13, lvl - 1)));					
					this.turntorecharge = alllegendaryshieldturntorecharge[id];
					this.shieldrecharged = (int)(alllegendaryshieldshieldrecharged[id] * (Math.pow(1.13, lvl - 1)));
					this.redtext = allredtext[id];
					
				}
				
				if(shieldeffect.contains("Insta-")) {
					turntorecharge = 1;
					
				}else if(shieldeffect.contains("Faster")) {
					turntorecharge /= 2;
					
				}else if(shieldeffect.contains("Improved")) {
					shieldrecharged *= 2;
					
				}else if(shieldeffect.contains("Durable")) {
					shieldmaxhp *= 2;
					shieldcurrenthp *= 2;
					
				}else if(shieldeffect.contains("Stronger")) {
					if(shieldblock * 2 < 100) {
						shieldblock *= 2;
					}else {
						shieldblock = 100;
					}
					
				}
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Setter
		public void addCurrentHP(int i) {
			if ((i > this.shieldrecharged || i < this.shieldrecharged) && i > 0) {
				i = this.shieldrecharged;
			}
			
			if(this.shieldcurrenthp + i >= shieldmaxhp) {
				this.shieldcurrenthp = this.shieldmaxhp;
				
			}else if (this.shieldcurrenthp + i <= 0){
				this.shieldcurrenthp = 0;
				
			}else {
				this.shieldcurrenthp += i;
			}
		}
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
}
