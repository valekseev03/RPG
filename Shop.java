package RolePlayingGame_v2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;




public class Shop {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Variables (part 1):	
	//Current Stock
		ArrayList<Weapon> weaponstock = new ArrayList<Weapon>();
		ArrayList<Armor> armorstock = new ArrayList<Armor>();
		ArrayList<Shield> shieldstock = new ArrayList<Shield>();
		ArrayList<Item> itemstock = new ArrayList<Item>();
		
	//Buy Back
		ArrayList<Weapon> soldWeapons = new ArrayList<Weapon>();
		ArrayList<Armor> soldArmor = new ArrayList<Armor>();
		ArrayList<Shield> soldShield = new ArrayList<Shield>();
		ArrayList<Item> soldItem = new ArrayList<Item>();
	
	//General
		int type = 0;
		int level = 1;
		int[] selectedIndex = new int[] {0,0,0,0};
		
		Inventory i;
		JTabbedPane tabs;
		
	//Swing
		JPanel shop = new JPanel();
		
		private JLabel name = new JLabel();
		private JLabel stat = new JLabel();
		
		private JPanel shopIcon = new JPanel();
		private JPanel shopStock = new JPanel();
		
		private JButton leftB = new JButton("<--");
		private JButton rightB = new JButton("-->");
		private JButton buyS = new JButton("Buy");
		private JButton sellS = new JButton("Sell");
		private JButton back = new JButton("Back");
		private JButton viewz = new JButton("View Item");
		int lastindex = 0;
	
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
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructor
	public Shop(Inventory i, JTabbedPane tabs) {
		this.i = i;
		this.tabs = tabs;
		weaponstock.add(new Weapon("Common", 0, "","",1));
		setupshowedItem();
		
		for(int a = 0; a < 4; a++) {
			type = a;
			stock();
		}
	}
	
