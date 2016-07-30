package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import com.dungeon.game.world.World;

public class Biospheres extends Generation {
	private ArrayList<Circle> rooms;
	private ArrayList<ArrayList<int[]>> halls;
	
	public Biospheres(World world, int width, int height){
		super(world, width, height);
		rooms = new ArrayList<Circle>();
		halls = new ArrayList<ArrayList<int[]>>();
		int x = width/2;
		int y = height/2;
		generateStartSphere(x,y);
	}
	
	public boolean generateStartSphere(int x, int y){
		int radius = 2+(int) (Math.random()*10);
		Circle room = new Circle(x,y,radius);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			addRoomToMap(room);
			return true;
		}
		return false;
	}

	private boolean isValidRoom(Circle room) {
		for(Circle i :rooms){
			if(i.overlaps(room))return false;
		}
		return true;
	}
	
	public void addRoomToMap(Circle room){
		rooms.add(room);
		for(int i = 0; i <map.length-1; i++){
			for(int k = 0; k <map[0].length-1; k++){
				if(room.contains(k, i))map[i][k]=tileMap.getTile(1);
			}
		}
	}

	@Override
	public void generateAreas() {
		
	}
}
