package com.dungeon.game.generator.rooms.room;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;

public abstract class Room {
	public Tile[][] room;
	
	public Rectangle roomBase;
	
	public ArrayList<Entity> entities;
	
	protected int[][] doorFinder;
	
	protected boolean[][] occupiedTiles;
	
	protected int[][] doorPos;
	
	public int x;
	public int y;
	
	public TileMap tileMap;
	
	World world;
	
	public Room(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap){
		this.world = world;
		x = (int) roomBase.x;
		y = (int) roomBase.y;
		this.roomBase = roomBase;
		this.room = new Tile[(int) roomBase.height][(int) roomBase.width];
		this.doorFinder = doorFinder;
		doorPos = new int[doorFinder.length][2];
		entities = new ArrayList<Entity>();
		this.tileMap = tileMap;
		rotate();
		generate();
//		commented code tests doors
//		if(checkOccupied(doorPos[0][0]+1, doorPos[0][1]))room[doorPos[0][1]][doorPos[0][0]+1] = tileMap.getTile(7);
//		if(checkOccupied(doorPos[0][0]-1, doorPos[0][1]))room[doorPos[0][1]][doorPos[0][0]-1] = tileMap.getTile(7);
//		if(checkOccupied(doorPos[0][0], doorPos[0][1]+1))room[doorPos[0][1]+1][doorPos[0][0]] = tileMap.getTile(7);
//		if(checkOccupied(doorPos[0][0], doorPos[0][1]-1))room[doorPos[0][1]-1][doorPos[0][0]] = tileMap.getTile(7);
		unrotate();
	}
	
	public abstract void generate();
	
	private void rotate(){
		if(doorFinder[0][0]==2||doorFinder[0][0]==3){
			room = new Tile[(int) room[0].length][(int) room.length];
			occupiedTiles = new boolean[room.length][room[0].length];
			doorPos[0][0] = 0;
			doorPos[0][1] = (int) (doorFinder[0][1]-x);
		}
		else {
			room = new Tile[(int) room.length][(int) room[0].length];
			occupiedTiles = new boolean[room.length][room[0].length];
			doorPos[0][0] = 0;
			doorPos[0][1] = (int) (doorFinder[0][2]- y);
		}
		
		//find doors
		for(int i = 0; i < doorFinder.length; i++){
			doorPos[i][0] = doorFinder[i][1]-x;
			doorPos[i][1] = doorFinder[i][2]-y;
		}
		
		if(doorFinder[0][0]==2||doorFinder[0][0]==3){
			for(int i = 0; i < doorFinder.length; i++){
				int temp = doorPos[i][0];
				doorPos[i][0] = doorPos[i][1];
				doorPos[i][1] = temp;
			}
		}
		
		if(doorFinder[0][0]==1||doorFinder[0][0]==3){
			for(int i = 0; i < doorFinder.length; i++){
				doorPos[i][0] = room[1].length-1-doorPos[i][0];
			}
		}
	}
	
	private void unrotate(){
//		for(int i = 0; i < room.length; i++){
//			for(int k = 0; k < room[0].length; k++){
//				if(checkOccupied(k, i))room[i][k] = tileMap.getTile(6);
//			}
//		}
		if(doorFinder[0][0]==1||doorFinder[0][0]==3){
			Tile[][] temp = room.clone();
			
			if(doorFinder[0][0]==1) room = new Tile[(int) roomBase.height][(int) roomBase.width];
			else room = new Tile[(int) roomBase.width][(int) roomBase.height];
			
			for(int i = 0; i < temp.length; i++){
				for(int k = 0; k < temp[i].length; k++){
					room[i][room[i].length-1-k]=temp[i][k];
				}
			}
			
			for(Entity e: entities) {
				e.x = room[0].length*Tile.TS-e.x;
				if(e instanceof Character){
					e.angle+=180;
				}else{
					e.flipX  = !e.flipX;	
				}
			}
		}
		
		if(doorFinder[0][0]==2||doorFinder[0][0]==3){
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
				if(e instanceof Character){
					e.angle+=180;
				}else{
					e.flipX  = !e.flipX;	
				}
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
	
	protected boolean checkOccupied(int x, int y){ // this is not working...
		if(x < 0 || y < 0 || x > occupiedTiles[0].length - 1 || y > occupiedTiles.length - 1)return false;
		return occupiedTiles[y][x];
	}
	
	protected void addToOccupied(int x, int y){
		if(x >= 0 && x < occupiedTiles[0].length && y >= 0 && y < occupiedTiles.length)occupiedTiles[y][x] = true;
	}
}
