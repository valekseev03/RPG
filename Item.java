package RolePlayingGame_v2;

public class Item {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Variables (pt 1)
		String[] allitems = new String[] {"Arrow", "Weak Healing Potion", "Medium Healing Potion", "Strong Healing Potion"};
		
		String itemname = "";
		int numberofitems = 0;
		int id = 0;
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Constructor 1
		public Item(String name, int number, int id) {
			this.itemname = name;
			this.id = id;
			this.numberofitems = number;
		}
		
		//Constructor 2
		public Item(int id, int number) {
			this.itemname = allitems[id];
			this.id = id;
			this.numberofitems = number;
		}
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Implement item types
		public void useItem() {
				if(this.numberofitems > 0) {
					this.numberofitems--;
				}
		}
		
		
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
