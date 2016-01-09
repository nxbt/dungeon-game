package com.dungeon.game.pathing;

import java.util.ArrayList;

public class Path {
	private int[] start;
	private int[] end;
	ArrayList<Area> areas;
	public Path(Area area, int[] start, int[] end){ // constructor used to create initial path
		areas = new ArrayList<Area>();
		areas.add(area);
		this.start = start;
		this.end = end;
	}
	public Path(Path path, Area area){ //constructor used to create paths
		this.areas = new ArrayList<Area>(path.areas);
		areas.add(area);
	}
	
	
}
