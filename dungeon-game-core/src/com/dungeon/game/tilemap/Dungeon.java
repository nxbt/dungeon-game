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
	
	public void createFloor(int width, int height){
		floors.add(new Floor(width, height));
	}
	
	public Floor getFloor(int level){
		return floors.get(level);
	}

}