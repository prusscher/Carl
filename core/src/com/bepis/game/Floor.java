package com.bepis.game;

import java.util.ArrayList;
import java.util.Random;

import com.bepis.game.Room;

public class Floor {
	
	private Long seed;
	private Random r;
	
	public Floor() {
		r = new Random();
	}
	
	public Floor(Long seed) {
		this.seed = seed;
		r = new Random(seed);
	}
	
	/**
	 * Generate a map based on the level and the seed for random generation
	 * @param level Level of difficulty to base map generation off of
	 * @return ArrayList<Room> : Linked list of rooms of the map
	 */
	public ArrayList<Room> generateFloor(int level) {
		// Get the appropriate number of lines and length
		ArrayList<Integer> lines = getLines(level);
		
		System.out.println("Generated Lines for level: " + level + "\n# Lines: " + lines.size());
		for(Integer i: lines)
			System.out.println("line:" + i);
		
		// Linked list of rooms
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		int id = 0;
		int line = lines.remove(0);
		System.out.println("Creating first line: " + line);
		
		// Use the first line to create rooms from the origin Outward
		rooms.add(new Room(id++, 0, 0));
		for(int j = 1; j < line; j++) {
			Room last = rooms.get(id-1); // Get Last Placed Room
			
			// Place the next room to the right of the last
			Room add = new Room(id++, last.getX()+1, last.getY());
			last.setRight(add);
			add.setLeft(last);
			rooms.add(id-1, add);
		}
		printLines(rooms);
		
		// Create rooms in an alternating horizontal/vertical fashion
		boolean hor = false;
		
		// While rooms still need to be placed
		while(lines.size() > 0) {
			System.out.println("LINE Remaining: " + lines.size());
			
			// Get a room from the list to start from
			
			line = lines.remove(0);	// Get the next line to place
			Room next = selectRoom(rooms, hor);
			System.out.println("Random Room Selected: " + next.getID() + " - placing hor: " + hor);
			
			if(hor) {
				int point = r.nextInt(line);	// Get starting point on line	
				Room temp = next; // Temp room for incrementing
				
				// Place rooms to the left
				for(int j = 0; j < point; j++) {
					// next = room to start from
					// If room exists, modify both rooms
					int existId = roomExists(temp, 3, rooms);
					
					if(existId == -1) { // Room doesnt exists, create new room
						Room add = new Room(id++, temp.getX()-1, temp.getY());
						add.setRight(temp);
						temp.setLeft(add);
						rooms.add(add.getID(), add);
						temp = add;
					} else {	// Room Exists, nothing needs to be created
						Room exist = rooms.get(existId); // Get the reference to the existing room
						exist.setRight(temp);	// Set the left and right's
						temp.setLeft(exist);
						temp = exist;			// Set the next room to the one that existed
					}
				}
				
				// Place rooms to the right
				for(int j = 0; j < line - point; j++) {
					// next = room to start from
					// If room exists, modify both rooms
					int existId = roomExists(temp, 1, rooms);
					
					if(existId == -1) { // Room doesnt exists, create new room
						Room add = new Room(id++, temp.getX()+1, temp.getY());
						add.setLeft(temp);
						temp.setRight(add);
						rooms.add(add.getID(), add);
						temp = add;
					} else {	// Room Exists, nothing needs to be created
						Room exist = rooms.get(existId); // Get the reference to the existing room
						exist.setLeft(temp);	// Set the left and right's
						temp.setRight(exist);
						temp = exist;			// Set the next room to the one that existed
					}
				}
				
			} else { // VERTICAL PLACEMENT
				
				int point = r.nextInt(line);	// Get starting point on line	
				Room temp = next; // Temp room for incrementing
				
				// Place rooms to the UP
				for(int j = 0; j < point; j++) {
					// next = room to start from
					// If room exists, modify both rooms
					int existId = roomExists(temp, 0, rooms);
					
					if(existId == -1) { // Room doesnt exists, create new room
						Room add = new Room(id++, temp.getX(), temp.getY()+1);
						add.setDown(temp);
						temp.setUp(add);
						rooms.add(add.getID(), add);
						temp = add;
					} else {	// Room Exists, nothing needs to be created
						Room exist = rooms.get(existId); // Get the reference to the existing room
						exist.setDown(temp);	// Set the left and right's
						temp.setUp(exist);
						temp = exist;			// Set the next room to the one that existed
					}
				}
				
				// Place rooms to the right
				for(int j = 0; j < line - point; j++) {
					// next = room to start from
					// If room exists, modify both rooms
					int existId = roomExists(temp, 2, rooms);
					
					if(existId == -1) { // Room doesnt exists, create new room
						Room add = new Room(id++, temp.getX(), temp.getY()-1);
						add.setUp(temp);
						temp.setDown(add);
						rooms.add(add.getID(), add);
						temp = add;
					} else {	// Room Exists, nothing needs to be created
						Room exist = rooms.get(existId); // Get the reference to the existing room
						exist.setUp(temp);	// Set the left and right's
						temp.setDown(exist);
						temp = exist;			// Set the next room to the one that existed
					}
				}
			}
			hor = !hor;	// Switch Placement
			printLines(rooms);
		}
		
		rooms = reorigin(rooms);
		
		return rooms;
	}
	
