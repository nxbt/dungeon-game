package com.dungeon.game.pathing;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;

public class Area {
	private ArrayList<int[]> points; //Array of all tiles in this area
	private ArrayList<int[]> edgePoints; //Array of all edges of this area.
	private ArrayList<Area> adjacentAreas; //Holds reference to all adjacent Areas
	private ArrayList<ArrayList<int[]>> edges; //Holds points where this Area Borders other Areas
	private ArrayList<int[][][][]> minPaths; //Holds information for the minimum viable paths from one edge to another;
	private ArrayList<Entity> entities; //contains data for all entities in the area;
	
	public Area(){
		points = new ArrayList<int[]>();
		edgePoints = new ArrayList<int[]>();
		adjacentAreas = new ArrayList<Area>();
		edges = new ArrayList<ArrayList<int[]>>();
		minPaths = new ArrayList<int[][][][]>();
		entities = new ArrayList<Entity>();
		
	}
	
	public void addRectangleToArea(int x, int y, int width, int height){ //adds a rectangle of points to the Area.
		for(int i = x; i < x+width; i++){
			for(int k = y; k< y+height; k++){
				points.add(new int[]{x,y});
			}
		}
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
}
