package com.dungeon.game.tilemap;

import java.util.ArrayList;

public class Dungeon {
	protected ArrayList<Floor> floors;
	
	protected int seed;
	
	public Dungeon() {
		floors = new ArrayList<Floor>();
		
		seed = (int) (Math.random()*1000000000);
		
		System.out.println(seed);
	}

}
