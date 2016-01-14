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
	
	public Area findArea(int[] point){
		for(Area area: areas){
			if(area.containsPoint(point))return area;
		}
		return null;
	}
	
	public ArrayList<int[]> findPath(int[] start, int[] end){
    		Area startArea = findArea(start);
    		Area endArea = findArea(end);
    		if(startArea.equals(endArea))return startArea.findPath(tm, start, end);
    		
    		ArrayList<Path> paths = new ArrayList<Path>();
    		paths.add(new Path(startArea,start,end));
    		for(int i = 0; i < paths.size(); i ++){
    			Path path = paths.get(i);
    			if(path.getLastArea()!=endArea){
    				for(Area area: path.getExpandAreas()){
    					paths.add(new Path(path, area));
    				}
    				paths.remove(path);
    				i--;
    			}
    		}
    		
    		Path shortestPath = paths.get(0);
    		for(Path path: paths){
    			if(path.getLength(tm)<shortestPath.getLength(tm))shortestPath = path;
    		}
    		
    		return shortestPath.getTiles(tm);
    		
	}
}
