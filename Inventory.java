package RolePlayingGame_v2;

//Imports
	import java.util.ArrayList;
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;
	import java.awt.GridLayout;

public class Inventory {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables (part 1):
			//General:
				//Player
					int health = 1;
					int level = 1;
					int xp = 0;
					int gold = 100;
					
					//Upgrades
					int emeralds = 0;
					int tokens = 0;
					int drops = 1;
					
					int x = 200;
					int y = 200;
					int[] location = new int[]{0,0};
					
				//JLabels
					private JLabel showHP = new JLabel();
					private JLabel showXP = new JLabel();
					private JLabel showLvl = new JLabel();
					private JLabel showGold = new JLabel();
					
					private JLabel name = new JLabel();
					private JLabel stat = new JLabel();
				
				//JButtons
					private JButton view = new JButton("View Item");
					private JButton use = new JButton("");
					
				//JComboBoxes
					private JComboBox<String> allTypes = new JComboBox<String>(new String[] {"Weapons","Armor","Shield","Items"});
				
				//JPanel
					JPanel playerinventory = new JPanel();
					
					
				//JTabbedPane
					private JTabbedPane tabs = new JTabbedPane();
					
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------					
	//Variables (part 2):				
			//Inventory Management:
				//General
					Upgrades u;
					int inventoryslots = 2;
					int[] indexselected = new int[] {0,0,0,0};
					int type = 0;
					
				//Weapon Management		
					ArrayList<Weapon> weapons = new ArrayList<Weapon>();
					ArrayList<Armor> armor = new ArrayList<Armor>();
					ArrayList<Shield> shields = new ArrayList<Shield>();
					
				//Item Management
					ArrayList<Item> items = new ArrayList<Item>();
					
				//ViewItem
					JPanel showedItem = new JPanel();
					
					JPanel itemstats = new JPanel();
					private JLabel itemname = new JLabel();
					private JLabel itemstat1 = new JLabel() ;
					private JLabel itemstat2= new JLabel();
					private JLabel itemstat3= new JLabel();
					private JLabel itemstat4= new JLabel();
					private JLabel itemstat5= new JLabel();
					
					private JLabel redtext= new JLabel();
					private JButton leave = new JButton("Back");
					
