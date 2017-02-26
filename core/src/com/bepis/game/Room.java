package com.bepis.game;

public class Room {
	private int id;
	private Room up, right, down, left;
	private int type;
	private int x, y;
	
	public Room(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		
		up = null;
		right = null;
		down = null;
		left = null;
		
		type = 5;	// Default room type
	}
	
	public int getID() { return id; }
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	public Boolean hasUp()		{ return up != null; }
	public Boolean hasRight()	{ return right != null; }
	public Boolean hasDown()	{ return down != null; }
	public Boolean hasLeft()	{ return left != null; }
	
	public Room getUp()		{ return up; }
	public Room getRight()	{ return right; }
	public Room getDown()	{ return down; }
	public Room getLeft()	{ return left; }
	
	public void setUp(Room up)		{ setRoom(up, 0); }
	public void setRight(Room right){ setRoom(right, 1); }
	public void setDown(Room down)	{ setRoom(down, 2); }
	public void setLeft(Room left)	{ setRoom(left, 3); }
	
	public int getType() { return type; }
	
	/**
	 * Set the next room in a direction
	 * @param next Reference Next Room 
	 * @param dir Direction 0: up, 1: right, 2: down, 3: left
	 */
	private void setRoom(Room next, int dir) {
		switch(dir) {
			case 0: up = next;
					break;
			case 1: right = next;
					break;
			case 2: down = next;
					break;
			case 3: left = next;
					break;
		}
		
		checkType();
	}
	
	/**
	 * Corrects the room type after a neighbor change
	 * 1 exit	: 0
	 * 2 exits	:	angle: 1
	 * 				line:  2
	 * 3 exits	: 3				
	 * 4 exits	: 4
	 */
	private void checkType() {
		// Get the number of rooms
		int num = 0;
		if(up != null)
			num++;
		if(right != null)
			num++;
		if(down != null)
			num++;
		if(left != null)
			num++;
		
		// Select the correct room type from the number of rooms and their position
		switch(num) {
			case 0:
				type = 5;
				System.err.println("Room " + id + ": No Neighbors after add");
				break;
			case 1:
				type = 0;
				break;
			case 2:
				if(up != null && down != null || right != null && left != null)
					type = 1;
				else 
					type = 2;
				break;
			case 3:
				type = 3;
				break;
			case 4:
				type = 4;
				break;
		}
	}
}
