package RolePlayingGame_v2;

//Imports
	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SaveGame {
//------------------------------------------------------------------------------------
	//Variables
		private FileReader fileReader;
		private FileWriter fileWriter;
		private BufferedWriter bufferedWriter;
		private BufferedReader bufferedReader;
		
		private File save = new File("savedata.txt");
		
		Inventory i;
		JFrame game;
//----------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructor
	public SaveGame(Inventory i, JFrame game) {
		this.i = i;
		this.game = game;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Save Game
	public void saveGame() {
		try {
			fileWriter = new FileWriter(save); 
			bufferedWriter = new BufferedWriter(fileWriter);
			//General
				bufferedWriter.write(encryptceasarCipher("P_LVL " + this.i.level));
				bufferedWriter.newLine();
				
				bufferedWriter.write(encryptceasarCipher("XP " + this.i.xp));
				bufferedWriter.newLine();
				
				bufferedWriter.write(encryptceasarCipher("HP " + this.i.health));
				bufferedWriter.newLine();
				
				bufferedWriter.write(encryptceasarCipher("GOLD " + this.i.gold));
				bufferedWriter.newLine();
			
				bufferedWriter.write(encryptceasarCipher("SLT " + this.i.inventoryslots));
				bufferedWriter.newLine();
				
			//Weapons
			for(int l = 0; l < this.i.weapons.size(); l++) {
				bufferedWriter.write(encryptceasarCipher("W_RAR " + this.i.weapons.get(l).weaponrarity));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("W_ID " + this.i.weapons.get(l).id));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("W_EF " + this.i.weapons.get(l).weaponeffect));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("W_EL " + this.i.weapons.get(l).weaponelement));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("W_ACC " + this.i.weapons.get(l).itemlevel));
				bufferedWriter.newLine();
			}
			
			//Armor
			for(int l = 0; l < this.i.armor.size(); l++) {
				bufferedWriter.write(encryptceasarCipher("A_RAR " + this.i.armor.get(l).armorrarity));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("A_ID " + this.i.armor.get(l).id + ""));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("A_EF " + this.i.armor.get(l).armoreffect));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("A_RES " + this.i.armor.get(l).armorresist));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("A_LVL " + this.i.armor.get(l).itemlevel));
				bufferedWriter.newLine();
			}
			
			//Shields
			for(int l = 0; l < this.i.shields.size(); l++) {
				bufferedWriter.write(encryptceasarCipher("S_RAR " + this.i.shields.get(l).shieldrarity));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("S_ID " + this.i.shields.get(l).id + ""));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("S_EF " + this.i.shields.get(l).shieldeffect));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("S_LVL " + this.i.shields.get(l).itemlevel));
				bufferedWriter.newLine();
			}
			
			//Items
			for(int l = 0; l < this.i.items.size(); l++) {
				bufferedWriter.write(encryptceasarCipher("I_NAME " + this.i.items.get(l).itemname));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("I_ID " + this.i.items.get(l).id + ""));
				bufferedWriter.newLine();
				bufferedWriter.write(encryptceasarCipher("I_NUM " + this.i.items.get(l).numberofitems));
				bufferedWriter.newLine();
			}
			
			for(int l = 0; l < this.i.indexselected.length; l++) {
				bufferedWriter.write(encryptceasarCipher("I_SEL " + this.i.indexselected[l]));
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			
		}catch(IOException ex) {
			System.out.println(ex);
		}
	}
	
	
