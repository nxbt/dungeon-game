package com.dungeon.game.pathing;

import java.util.ArrayList;

public class AreaMap {
	ArrayList<Area> areas; //contains data for all areas;
	
	public AreaMap(){
		areas = new ArrayList<Area>();
	}
	
	public addArea(Area area){
		areas.add(area);
	}
}
