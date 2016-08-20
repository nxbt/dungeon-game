package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.world.Tile;

public class AreaMap {
	private ArrayList<Area> areas; //contains data for all areas;
	private Tile[][] tm; //contains tile data for the entire areaMap;
	
	private ArrayList<ArrayList<Path>> minPaths;
	
	public ArrayList<int[]> lastPath;
	
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
			System.out.println("Calculating Area Edges: "+(int)((areas.indexOf(area)+1)/(float)areas.size()*100)+"%");
		}
	}
	
	private void calculateBorders(){
		for(Area area: areas){
			for(Area a: areas){
				if(!area.equals(a))area.calculateBorders(a);
			}

			System.out.println("Finding Adjacent Areas: "+(int)((areas.indexOf(area)+1)/(float)areas.size()*100)+"%");
		}
	}
	
	private void calculateMinPaths(){
		for(Area area: areas){
			area.begin();
			area.calculateMinPaths(tm);

			System.out.println("Finding Min Paths Across Areas: "+(int)((areas.indexOf(area)+1)/(float)areas.size()*100)+"%");
		}
	}
	
	private void calculateAreaMinPaths(){
		minPaths = new ArrayList<ArrayList<Path>>();
		for(Area area: areas){
			minPaths.add(new ArrayList<Path>());
			for(Area a: areas){
				Path minPath = null;
				if(!area.equals(a)){
					minPath = findMinPath(area.getCenter(), a.getCenter());
				}
				minPaths.get(minPaths.size()-1).add(minPath);
			}
			System.out.println("Finding Min Paths Between Areas: "+(int)((areas.indexOf(area)+1)/(float)areas.size()*100)+"%");
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
		for(int i = 0; i < paths.size(); i++){
			Path path = paths.get(i);
			if(!path.getLastArea().equals(endArea)){
				for(Area area: path.getExpandAreas()){
					boolean toAdd = true;
					Path p = new Path(path, area);
					for(Path pth: paths){
						if(pth.isAreaOnPath(p.getLastArea())){
							if(p.getLength(tm)>=pth.getLengthUpTo(tm, p.getLastArea())){
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
		//WHY IS THIS MORE THAN 1???
		Path shortestPath = paths.get(0);
		if(paths.size()>2){
			for(Path path: paths){
				if(path.getLength(tm)<shortestPath.getLength(tm))shortestPath = path;
			}
		}
		
		return shortestPath;
	}
	public Path findAreaPath(int[] start, int[] end){
    		Area startArea = findArea(start);
    		Area endArea = findArea(end);
    		if(startArea!=null&&endArea!=null){
    			Path path = minPaths.get(areas.indexOf(startArea)).get(areas.indexOf(endArea));
        		path.start = start;
        		path.end = end;
        		
        		return path;
        		
    		}else return null;
	}
	
	public int[] findPath(int[] start, int[] end){
		ArrayList<int[]> path;
		Area startArea = findArea(start);
		Area endArea = findArea(end);
		if(startArea.equals(endArea)){
			path = startArea.findPath(tm, start, end);
		}
		else if(startArea == null||endArea == null){
			System.out.println("PATHFINDING ERROR");
			return start;
		}else path = findAreaPath(start, end).getTiles(tm);
		lastPath = path;
		if(path.size()==1)return path.get(0);
		Vector2 startPoint_bl = new Vector2(path.get(0)[0]*Tile.TS,path.get(0)[1]*Tile.TS);
		Vector2 startPoint_br = new Vector2(path.get(0)[0]*Tile.TS+Tile.TS,path.get(0)[1]*Tile.TS);
		Vector2 startPoint_tl = new Vector2(path.get(0)[0]*Tile.TS,path.get(0)[1]*Tile.TS+Tile.TS);
		Vector2 startPoint_tr = new Vector2(path.get(0)[0]*Tile.TS+Tile.TS,path.get(0)[1]*Tile.TS+Tile.TS);
		Vector2 endPoint_bl;
		Vector2 endPoint_br;
		Vector2 endPoint_tl;
		Vector2 endPoint_tr;
		boolean changeDestination;
		int[] destination = path.get(1);
		Polygon tilePolygon;
		for(int[] point: path){
			changeDestination = true;
			endPoint_bl = new Vector2(point[0]*Tile.TS,point[1]*Tile.TS);
			endPoint_br = new Vector2(point[0]*Tile.TS+Tile.TS,point[1]*Tile.TS);
			endPoint_tl = new Vector2(point[0]*Tile.TS,point[1]*Tile.TS+Tile.TS);
			endPoint_tr = new Vector2(point[0]*Tile.TS+Tile.TS,point[1]*Tile.TS+Tile.TS);
			for(int i = Math.min(path.get(0)[1], point[1]); i <= Math.max(path.get(0)[1], point[1]);i++){
				for(int k = Math.min(path.get(0)[0], point[0]); k<= Math.max(path.get(0)[0], point[0]);k++){	
					tilePolygon = new Polygon(new float[]{k*Tile.TS, i*Tile.TS,(k+1)*Tile.TS, i*Tile.TS,(k+1)*Tile.TS, (i+1)*Tile.TS, k*Tile.TS, (i+1)*Tile.TS});
					if(tm[i][k].data==1&&(Intersector.intersectSegmentPolygon(startPoint_bl, endPoint_bl, tilePolygon)||Intersector.intersectSegmentPolygon(startPoint_br, endPoint_br, tilePolygon)||Intersector.intersectSegmentPolygon(startPoint_tl, endPoint_tl, tilePolygon)||Intersector.intersectSegmentPolygon(startPoint_tr, endPoint_tr, tilePolygon))){
						changeDestination = false;
					}
				}
			}
			if(changeDestination)destination = point;
			else break;
		}
		return destination;
	}
}
