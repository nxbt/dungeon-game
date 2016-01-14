package com.dungeon.game.pathing;

import java.util.ArrayList;

public class Path {
	private int[] start; //start position of the path
	private int[] end; //end position of the path
	ArrayList<Area> areas; //areas along the path
	public Path(Area area, int[] start, int[] end){ // constructor used to create initial path
		areas = new ArrayList<Area>();
		areas.add(area);
		this.start = start;
		this.end = end;
	}
	public Path(Area area, Path path){ //constructor used to create paths
		areas = new ArrayList<Area>(path.areas);
		areas.add(area);
		start = new int[]{area.start[0],area.start[1]};
		end = new int[]{area.end[0],area.end[1]};
	}
	
	public Area getLastArea(){
		return areas.get(areas.size()-1);
	}
	
	public boolean isAreaOnPath(Area area){
		return areas.contains(area)
	}
	
	public ArrayList<Area> getExpandAreas(){
		ArrayList<Area>  expandAreas = new ArrayList<Area>();
		for(Area area: getLastArea.adjacentAreas()){
			if(!isAreaOnPath(area))expandAreas.add(area);
		}
		return expandAreas;
	}
	
	
	public getTiles(){
		
	}
	
	
}
