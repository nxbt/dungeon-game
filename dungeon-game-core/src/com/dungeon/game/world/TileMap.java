package com.dungeon.game.world;

public class TileMap {
	public Tile[] tiles;
	public String fileName;
	
	public TileMap(String fileName){
		this.fileName = fileName;
		createTiles();
	}
	
	public void createTiles(){
		tiles = new Tile[30];
		for(int i = 0; i < tiles.length; i++){
			tiles[i] = new Tile(i);
		}
		
	}
	
	public Tile getTile(int id){
		return tiles[id].clone();
	}
}
