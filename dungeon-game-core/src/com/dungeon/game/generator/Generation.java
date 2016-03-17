package com.dungeon.game.generator;

import java.util.ArrayList;

import com.dungeon.game.entity.Door;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.pathing.Area;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Generation {
	protected World world;
	protected int width;
	protected int height;
	protected int[][] map;
	protected ArrayList<Entity> entities;
	
	public ArrayList<Area> areas;
	public Generation(World world, int width, int height){
		areas = new ArrayList<Area>();
		this.height = height;
		this.width = width;
		this.map = new int[height][width];
		entities = new ArrayList<Entity>();
		
		generateClearDungeon();
		this.world = world;
		
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void generateClearDungeon(){
		for(int i = 0; i<map.length;i++){
			for(int k = 0; k<map[i].length;k++){
				map[i][k]=1;
			}
		}
	}
	
	public void addDoor(int x, int y, int dir){
		if(dir==0)entities.add(new Door(world, x*Tile.TS,y*Tile.TS,0));
		if(dir==1)entities.add(new Door(world, x*Tile.TS,y*Tile.TS,1));
	}
	
	public abstract void generateAreas();
}