//----------------------------------------------------------------------------------------------------------------------------------------------------
	//Load Game
		public void loadGame() {
			String line = "";
			String str1 = null;
			String str2 = null;
			String str3 = null;
			String str5 = null;
			String str6 = null;
			int indexz = 0;
			
			i.weapons = new ArrayList<Weapon>();
			i.shields = new ArrayList<Shield>();
			i.armor = new ArrayList<Armor>();
			i.items = new ArrayList<Item>();
			
			try {
				fileReader = new FileReader(save);
				bufferedReader = new BufferedReader(fileReader);
				
				
				 while((line = bufferedReader.readLine()) != null) {  
					 //add Weapons
					 if(line.contains(encryptceasarCipher("W_RAR"))){						 
						 str1 = decryptceasarCipher(line.substring(6,line.length()));
						 line = bufferedReader.readLine();
						 
						 str2 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str3 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str5 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str6 = decryptceasarCipher(line.substring(6,line.length()));
						 
						 
						 
						 this.i.weapons.add(new Weapon(str1, Integer.parseInt(str2), str3, str5, Integer.parseInt(str6)));
						 
					 }
					 
					 //add Armor
					 if(line.contains(encryptceasarCipher("A_RAR"))){
						
						 str1 = decryptceasarCipher(line.substring(7,line.length()));
						 line = bufferedReader.readLine();
						 
						 str2 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str3 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str5 = decryptceasarCipher(line.substring(6,line.length()));
						 line = bufferedReader.readLine();
						 
						 str6 = decryptceasarCipher(line.substring(6,line.length()));
						 
						 
						 
						 this.i.armor.add(new Armor(str1, Integer.parseInt(str2), str3, str5, Integer.parseInt(str6)));
						 
					 }
					 
					 //add Shields
					 if(line.contains(encryptceasarCipher("S_RAR"))){
						
						 str1 = decryptceasarCipher(line.substring(6,line.length()));
						 line = bufferedReader.readLine();
						 
						 str2 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str3 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str5 = decryptceasarCipher(line.substring(6,line.length()));
						 
						 						 
						 this.i.shields.add(new Shield(str1, Integer.parseInt(str2), str3, Integer.parseInt(str5)));
						
					 }
					 
					 //add Items
					 if(line.contains(encryptceasarCipher("I_NAME"))){
						 
						 str1 = decryptceasarCipher(line.substring(7,line.length()));
						 line = bufferedReader.readLine();
						 
						 str2 = decryptceasarCipher(line.substring(5,line.length()));
						 line = bufferedReader.readLine();
						 
						 str3 = decryptceasarCipher(line.substring(6,line.length()));
						 
						 this.i.items.add(new Item(str1, Integer.parseInt(str3), Integer.parseInt(str2)));
						 
					 }
					 
					 //General
					 if(line.contains(encryptceasarCipher("P_LVL"))){
						 i.level = Integer.parseInt(decryptceasarCipher(line.substring( 6, line.length() )));
					 }
					 
					 if(line.contains(encryptceasarCipher("HP"))){
						 i.health = Integer.parseInt(decryptceasarCipher(line.substring( 3, line.length() )));
					 }
					 
					 if(line.contains(encryptceasarCipher("XP"))){
						 i.xp = Integer.parseInt(decryptceasarCipher(line.substring( 3, line.length() )));
					 }
					 
					 if(line.contains(encryptceasarCipher("GOLD"))){
						 i.gold = Integer.parseInt(decryptceasarCipher(line.substring( 5, line.length() )));
					 }
					 
					 if(line.contains(encryptceasarCipher("I_SEL "))) {
						 i.indexselected[indexz] = Integer.parseInt(decryptceasarCipher(line.substring( 6, line.length() )));;
						 indexz++;
					 }

					 if(line.contains(encryptceasarCipher("SLT "))){
						 i.inventoryslots = Integer.parseInt(decryptceasarCipher(line.substring( 4, line.length() )));
					 }
				 }
				 
			}catch(IOException ex) {
				JOptionPane.showMessageDialog(game, "Error: Save Data Not Found. Starting New Game...");				
				this.game.dispose();
				Game g = new Game();
				g.setUp();
			}
			
			i.updateInventory();
		}
	
	
//----------------------------------------------------------------------------------------------------------------------------------------------------
	//Caesar Ciphers
	
		//Encrypt
			private String encryptceasarCipher(String toencrypt) {
				char[] stored = toencrypt.toCharArray();
				String finalStr = "";
				
				for(int i = 0; i < stored.length; i++) {
					stored[i]++;
					finalStr += stored[i] + "";
				}
				
				return finalStr;
			}
			
		//Decrypt
			private String decryptceasarCipher(String todecrypt) {
				char[] stored = todecrypt.toCharArray();
				String finalStr = "";
				
				for(int i = 0; i < stored.length; i++) {
					stored[i]--;
					finalStr += stored[i] + "";
				}
				
				return finalStr;
			}
		
//----------------------------------------------------------------------------------------------------------------------------------------------------		
}
