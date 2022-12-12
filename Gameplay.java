
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Gameplay {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables(pt 1)
		
		//General
			//int[][] map = new int[53][40];
			int x = 200;
			int y = 200;
			
									// x,y
			int[] location = new int[]{0,0};
			int movementspeed = 1;	
			int moves = 2;
			int enemychance = 100;
		//Classes
			Battle b;
			SaveGame save;
			Shop shop;
		
		//Swing
			JButton background;
			JLabel player;
			JPanel game;
			JTabbedPane tabs;

	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructor
	public Gameplay(Battle b, SaveGame save, Shop shop, JTabbedPane tabs) {
		game = new JPanel();
		game.setLayout(new GridLayout(1,1,1,1));
		game.setSize(530, 400);
		background = new JButton("");
		player = new JLabel("p");
		background.add(player);
		background.setBounds(0,0,500,370);
		background.setSize(500,400);
		background.addKeyListener(new CustomKeyListener());
		game.add(background);
		
		this.save = save;
		this.b = b;
		this.shop = shop;
		this.tabs = tabs;
		
		moves = save.i.u.moves;
		enemychance = save.i.u.enemyspawnchance;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Key Listener Class
	class CustomKeyListener implements KeyListener{
		//Key Pressed
		public void keyPressed(KeyEvent e) {
				x = save.i.x;
				y = save.i.y;
				location[0] = save.i.location[0];
				location[1] = save.i.location[1];
				
				
				movementspeed = 10;				
				
				if(location[0] != 0 || location[1] != 0) {
					moves--;
					movementspeed = save.i.u.speed;				
					enemychance = save.i.u.enemyspawnchance;
					
				}
				
				//Up Movement:
					if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
					{
						y-=movementspeed;
						player.setLocation(x, y);
						//System.out.println("W");			
						if(y < 0) {
		    	  			location[1]++;
		    	  			y = 320;
		    	  		}
						player.setLocation(x, y);
						
						int random = (int)(Math.random() * 100);						
						if(random <= enemychance &&(location[0] != 0 || location[1] != 0)) {
							save.saveGame();
							b.startEnemyBattle(new int[] {1}, false);							
						}
			    	}
		    	
				//Down Movement:
					if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
					{
						y+=movementspeed;
						System.out.println("S");	
						//player.setLocation(x, y);
						if(y > 320) {		    	  
		    	  			location[1]--;
		    	  			y = 0;
		    	  		}
						player.setLocation(x, y);
						
						int random = (int)(Math.random() * 100);						
						if(random <= enemychance &&(location[0] != 0 || location[1] != 0)){
							save.saveGame();
							b.startEnemyBattle(new int[] {1}, false);
						}
			    	}
		    	  	
				//Left Movement:
					if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
					{
						x-=movementspeed;					
						player.setLocation(x, y);
						System.out.println("A");	
						if(x < 0) {		    	  
		    	  			location[0]--;
		    	  			x = 500;
		    	  		}
						player.setLocation(x, y);
						
						int random = (int)(Math.random() * 100);						
						if(random <= enemychance &&(location[0] != 0 || location[1] != 0)) {
							save.saveGame();
							b.startEnemyBattle(new int[] {1}, false);
						}
			    	}
		    	 
				//Right Movement:
					if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
					{
						x+=movementspeed;
						player.setLocation(x, y);
						System.out.println("D");	
						if(x > 500) {		    	  
		    	  			location[0]++;
		    	  			x = 0;
		    	  		}
						player.setLocation(x, y);
						
						int random = (int)(Math.random() * 100);						
						if(random <= enemychance &&(location[0] != 0 || location[1] != 0)) {
							save.saveGame();
							b.startEnemyBattle(new int[] {1}, false);
						}
			    	}
		    	 
				//Save Movement:
					if(e.getKeyCode() == KeyEvent.VK_E)
			         {		
						System.out.println("E");
			    	  	save.saveGame();
			         }
					
					if(e.getKeyCode() == KeyEvent.VK_SPACE)
			         {		  
						System.out.println("Space");
						
						//Shop
						shop.setStore((int)(Math.random() * 4), 1);
						tabs.setEnabledAt(0, false);
						tabs.setSelectedIndex(tabs.indexOfTab("Shop"));
			         } 
					
					//System.out.println(x+ "," + y);
					
					if(moves <= 0) {
						moves = save.i.u.moves;
						location[0] = 0;
						location[1] = 0;
					}
					
				save.i.setLocal(x, y, location[0], location[1]);
				
				System.out.println(x + "," + y);
				System.out.println(location[0] + "," + location[1]);
	      }
				
		
			
		  //Key Released
	      public void keyReleased(KeyEvent e) {}
	      
	      //Key Typed
	      public void keyTyped(KeyEvent e) {}
	      
	   }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Public Methods
		//setcheckPoint
		public void setcheckPoint() {
			
		}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

}
