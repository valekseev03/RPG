
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

public class Upgrades {
		//Variables
			int hp = 1;
			int lives = 1;
			int xp = 1;
			
			int golddrops = 1;
			int inventorystorage = 1;
			
			
			int weaponraritydrops = 0;
			int weaponlvl = 1;
			
			int enemyspawnchance = 100;
			int moves = 5;
			int speed = 1;
			
			
		//Swing
			JPanel upgrade = new JPanel();
			
			JButton upgrade1 = new JButton("Upgrade");
			JButton upgrade2 = new JButton("Upgrade");
			JButton upgrade3 = new JButton("Upgrade");
			JButton upgrade4 = new JButton("Upgrade");
			JButton upgrade5 = new JButton("Upgrade");
			JButton upgrade6 = new JButton("Upgrade");
			JButton upgrade7 = new JButton("Upgrade");
			JButton upgrade8 = new JButton("Upgrade");
			JButton upgrade9 = new JButton("Upgrade");
			JButton upgrade10 = new JButton("Upgrade");
			
			JLabel upgradez1 = new JLabel("HP: 1/200");
			JLabel upgradez2 = new JLabel("Lives: 1/5");
			JLabel upgradez3 = new JLabel("Lvl: 1/50");
			JLabel upgradez4 = new JLabel("Loot Drops: 1/100");
			JLabel upgradez5 = new JLabel("Inventory Storage: 1/1000");
			JLabel upgradez6 = new JLabel("Loot Rarity: 0/6");
			JLabel upgradez7 = new JLabel("Loot Lvl: 1/50");
			JLabel upgradez8 = new JLabel("Enemy Spawn Chance: 100/100");
			JLabel upgradez9 = new JLabel("Moves: 5/1000");
			JLabel upgradez10 = new JLabel("Speed: 1/20");

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			public Upgrades() {
				setUp();
			}
			
			public void setUp() {
				upgrade.setLayout(new GridLayout(10,2,0,0));
				
				upgrade.add(upgradez1);
				upgrade.add(upgrade1);
				
				upgrade.add(upgradez2);
				upgrade.add(upgrade2);
				
				upgrade.add(upgradez3);
				upgrade.add(upgrade3);
				
				upgrade.add(upgradez4);
				upgrade.add(upgrade4);
				
				upgrade.add(upgradez5);
				upgrade.add(upgrade5);
				
				upgrade.add(upgradez6);
				upgrade.add(upgrade6);
				
				upgrade.add(upgradez7);
				upgrade.add(upgrade7);
				
				upgrade.add(upgradez8);
				upgrade.add(upgrade8);
				
				upgrade.add(upgradez9);
				upgrade.add(upgrade9);
				
				upgrade.add(upgradez10);
				upgrade.add(upgrade10);	
				
			}
			
}
