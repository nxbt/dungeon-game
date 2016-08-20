package com.dungeon.game.generator.rooms.hallway;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;

public abstract class Hallway {
	
	public int[][] cords; //chords of the tiles in the hallway
	
	public Tile[] tiles; // the tiles in the hallway
	
	public ArrayList<int[]> additionalChords; //if the hallway modifies any tiles not IN the hallway
	
	public ArrayList<Tile> additionalTiles; //if the hallway modifies any tiles not IN the hallway
	
	public ArrayList<Entity> entities;
	
	public TileMap tileMap;
	
	public int x; //the x of the first tile in the world
	
	public int y; //the y of the first tile in the world
	
	public Hallway(ArrayList<int[]> coordinates, TileMap tileMap){
		x = coordinates.get(0)[0];
		y = coordinates.get(0)[1];
		cords = new int[coordinates.size()][2];
		for(int i = 0; i < coordinates.size(); i++){
			cords[i] = new int[]{coordinates.get(i)[0] - x, coordinates.get(i)[1] - y, coordinates.get(i)[2]};
		}
		tiles = new Tile[cords.length];
		this.tileMap = tileMap;
		additionalChords = new ArrayList<int[]>();
		additionalTiles = new ArrayList<Tile>();
		
		entities = new ArrayList<Entity>();
		
		generate();
		
	}
	
	public abstract void generate();
	
	public void addToMap(Tile[][] tileMap, ArrayList<Entity> floorEntities){
		
		for(int i = 0; i < cords.length; i++){
			int x = cords[i][0] + this.x;
			int y = cords[i][1] + this.y;
			if(tiles[i] != null)tileMap[y][x] = tiles[i];
		}
		
		for(int i = 0; i < additionalChords.size(); i++){
			int x = additionalChords.get(i)[0] + this.x;
			int y = additionalChords.get(i)[1] + this.y;
			tileMap[y][x] = additionalTiles.get(i);
		}
		
		for(Entity e: entities) {
			e.x += this.x*Tile.TS;
			e.y += this.y*Tile.TS;
			floorEntities.add(e);
		}
	}

}
