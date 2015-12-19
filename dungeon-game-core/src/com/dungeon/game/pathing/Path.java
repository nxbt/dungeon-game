package com.dungeon.game.pathing;

import java.util.ArrayList;

public class Path {
	ArrayList<Area> areas;
	public Path(Area area){
		areas = new ArrayList<Area>();
		areas.add(area);
	}
	public Path(ArrayList<Area> areas, Area area){
		this.areas = areas;
		areas.add(area);
	}
}
