package RolePlayingGame_v2;

public class Enemy {
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables (pt 1)
		//Enemy Attributes
		boolean boss = false;
		int id = 0;
		String[] enemyattacknames;
		String[] enemyattackeffect;
		String enemyname;
		String resistances;
		int enemymaxhp;
		int enemychp;
		int enemycshield;
		int enemycarmor;
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables (pt 2)
		//All Enemies and Bosses
		private String[] allenemynames = new String[] {"Troll", "Goblin"};
		private int[] allenemyhp = new int[] {50, 25};
		private int[] allenemyarmor = new int[] {50, 0};
		private int[] allenemyshield = new int[] {0, 0};
		
		private String[][] allenemyattack = new String[][] {{"",""},
													        {"",""},
													        {"",""},
													        {"",""}};
													
		private String[][] allenemyattackeffect = new String[][] {{"DMG(10)","DMG(5)"},
														          {"DMG(25)","HEAL(10)"},
														          {"DMG(100)","falseCLONE(0)"},														
														          {"",""}};
	
														          
		private String[] allresists = new String[] {"", "Fire", "Corrosive", "Ice", "Electricity"};	
		
		private String[] allelements = new String[] {"", "Fire ", "Corrosive ", "Ice", "Electric "};	
														   
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
													         
	public Enemy(int number, int resist, boolean boss, int id, int lvl) {
		this.boss = boss;
		this.id = id;
		this.enemyattacknames = new String[] {allenemyattack[0][id],allenemyattack[1][id],allenemyattack[2][id],allenemyattack[3][id]};
		this.enemyattackeffect = new String[] {allenemyattackeffect[0][id],allenemyattackeffect[1][id],allenemyattackeffect[2][id],allenemyattackeffect[3][id]};
		this.enemymaxhp = (int)(allenemyhp[id] * Math.pow(1.13, lvl - 1));
		this.enemychp = (int)(allenemyhp[id] * Math.pow(1.13, lvl - 1));
		this.enemycshield = (int)(allenemyshield[id] * Math.pow(1.13, lvl - 1));
		this.enemycarmor = (int)(allenemyarmor[id] * Math.pow(1.13, lvl - 1));
		if(this.boss == false) {
			this.enemyname = allenemynames[id] + " #" + number;
		}else {
			this.enemyname = allenemynames[id];
		}
		this.resistances = allresists[resist];
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public Enemy clone(boolean boss, int id, int number , int lvl, int resist) {
		return new Enemy(number, resist, boss, id, lvl);
	}
	
	public void heal(int hp) {
		this.enemychp += hp;
	}
}
