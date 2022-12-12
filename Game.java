
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Game {
//-----------------------------------------------------------------------------------------------------------------------------------------
	//Variables (part 1)
		//General 
			//Version
				double version = 0.1;
				/*(can be used to update game)*/
			
		//Swing
			//JTabbedPane
				JTabbedPane tabs = new JTabbedPane();
		
			//JFrame
				JFrame game = new JFrame("");
			
			//JPanel
				JPanel title = new JPanel();
	
		//Classes
			Upgrades upgrade = new Upgrades();
			Inventory inventory = new Inventory(tabs, upgrade);
			Battle battle = new Battle(tabs,inventory, game);
			Shop shop = new Shop(inventory, tabs);
			
			SaveGame save = new SaveGame(inventory, game);
			Gameplay gameplay = new Gameplay(battle, save, shop, tabs);

//-----------------------------------------------------------------------------------------------------------------------------------------
	//Constructor
	public Game() {
		game.setSize(530, 400);
		setupTitle();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);	
		game.setResizable(false);
	}
//-----------------------------------------------------------------------------------------------------------------------------------------
	//Public Methods
		//Setup Tabbed Pane
			public void setUp() {
				inventory.setUp();
				shop.setupShop();
				tabs.addTab("Game", gameplay.game);
				tabs.addTab("Inventory", inventory.playerinventory);
				tabs.addTab("Upgrades", upgrade.upgrade);
				
				tabs.removeTabAt(0);
				game.add(tabs);
			}
//-------------------------------------------------------------------------------------------------------------------------------------
	//Private Methods
		//Buttons Pressed on Title Page
			private class title implements ActionListener{		
				public void actionPerformed(ActionEvent ae) {
					if(ae.getActionCommand() == "Load Game") {
						
						setUp();
						save.loadGame();
						
					}else {
						setUp();
					}
				}
			}
			
		//Setup Title Page
			public void setupTitle() {
				title.setLayout(new GridLayout(1,1,1,1));
				ImageIcon look = new ImageIcon();
				JLabel titleofgame = new JLabel(look);
				
				title.add(titleofgame);
				title.setBounds(0,0,1500,1400);
				title.setSize(1500,1400);
			
				
				JButton newg = new JButton("New Game");
				newg.setSize(100,100);
				newg.setBounds(0,0,100,25);
				newg.setLocation(0, 250);
				titleofgame.add(newg);
				
				JButton con = new JButton("Load Game");
				con.setSize(100,100);
				con.setBounds(0,0,100,25);
				con.setLocation(0, 280);
				titleofgame.add(con);
				
				
				newg.addActionListener(new title());
				con.addActionListener(new title());
	
				tabs.add(title);
				game.add(tabs);
			}
//-------------------------------------------------------------------------------------------------------------------------------------

}
