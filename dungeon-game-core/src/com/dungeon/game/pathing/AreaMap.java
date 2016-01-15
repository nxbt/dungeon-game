package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.dungeon.game.world.Tile;

public class AreaMap {
	private ArrayList<Area> areas; //contains data for all areas;
	private Tile[][] tm; //contains tile data for the entire areaMap;
	
	private ArrayList<ArrayList<Path>> minPaths;
	
	public AreaMap(Tile[][] tm){
		areas = new ArrayList<Area>();
		this.tm = tm;
	}
	
	public void addArea(Area area){
		areas.add(area);
	}
	
	private void calculateEdges(){
		for(Area area: areas){
			area.calculateEdges();
		}
	}
	
	private void calculateBorders(){
		for(Area area: areas){
			for(Area a: areas){
				if(!area.equals(a))area.calculateBorders(a);
			}
		}
	}
	
	private void calculateMinPaths(){
		for(Area area: areas){
			area.begin();
			area.calculateMinPaths(tm);
		}
	}
	
	private void calculateAreaMinPaths(){
		minPaths = new ArrayList<ArrayList<Path>>();
		for(Area area: areas){
			minPaths.add(new ArrayList<Path>());
			for(Area a: areas){
				Path minPath = null;
				if(!area.equals(a)){
					minPath = findMinPath(area.points.get(2), a.points.get(2));
				}
				minPaths.get(minPaths.size()-1).add(minPath);
			}
		}
	}
	
	public void prepAreas(){
		calculateEdges();
		calculateBorders();
		calculateMinPaths();
		calculateAreaMinPaths();
	}
	
	public Area findArea(int[] point){
		for(Area area: areas){
			if(area.containsPoint(point))return area;
		}
		return null;
	}
	
	public Path findMinPath(int[] start, int[] end){
		Area startArea = findArea(start);
		Area endArea = findArea(end);
		startArea.begin();
		endArea.begin();
		
		ArrayList<Path> paths = new ArrayList<Path>();
		paths.add(new Path(startArea,start,end));
		System.out.println("Started Pathing");
		for(int i = 0; i < paths.size(); i++){
			System.out.println(paths.size());
			Path path = paths.get(i);
			if(path.getLastArea()!=endArea){
				for(Area area: path.getExpandAreas()){
					boolean toAdd = true;
					Path p = new Path(path, area);
					for(Path pth: paths){
						if(pth.isAreaOnPath(p.getLastArea())){
							if(path.getLength(tm)<pth.getLengthUpTo(tm, path.getLastArea())){
								toAdd = false;
								break;
							}
						}
					}
					if(toAdd)paths.add(p);
				}
				paths.remove(path);
				i--;
			}
		}
		System.out.println(paths.size());
		Path shortestPath = paths.get(0);
		for(Path path: paths){
			if(path.getLength(tm)<shortestPath.getLength(tm))shortestPath = path;
		}
		
		return shortestPath;
	}
	public Path findAreaPath(int[] start, int[] end){
    		Area startArea = findArea(start);
    		Area endArea = findArea(end);
    		Path path = minPaths.get(areas.indexOf(startArea)).get(areas.indexOf(endArea));
    		path.start = start;
    		path.end = end;
//    		startArea.begin();
//    		endArea.begin();
//    		
//    		ArrayList<Path> paths = new ArrayList<Path>();
//    		paths.add(new Path(startArea,start,end));
//    		System.out.println("Started Pathing");
//    		for(int i = 0; i < paths.size(); i++){
//    			System.out.println(paths.size());
//    			Path path = paths.get(i);
//    			if(path.getLastArea()!=endArea){
//    				for(Area area: path.getExpandAreas()){
//    					boolean toAdd = true;
//    					Path p = new Path(path, area);
//    					for(Path pth: paths){
//    						if(pth.isAreaOnPath(p.getLastArea())){
//    							if(path.getLength(tm)<pth.getLengthUpTo(tm, path.getLastArea())){
//    								toAdd = false;
//    								break;
//    							}
//    						}
//    					}
//    					if(toAdd)paths.add(p);
//    				}
//    				paths.remove(path);
//    				i--;
//    			}
//    		}
//    		System.out.println(paths.size());
//    		Path shortestPath = paths.get(0);
//    		for(Path path: paths){
//    			if(path.getLength(tm)<shortestPath.getLength(tm))shortestPath = path;
//    		}
    		
    		return path;
    		
	}
	
	public ArrayList<int[]> findPath(int[] start, int[] end){
		Area startArea = findArea(start);
		Area endArea = findArea(end);
		if(startArea.equals(endArea))return startArea.findPath(tm, start, end);
		return findAreaPath(start, end).getTiles(tm);
	}
}
