package com.dungeon.game.world;

import java.util.ArrayList;

public class World {
	public ArrayList<Dungeon> dungeons;
	
	public World() {
		dungeons = new ArrayList<Dungeon>();
		
		dungeons.add(new Dungeon());
	}

}
