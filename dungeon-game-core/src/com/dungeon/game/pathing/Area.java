package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;

public class Area {
	private ArrayList<int[]> points; //Array of all tiles in this area
	private ArrayList<int[]> edgePoints; //Array of all edges of this area.
	private ArrayList<Area> adjacentAreas; //Holds reference to all adjacent Areas
	private ArrayList<ArrayList<int[]>> edges; //Holds points where this Area Borders other Areas
	private ArrayList<ArrayList<ArrayList<ArrayList<int[]>>>> minPaths; //Holds information for the minimum viable paths from one edge to another;
	private ArrayList<Entity> entities; //contains data for all entities in the area;
	
	public Area(){
		points = new ArrayList<int[]>();
		edgePoints = new ArrayList<int[]>();
		adjacentAreas = new ArrayList<Area>();
		edges = new ArrayList<ArrayList<int[]>>();
		minPaths = new ArrayList<ArrayList<ArrayList<ArrayList<int[]>>>>();
		entities = new ArrayList<Entity>();
		
	}
	
	public void addRectangleToArea(int x, int y, int width, int height){ //adds a rectangle of points to the Area.
		for(int i = x; i < x+width; i++){
			for(int k = y; k< y+height; k++){
				points.add(new int[]{x,y});
			}
		}
		calculateEdges();
	}
	private void calculateEdges() { //recalculates the edges of this area;
		edgePoints = new ArrayList<int[]>();
		for(int[] i: points){
			boolean topFound = false;
			boolean botFound = false;
			boolean leftFound = false;
			boolean rightFound = false;
			for(int[] k: points){
				if(i[0]+1==k[0]&&i[1]==k[1]) rightFound = true;
				if(i[0]-1==k[0]&&i[1]==k[1]) leftFound = true;
				if(i[0]==k[0]&&i[1]+1==k[1]) topFound = true;
				if(i[0]==k[0]&&i[1]-1==k[1]) botFound = true;
			}
			
			if(!(topFound&&botFound&&leftFound&&rightFound))edgePoints.add(i);
		}
	}
	public void calculateBorders(Area a){ //find the borders between this area and another one.
		ArrayList<int[]> borders = new ArrayList<int[]>();
		for(int[] i:edgePoints){
			for(int[] k: a.edgePoints){
				if(i[0]-1==k[0]&&i[1]==k[1])borders.add(new int[]{i[0], k[0]});
				else if(i[0]+1==k[0]&&i[1]==k[1])borders.add(new int[]{i[0], k[0]});
				else if(i[0]==k[0]&&i[1]-1==k[1])borders.add(new int[]{i[0], k[0]});
				else if(i[0]==k[0]&&i[1]+1==k[1])borders.add(new int[]{i[0], k[0]});
			}
		}
		if(borders.size()>0){
			int indexOfArea = 0;
			if(adjacentAreas.indexOf(a)==-1){
				adjacentAreas.add(a);
			}
			indexOfArea = adjacentAreas.indexOf(a);
			
			edges.set(indexOfArea, borders);
		}
	}
	
	public void calculateMinPaths(Tile[][] tm){ //calculates the shortest path between any two edges of this area;
		for(int i = 0; i < edges.size();i++){
			for(int k = 0; k <edges.size();k++){
				if(i!=k){
					for(int[] start: edges.get(i)){
						for(int[] end: edges.get(k)){
							//pathfind from start to end and add that to the array minPaths
						}
					}
				}
			}
		}
	}
	
	public ArrayList<int[]> findPath(Tile[][] tm, int[] start, int[] end){ //uses A* to find a path within from one edge to another
		ArrayList<int[]> queue = new ArrayList<int[]>();
		queue.add(new int[]{end[0],end[1],0});
		boolean endQueue = false;
		
		for(int i = 0; i < queue.size(); i++){
			ArrayList<int[]> toAdd = new ArrayList<int[]>();
			//finish Pathfinding code here
			
		}
		return queue;
	}
}
