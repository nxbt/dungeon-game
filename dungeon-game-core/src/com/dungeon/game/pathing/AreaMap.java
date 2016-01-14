package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.dungeon.game.world.Tile;

public class AreaMap {
	private ArrayList<Area> areas; //contains data for all areas;
	private Tile[][] tm; //contains tile data for the entire areaMap;
	
	public AreaMap(Tile[][] tm){
		areas = new ArrayList<Area>();
		this.tm = tm;
	}
	
	public void addArea(Area area){
		areas.add(area);
	}
	
	private void calculateBorders(){
		for(Area area: areas){
			for(Area a: areas){
				if(!area.equals(area))area.calculateBorders(a);
			}
		}
	}
	
	private void calculateMinPaths(){
		for(Area area: areas){
			area.calculateMinPaths(tm);
		}
	}
	
	public void prepAreas(){
		calculateMinPaths();
		calculateBorders();
	}
}