					private JLabel icon = new JLabel("");
					int lastindex;
					
				
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Public Methods
		//Constructor
			public Inventory(JTabbedPane tabs, Upgrades u) {
				this.tabs = tabs;
				this.u = u;
				
				//Weapons: name, rarity, dmg, type, effect, element, attacks, text, id, accuracy
				weapons.add(new Weapon("Wooden Sword","Common", 10, 0, 0, 0, 1, "", 0, 75));
				
				//Sheilds: name, rarity, block, effect, text, hp, turns, recharge, id
				shields.add(new Shield("Wooden Shield","Common", 5,0, "",50, 2, 25, 0));
				
				//Armor: name, rarity, hp, redtext, id, effect
				armor.add(new Armor("Leather Armor", "Common", 50, "", 0, 0, 0));
				
								
				//Items: name, number, id
				items.add(new Item("Arrows", 100, 0));
				items.add(new Item("Weak Healing Potion", 10, 0));
				
				
				updateInventory();		
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Inventory UI Setup
			public void setUp() {
				//Setup
					//Layout
						playerinventory.setLayout(new GridLayout(6,2,0,0));
					
					//Levels
						showLvl.setBorder(BorderFactory.createEtchedBorder());
						playerinventory.add(showLvl);
					
					//XP
						showXP.setBorder(BorderFactory.createEtchedBorder());
						playerinventory.add(showXP);
					
					//Gold
						showGold.setBorder(BorderFactory.createEtchedBorder());
						playerinventory.add(showHP);
					
					//HP
						showHP.setBorder(BorderFactory.createEtchedBorder());
						playerinventory.add(showGold);
					
					//Name
						name.setBorder(BorderFactory.createEtchedBorder());
						playerinventory.add(name);
					
					//Stat
						stat.setBorder(BorderFactory.createEtchedBorder());
						playerinventory.add(stat);
					
					//Arrows
						JButton arrow = new JButton("<---");
						arrow.addActionListener(new leftArrow());
						playerinventory.add(arrow);
					
						arrow = new JButton("--->");
						arrow.addActionListener(new rightArrow());
						playerinventory.add(arrow);
					
					//View Item
						view.addActionListener(new viewItem());
						playerinventory.add(view);
						
					//Use
						use.addActionListener(new useItem());
						playerinventory.add(use);
					
					//All Types
						allTypes.addActionListener(new changer());
						playerinventory.add(allTypes);
						playerinventory.add(new JButton(""));
						
					setupshowedItem();
				//End
			}
			
			public void setLocal(int a, int b, int c, int d) {
				x = a;
				y = b;
				location[0] = c;
				location[1] = d;
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//SetUp Show Item
			public void setupshowedItem() {
				showedItem.setLayout(new GridLayout(1,2,1,1));
				itemstats.setLayout(new GridLayout(8,1,1,1));
				itemstats.add(itemname);
				itemstats.add(itemstat1);     
				itemstats.add(itemstat2); 
				itemstats.add(itemstat3);     
				itemstats.add(itemstat4);
				itemstats.add(itemstat5);
				itemstats.add(redtext);
				
				leave.addActionListener(new back());
				itemstats.add(leave);
				
				showedItem.add(itemstats);
				showedItem.add(icon);
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Private Methods
		//UI
			public void updateInventory() {
				//General
					xp = u.xp;
				
					showHP.setText("HP: " + shortener(health + ""));
					showXP.setText("XP: " + shortener(xp + "") + "/" + shortener((int)((60 * Math.pow(this.level + 1, 2.8)) - 60) + ""));
					showLvl.setText("Level: " + level );
					showGold.setText("Gold: " + shortener(gold + ""));
				
					
				//For Weapons
					if(allTypes.getSelectedItem() == "Weapons") {
						name.setText("Weapon Name: " + weapons.get(indexselected[type]).weaponeffect + weapons.get(indexselected[type]).weaponname);
						stat.setText("Weapon Dmg: " + shortener(weapons.get(indexselected[type]).weapondamage + ""));
						
						if(weapons.get(indexselected[type]).weaponrarity == "Common") {
							name.setForeground(Color.gray);
							stat.setForeground(Color.gray);
							
						}else if (weapons.get(indexselected[type]).weaponrarity == "Uncommon") {
							name.setForeground(Color.green);
							stat.setForeground(Color.green);
							
						}else if (weapons.get(indexselected[type]).weaponrarity == "Rare") {
							name.setForeground(Color.blue);
							stat.setForeground(Color.blue);
							
						}else if (weapons.get(indexselected[type]).weaponrarity == "Epic") {
							name.setForeground(Color.magenta);
							stat.setForeground(Color.magenta);
							
						}else if (weapons.get(indexselected[type]).weaponrarity == "Legendary") {
							name.setForeground(Color.orange);
							stat.setForeground(Color.orange);
						}
						
				//For Shields
					}else if(allTypes.getSelectedItem() == "Shield") {
						name.setText("Shield Name: " + shields.get(indexselected[type]).shieldeffect + shields.get(indexselected[type]).shieldname);
						stat.setText("Shield Protection: " + shields.get(indexselected[type]).shieldblock + "%");
						
						if(shields.get(indexselected[type]).shieldrarity == "Common") {
							name.setForeground(Color.gray);
							stat.setForeground(Color.gray);
							
						}else if (shields.get(indexselected[type]).shieldrarity == "Uncommon") {
							name.setForeground(Color.green);
							stat.setForeground(Color.green);
							
						}else if (shields.get(indexselected[type]).shieldrarity == "Rare") {
							name.setForeground(Color.blue);
							stat.setForeground(Color.blue);
							
						}else if (shields.get(indexselected[type]).shieldrarity == "Epic") {
							name.setForeground(Color.magenta);
							stat.setForeground(Color.magenta);
							
						}else if (shields.get(indexselected[type]).shieldrarity == "Legendary") {
							name.setForeground(Color.orange);
							stat.setForeground(Color.orange);
						}
						
				//For Armor
					}else if(allTypes.getSelectedItem() == "Armor") {
			
						name.setText("Armor Name: " + armor.get(indexselected[type]).armoreffect + armor.get(indexselected[type]).armorname);
						stat.setText("Armor HP: " + shortener(armor.get(indexselected[type]).armordhp + ""));
						
						if(armor.get(indexselected[type]).armorrarity == "Common") {
							name.setForeground(Color.gray);
							stat.setForeground(Color.gray);
							
						}else if (armor.get(indexselected[type]).armorrarity == "Uncommon") {
							name.setForeground(Color.green);
							stat.setForeground(Color.green);
							
						}else if (armor.get(indexselected[type]).armorrarity == "Rare") {
							name.setForeground(Color.blue);
							stat.setForeground(Color.blue);
							
						}else if (armor.get(indexselected[type]).armorrarity == "Epic") {
							name.setForeground(Color.magenta);
							stat.setForeground(Color.magenta);
							
						}else if (armor.get(indexselected[type]).armorrarity == "Legendary") {
							name.setForeground(Color.orange);
							stat.setForeground(Color.orange);
						}
					}
					
				//For Items 
					if(allTypes.getSelectedItem() == "Items") {
						name.setText("Item: " + items.get(indexselected[type]).itemname);
						name.setForeground(Color.black);
						
						stat.setText("Number of Items: " + shortener(items.get(indexselected[type]).numberofitems + ""));
						stat.setForeground(Color.black);
					}
					
				
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//UI Elements
			
			//View Item
			private class viewItem implements ActionListener{
				public void actionPerformed(ActionEvent ae) {
					lastindex = tabs.getSelectedIndex();
					for(int a = 0; a < tabs.getTabCount(); a++) {						
						tabs.setEnabledAt(a, false);
					}
					
					tabs.insertTab("Item", null, showedItem, null, 0);
					tabs.setSelectedIndex(0);
					
					itemname.setForeground(Color.black);
					itemstat1.setForeground(Color.black);
					itemstat2.setForeground(Color.black);
					itemstat3.setForeground(Color.black);
					itemstat4.setForeground(Color.black);
					itemstat5.setForeground(Color.black);
					redtext.setForeground(Color.black);
					
					if (type == 0) {
						itemname.setText("Name: " + weapons.get(indexselected[type]).weaponname);
						itemstat1.setText("Damage: " + shortener(weapons.get(indexselected[type]).weapondamage + ""));
						itemstat2.setText("Accuracy: "+ weapons.get(indexselected[type]).accuracy);
						itemstat3.setText("Attacks Per Turn: "+ weapons.get(indexselected[type]).attacksperturn);
						
						if(weapons.get(indexselected[type]).weaponeffect != "") {
							itemstat4.setText("Effect: "+ weapons.get(indexselected[type]).weaponeffect);
							
						}else {
							itemstat4.setText("Effect: N/A");
						}
						

						if(weapons.get(indexselected[type]).weaponelement != "") {
							itemstat5.setText("Element: "+ weapons.get(indexselected[type]).weaponelement);
							
						}else {
							itemstat5.setText("Element: N/A");
						}
						
						if(weapons.get(indexselected[type]).redtext != "") {
							redtext.setText("\"" + weapons.get(indexselected[type]).redtext + "\"");
							redtext.setForeground(Color.red);
						}else {
							redtext.setText("");
						}
						
					}else if (type == 1) {
						itemname.setText("Name: " + armor.get(indexselected[type]).armorname);
						itemstat1.setText("HP: " + shortener(armor.get(indexselected[type]).armordhp + ""));
						
						if(armor.get(indexselected[type]).armoreffect != "") {
							itemstat2.setText("Effect: " + armor.get(indexselected[type]).armoreffect);
						}else {
							itemstat2.setText("Effect: N/A");	
						}
						
						
						if(armor.get(indexselected[type]).armorresist != "") {
							itemstat3.setText("Resistance: " + armor.get(indexselected[type]).armorresist);
						}else {
							itemstat3.setText("Resistance: N/A");	
						}
						
						if(armor.get(indexselected[type]).redtext != "") {
							itemstat4.setText("\"" + armor.get(indexselected[type]).redtext + "\"");
							itemstat4.setForeground(Color.red);
						}else {
							itemstat4.setText("");
						}
						
						
						itemstat5.setText("");
						redtext.setText("");
						
					}else if (type == 2) {
						itemname.setText("Name: " + shields.get(indexselected[type]).shieldname);
						itemstat1.setText("HP: " + shortener(shields.get(indexselected[type]).shieldcurrenthp + ""));
						itemstat2.setText("Protection: "+ shields.get(indexselected[type]).shieldblock + "%");
						itemstat3.setText("Turns till Recharge: " + shields.get(indexselected[type]).turntorecharge);
						itemstat4.setText("Shield Recharged: " + shields.get(indexselected[type]).shieldrecharged);
						
						if(shields.get(indexselected[type]).shieldeffect != "") {
							itemstat5.setText("Effect: " + shields.get(indexselected[type]).shieldeffect);
						}else {
							itemstat5.setText("Effect: N/A");
						}
						
						if(shields.get(indexselected[type]).redtext != "") {
							redtext.setText("\"" + shields.get(indexselected[type]).redtext + "\"");
							redtext.setForeground(Color.red);
						}else {
							redtext.setText("");
						}
						
					}else if (type == 3){
						itemname.setText("Name: " + items.get(indexselected[type]).itemname);
						itemstat1.setText("Number: " + shortener(items.get(indexselected[type]).numberofitems + ""));
						itemstat2.setText("");
						itemstat3.setText("");
						itemstat4.setText("");
						itemstat5.setText("");
						redtext.setText("");
						
					}
				
					
			}
			}
			
			private class back implements ActionListener{
				public void actionPerformed(ActionEvent ae) {
					tabs.remove(showedItem);
					
					for(int a = 0; a < tabs.getTabCount(); a++) {						
						
							tabs.setEnabledAt(a, true);
						
					}
					
					if(tabs.indexOfTab("Battle") != -1) {
						tabs.setEnabledAt(1, false);
					}
					
					if(tabs.indexOfTab("Shop") != -1) {
						tabs.setEnabledAt(0, false);
					}
					tabs.setSelectedIndex(lastindex);
					
				}
			}
			//Use Item
			private class useItem implements ActionListener{
				public void actionPerformed(ActionEvent ae) { 
					if(ae.getActionCommand() == "Use") {
						
						//Detects 
						if(items.get(indexselected[type]).numberofitems > 0) {
							
							//Detects Types of Healing Potions and HP < Max HP
							if(items.get(indexselected[type]).itemname.contains("eal") && (health < u.hp)) {
								
								items.get(indexselected[type]).useItem();
								
								if(items.get(indexselected[type]).itemname.contains("eak")) {
									health += 10 * level;
									
								}else if(items.get(indexselected[type]).itemname.contains("edium")) {
									health += 50 * level;
									
								}else if(items.get(indexselected[type]).itemname.contains("trong")) {
									health += 100 * level;
									
								}
							}
							
							
						}
						
						updateInventory();
					}
				}
			}
			
			//Left Arrow
			private class leftArrow implements ActionListener{
				public void actionPerformed(ActionEvent ae) { 
				
					if(type == 0) {
						if(indexselected[type] != 0) {
							indexselected[type]--;
							updateInventory();
						}else {
							indexselected[type] = weapons.size() - 1;
							updateInventory();
						}
					}else if (type == 1) {
						if(indexselected[type] != 0) {
							indexselected[type]--;
							updateInventory();
						}else {
							indexselected[type] = armor.size() - 1;
							updateInventory();
						}
					}else if (type == 2) {
						if(indexselected[type] != 0) {
							indexselected[type]--;
							updateInventory();
						}else {
							indexselected[type] = shields.size() - 1;
							updateInventory();
						}
					}else {
						if(indexselected[type] != 0) {
							indexselected[type]--;
							updateInventory();
						}else {
							indexselected[type] = items.size() - 1;
							updateInventory();
						}
					}
				}
			}
			
			//Right Arrow
			private class rightArrow implements ActionListener{
				public void actionPerformed(ActionEvent ae) { 
					if(type == 0) {
						if((indexselected[type] != weapons.size() - 1)) {
							indexselected[type]++;
							updateInventory();
						}else {
							indexselected[type] = 0;
							updateInventory();
						}
					}else if (type == 1) {
						if((indexselected[type] != armor.size() - 1)) {
							indexselected[type]++;
							updateInventory();
						}else {
							indexselected[type] = 0;
							updateInventory();
						}
					}else if (type == 2) {
						if((indexselected[type] != shields.size() - 1)) {
							indexselected[type]++;
							updateInventory();
						}else {
							indexselected[type] = 0;
							updateInventory();
						}
					}else {
						if(indexselected[type] != items.size() - 1) {
							indexselected[type]++;
							updateInventory();
						}else {
							indexselected[type] = 0;
							updateInventory();
						}
					}
				}
			}
			
			//Changing Type
			private class changer implements ActionListener{
				public void actionPerformed(ActionEvent ae) { 
					type = allTypes.getSelectedIndex();
					updateInventory();
					
					if(allTypes.getSelectedItem() == "Items") {
						use.setText("Use");
					}else {
						use.setText("");
					}
				}
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			public String shortener(String str) {
				String s = str;
				
				if(s.length() > 13) {
					s = (Integer.parseInt(s) + 1*Math.pow(10, 12)) + "";
					s = s.substring(0, s.length() - 12) + "T";
					
				}else if (s.length() > 10) {
					s = (Integer.parseInt(s) + 1*Math.pow(10, 9)) + "";
					s = s.substring(0, s.length() - 9) + "B";
					
				}else if (s.length() > 7) {
					s = (Integer.parseInt(s) + 1*Math.pow(10, 6)) + "";
					s = s.substring(0, s.length() - 6) + "M";
					
				}else if (s.length() > 4) {
					s = ((int)Integer.parseInt(s) + 1*Math.pow(10, 3)) + "";
					s = s.substring(0, str.length() - 3) + "K";
				}
				
				return s;
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//Damage Player
				public void damage(int dmg) {
					//If Shield Up
					if(shields.get(indexselected[2]).shieldcurrenthp > 0) {
						//If Armor Up
						if(armor.get(indexselected[1]).armordhp > 0) {
							armor.get(indexselected[1]).armordhp -= ((double)(100.00 - shields.get(indexselected[2]).shieldblock)/100.0) * dmg;
							shields.get(indexselected[2]).shieldcurrenthp-= ((double)(shields.get(indexselected[2]).shieldblock)/100.0) * dmg;
							
						//If Armor Down
						}else {
							health -= ((double)(100.00 - shields.get(indexselected[2]).shieldblock)/100.0) * dmg;
							shields.get(indexselected[2]).shieldcurrenthp -= ((double)(shields.get(indexselected[2]).shieldblock)/100.0) * dmg;
						}
						
					//If Shield Down	
					}else {
						//If Armor Up
						if(armor.get(indexselected[1]).armordhp > 0) {
							armor.get(indexselected[1]).armordhp -= dmg;
							
						//If Armor Down
						}else {
							health -= dmg;
						}
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
