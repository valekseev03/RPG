package RolePlayingGame_v2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Map {
	JPanel fasttravel = new JPanel();
	ArrayList<String> unlockedlocations = new ArrayList<String>();
	int currentlocation = 0;
	int x = 0;
	int y = 0;
	JComboBox locationz;
	int lastlocation = 0;
	JButton traveltolocation = new JButton("Travel to Location");
	
	public Map(int x, int y) {
		unlockedlocations.add("Boorn");
		this.x = x;
		this.y = y;
	}
	
	public void setUp() {
		fasttravel.setLayout(new GridLayout(4,4,0,0));
		locationz = new JComboBox(unlockedlocations.toArray());
		traveltolocation = new JButton("Travel to Location");
		traveltolocation.addActionListener(new travel());
		fasttravel.add(locationz);
		fasttravel.add(traveltolocation);
	}
	
	public void reset() {
		fasttravel.remove(locationz);
		fasttravel.remove(traveltolocation);
		fasttravel.setLayout(new GridLayout(4,4,0,0));
		
		locationz = new JComboBox(unlockedlocations.toArray());
		
		traveltolocation = new JButton("Travel to Location");
		traveltolocation.addActionListener(new travel());
		fasttravel.add(locationz);
		fasttravel.add(traveltolocation);
	}
	
	
	public void addLocation(String location) {
		unlockedlocations.add(location);
		reset();
	}
	
	public int returnLocation() {
		return currentlocation;
	}
	
	
	private class travel implements ActionListener{
		public void actionPerformed(ActionEvent ae) { 
			currentlocation = locationz.getSelectedIndex();
			JOptionPane.showMessageDialog(fasttravel, "You Have Traveled To " + unlockedlocations.get(currentlocation));
			x = 0;
			y= 0;
		}
	}
}