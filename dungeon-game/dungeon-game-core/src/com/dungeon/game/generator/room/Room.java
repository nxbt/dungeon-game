package com.dungeon.game.generator.room;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public abstract class Room {
	public Tile[][] room;
	
	public Rectangle roomBase;
	
	public ArrayList<Entity> entities;
	
	protected int[] doorFinder;
	
	protected boolean[][] occupiedTiles;
	
	protected int[] doorPos;
	
	public int x;
	public int y;
	
	public TileMap tileMap;
	
	World world;
	
	public Room(World world, Rectangle roomBase, int[] doorFinder, TileMap tileMap){
		this.world = world;
		x = (int) roomBase.x;
		y = (int) roomBase.y;
		this.roomBase = roomBase;
		this.room = new Tile[(int) roomBase.height][(int) roomBase.width];
		this.doorFinder = doorFinder;
		doorPos = new int[2];
		entities = new ArrayList<Entity>();
		this.tileMap = tileMap;
		rotate();
		generate();
		unrotate();
	}
	
	public abstract void generate();
	
	private void rotate(){
		if(doorFinder[0]==2||doorFinder[0]==3){
			room = new Tile[(int) room[0].length][(int) room.length];
			occupiedTiles = new boolean[room.length][room[0].length];
			doorPos[0] = 0;
			doorPos[1] = (int) (doorFinder[1]-x);
		}
		else {
			room = new Tile[(int) room.length][(int) room[0].length];
			occupiedTiles = new boolean[room.length][room[0].length];
			doorPos[0] = 0;
			doorPos[1] = (int) (doorFinder[2]- y);
		}
	}
	
	private void unrotate(){
		if(doorFinder[0]==1||doorFinder[0]==3){
			Tile[][] temp = room.clone();
			
			if(doorFinder[0]==1) room = new Tile[(int) roomBase.height][(int) roomBase.width];
			else room = new Tile[(int) roomBase.width][(int) roomBase.height];
			
			for(int i = 0; i < temp.length; i++){
				for(int k = 0; k < temp[i].length; k++){
					room[i][room[i].length-1-k]=temp[i][k];
				}
			}
			
			for(Entity e: entities) {
				e.x = room[0].length*Tile.TS-e.x;
				e.flipX  = !e.flipX;
			}
		}
		
		if(doorFinder[0]==2||doorFinder[0]==3){
			Tile[][] temp = room.clone();
			room = new Tile[(int) roomBase.height][(int) roomBase.width];
			for(int i = 0; i < temp.length; i++){
				for(int k = 0; k < temp[i].length; k++){
					room[k][i]=temp[i][k];
				}
			}
			for(Entity e: entities) {
				float tempX = e.x;
				e.x = e.y;
				e.y = tempX;
				e.angle-=90;
				e.flipX = !e.flipX;
			}
		}
	}
	
	public void addToMap(Tile[][] tileMap, ArrayList<Entity> floorEntities){
		int x = (int) this.x;
		int y = (int) this.y;
		
		for(int i = 0; i < roomBase.height; i++){
			for(int k = 0; k < roomBase.width; k++){
				if(room[i][k] != null)tileMap[y][x] = room[i][k];
				x++;
			}
			y++;
			x = (int) this.x;
		}
		
		for(Entity e: entities) {
			e.x += this.x*Tile.TS;
			e.y += this.y*Tile.TS;
			floorEntities.add(e);
		}
	}
	
	protected boolean checkOccupied(int x, int y){
		return occupiedTiles[y][x];
	}
	
	protected void addToOccupied(int x, int y){
		if(x >= 0 && x < occupiedTiles[0].length && y >= 0 && y < occupiedTiles.length)occupiedTiles[y][x] = true;
	}
}
