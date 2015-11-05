package com.dungeon.game.world;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;

public class Floor {
	private static final String DEFAULT = "Tilemap.png";
	
	private TextureRegion[] spritesheet;
	
	public Tile[][] tm;
	
	public ArrayList<Entity> entities;
	
	private int width;
	private int height;
	
	public Floor(int width, int height) {
		this.width = width;
		this.height = height;
		
		entities = new ArrayList<Entity>();
		
		tm = new Tile[height][width];
		
		Texture tempsheet = new Texture(DEFAULT);
		
		int sheetWidth = tempsheet.getWidth()/Tile.TS;
		int sheetHeight = tempsheet.getHeight()/Tile.TS;
		
		spritesheet = new TextureRegion[sheetWidth * sheetHeight];
		
		for(int i = 0; i < sheetHeight; i++) {
			for(int k = 0; k < sheetWidth; k++) {
				spritesheet[i*sheetWidth+k] = new TextureRegion(new Texture(DEFAULT),k*Tile.TS,i*Tile.TS,Tile.TS,Tile.TS);
			}
		}
		int[][] map = generateRoomDungeon(width, height);
		//temp: remove once random generator has been created
		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++){
				tm[i][k] = new Tile(spritesheet,map[i][k]);
			}
		}
	}

	private static ArrayList<Rectangle> rooms;
	
	public static int[][] generateRoomDungeon(int width, int height){
		int[][] map = new int[height][width];
		rooms = new ArrayList<Rectangle>();
		map = generateClearDungeon(map);
		int x = height/2;
		int y = width/2;
		int[] dataForNext = new int[3];
		map = generateStartRoom(map, rooms, x, y);
		return map;
	}
	
	public static int[][] generateStartRoom(int[][] map, ArrayList<Rectangle> riooms, int x, int y){
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int) (Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x,y,width,height);
		int nextX;
		int nextY;
		if(isValidRoom(map, room, rooms)){
			map = addRoomToMap(map, room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(map, rooms, nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(map, rooms, nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(map, rooms, nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(map, rooms, nextX, nextY);
				}
			}
		}
		return map;
	}
	
	public static int[][] generateBelowRoom(int[][] map, ArrayList<Rectangle> riooms, int x, int y){
		int doorX = x;
		int doorY = y-1;
		int height = (int) (3+Math.random()*map.length/7);
		y-=1+height;
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(map, room, rooms)){
			map[doorY][doorX]=0;
			map = addRoomToMap(map, room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(map, rooms, nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(map, rooms, nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(map, rooms, nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(map, rooms, nextX, nextY);
				}
			}
		}
		return map;
	}
	
	public static int[][] generateAboveRoom(int[][] map, ArrayList<Rectangle> riooms, int x, int y){
		int doorX = x;
		int doorY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y++;
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(map, room, rooms)){
			map[doorY][doorX]=0;
			map = addRoomToMap(map, room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(map, rooms, nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(map, rooms, nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(map, rooms, nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(map, rooms, nextX, nextY);
				}
			}
		}
		return map;
	}
	
	public static int[][] generateLeftRoom(int[][] map, ArrayList<Rectangle> riooms, int x, int y){
		int doorX = x-1;
		int doorY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int)(Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=1+width;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(map, room, rooms)){
			map[doorY][doorX]=0;
			map = addRoomToMap(map, room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(map, rooms, nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(map, rooms, nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(map, rooms, nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(map, rooms, nextX, nextY);
				}
			}
		}
		return map;
	}
	
	public static int[][] generateRightRoom(int[][] map, ArrayList<Rectangle> riooms, int x, int y){
		int doorX = x;
		int doorY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int)(Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x++;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		if(isValidRoom(map, room, rooms)){
			map[doorY][doorX]=0;
			map = addRoomToMap(map, room);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateBelowRoom(map, rooms, nextX, nextY);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateAboveRoom(map, rooms, nextX, nextY);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateLeftRoom(map, rooms, nextX, nextY);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateRightRoom(map, rooms, nextX, nextY);
				}
			}
		}
		return map;
	}
	
	public static boolean isValidRoom(int[][] map, Rectangle room, ArrayList<Rectangle> riooms){
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
	
	public static int[][] addRoomToMap(int[][] map, Rectangle room){
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
		return map;
	}
	
	public static int[][] generateClearDungeon(int[][] map){
		for(int i = 0; i<map.length;i++){
			for(int k = 0; k<map[i].length;k++){
				map[i][k]=2;
			}
		}
		return map;
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		int startHeight = (int) (y-Gdx.graphics.getHeight()/2)/Tile.TS;
		int endHeight = (int)(y+Gdx.graphics.getHeight()/2)/Tile.TS+1;
		int startWidth = (int) (x-Gdx.graphics.getWidth()/2)/Tile.TS-1;
		int endWidth = (int)(x+Gdx.graphics.getWidth()/2)/Tile.TS+1;
		
		startHeight = Math.max(startHeight,0);
		endHeight = Math.min(endHeight,tm.length);
		startWidth = Math.max(startWidth,0);
		endWidth = Math.min(endWidth,tm[0].length);
		
		for(int i = startHeight; i < endHeight; i++){
			for(int k = startWidth; k < endWidth; k++){
				batch.draw(tm[i][k].texture, k*Tile.TS, i*Tile.TS);
			}
		}
	}

}
