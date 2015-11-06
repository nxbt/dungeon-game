package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Rooms {
	private int width;
	private int height;
	private int[][] map;
	private ArrayList<Rectangle> rooms;
	
	public Rooms(int width, int height){
		this.width = width;
		this.height = height;
		map = new int[height][width];
		rooms = new ArrayList<Rectangle>();
		generateClearDungeon();
		int x = height/2;
		int y = width/2;
		generateStartRoom(x, y);
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public void generateStartRoom(int x, int y){
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int) (Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x,y,width,height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			addRoomToMap(room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(nextX, nextY);
				}
			}
		}
	}
	
	public void generateBelowRoom(int x, int y){
		int doorX = x;
		int doorY = y-1;
		int height = (int) (3+Math.random()*map.length/7);
		y-=1+height;
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			map[doorY][doorX]=0;
			addRoomToMap(room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(nextX, nextY);
				}
			}
		}
	}
	
	public void generateAboveRoom(int x, int y){
		int doorX = x;
		int doorY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y++;
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			map[doorY][doorX]=0;
			addRoomToMap(room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(nextX, nextY);
				}
			}
		}
	}
	
	public void generateLeftRoom(int x, int y){
		int doorX = x-1;
		int doorY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int)(Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=1+width;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			map[doorY][doorX]=0;
			addRoomToMap(room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(nextX, nextY);
				}
			}
		}
	}
	
	public void generateRightRoom(int x, int y){
		int doorX = x;
		int doorY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int)(Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x++;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			map[doorY][doorX]=0;
			addRoomToMap(room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(nextX, nextY);
				}
			}
		}
	}
	
	public boolean isValidRoom(Rectangle room){
		boolean result = true;
		for(Rectangle i: rooms){
			if(Intersector.intersectRectangles(new Rectangle(room.x-1,room.y-1,room.width+1,room.height+1), new Rectangle(i.x-1,i.y-1,i.width+1,i.height+1), new Rectangle()))result = false;
		}
		if(room.x<1)result = false;
		if(room.y<1)result = false;
		if(room.y+room.height>map.length-1)result = false;
		if(room.x+room.width>map[0].length-1)result = false;
		return result;
	}
	
	public void addRoomToMap(Rectangle room){
		rooms.add(room);
		int x = (int) room.x;
		int y = (int) room.y;
		float width = room.width;
		float height = room.height;
		for(int i = 0; i<height; i++){
			for(int k = 0; k<width; k++){
				map[y][x]=1;
				x++;
			}
			y++;
			x-=width;
		}
	}
	
	public void generateClearDungeon(){
		for(int i = 0; i<map.length;i++){
			for(int k = 0; k<map[i].length;k++){
				map[i][k]=2;
			}
		}
	}
}