	//Set Store
	public void setStore(int typ, int lvl) {
		type = typ;
		level = lvl;
		selectedIndex = new int[] {0,0,0,0};		
		updateShop();
		tabs.add("Shop",shop);
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Public Methods
	
		//buy
		public void buy() {
			i.updateInventory();
			
			//weapons
			if(type == 0 && i.weapons.size() != i.inventoryslots) {
				if(i.gold >= (2* weaponstock.get(selectedIndex[type]).weapondamage * rarityvalue(weaponstock.get(selectedIndex[type]), null, null))) {
					i.weapons.add(weaponstock.get(selectedIndex[type]));
					i.gold -= 2*(weaponstock.get(selectedIndex[type]).weapondamage * rarityvalue(weaponstock.get(selectedIndex[type]), null, null));
					weaponstock.remove(selectedIndex[type]);
					if(selectedIndex[type] > weaponstock.size() - 1) {
						selectedIndex[type] = weaponstock.size() - 1;
						if(weaponstock.size() < 1) {
							stock();
							selectedIndex[type] = 0;
						}
					}
				}
			//armor
			}else if (type == 1 && i.armor.size() < i.inventoryslots) {
				if(i.gold >= (2 *armorstock.get(selectedIndex[type]).armormaxhp * rarityvalue(null, armorstock.get(selectedIndex[type]), null))) {
					i.armor.add(armorstock.get(selectedIndex[type]));
					i.gold -= 2*(armorstock.get(selectedIndex[type]).armormaxhp * rarityvalue(null, armorstock.get(selectedIndex[type]), null));
					armorstock.remove(armorstock.get(selectedIndex[type]));
					if(selectedIndex[type] > armorstock.size() - 1) {
						selectedIndex[type] = armorstock.size() - 1;
						if(armorstock.size() < 1) {
							stock();
							selectedIndex[type] = 0;
						}						
					}				
				}
			//shield
			}else if (type == 2 && i.shields.size() != i.inventoryslots) {
				if(i.gold >= (2* shieldstock.get(selectedIndex[type]).shieldmaxhp * rarityvalue(null, null, shieldstock.get(selectedIndex[type])))) {
					i.shields.add(shieldstock.get(selectedIndex[type]));
					i.gold -= 2 *(shieldstock.get(selectedIndex[type]).shieldmaxhp * rarityvalue(null, null, shieldstock.get(selectedIndex[type])));
					shieldstock.remove(selectedIndex[type]);
					
					if(selectedIndex[type] > shieldstock.size() - 1) {
						selectedIndex[type] = shieldstock.size() - 1;
						if(shieldstock.size() < 1) {
							stock();
							selectedIndex[type] = 0;
						}
					}	
				}
			//item
			}else if (type == 3) {
				if(i.gold > (int)(2*(5 + (2 *itemstock.get(selectedIndex[type]).id))* Math.pow(1.13, level - 1))) {
					if(containItem(itemstock.get(selectedIndex[type]).id) > -1) {
						i.items.get(containItem(itemstock.get(selectedIndex[type]).id)).numberofitems ++;
						i.gold -= (int)(2*(5 + (2 *itemstock.get(selectedIndex[type]).id))* Math.pow(1.13, level - 1));
					}else {
						i.items.add(new Item(itemstock.get(selectedIndex[type]).id, 1));
						i.gold -= (int)(2*(5 + (2 *itemstock.get(selectedIndex[type]).id))* Math.pow(1.13, level - 1));
					}
					
					itemstock.get(selectedIndex[type]).numberofitems--;
					if(itemstock.get(selectedIndex[type]).numberofitems < 1) {
						itemstock.remove(selectedIndex[type]);
						if(selectedIndex[type] > itemstock.size() - 1) {
							selectedIndex[type] = itemstock.size() - 1;
							if(itemstock.size() < 1) {
								stock();
								selectedIndex[type] = 0;
							}
						}	
					}
				}
			}
		}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//sell
		public void sell(int index) {
			//weapon
			i.type = type;
			i.updateInventory();
			
			if(type == 0) {
				if(i.weapons.size() > 1) {
					i.gold += i.weapons.get(index).weapondamage * rarityvalue(i.weapons.get(index), null, null);
					weaponstock.add(i.weapons.get(index));
					i.weapons.remove(index);
					if(i.indexselected[type] > i.weapons.size() - 1) {
						i.indexselected[type] = i.weapons.size() - 1;
					}
				}
			//armor
			}else if (type == 1) {
				if(i.armor.size() > 1) {
					i.gold += i.armor.get(index).armormaxhp * rarityvalue(null, i.armor.get(index), null);
					armorstock.add(i.armor.get(index));
					i.armor.remove(index);
					if(i.indexselected[type] > i.armor.size() - 1) {
						i.indexselected[type] = i.armor.size() - 1;
					}
				}
			//shield	
			}else if (type == 2) {
				if(i.shields.size() > 1) {
					i.gold += i.shields.get(index).shieldmaxhp * rarityvalue(null, null, i.shields.get(index));
					shieldstock.add(i.shields.get(index));
					i.shields.remove(index);
					if(i.indexselected[type] > i.shields.size() - 1) {
						i.indexselected[type] = i.shields.size() - 1;
					}
				}
			//item	
			}else if (type == 3) {
				if(i.items.get(index).numberofitems > 0) {
					i.items.get(index).numberofitems--;
					i.gold += (int)((5))* Math.pow(1.13, level - 1);
					if(i.indexselected[type] > i.items.size() - 1) {
						i.indexselected[type] = i.items.size() - 1;
					}
				}
			}
		}
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Private Methods
	
		//stock
			private void stock() {
				int[] random = new int[] {0,0,0,0};
				String[] itemrarity = new String[] {"Common", "Uncommon", "Rare", "Epic", "Legendary"};
				
				//Weapons
				if(type == 0) {
					weaponstock = new ArrayList<Weapon>();
					weaponstock.add(new Weapon("Common", 0, "","",1));
					int randsize = (int)(Math.random() * 6) + 10;
					
					for(int a = 0; a < randsize; a++) {
						random[0] = (int)(Math.random() * 100);
						
						if(random[0] <= 50) { random[0] = 0; 						
						}else if (random[0] <= 75) { random[0] = 1; 						
						}else if(random[0] <= 90) { random[0] = 2; 									
						}else if (random[0] <= 98) { random[0] = 3; 					
						}else if (random[0] <= 99) {random[0] = 4;}else {
							random[0] = 0;
						}
						
						if(random[0] == 0) {random[2] = (int)(Math.random()* i.weapons.get(0).allcommonweaponnames.length);								
						}else if (random[0] == 1) {random[2] = (int)(Math.random()* i.weapons.get(0).alluncommonweaponnames.length);								
						}else if (random[0] == 2) {random[2] = (int)(Math.random()* i.weapons.get(0).allrareweaponnames.length);						
						}else if (random[0] == 3) {random[2] = (int)(Math.random()* i.weapons.get(0).allepicweaponnames.length);								
						}else {random[2] = (int)(Math.random()* i.weapons.get(0).alllegendaryweaponnames.length);}
						
						random[1] = (int)(Math.random()* 100);
						
						if(random[1] <= 80) {
							random[1] = 0;
						}else {
							random[1] = (int)(Math.random()* (weaponstock.get(0).allweaponeffects.length - 1)) + 1;
						}
						
						random[3] = (int)(Math.random()* 100);
						
						if(random[3] <= 80) {
							random[3] = 0;
						}else {
							random[3] = (int)(Math.random()* (weaponstock.get(0).allweaponelements.length - 1)) + 1;
						}
		
						weaponstock.add(new Weapon(itemrarity[random[0]], random[2], i.weapons.get(0).allweaponeffects[random[1]], i.weapons.get(0).allweaponelements[random[3]], level));
					}
				
				//Armor	
				}else if (type == 1) {
					armorstock = new ArrayList<Armor>();
					armorstock.add(new Armor("Common", 0, "","",1));
					int randsize = (int)(Math.random() * 3) + 10;
					
					for(int a = 0; a < randsize; a++) {
						random[0] = (int)(Math.random() * 100);
						
						if(random[0] <= 50) { random[0] = 0; 						
						}else if (random[0] <= 75) { random[0] = 1; 						
						}else if(random[0] <= 90) { random[0] = 2; 									
						}else if (random[0] <= 98) { random[0] = 3; 					
						}else if (random[0] <= 99) {random[0] = 4;}else {
							random[0] = 0;
						}
						
						if(random[0] == 0) {random[2] = (int)(Math.random()* i.armor.get(0).allcommonarmornames.length);								
						}else if (random[0] == 1) {random[2] = (int)(Math.random()* i.armor.get(0).alluncommonarmornames.length);								
						}else if (random[0] == 2) {random[2] = (int)(Math.random()* i.armor.get(0).allrarearmornames.length);						
						}else if (random[0] == 3) {random[2] = (int)(Math.random()* i.armor.get(0).allepicarmornames.length);								
						}else {random[2] = (int)(Math.random()* i.armor.get(0).alllegendaryarmornames.length);}
						
						random[1] = (int)(Math.random() * 100);
						
						if(random[1] <= 80) {
							random[1] = 0;
						}else {
							random[1] = (int)(Math.random()* (armorstock.get(0).allarmoreffects.length - 1)) + 1;
						}
						
						random[3] = (int)(Math.random()* 100);
						
						if(random[3] <= 80) {
							random[3] = 0;
						}else {
							random[3] = (int)(Math.random()* (armorstock.get(0).allarmorresist.length - 1)) + 1;
						}
						
						armorstock.add(new Armor(itemrarity[random[0]], random[2], i.armor.get(0).allarmoreffects[random[1]], i.armor.get(0).allarmorresist[random[3]], level));
					}
				
				//Shields
				}else if(type == 2) {
					shieldstock = new ArrayList<Shield>();
					shieldstock.add(new Shield("Common", 0, "", 1));
					int randsize = (int)(Math.random() * 8) + 7;
					
					for(int a = 0; a < randsize; a++) {
						random[0] = (int)(Math.random() * 100);
						
						if(random[0] <= 50) { random[0] = 0; 						
						}else if (random[0] <= 75) { random[0] = 1; 						
						}else if(random[0] <= 90) { random[0] = 2; 									
						}else if (random[0] <= 98) { random[0] = 3; 					
						}else if (random[0] <= 99) {random[0] = 4;}else {
							random[0] = 0;
						}
						
						if(random[0] == 0) {random[2] = (int)(Math.random()* i.shields.get(0).allcommonshieldnames.length);								
						}else if (random[0] == 1) {random[2] = (int)(Math.random()* i.shields.get(0).alluncommonshieldnames.length);								
						}else if (random[0] == 2) {random[2] = (int)(Math.random()* i.shields.get(0).allrareshieldnames.length);						
						}else if (random[0] == 3) {random[2] = (int)(Math.random()* i.shields.get(0).allepicshieldnames.length);								
						}else {random[2] = (int)(Math.random()* i.shields.get(0).alllegendaryshieldnames.length);}
						
						random[1] = (int)(Math.random() * 100);
						
						if(random[1] <= 80) {
							random[1] = 0;
						}else {
							random[1] = (int)(Math.random()* (shieldstock.get(0).allshieldeffects.length - 1)) + 1;
						}
						
						shieldstock.add(new Shield(itemrarity[random[0]], random[2], i.shields.get(0).allshieldeffects[random[1]], level));
					}
				
				//Items
				}else if (type == 3) {
					itemstock = new ArrayList<Item>();
					int randsize = (int)(Math.random() * 10) + 10;
					
					for(int a = 0; a < randsize; a++) {
						random[1] = (int)(Math.random()* i.items.get(0).allitems.length);
						random[2] = (int)(Math.random()* 69) + 1;
						
						if(containItemStock(random[1]) != -1) {
							itemstock.get(containItemStock(random[1])).numberofitems += random[2];
						}else {
							itemstock.add(new Item(random[1], random[2]));
						}
						
					}
				}
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
			//helper method rarityvalue
			private int rarityvalue(Weapon w, Armor a, Shield s) {
				String[] itemrarity = new String[] {"Common", "Uncommon", "Rare", "Epic", "Legendary"};
				
				for(int z = 0; z < 5; z++) {
					if(w!= null && w.weaponrarity == itemrarity[z]) {
						return (z + 1);
					}
					
					if(a != null && a.armorrarity == itemrarity[z]) {
						return (z + 1);
					}
					
					if(s!= null && s.shieldrarity == itemrarity[z]) {
						return (z + 1);
					}
						
				}
				
				return 1;
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			public void setupShop() {
				name = new JLabel();
				stat = new JLabel();
				
				back = new JButton("Back");
				shopIcon = new JPanel();
				shopStock = new JPanel();
				
				leftB = new JButton("<--");
				rightB = new JButton("-->");
				buyS = new JButton("Buy");
				sellS = new JButton("Sell");
				
				shop.setLayout(new GridLayout(2,1,0,0));
				shop.add(shopIcon);
				
				shopStock.setLayout(new GridLayout(5,2,0,0));
				shopStock.add(name);
				shopStock.add(stat);
				
				leftB.addActionListener(new leftArrow());
				shopStock.add(leftB);
				
				rightB.addActionListener(new rightArrow());
				shopStock.add(rightB);
				
				buyS.addActionListener(new buying());
				shopStock.add(buyS);
				
				sellS.addActionListener(new sell());
				shopStock.add(sellS);
				
				viewz.addActionListener(new viewItem());
				shopStock.add(viewz);
				
				back.addActionListener(new Back());
				shopStock.add(back);
				
			
				
				shop.add(shopStock);
				//tabs.add("Shop", shop);
				updateShop();
			}
			
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			
			private void updateShop() {
				if(type == 0) {
					name.setText("Name: " + weaponstock.get(selectedIndex[type]).weaponeffect + weaponstock.get(selectedIndex[type]).weaponname);
					stat.setText("Cost: " + i.shortener("" +(2*(weaponstock.get(selectedIndex[type]).weapondamage * rarityvalue(weaponstock.get(selectedIndex[type]), null, null)))));
					//weapons
					if(weaponstock.get(selectedIndex[type]).weaponrarity == "Common") {
						name.setForeground(Color.gray);
						stat.setForeground(Color.gray);
						
					}else if (weaponstock.get(selectedIndex[type]).weaponrarity == "Uncommon") {
						name.setForeground(Color.green);
						stat.setForeground(Color.green);
						
					}else if (weaponstock.get(selectedIndex[type]).weaponrarity == "Rare") {
						name.setForeground(Color.blue);
						stat.setForeground(Color.blue);
						
					}else if (weaponstock.get(selectedIndex[type]).weaponrarity == "Epic") {
						name.setForeground(Color.magenta);
						stat.setForeground(Color.magenta);
						
					}else if (weaponstock.get(selectedIndex[type]).weaponrarity == "Legendary") {
						name.setForeground(Color.orange);
						stat.setForeground(Color.orange);
					}
				
				//armor	
				}else if (type == 1) {
					name.setText("Name: " +  armorstock.get(selectedIndex[type]).armoreffect + armorstock.get(selectedIndex[type]).armorname);
					stat.setText("Cost: " + i.shortener("" +(2*(armorstock.get(selectedIndex[type]).armormaxhp * rarityvalue(null, armorstock.get(selectedIndex[type]), null)))));
					
					if(armorstock.get(selectedIndex[type]).armorrarity == "Common") {
						name.setForeground(Color.gray);
						stat.setForeground(Color.gray);
						
					}else if (armorstock.get(selectedIndex[type]).armorrarity == "Uncommon") {
						name.setForeground(Color.green);
						stat.setForeground(Color.green);
						
					}else if (armorstock.get(selectedIndex[type]).armorrarity == "Rare") {
						name.setForeground(Color.blue);
						stat.setForeground(Color.blue);
						
					}else if (armorstock.get(selectedIndex[type]).armorrarity == "Epic") {
						name.setForeground(Color.magenta);
						stat.setForeground(Color.magenta);
						
					}else if (armorstock.get(selectedIndex[type]).armorrarity == "Legendary") {
						name.setForeground(Color.orange);
						stat.setForeground(Color.orange);
					}
				//shields	
				}else if (type == 2) {
					name.setText("Name: "  + shieldstock.get(selectedIndex[type]).shieldeffect + shieldstock.get(selectedIndex[type]).shieldname);
					stat.setText("Cost: " + i.shortener("" +(2* (shieldstock.get(selectedIndex[type]).shieldmaxhp * rarityvalue(null, null, shieldstock.get(selectedIndex[type]))))));
					
					if(shieldstock.get(selectedIndex[type]).shieldrarity == "Common") {
						name.setForeground(Color.gray);
						stat.setForeground(Color.gray);
						
					}else if (shieldstock.get(selectedIndex[type]).shieldrarity == "Uncommon") {
						name.setForeground(Color.green);
						stat.setForeground(Color.green);
						
					}else if (shieldstock.get(selectedIndex[type]).shieldrarity == "Rare") {
						name.setForeground(Color.blue);
						stat.setForeground(Color.blue);
						
					}else if (shieldstock.get(selectedIndex[type]).shieldrarity == "Epic") {
						name.setForeground(Color.magenta);
						stat.setForeground(Color.magenta);
						
					}else if (shieldstock.get(selectedIndex[type]).shieldrarity == "Legendary") {
						name.setForeground(Color.orange);
						stat.setForeground(Color.orange);
					}
				}else if (type ==3) {
					name.setText("Name: " + itemstock.get(selectedIndex[type]).itemname);
					stat.setText("Cost: " + i.shortener((int)(2*(5 + (2 *itemstock.get(selectedIndex[type]).id))* Math.pow(1.13, level - 1)) + "") + "; Number: " + i.shortener("" + itemstock.get(selectedIndex[type]).numberofitems));
					name.setForeground(Color.black);
					stat.setForeground(Color.black);
				}
				i.updateInventory();
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			public int containItem(int id) {
				for(int a = 0; a < i.items.size(); a++) {
					if(i.items.get(a).id == id) {
						return a;
					}
				}
				return -1;
			}
			
			public int containItemStock(int id) {
				for(int a = 0; a < itemstock.size(); a++) {
					if(itemstock.get(a).id == id) {
						return a;
					}
				}
				return -1;
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			private class leftArrow implements ActionListener{
				public void actionPerformed(ActionEvent ae) { 
				
					if(type == 0) {
						if(selectedIndex[type] != 0) {
							selectedIndex[type]--;
							updateShop();
						}else {
							selectedIndex[type] = weaponstock.size() - 1;
							updateShop();
						}
					}else if (type == 1) {
						if(selectedIndex[type] != 0) {
							selectedIndex[type]--;
							updateShop();
						}else {
							selectedIndex[type] = armorstock.size() - 1;
							updateShop();
						}
					}else if (type == 2) {
						if(selectedIndex[type] != 0) {
							selectedIndex[type]--;
							updateShop();
						}else {
							selectedIndex[type] = shieldstock.size() - 1;
							updateShop();
						}
					}else {
						if(selectedIndex[type] != 0) {
							selectedIndex[type]--;
							updateShop();
						}else {
							selectedIndex[type] = itemstock.size() - 1;
							updateShop();
						}
					}
				}
			}
			
			//Right Arrow
			private class rightArrow implements ActionListener{
				public void actionPerformed(ActionEvent ae) { 
					if(type == 0) {
						if((selectedIndex[type] != weaponstock.size() - 1)) {
							selectedIndex[type]++;
							updateShop();
						}else {
							selectedIndex[type] = 0;
							updateShop();
						}
					}else if (type == 1) {
						if((selectedIndex[type] != armorstock.size() - 1)) {
							selectedIndex[type]++;
							updateShop();
						}else {
							selectedIndex[type] = 0;
							updateShop();
						}
					}else if (type == 2) {
						if((selectedIndex[type] != shieldstock.size() - 1)) {
							selectedIndex[type]++;
							updateShop();
						}else {
							selectedIndex[type] = 0;
							updateShop();
						}
					}else {
						if(selectedIndex[type] != itemstock.size() - 1) {
							selectedIndex[type]++;
							updateShop();
						}else {
							selectedIndex[type] = 0;
							updateShop();
						}
					}
				}
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			//Buy
			private class buying implements ActionListener{
				public void actionPerformed(ActionEvent ae) {
					buy();
					updateShop();
				}
			}
			
			//Sell
			private class sell implements ActionListener{
				public void actionPerformed(ActionEvent ae) {
					sell(i.indexselected[i.type]);
					updateShop();
				}
			}
			
			//Back
			private class Back implements ActionListener{
				public void actionPerformed(ActionEvent ae) {
					tabs.remove(shop);
					tabs.setEnabledAt(0, true);
					tabs.setSelectedIndex(0);
				}
			}
			
			private class backtogame implements ActionListener{
				public void actionPerformed(ActionEvent ae) {
					tabs.remove(showedItem);
					
					for(int a = 0; a < tabs.getTabCount(); a++) {						
						
							tabs.setEnabledAt(a, true);
						
					}
					tabs.setEnabledAt(0, false);
					tabs.setSelectedIndex(lastindex);
				}
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			//Setup Showeditem
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
				
				leave.addActionListener(new backtogame());
				itemstats.add(leave);
				
				showedItem.add(itemstats);
				showedItem.add(icon);
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
						itemname.setText("Name: " + weaponstock.get(selectedIndex[type]).weaponname);
						itemstat1.setText("Damage: " + i.shortener(weaponstock.get(selectedIndex[type]).weapondamage + ""));
						itemstat2.setText("Accuracy: "+ weaponstock.get(selectedIndex[type]).accuracy);
						itemstat3.setText("Attacks Per Turn: "+ weaponstock.get(selectedIndex[type]).attacksperturn);
						
						if(weaponstock.get(selectedIndex[type]).weaponeffect != "") {
							itemstat4.setText("Effect: "+ weaponstock.get(selectedIndex[type]).weaponeffect);
							
						}else {
							itemstat4.setText("Effect: N/A");
						}
						

						if(weaponstock.get(selectedIndex[type]).weaponelement != "") {
							itemstat5.setText("Element: "+ weaponstock.get(selectedIndex[type]).weaponelement);
							
						}else {
							itemstat5.setText("Element: N/A");
						}
						
						if(weaponstock.get(selectedIndex[type]).redtext != "") {
							redtext.setText("\"" + weaponstock.get(selectedIndex[type]).redtext + "\"");
							redtext.setForeground(Color.red);
						}else {
							redtext.setText("");
						}
						
					}else if (type == 1) {
						itemname.setText("Name: " + armorstock.get(selectedIndex[type]).armorname);
						itemstat1.setText("HP: " + i.shortener(armorstock.get(selectedIndex[type]).armormaxhp + ""));
						
						if(armorstock.get(selectedIndex[type]).armoreffect != "") {
							itemstat2.setText("Effect: " + armorstock.get(selectedIndex[type]).armoreffect);
						}else {
							itemstat2.setText("Effect: N/A");	
						}
						
						
						if(armorstock.get(selectedIndex[type]).armorresist != "") {
							itemstat3.setText("Resistance: " + armorstock.get(selectedIndex[type]).armorresist);
						}else {
							itemstat3.setText("Resistance: N/A");	
						}
						
						if(armorstock.get(selectedIndex[type]).redtext != "") {
							itemstat4.setText("\"" + armorstock.get(selectedIndex[type]).redtext + "\"");
							itemstat4.setForeground(Color.red);
						}else {
							itemstat4.setText("");
						}
						
						
						itemstat5.setText("");
						redtext.setText("");
						
					}else if (type == 2) {
						itemname.setText("Name: " + shieldstock.get(selectedIndex[type]).shieldname);
						itemstat1.setText("HP: " + i.shortener(shieldstock.get(selectedIndex[type]).shieldcurrenthp + ""));
						itemstat2.setText("Protection: "+ shieldstock.get(selectedIndex[type]).shieldblock + "%");
						itemstat3.setText("Turns till Recharge: " + shieldstock.get(selectedIndex[type]).turntorecharge);
						itemstat4.setText("Shield Recharged: " + shieldstock.get(selectedIndex[type]).shieldrecharged);
						
						if(shieldstock.get(selectedIndex[type]).shieldeffect != "") {
						}else {
							itemstat5.setText("Effect: N/A");
						}
						
						if(shieldstock.get(selectedIndex[type]).redtext != "") {
							redtext.setText("\"" + shieldstock.get(selectedIndex[type]).redtext + "\"");
							redtext.setForeground(Color.red);
						}else {
							redtext.setText("");
						}
						
					}else if (type == 3){
						itemname.setText("Name: " + itemstock.get(selectedIndex[type]).itemname);
						itemstat1.setText("Number: " + i.shortener(itemstock.get(selectedIndex[type]).numberofitems + ""));
						itemstat2.setText("");
						itemstat3.setText("");
						itemstat4.setText("");
						itemstat5.setText("");
						redtext.setText("");
						
					}
				
					
			}
			}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	}
			
