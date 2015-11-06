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
	
	public boolean generateStartRoom(int x, int y){
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int) (Math.random()*height);
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width);
		Rectangle room = new Rectangle(x,y,width,height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			addRoomToMap(room);
			for(int i = 0; i<1;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateHallWay(nextX, nextY-1, dir);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean generateBelowRoom(int x, int y){
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
					generateHallWay(nextX, nextY-1, dir);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean generateAboveRoom(int x, int y){
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
					generateHallWay(nextX, nextY-1, dir);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean generateLeftRoom(int x, int y){
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
					generateHallWay(nextX, nextY-1, dir);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean generateRightRoom(int x, int y){
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
					generateHallWay(nextX, nextY-1, dir);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir);
				}
			}
			return true;
		}
		return false;
	}
	
	public void generateHallWay(int x, int y, int dir){
		boolean generateHall = true;
		boolean generateRoom = true;
		ArrayList<int[]> hallCoordinates = new ArrayList<int[]>();
		hallCoordinates.add(new int[]{x,y});
		int length=5+(int) (Math.random()*5);
		for(int i = 0; i < length; i++){
			if(dir == 0)y--;
			if(dir == 1)y++;
			if(dir == 2)x--;
			if(dir == 3)x++;
			if(isValidHallTile(x,y)&&!hallCoordinates.contains(new int[]{x,y})){
				hallCoordinates.add(new int[]{x,y});
				if(Math.random()>0.6){
					if(dir == 0||dir == 1){
						dir=2+(int) (Math.random()*2);
					}else{
						dir=(int) (Math.random()*2);
					}
				}
			} else{
				if(x>0&&y>0&&y<map.length-1&&x<map[0].length-1){
					i=length;
					generateRoom = false;
					generateHall = false;
				}else{
					i=length;
					generateHall = false;
					generateRoom = false;
				}
			}
		}
		if(generateRoom&&generateHall){
			if(dir == 0){
				generateHall=generateBelowRoom(x, y--);
			}else if(dir == 1){
				generateHall=generateAboveRoom(x, y++);
			}else if(dir == 2){
				generateHall=generateLeftRoom(x--, y);
			}else if(dir == 3){
				generateHall=generateRightRoom(x++, y);
			}
		}
		if(generateHall){
			System.out.println("Hall Generated");
			for(int i=0;i<hallCoordinates.size();i++){
				System.out.println("Hall Made: "+hallCoordinates.get(i)[0]+", "+hallCoordinates.get(i)[1]);
				map[hallCoordinates.get(i)[1]][hallCoordinates.get(i)[0]]=0;
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
	
	public boolean isValidHallTile(int x, int y){
		boolean result = true;
		for(Rectangle i: rooms){
			boolean xInter = false;
			boolean yInter = false;
			if(x>=i.getX()-1&&x<i.getX()+i.getWidth()+1)xInter = true;
			if(y>=i.getY()-1&&y<i.getY()+i.getHeight()+1)yInter = true;
			if(xInter&&yInter)result = false;
			if(x<1||y<1||y>map.length-1||x>map[0].length-1)result = false;
		}
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
