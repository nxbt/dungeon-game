package com.dungeon.game.world;

import java.util.ArrayList;

public class Dungeon {
	public ArrayList<Floor> floors;
	
	protected int seed;
	
	public Dungeon() {
		floors = new ArrayList<Floor>();
		
		seed = (int) (Math.random()*1000000000);
		
		floors.add(new Floor(100, 100));
	}
}