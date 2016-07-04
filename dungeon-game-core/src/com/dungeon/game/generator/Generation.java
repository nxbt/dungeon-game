package com.dungeon.game.generator;

import java.util.ArrayList;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.furniture.Door;
import com.dungeon.game.pathing.Area;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Generation {
	protected World world;
	
	protected int width;
	protected int height;
	
	public int[][] map;
	public int[][] rotations;
	public boolean[][] flips;
	
	protected ArrayList<Entity> entities;
	
	public ArrayList<Area> areas;
	public Generation(World world, int width, int height){
		areas = new ArrayList<Area>();
		this.height = height;
		this.width = width;
		map = new int[height][width];
		rotations = new int[height][width];
		flips = new boolean[height][width];
		entities = new ArrayList<Entity>();
		
		generateClearDungeon();
		this.world = world;
		
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void generateClearDungeon(){
		for(int i = 0; i<map.length;i++){
			for(int k = 0; k<map[i].length;k++){
				map[i][k]=15;
				rotations[i][k]=0;
				flips[i][k] = false;
			}
		}
	}
	
	public void addDoor(int x, int y, int dir){
		if(dir==0)entities.add(new Door(world, x*Tile.TS,y*Tile.TS,0));
		if(dir==1)entities.add(new Door(world, x*Tile.TS,y*Tile.TS,1));
	}
	
	public void makeWalls(int strt_id, int bent_id, int revr_id, int thre_id, int four_id) {
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
					if(sides == 1 && (corners == 10 || corners == 9 || corners == 12 || corners == 4 || corners == 8 || corners == 5 || corners == 7 || corners == 11 || corners == 14 || corners == 13 || corners ==6)){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 3;
					}
					else if(sides == 2 && (corners == 3||corners == 6||corners == 10||corners == 8||corners == 2||corners == 12||corners == 13||corners == 7 || corners == 11 || corners == 14 || corners == 9)){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 2;
					}
					else if(sides == 4 && (corners == 5||corners == 9||corners == 3||corners == 2||corners == 1||corners == 10||corners == 14||corners == 13 || corners == 7 || corners == 11 || corners == 6)){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 1;
					}
					else if(sides == 8 && (corners == 12||corners == 6||corners == 5||corners == 1||corners == 4||corners == 3||corners == 11||corners == 14 || corners == 13 || corners == 7 || corners == 9)){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 0;
					}
					else if(sides == 0 && corners == 3){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 1;
					}
					else if(sides == 0 && corners == 5){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 0;
					}
					else if(sides == 0 && corners == 12){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 3;
					}
					else if(sides == 0 && corners == 10){
						map[i][k]= thre_id;
						flips[i][k] = false;
						rotations[i][k] = 2;
					}
					else if(sides == 1 || sides == 5) {
						map[i][k] = strt_id;
						flips[i][k] = false;
						rotations[i][k] = 0;
					}
					else if(sides == 4) {
						map[i][k] = strt_id;
						flips[i][k] = false;
						rotations[i][k] = 2;
					}
					else if(sides == 2 || sides == 10) {
						map[i][k] = strt_id;
						flips[i][k] = false;
						rotations[i][k] = 1;
					}
					else if(sides == 8) {
						map[i][k] = strt_id;
						flips[i][k] = false;
						rotations[i][k] = 3;
					}
					else if(sides == 14) {
						map[i][k] = revr_id;
						flips[i][k] = false;
						rotations[i][k] = 1;
					}
					else if(sides == 13) {
						map[i][k] = revr_id;
						flips[i][k] = false;
						rotations[i][k] = 0;
					}
					else if(sides == 11) {
						map[i][k] = revr_id;
						flips[i][k] = false;
						rotations[i][k] = 3;
					}
					else if(sides == 7) {
						map[i][k] = revr_id;
						flips[i][k] = false;
						rotations[i][k] = 2;
					}
					else if(sides == 3) {
						map[i][k] = bent_id;
						flips[i][k] = true;
						rotations[i][k] = 3;
					}
					else if(sides == 6) {
						map[i][k] = bent_id;
						flips[i][k] = false;
						rotations[i][k] = 2;
					}
					else if(sides == 9) {
						map[i][k] = bent_id;
						flips[i][k] = false;
						rotations[i][k] = 0;
					}
					else if(sides == 12) {
						map[i][k] = bent_id;
						flips[i][k] = true;
						rotations[i][k] = 1;
					}
					else if(corners == 1) {
						map[i][k] = bent_id;
						flips[i][k] = false;
						rotations[i][k] = 1;
					}
					else if(corners == 2) {
						map[i][k] = bent_id;
						flips[i][k] = true;
						rotations[i][k] = 2;
					}
					else if(corners == 4) {
						map[i][k] = bent_id;
						flips[i][k] = true;
						rotations[i][k] = 0;
					}
					else if(corners == 8) {
						map[i][k] = bent_id;
						flips[i][k] = false;
						rotations[i][k] = 3;
					}
					else if(sides == 0 && (corners == 6 || corners == 9 || corners == 15 || corners == 14 || corners == 13 || corners == 11 || corners == 7)){
						map[i][k] = four_id;
						flips[i][k] = false;
						rotations[i][k] = 0;
					}
				}
			}
		}
	}
	
	public abstract void generateAreas();
}
