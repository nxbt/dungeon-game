package com.dungeon.game.generator.objects;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class GenObject {
	
	protected World world;
	
	public int x;
	
	public int y;
	
	public int width;
	
	public int height;
	
	private Tile[][] tileMap;
	
	protected boolean[][] reservedTiles;
	
	private ArrayList<Entity> entities;
	
	public GenObject(World world, int x, int y, int width, int height){
		this.world = world;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		reservedTiles = new boolean[width][height];
		
		entities = new ArrayList<Entity>();
	}
	
	public boolean isOverlaping(GenObject g){ //do any tiles from these 2 zones overlap?
		boolean[][] tilesThis = getOccupiedTiles();
		boolean[][] tilesOther = g.getOccupiedTiles();
		
		int xDiff = x - g.x;
		int yDiff = y - g.y;
		
		for(int x = 0; x < tilesThis.length; x++){
			for(int y = 0; y < tilesThis[0].length; y++){
				if(x + xDiff >= 0 && x + xDiff < tilesOther.length && y + yDiff >= 0 && y + yDiff < tilesOther[0].length && tilesThis[x][y] && tilesOther[x + xDiff][y + yDiff])return true;
			}
		}
		return false;
	}
	
	public boolean isConflicting(GenObject g){ //are any tiles from these 2 zones overlaping or adjacent?
		boolean[][] tilesThis = getOccupiedTiles();
		boolean[][] tilesOther = g.getOccupiedTiles();
		
		int xDiff = x - g.x;
		int yDiff = y - g.y;
		
		for(int x = 0; x < tilesThis.length; x++){
			for(int y = 0; y < tilesThis[0].length; y++){
				if(x + xDiff >= 0 && x + xDiff < tilesOther.length && y + yDiff >= 0 && y + yDiff < tilesOther[0].length && tilesThis[x][y] && tilesOther[x + xDiff][y + yDiff])return true;
				if(x + xDiff - 1 >= 0 && x + xDiff - 1 < tilesOther.length && y + yDiff >= 0 && y + yDiff < tilesOther[0].length && tilesThis[x][y] && tilesOther[x + xDiff - 1][y + yDiff])return true;
				if(x + xDiff + 1 >= 0 && x + xDiff + 1 < tilesOther.length && y + yDiff >= 0 && y + yDiff < tilesOther[0].length && tilesThis[x][y] && tilesOther[x + xDiff + 1][y + yDiff])return true;
				if(x + xDiff >= 0 && x + xDiff < tilesOther.length && y + yDiff - 1 >= 0 && y + yDiff - 1 < tilesOther[0].length && tilesThis[x][y] && tilesOther[x + xDiff][y + yDiff - 1])return true;
				if(x + xDiff >= 0 && x + xDiff < tilesOther.length && y + yDiff + 1 >= 0 && y + yDiff + 1 < tilesOther[0].length && tilesThis[x][y] && tilesOther[x + xDiff][y + yDiff + 1])return true;
			}
		}
		return false;
	}
	
	public boolean isAdjacent(int x, int y){ //is the x and y, adjacent, but not part of this tile
		if(isOverlapping(x, y))return false;
		if(isOverlapping(x + 1, y))return true;
		if(isOverlapping(x - 1, y))return true;
		if(isOverlapping(x, y + 1))return true;
		if(isOverlapping(x, y - 1))return true;
		return false;
	}
	
	public boolean isOverlapping(int x, int y){ //is the x and y overlapping this area?
		if(x - this.x < 0 || x - this.x  > width - 1 || y - this.y < 0 || y - this.y  > height - 1)return false;
		return getOccupiedTiles()[x - this.x][y - this.y];
		
	}
	
	public boolean isConflicting(int x, int y){
		return isAdjacent(x, y) || isOverlapping(x, y);
	}
	
	public boolean[][] getOccupiedTiles(){
		return reservedTiles;
	}
	
	public void addToMap(Tile[][] map, ArrayList<Entity> mapEntities){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				map[this.y + y][this.x + x] = tileMap[x][y]; //gotta swap x and y untill we fix our shit...
			}
		}
		mapEntities.addAll(entities);
	}
}
