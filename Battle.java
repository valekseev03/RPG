package RolePlayingGame_v2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Battle {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables (pt1)
		//General
			public boolean battle;
			private boolean bossbattle;
			int storylevel = 1;
			private double multiplier = 1;
			private double dmultiplier = 1;
			private double emultiplier = 1;
			
			private int damagedone = 0;
			
			private int turnsofInvincibilty = 0;
			
			private int turnsyoualive = -1;
			private int turnsenemyalive = -1;
			
			int lives = 1;
		//Enemies		
			private ArrayList<Enemy> allenemies = new ArrayList<Enemy>();
			      
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables (pt2)	
		//Java
			private int turn = 0;
			private int[] random = new int[] {0,0,0};
			
			                                   //player, enemy
			private int[] selectedIndex = new int[] {0,0};
			
		//Swing
			JFrame game;
			JPanel battlez;
			private JButton enemy;
			
			private JButton leftplayer= new JButton("<----  ");
			private JButton leftenemy = new JButton("<----");
			private JButton rightplayer = new JButton("---->  ");
			private JButton rightenemy = new JButton("---->");
			
			private JLabel enemyhp = new JLabel("");;
			private JLabel enemyname = new JLabel("");;
			private JLabel playerhp = new JLabel("");;
			private JLabel playername = new JLabel("");
		//Imports
			private JTabbedPane tabs;
			private Inventory i;
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//Constructor
		public Battle(JTabbedPane tabs, Inventory i, JFrame game) {
			this.i = i;
			this.tabs = tabs;
			this.game = game;
		}
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//Public Methods	
		//Enemy Encounter
		public void startEnemyBattle(int[] indexes, boolean boss) {
			battle = true;
			bossbattle = boss;
			selectedIndex = new int[] {0,0};
			 
			for(int a = 0; a < indexes.length; a++) {
				allenemies.add(new Enemy(a + 1, 0, boss, indexes[a], storylevel));
			}
			
			turn = 0;
			for(int a = 0; a < tabs.getTabCount(); a++) {
				if(a != 1) {
					tabs.setEnabledAt(a, false);
				}
			}
			
			setUp();
			tabs.insertTab("Battle", null, battlez, null, 0);
			tabs.setSelectedIndex(0);
		}
		
		
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Private Methods
			//Battle:
		
				//checkWin
				private void checkifWin() {
					if(allenemies.size() <= 0) {
						turnsyoualive = -1;
						turnsenemyalive = -1;
						if(!i.armor.get(i.indexselected[1]).armorname.contains("Bloodletter")) {
							i.armor.get(i.indexselected[1]).armordhp = (int)(i.armor.get(i.indexselected[1]).armormaxhp * (Math.pow(1.13, i.armor.get(i.indexselected[1]).itemlevel)));				
						}
						
						if(!i.shields.get(i.indexselected[2]).shieldname.contains("Evolution")) {
							i.shields.get(i.indexselected[2]).shieldcurrenthp = (int)(i.shields.get(i.indexselected[2]).shieldmaxhp * (Math.pow(1.13, i.shields.get(i.indexselected[2]).itemlevel)));;
						}
						
						battle = false;
						tabs.remove(battlez);
						
						for(int a = 0; a < tabs.getTabCount(); a++) {							 
							tabs.setEnabledAt(a, true);							
						}
						
						tabs.setSelectedIndex(0);
						
						//Drops
						i.gold += i.drops * (int)(Math.random() * (storylevel + 1));
						i.emeralds += i.drops * (int)(Math.random() * (storylevel));
						}
					}
					
						
					
				
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

				//checkifDead
				private void checkifDead(boolean player) {
					i.updateInventory();
					
					//Player Death
					if(player) {
						if(i.health <= 0) {
							if(battle) {
								lives--;
								if(lives <= 0) {
									
									battle = false;	
									updateBattle();
									System.out.println("U dead");
									JOptionPane.showMessageDialog(tabs, "You Have Died!");
									
									game.dispose();
									Game g = new Game();
									g.setUp();
									g.save.loadGame();
									
								}else {
									i.health = i.u.hp;
								}
							}
						}
					//Enemy Death	
					}else {
						if(battle) {
							if(allenemies.get(selectedIndex[1]).enemychp <= 0) {
								
								allenemies.remove(selectedIndex[1]);
								
								
								if(selectedIndex[1] >= allenemies.size()) {
									selectedIndex[1] = allenemies.size() -1;
								}
								
								if(allenemies.size() > 0) {
									updateBattle();
								}
								checkifWin();
							}
						}
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				
				//attackEnemy
				private void attackEnemy() {
					//Turn Counter
						JOptionPane.showMessageDialog(tabs, "Turn: " + (turn + 1)); 
					
					if(battle) {
					turn++;
					
					
					//Grim
					if(turnsyoualive > 0) {
						turnsyoualive--;
						
					}else if (turnsyoualive == 0) {
						i.health = -10000;
						turnsyoualive = -1;
						armorEffects();
						shieldEffects();
						checkifDead(true);
					}
					
					//Shield Recharge
					if(turn % i.shields.get(i.indexselected[2]).turntorecharge == 0) {
						i.shields.get(i.indexselected[2]).addCurrentHP(i.shields.get(i.indexselected[2]).shieldrecharged);
					}
					
					//Amplifying (shield)
					if(i.shields.get(i.indexselected[2]).shieldeffect.contains("Amplifying ")) {
						if(i.shields.get(i.indexselected[2]).shieldcurrenthp >= i.shields.get(i.indexselected[2]).shieldmaxhp) {
							dmultiplier += 1.0;
						}else {
							dmultiplier = 1.0;
						}
					}
					
					//Attacks Per Turn
					for(int a = 0; a < i.weapons.get(i.indexselected[0]).attacksperturn; a++) {
						random[0] = (int)(Math.random() * 100);
						
						//Elements
						multiplier = 1;
						
							//Corrosion (Anti-Armor)
							if(battle) {
								if(allenemies.get(selectedIndex[1]).enemycarmor > 0 && i.weapons.get(i.indexselected[0]).weaponelement.contains("Corrosive")) {
									if(allenemies.get(selectedIndex[1]).resistances.contains("Corrosive")) {
										multiplier -= 0.25;
									}else {
										multiplier += 1.0;
									}
								}else {
									if(allenemies.get(selectedIndex[1]).enemycarmor > 0) {
										multiplier -= 0.5;
									}
								}
							
							//Fire (Anti-Flesh)
								if(allenemies.get(selectedIndex[1]).enemycarmor <= 0 && i.weapons.get(i.indexselected[0]).weaponelement.contains("Fire")) {
									if(allenemies.get(selectedIndex[1]).resistances.contains("Fire")) {
										multiplier -= 0.25;
									}else {
										multiplier += 1.0;
									}						
								}
							
							//Electricity (Anti-Shield)
								if(allenemies.get(selectedIndex[1]).enemycshield > 0 && i.weapons.get(i.indexselected[0]).weaponelement.contains("Electricity")) {
									if(allenemies.get(selectedIndex[1]).resistances.contains("Electricity")) {
										multiplier -= 0.25;
									}else {
										multiplier += 1;
									}
								}else {
									if(allenemies.get(selectedIndex[1]).enemycshield > 0) {
										multiplier -= 0.1;
									}
								}
							}
						
					//Accuracy:
						
						//Hit
						if((random[0] <= i.weapons.get(i.indexselected[0]).accuracy) && battle) {
							//Crossbow or Bow 
							if(i.items.get(0).numberofitems > 0 && (i.weapons.get(i.indexselected[0]).weaponname.contains("bow") || i.weapons.get(i.indexselected[0]).weaponname.contains("Bow"))) {
								if(turnsofInvincibilty == 0) {
									if(allenemies.get(selectedIndex[1]).enemycarmor > 0) {
										allenemies.get(selectedIndex[1]).enemycarmor -= i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier;
										JOptionPane.showMessageDialog(tabs, "You Attack " + allenemies.get(selectedIndex[1]).enemyname + " For " + i.shortener((int)(i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier) + "" ) + " Damage");							
									}else {
										allenemies.get(selectedIndex[1]).enemychp -= i.weapons.get(i.indexselected[0]).weapondamage * multiplier;		
										JOptionPane.showMessageDialog(tabs, "You Attack " + allenemies.get(selectedIndex[1]).enemyname + " For " + i.shortener((int)(i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier) + "" ) + " Damage");							
									}
								}
								
								i.items.get(0).numberofitems--;
								
								
							//Normal Weapons	
							}else if (!i.weapons.get(i.indexselected[0]).weaponname.contains("bow") && !i.weapons.get(i.indexselected[0]).weaponname.contains("Bow")){
								if(turnsofInvincibilty == 0) {
									if(allenemies.get(selectedIndex[1]).enemycarmor > 0) {
										allenemies.get(selectedIndex[1]).enemycarmor -= i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier;
										JOptionPane.showMessageDialog(tabs, "You Attack " + allenemies.get(selectedIndex[1]).enemyname + " For " + i.shortener((int)(i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier) + "" ) + " Damage");							
									}else {
										allenemies.get(selectedIndex[1]).enemychp -= i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier;
										JOptionPane.showMessageDialog(tabs, "You Attack " + allenemies.get(selectedIndex[1]).enemyname + " For " + i.shortener((int)(i.weapons.get(i.indexselected[0]).weapondamage * multiplier * dmultiplier) + "" ) + " Damage");							
									}
								}
								
								
							}
							
				
							
							weaponEffects();
							updateBattle();
						
						//Miss
						}else {
							
							//Crossbow/Bow 
							if(i.items.get(0).numberofitems > 0 && (i.weapons.get(i.indexselected[0]).weaponname.contains("bow") || i.weapons.get(i.indexselected[0]).weaponname.contains("Bow"))) {		
								i.items.get(0).numberofitems--;
							}
							
							JOptionPane.showMessageDialog(tabs, "You Miss " + allenemies.get(selectedIndex[1]).enemyname);
							
							//System.out.println("Turn: " + turn);
							//System.out.println("Miss: " + (100 - i.weapons.get(i.indexselected[0]).accuracy) + "%");
							updateBattle();
						}
						
						//Check if Dead
						checkifDead(false);	
					}
					
					//End of Turn						
						checkifDead(false);	
						
						int r1 = (int)(Math.random() * 200);
						int r2 = (int)(Math.random() * 100);
						
						if(battle) {
							//Ice (Turn Canceler)
							if(i.weapons.get(i.indexselected[0]).weaponelement.contains("Ice")) {
								if(allenemies.get(selectedIndex[1]).resistances.contains("Ice")) {
										getAttacked();								
								}else {
									if(r1 >= r2) {
										getAttacked();
									}else {
										JOptionPane.showMessageDialog(tabs, "Enemies Frozen");
										
									}
								}
							}else {
								getAttacked();
							}
						
						}
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

				//getAttacked
				private void getAttacked() {
					for(int a = 0; a < allenemies.size(); a++) {
						random[0] = (int)(Math.random() * 100);
						
						if(turnsenemyalive > 0) {
							JOptionPane.showMessageDialog(tabs, "Grim On " + allenemies.get(selectedIndex[1]).enemyname + " For " + turnsenemyalive + " Turns");
							turnsenemyalive--;
							
						}else if (turnsenemyalive == 0) {
							JOptionPane.showMessageDialog(tabs, "Grim On " + allenemies.get(selectedIndex[1]).enemyname + " For " + turnsenemyalive + " Turns");
							allenemies.get(selectedIndex[1]).enemychp = -10000;
							turnsenemyalive = -1;
							checkifDead(false);
						}
						
						//Elements
						emultiplier = 1;
							if(battle) {
							//Corrosion (Anti-Armor)
							if(allenemies.get(a).resistances.contains("Corrosive") && i.armor.get(i.indexselected[2]).armordhp > 0) {
								if(i.armor.get(i.indexselected[1]).armorresist.contains("Corrosive")) {
										emultiplier -= 0.25;
									}else {
										emultiplier += 1.0;
									}
								}else {
									if(i.armor.get(i.indexselected[2]).armordhp > 0) {
										emultiplier -= 0.5;
									}
								}
							
							//Fire (Anti-Flesh)
								if(allenemies.get(a).resistances.contains("Fire") && i.armor.get(i.indexselected[2]).armordhp <= 0) {
									if(i.armor.get(i.indexselected[1]).armorresist.contains("Fire")) {
										emultiplier -= 0.25;
									}else {
										emultiplier += 1.0;
									}
								}
							
							//Electricity (All-Rounder)
								if(allenemies.get(a).resistances.contains("Electricity") && i.shields.get(i.indexselected[1]).shieldcurrenthp > 0) {
									if(i.armor.get(i.indexselected[1]).armorresist.contains("Electricity")) {
										emultiplier -= 0.1;
									}else {
										emultiplier += 0.5;
									}
									
								}else {
									if(i.shields.get(i.indexselected[1]).shieldcurrenthp > 0) {
										emultiplier -= 0.25;
									}
								}
							}
						//Attack Randomizer
						if(random[0] <= 60) {
							random[0] = 0;
						}else if(random[0] <= 90) {
							random[0] = 1;
						}else {
							random[0] = 2;
						}
						
						//Invincibility Drain
						if(turnsofInvincibilty > 0) {
							turnsofInvincibilty--;
						}
					
						//Attack
						String s = allenemies.get(a).enemyattackeffect[random[0]];
						damagedone = 0;
						
						if(battle) {
							//Damage
							if(s.contains("DMG")) {
								i.damage(((int)(Integer.parseInt(s.substring(4,s.length() - 1)) * Math.pow(1.13, storylevel - 1) * emultiplier)));
								damagedone = ((int)(Integer.parseInt(s.substring(4,s.length() - 1)) * Math.pow(1.13, storylevel - 1) * emultiplier));
								JOptionPane.showMessageDialog(tabs, allenemies.get(a).enemyname + " Attacked You For " + i.shortener((int)((Integer.parseInt(s.substring(4,s.length() - 1)) * emultiplier) * Math.pow(1.13, storylevel - 1)) + "") + " Damage");
															
							//Clone
							}else if (s.contains("CLONE")) {
								allenemies.add(allenemies.get(a).clone(s.substring(0, 4) == "true", (Integer.parseInt(s.substring(11,s.length() - 1))), allenemies.size() + 1, storylevel , 0));
								JOptionPane.showMessageDialog(tabs, allenemies.get(a).enemyname + " Summoned " + allenemies.get(allenemies.size() - 1).enemyname);
								selectedIndex[1] = allenemies.size() - 1;
								updateBattle();
							//Heal
							}else if (s.contains("HEAL")) {					
								allenemies.get(a).heal(((int)((Integer.parseInt(s.substring(5,s.length() - 1)) * Math.pow(1.13, storylevel - 1)))));
								JOptionPane.showMessageDialog(tabs, allenemies.get(a).enemyname + " Healed For " + i.shortener((int)(Integer.parseInt(s.substring(5,s.length() - 1)) * Math.pow(1.13, storylevel - 1)) + "") + " Health");
							
							//Invincibility
							}else if (s.contains("INVINCIBLE")) {								
								turnsofInvincibilty = (int)((Integer.parseInt(s.substring(11,s.length() - 1))));
								JOptionPane.showMessageDialog(tabs, allenemies.get(a).enemyname + " Became Invincible For " + turnsofInvincibilty + " Turns");
							
							}else if (s.contains("GRIM")) {								
								turnsyoualive = (int)((Integer.parseInt(s.substring(5,s.length() - 1))));
								JOptionPane.showMessageDialog(tabs, allenemies.get(a).enemyname + " Marked You For " + turnsofInvincibilty + " Turns Till Death");
							
							}else if (s.contains("PIERCE")){
								i.health -= ((int)(Integer.parseInt(s.substring(4,s.length() - 1)) * Math.pow(1.13, storylevel - 1) * emultiplier));
								damagedone = ((int)(Integer.parseInt(s.substring(4,s.length() - 1)) * Math.pow(1.13, storylevel - 1) * emultiplier));
								JOptionPane.showMessageDialog(tabs, allenemies.get(a).enemyname + " Pierced Your Shields And Attacked You For " + i.shortener((int)((Integer.parseInt(s.substring(4,s.length() - 1)) * emultiplier) * Math.pow(1.13, storylevel - 1)) + "") + " Damage");
									
							}
							
							//End of Turn
							armorEffects();
							shieldEffects();
							updateBattle();							
							checkifDead(true);
							int r1 = (int)(Math.random() * 300);
							int r2 = (int)(Math.random() * 100);
							
							if(battle) {
								//Ice (Turn Canceler)
								if(allenemies.get(selectedIndex[1]).resistances.contains("Ice")) {
									if(i.armor.get(i.indexselected[1]).armorresist.contains("Ice")) {
																			
									}else {
										if(r1 <= r2) {
											JOptionPane.showMessageDialog(tabs, "You've Been Frozen");
											getAttacked();
										}
									}
								}else {
									
								}
							
							}
						}
					}	
				}
							
				
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				
				//Update battle
				private void updateBattle() {
					if(battle) {
					enemyhp.setText("HP: " + i.shortener(allenemies.get(selectedIndex[1]).enemychp + "") +  "; Armor: " + i.shortener(allenemies.get(selectedIndex[1]).enemycarmor + "") +  "; Shield: " + i.shortener(allenemies.get(selectedIndex[1]).enemycshield + ""));
					enemyname.setText("Name: " + allenemies.get(selectedIndex[1]).enemyname);
					
					if((i.weapons.get(i.indexselected[0]).weaponname.contains("bow") || i.weapons.get(i.indexselected[0]).weaponname.contains("Bow"))) {
						playername.setText("Name: Player; Arrows: " + i.shortener(i.items.get(0).numberofitems + ""));
					}else {
						playername.setText("Name: Player");
					}
					
					playerhp.setText("HP: " + i.shortener(i.health + "") + "; Armor: " + i.shortener(i.armor.get(i.indexselected[1]).armordhp + "") + "; Shield: " + i.shortener(i.shields.get(i.indexselected[2]).shieldcurrenthp + ""));
					}
				}
				
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				//Button Prompts
				private class attack implements ActionListener{
					public void actionPerformed(ActionEvent ae) { 
						attackEnemy();
					}
				}
				
				private class leftarrow implements ActionListener{
					public void actionPerformed(ActionEvent ae) {
							updateBattle();
							if((selectedIndex[1] > 0)) {
								selectedIndex[1]--;
								updateBattle();
								//System.out.println(selectedIndex[1]);
							}else {
								selectedIndex[1] = allenemies.size() - 1;
								updateBattle();
								//System.out.println(selectedIndex[1]);
							}
							
												
							updateBattle();
							
						
				}
				}
				
				private class rightarrow implements ActionListener{
					public void actionPerformed(ActionEvent ae) { 
							updateBattle();
							if((selectedIndex[1] < allenemies.size() - 1)) {
								selectedIndex[1]++;
								updateBattle();
								//System.out.println(selectedIndex[1]);
							}else {
								selectedIndex[1] = 0;
								updateBattle();
								//System.out.println(selectedIndex[1]);
							}
							
							updateBattle();
						
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//Setup Battle
				public void setUp() {
					battlez = new JPanel();
					battlez.setLayout(new GridLayout(2,1,1,1));
					JPanel stats = new JPanel();
					JPanel stats2 = new JPanel();
					JPanel fight = new JPanel();
					JPanel fullstats = new JPanel();
					
					stats.setLayout(new GridLayout(2,2,1,1));
					stats.add(playername);
					stats.add(enemyname);
					
					stats.add(playerhp);
					stats.add(enemyhp); 
					
					stats2.setLayout(new GridLayout(2,4,1,1));
					stats2.add(leftplayer);
					stats2.add(rightplayer);
					
					leftenemy.addActionListener(new leftarrow());
					stats2.add(leftenemy);
					rightenemy.addActionListener(new rightarrow());
					stats2.add(rightenemy);
					
					stats2.add(new JLabel(""));
					stats2.add(new JLabel(""));
					stats2.add(new JLabel(""));
					stats2.add(new JLabel(""));
					
					fullstats.setLayout(new GridLayout(2,1,1,1));
					fullstats.add(stats);
					fullstats.add(stats2);
					
					fight.setLayout(new GridLayout(1,2,1,1));
					fight.add(new JButton("player"));
					enemy = new JButton("enemy");
					enemy.addActionListener(new attack());
					fight.add(enemy);
					
					battlez.add(fullstats);
					battlez.add(fight);
					updateBattle();
				}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				
			//Item Effects
				private int weaponEffects() {
					dmultiplier	= 1;
					
					if(battle) {
						int r1 = (int)(Math.random() * 200);
						int r2 = (int)(Math.random() * 100);
						//System.out.println(r1 + " vs " + r2);
						
					
					//Grim	
					if(i.weapons.get(i.indexselected[0]).weaponeffect.contains("Grim") && (r1 <= r2)) {
						if(turnsenemyalive == -1) {				
							turnsenemyalive = (int)(Math.random() * 5) + 1;
							JOptionPane.showMessageDialog(tabs, "Grim On " + allenemies.get(selectedIndex[1]).enemyname + " Applied For " + turnsenemyalive + " Turns");
							
						}else if (turnsenemyalive - 1 > 0){
							JOptionPane.showMessageDialog(tabs, "Grim On " + allenemies.get(selectedIndex[1]).enemyname + " Decreased To " + turnsenemyalive + " Turns");
							turnsenemyalive--;
							
						}else {
							turnsenemyalive = 0;
						}
						
						
					}
					
					//Critting
					if(i.weapons.get(i.indexselected[0]).weaponeffect.contains("Critting") && (r1 <= r2)) {
						allenemies.get(selectedIndex[1]).enemychp -= i.weapons.get(i.indexselected[0]).weapondamage * 10;		
						JOptionPane.showMessageDialog(tabs, "Critting For " + i.shortener((i.weapons.get(i.indexselected[0]).weapondamage * 10) + "") + " Damage");
					}
					
					
					
					//Infinite
					if(i.weapons.get(i.indexselected[0]).weaponeffect.contains("Infinite") && (r1 <= r2)) {
						JOptionPane.showMessageDialog(tabs, "Infinite: Extra Turn");
						attackEnemy();
					}	
							
					}
					
					//One-Shot Legendary
					if(i.weapons.get(i.indexselected[0]).weaponname.contains("One-Shot")) {
						i.weapons.remove(i.indexselected[0]);
						
						if(i.weapons.size() < 1) {
							i.weapons.add(i.indexselected[0], new Weapon("One-Shot Shards", "Common", 1, 0, 0, 0, 1, "That Shot Was Taken..", 0, 100));
						}else {
							if(i.indexselected[0] > i.weapons.size() - 1) {
								i.indexselected[0] = i.weapons.size() - 1;
							}
						}
						
					}
					
					return -1;
					
				}
				
				private int shieldEffects() {
					if(battle) {
						
					//Evolution Legendary	
					if(i.shields.get(i.indexselected[2]).shieldname.contains("Evolution") && (turn% i.shields.get(i.indexselected[2]).turntorecharge) == 0) {
						if(i.shields.get(i.indexselected[2]).shieldcurrenthp > 1) {							
							i.shields.get(i.indexselected[2]).shieldcurrenthp += ((i.shields.get(i.indexselected[2]).shieldmaxhp) - damagedone);
						}
					}
					
					//Deathless Legendary
					if(i.shields.get(i.indexselected[2]).shieldname.contains("Deathless")) {
						if(i.health <= 0 && i.shields.get(i.indexselected[2]).shieldcurrenthp > 0) {
							i.health = 100;
						}
					}
					
					//Gump Legendary
					if(i.shields.get(i.indexselected[2]).shieldname.contains("Gump")) {
						int r1 = (int)(Math.random() * 100);

							if(r1 <= 5) {
								i.health *= 2;
								//System.out.println("I never thanked you for saving my life.");
								
							}else if(r1 <= 10) {
								i.shields.get(i.indexselected[2]).shieldcurrenthp *= 2;
								//System.out.println("What’s normal anyways?");
								
							}else if(r1 <= 15) {
								i.armor.get(i.indexselected[1]).armordhp *= 2;
								//System.out.println("You have to do the best with what God gave you.");
								
							}else if(r1 <= 17) {
								allenemies.get(selectedIndex[1]).enemychp = -10000;
								turnsenemyalive = -1;
								checkifDead(false);
								//System.out.println("Mama always said, dying was a part of life. I sure wish it wasn’t.");
							
							
							}else if (r1 <= 19) {
								allenemies = new ArrayList<Enemy>();
								//System.out.println("I’m pretty tired… I think I’ll go home now.");
								checkifWin();
							}else if (r1 <= 50) {	
							
								//Add more effects to Gump
								
							}else if(r1 <= 100) {						
								turn++;
								//System.out.println("My Mama always said you’ve got to put the past behind you before you can move on.");
							}
							
						//End
					}
					
					
						//Thorns
						if(i.shields.get(i.indexselected[2]).shieldeffect.contains("Thorny")) {
							if(i.shields.get(i.indexselected[2]).shieldcurrenthp > 0) {
								allenemies.get(selectedIndex[1]).enemychp -= (int)(10 * Math.pow(1.13 , i.shields.get(i.indexselected[2]).itemlevel) - 1);
							}
						}
						
						//Singularity
						if(i.shields.get(i.indexselected[2]).shieldeffect.contains("Singularity ")) {
							if(i.shields.get(i.indexselected[2]).shieldcurrenthp <= 0) {
								allenemies.get(selectedIndex[1]).enemychp -= (int)(50 * Math.pow(1.13 , i.shields.get(i.indexselected[2]).itemlevel) - 1);
							}
						}
						
						//Reflecting
						if(i.shields.get(i.indexselected[2]).shieldeffect.contains("Reflecting ")) {
							if(i.shields.get(i.indexselected[2]).shieldcurrenthp > 0 && damagedone > 0) {
								i.shields.get(i.indexselected[2]).shieldcurrenthp += (damagedone / 4);
								allenemies.get(selectedIndex[1]).enemychp -= damagedone;
							}
						}
						
						//Absorbing
						if(i.shields.get(i.indexselected[2]).shieldeffect.contains("Absorbing ")) {
							if(i.shields.get(i.indexselected[2]).shieldcurrenthp > 0 && damagedone > 0) {
								i.shields.get(i.indexselected[2]).shieldcurrenthp += (damagedone / 10);
								dmultiplier += 0.25;
							}
						}
											
					}
					
					
					return -1;
				}
				
				private int armorEffects() {
					if(battle) {						
					//Bloodletter Legendary	
					if(i.armor.get(i.indexselected[1]).armorname.contains("Bloodletter")) {
						if(i.health > 1) {
							i.armor.get(i.indexselected[1]).armordhp += (i.health - 1);
							i.health = 1;
						}
					}
					}
					return -1;
				}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				

}