package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.dungeon.game.world.Tile;

public class PathOld {
	protected int[] start; //start position of the path
	protected int[] end; //end position of the path
	ArrayList<Area> areas; //areas along the path
	public PathOld(Area area, int[] start, int[] end){ // constructor used to create initial path
		areas = new ArrayList<Area>();
		areas.add(area);
		this.start = start;
		this.end = end;
	}
	public PathOld(PathOld path, Area area){ //constructor used to create paths
		areas = new ArrayList<Area>(path.areas);
		areas.add(area);
		start = new int[]{path.start[0],path.start[1]};
		end = new int[]{path.end[0],path.end[1]};
	}
	
	public Area getLastArea(){
		return areas.get(areas.size()-1);
	}
	
	public boolean isAreaOnPath(Area area){
		return areas.contains(area);
	}
	
	public ArrayList<Area> getExpandAreas(){
		ArrayList<Area>  expandAreas = new ArrayList<Area>();
		for(Area area: getLastArea().adjacentAreas){
			if(!isAreaOnPath(area)){
				expandAreas.add(area);
			}
		}
		return expandAreas;
	}
	
	
	public ArrayList<int[]> getTiles(Tile[][] tm){
		ArrayList<int[]> Tiles = new ArrayList<int[]>();
		//find tiles in 3 stages
		//start point to edge
		//intermediate areas
		for(Area area: areas){
			ArrayList<int[]> subPath;
			if(area.equals(areas.get(0))){
				subPath = area.getMinPath(tm, start,null, areas.get(areas.indexOf(area)+1));
			}else if(area.equals(getLastArea())){
				int[] startPoint = area.findAdjacentEdge(Tiles.get(Tiles.size()-1));
				subPath = area.findPath(tm, startPoint, end);
			}else {
				int[] startPoint = area.findAdjacentEdge(Tiles.get(Tiles.size()-1));
				subPath = area.getMinPath(tm, startPoint,areas.get(areas.indexOf(area)-1), areas.get(areas.indexOf(area)+1));
	
			}
			for(int[] point: subPath){
				Tiles.add(point);
			}
		}
		//edge to end point
		return Tiles;
	}
	
	public int getLength(Tile[][] tm){
		return getTiles(tm).size();
	}
	public int getLengthUpTo(Tile[][] tm, Area lastArea) {
		PathOld path = new PathOld(this.areas.get(0), this.start, lastArea.getCenter());
		for(Area area: areas){
			if(areas.indexOf(area)!=0){
				path = new PathOld(path, area);
				if(area.equals(lastArea))break;
			}
		}
		return path.getLength(tm);
	}
	
	
}
