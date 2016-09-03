package com.dungeon.game.generator;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.furniture.Fireplace;
import com.dungeon.game.pathing.Graph;
import com.dungeon.game.world.Floor;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public abstract class Generation {
	protected World world;
	
	protected int width;
	protected int height;
	
	public Tile[][] map;
	
	protected ArrayList<Entity> entities;
	
	public int textureSeed;
	
	public TileMap tileMap;
	public Generation(World world, int width, int height, int textureSeed, Object[] args){
		this.textureSeed = textureSeed;
		tileMap = Floor.tileMap1;
		this.height = height;
		this.width = width;
		map = new Tile[height][width];
		entities = new ArrayList<Entity>();
		this.world = world;
		generate(args);
		
		
	}
	
	protected void generate(Object[] args){
		generateClearDungeon();
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void generateClearDungeon(){
		for(int i = 0; i<map.length;i++){
			for(int k = 0; k<map[i].length;k++){
				map[i][k]=tileMap.getTile(15);
//				rotations[i][k]=0;
//				flips[i][k] = false;
			}
		}
	}
	
	public void makeWalls(int id) {
		for(int i = 0; i < map.length; i++) {
			for(int k = 0; k < map[0].length; k++) {
				if(Tile.isSolid(map[i][k])) {
					int sides = 0;
					int corners = 0;
					
					if(k != 0 && !Tile.isSolid(map[i][k-1])) sides += 1; //left
					if(i != 0 && !Tile.isSolid(map[i-1][k])) sides += 2; //bottom
					if(k != map[i].length-1 && !Tile.isSolid(map[i][k+1])) sides += 4; //right
					if(i != map.length-1 && !Tile.isSolid(map[i+1][k])) sides += 8; //upper
					
					if(i != 0 && k != 0 && !Tile.isSolid(map[i-1][k-1])) corners += 1; //bottom left
					if(i != map.length-1 && k != 0 && !Tile.isSolid(map[i+1][k-1])) corners += 2; //upper left
					if(i != 0 && k != map[i].length-1 && !Tile.isSolid(map[i-1][k+1])) corners += 4; //bottom right
					if(i != map.length-1 && k != map[i].length-1 && !Tile.isSolid(map[i+1][k+1])) corners += 8; //upper right
					if(!(sides == 0 && corners == 0)){
						map[i][k] = tileMap.getTile(id);
					}
				}
			}
		}
	}
	
	public void generateSprites(){
		for(Entity e: entities){
			if(e instanceof Fireplace)((Fireplace) e).genTexture(textureSeed);
		}
	}
	
	public abstract Graph getPathGraph();
}
