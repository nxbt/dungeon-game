package com.dungeon.game.generator;

import java.util.ArrayList;

import com.dungeon.game.entity.Chest;
import com.dungeon.game.entity.Door;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;

public abstract class Generation {
	private int width;
	private int height;
	protected int[][] map;
	protected ArrayList<Entity> entities;
	public Generation(int width, int height){
		this.height = height;
		this.width = width;
		this.map = new int[height][width];
		entities = new ArrayList<Entity>();
		generateClearDungeon();
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
		if(dir==0)entities.add(new Door(x*Tile.TS,y*Tile.TS,0));
		if(dir==1)entities.add(new Door(x*Tile.TS,y*Tile.TS,1));
	}
}
