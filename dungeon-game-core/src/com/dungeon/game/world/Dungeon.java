package com.dungeon.game.world;

import java.util.ArrayList;

public class Dungeon {
	public ArrayList<Floor> floors;
	
	protected int seed;
	
	public World world;
	
	public Dungeon(World world) {
		this.world = world;
		floors = new ArrayList<Floor>();
		
		seed = (int) (Math.random()*1000000000);

//		floors.add(new Floor(world, "castle", 50, 50, 25, 25, 0, 0));
		floors.add(new Floor(world, "tutorial", 100, 100, 25, 25, 0, 0));
	}
}