	private ArrayList<Room> reorigin(ArrayList<Room> rooms) {
		int xShift = 0;
		int yShift = 0;
		
		// Find the smallest X and Y and set shifts
		for(Room r : rooms) {
			if(r.getX() < xShift)
				xShift = r.getX();
			if(r.getY() < yShift)
				yShift = r.getY();
		}
		
		xShift = Math.abs(xShift);
		yShift = Math.abs(yShift);
		
		// Shift all rooms up and right
		for(Room r : rooms) {
			r.setX(r.getX() + xShift);
			r.setY(r.getY() + yShift);
		}
		
		return rooms;
	}
	
	private int roomExists(Room current, int dir, ArrayList<Room> rooms) {
		int x = current.getX(); // Desired x
		int y = current.getY(); // Desired Y
		
		// id for the found room (-1 if dne)
		int foundRoom = -1;
		
		// Set the desired x and y correct according the search direction
		switch(dir) {
			case 0:
				y += 1;
				break;
			case 1:
				x += 1;
				break;
			case 2:	
				y -= 1;
				break;
			case 3:	
				x -= 1;
				break;
		}
		
		// Look through all rooms for desired room
		for(Room r : rooms) {
			if(r.getX() == x && r.getY() == y)
				foundRoom = r.getID();
		}
		
		// Return the id of the room in the desired direction
		return foundRoom;
	}
	
	private Room selectRoom(ArrayList<Room> rooms, boolean hor) {
		// Select a random room
		boolean accept = false;
		Room start = null;
		
		while(!accept) {
			// Select room
			start = rooms.get(r.nextInt(rooms.size()));
		
			// Check if space is available in selected direction
			if(hor) {
				// Cant place in horizontal
				if(start.hasRight() || start.hasLeft()) {
					continue;
				} else { // CAN Place
 					accept = true;
				}	
			} else {
				// Cant place in vertical
				if(start.hasUp() || start.hasDown()) {
					continue;
				} else { // CAN Place
					accept = true;
				}
			}
		}
		
		return start;
	}
	
	private void printLines(ArrayList<Room> rooms) {
		System.out.println("\nPrinting Rooms:");
		for(Room r : rooms)
			System.out.println(r.getID() + " (" + r.getX() + ", " + r.getY() + ")");
	}
	
	private ArrayList<Integer> getLines(int level) {
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		int numLines = fixLevel(level);
		
		for(int j = 0; j < numLines; j++)
			out.add(getLength(numLines, r));
		
		return out;
	}
	
	private int getLength(int level, Random r) {
		return 2 + r.nextInt(level/2);
	}
	
	private int fixLevel(int level) {
		int fixedLevel = 4;
		fixedLevel += level/2;
		if(level > 8)
			fixedLevel = (int)Math.sqrt(level)+8;
		return fixedLevel;
	}
}
