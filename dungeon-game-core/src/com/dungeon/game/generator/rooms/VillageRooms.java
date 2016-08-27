package com.dungeon.game.generator.rooms;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.furniture.Stair;
import com.dungeon.game.generator.Generation;
import com.dungeon.game.generator.rooms.hallway.BasicHall;
import com.dungeon.game.generator.rooms.hallway.Hallway;
import com.dungeon.game.generator.rooms.room.BasicRoom;
import com.dungeon.game.generator.rooms.room.GeneralStore;
import com.dungeon.game.generator.rooms.room.Library;
import com.dungeon.game.generator.rooms.room.Quarters;
import com.dungeon.game.generator.rooms.room.Room;
import com.dungeon.game.generator.rooms.room.StairRoom;
import com.dungeon.game.generator.rooms.room.TrainingCenter;
import com.dungeon.game.pathing.Area;
import com.dungeon.game.utilities.MethodArray;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class VillageRooms extends Rooms {
	private ArrayList<Rectangle> specialRooms;
	private ArrayList<Rectangle> normRooms;
	
	//make special room choosing code better?
	
	public VillageRooms(World world, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY, int textureSeed, Object[] args){
		super(world, width, height, centerX, centerY, upTrapX, upTrapY, textureSeed, args);
	}
	
	protected void generate(Object[] args){
		generateClearDungeon();
		if(world.curDungeon!=null)entities.add(new Stair(world, (Integer)(args[1])*Tile.TS-Tile.TS/2, (Integer)(args[2])*Tile.TS-Tile.TS/2, false, (Integer)(args[3])+1, (Integer)(args[4])+1));
		roomGenerators = new MethodArray(4){
			public void a(int x, int y, int width, int height, int dir, Rectangle room){
				int nextX = (int) (x+width*Math.random());
				int nextY = y;
				generateHallWay(nextX, nextY-1, dir, room);
			}
			public void b(int x, int y, int width, int height, int dir, Rectangle room){
				int nextX = (int) (x+width*Math.random());
				int nextY = y+height;
				generateHallWay(nextX, nextY, dir, room);
			}
			public void c(int x, int y, int width, int height, int dir, Rectangle room){
				int nextX = x;
				int nextY = (int) (y+height*Math.random());
				generateHallWay(nextX-1, nextY, dir, room);
			}
			public void d(int x, int y, int width, int height, int dir, Rectangle room){
				int nextX = x+width;
				int nextY = (int) (y+height*Math.random());
				generateHallWay(nextX, nextY, dir, room);
			}
		};
		do{
			generateClearDungeon();
			rooms = new ArrayList<Rectangle>();
			specialRooms = new ArrayList<Rectangle>();
			normRooms = new ArrayList<Rectangle>();
			halls = new ArrayList<ArrayList<int[]>>();
			hallEnds = new ArrayList<ArrayList<Rectangle>>();
			generateStartRoom((Integer)(args[1]), (Integer)(args[2]));
		}while(specialRooms.size() < 3);
		if(args.length > 0 && !args[0].equals("test")){
			populateSpecialRooms();
			populateNormRooms();
			populateHallWays();
			makeWalls(10, 11, 12, 13, 14);
		}
		
	}
	

	public boolean generateStartRoom(int x, int y){
		int originX = x;
		int originY = y;
		int height = (int) (3+Math.random()*map.length/7);
		y-=(int) (Math.random()*height)+1;
		int width = (int) (3+Math.random()*map[0].length/7);
		x-=(int) (Math.random()*width)+1;
		Rectangle room = new Rectangle(x,y,width,height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			addRoomToMap(room, false, false);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				try {
					roomGenerators.methods[dir].invoke(roomGenerators, x, y, width, height, dir, room);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}else //generateStartRoom(originX, originY);
		return false;
	}
	
	public boolean generateBelowRoom(int x, int y, ArrayList<int[]> hall, Rectangle lastRoom){
		int doorX = x;
		int doorY = y-1;
		int height = (int) (5+Math.random()*map.length/20);
		y-=height;
		int width = (int) (5+Math.random()*map[0].length/20);
		x-=(int) (Math.random()*width);
		boolean special = Math.random()>1-(Math.sqrt((this.width/2-x)*(this.width/2-x)+(this.height/2-y)*(this.height/2-y))/Math.sqrt((this.width/2)*(this.width/2)+(this.height/2)*(this.height/2)))/1.5f;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		halls.add(hall);
		if(isValidRoom(room)){
			ArrayList<Rectangle> hallEnd = new ArrayList<Rectangle>();
			hallEnd.add(room);
			hallEnd.add(lastRoom);
			hallEnds.add(hallEnd);
			for(int i=0;i<hall.size();i++){
				map[hall.get(i)[1]][hall.get(i)[0]]=tileMap.getTile(0);
			}
			addRoomToMap(room, true, special);
			for(int i = 0; i<(special?0:100);i++){
				int dir = (int) (Math.random()*4);
				try {
					roomGenerators.methods[dir].invoke(roomGenerators, x, y, width, height, dir, room);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		halls.remove(halls.size()-1);
		return false;
	}
	
	public boolean generateAboveRoom(int x, int y, ArrayList<int[]> hall, Rectangle lastRoom){
		int doorX = x;
		int doorY = y;
		int height = (int) (5+Math.random()*map.length/20);
		int width = (int) (5+Math.random()*map[0].length/20);
		x-=(int) (Math.random()*width);
		boolean special = Math.random()>1-(Math.sqrt((this.width/2-x)*(this.width/2-x)+(this.height/2-y)*(this.height/2-y))/Math.sqrt((this.width/2)*(this.width/2)+(this.height/2)*(this.height/2)))/2;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		halls.add(hall);
		if(isValidRoom(room)){
			ArrayList<Rectangle> hallEnd = new ArrayList<Rectangle>();
			hallEnd.add(room);
			hallEnd.add(lastRoom);
			hallEnds.add(hallEnd);
			for(int i=0;i<hall.size();i++){
				map[hall.get(i)[1]][hall.get(i)[0]]=tileMap.getTile(0);
			}
			addRoomToMap(room, true,special);
			for(int i = 0; i<(special?0:100);i++){
				int dir = (int) (Math.random()*4);
				try {
					roomGenerators.methods[dir].invoke(roomGenerators, x, y, width, height, dir, room);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		halls.remove(halls.size()-1);
		return false;
	}
	
	public boolean generateLeftRoom(int x, int y, ArrayList<int[]> hall, Rectangle lastRoom){
		int doorX = x-1;
		int doorY = y;
		int height = (int) (5+Math.random()*map.length/20);
		y-=(int)(Math.random()*height);
		int width = (int) (5+Math.random()*map[0].length/20);
		x-=width;
		boolean special = Math.random()>1-(Math.sqrt((this.width/2-x)*(this.width/2-x)+(this.height/2-y)*(this.height/2-y))/Math.sqrt((this.width/2)*(this.width/2)+(this.height/2)*(this.height/2)))/2;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		halls.add(hall);
		if(isValidRoom(room)){
			ArrayList<Rectangle> hallEnd = new ArrayList<Rectangle>();
			hallEnd.add(room);
			hallEnd.add(lastRoom);
			hallEnds.add(hallEnd);
			for(int i=0;i<hall.size();i++){
				map[hall.get(i)[1]][hall.get(i)[0]]=tileMap.getTile(0);
			}
			addRoomToMap(room, true,special);
			for(int i = 0; i<(special?0:100);i++){
				int dir = (int) (Math.random()*4);
				try {
					roomGenerators.methods[dir].invoke(roomGenerators, x, y, width, height, dir, room);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		halls.remove(halls.size()-1);
		return false;
	}
	
	public boolean generateRightRoom(int x, int y, ArrayList<int[]> hall, Rectangle lastRoom){
		int doorX = x;
		int doorY = y;
		int height = (int) (5+Math.random()*map.length/20);
		y-=(int)(Math.random()*height);
		int width = (int) (5+Math.random()*map[0].length/20);
		boolean special = Math.random()>1-(Math.sqrt((this.width/2-x)*(this.width/2-x)+(this.height/2-y)*(this.height/2-y))/Math.sqrt((this.width/2)*(this.width/2)+(this.height/2)*(this.height/2)))/2;
		Rectangle room = new Rectangle(x, y, width, height);
		int nextX;
		int nextY;
		halls.add(hall);
		if(isValidRoom(room)){
			ArrayList<Rectangle> hallEnd = new ArrayList<Rectangle>();
			hallEnd.add(room);
			hallEnd.add(lastRoom);
			hallEnds.add(hallEnd);
			for(int i=0;i<hall.size();i++){
				map[hall.get(i)[1]][hall.get(i)[0]]=tileMap.getTile(0);
			}
			addRoomToMap(room, true,special);
			for(int i = 0; i<(special?0:100);i++){
				int dir = (int) (Math.random()*4);
				try {
					roomGenerators.methods[dir].invoke(roomGenerators, x, y, width, height, dir, room);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		halls.remove(halls.size()-1);
		return false;
	}
	
	public void generateHallWay(int x, int y, int dir, Rectangle room){
		Rectangle hallDest = null;
		boolean justTurned = true;
		boolean generateHall = true;
		boolean generateRoom = true;
		boolean addHallToArrayList = true;
		ArrayList<int[]> hallCoordinates = new ArrayList<int[]>();
		hallCoordinates.add(new int[]{x,y, dir});
		int length=5+(int) (Math.random()*15);
		for(int i = 0; i < length; i++){
			if(dir == 0)y--;
			if(dir == 1)y++;
			if(dir == 2)x--;
			if(dir == 3)x++;
			if(isValidHallTile(x,y, hallCoordinates)){
				hallCoordinates.add(new int[]{x,y,dir});
				if(Math.random()>0.2&&justTurned==false){
					justTurned=true;
					if(dir == 0||dir == 1){
						dir=2+(int) (Math.random()*2);
					}else{
						dir=(int) (Math.random()*2);
					}
				}else{
					justTurned = false;
				}
			} else if(hallCoordinates.size()>=4){
				hallCoordinates.add(new int[]{x,y,dir});
				if(dir == 0)y--;
				if(dir == 1)y++;
				if(dir == 2)x--;
				if(dir == 3)x++;
				if(isTileInRoom(x,y, room)!=null&&Math.random()>0.9){
					i=length;
					generateRoom = false;
					generateHall = true;
					hallDest = isTileInRoom(x,y,room);
					if(isHallTaken(room, hallDest))generateHall = false;
				}else if(isTileInHall(x,y, room, hallCoordinates)&&isTileInRoom(x,y, room)==null){
					addHallToArrayList = false;
					i=length;
					generateRoom = false;
					generateHall = true;
					
				}else{
					i=length;
					generateHall = false;
					generateRoom = false;
				}
			}else{
				i=length;
				generateHall = false;
				generateRoom = false;
			}
		}
		if(generateRoom&&generateHall){
			generateHall=false;
			if(hallCoordinates.get(hallCoordinates.size()-1)[2] == 0){
				generateBelowRoom(x, y, hallCoordinates, room);
			}else if(hallCoordinates.get(hallCoordinates.size()-1)[2] == 1){
				generateAboveRoom(x, y+1, hallCoordinates, room);
			}else if(hallCoordinates.get(hallCoordinates.size()-1)[2] == 2){
				generateLeftRoom(x, y, hallCoordinates, room);
			}else if(hallCoordinates.get(hallCoordinates.size()-1)[2] == 3){
				generateRightRoom(x+1, y, hallCoordinates, room);
			}
		}
		if(generateHall){
			if(addHallToArrayList){
				ArrayList<Rectangle> hallEnd = new ArrayList<Rectangle>();
				hallEnd.add(room);
				hallEnd.add(hallDest);
				hallEnds.add(hallEnd);
				halls.add(hallCoordinates);
			}
			for(int i=0;i<hallCoordinates.size();i++){
				map[hallCoordinates.get(i)[1]][hallCoordinates.get(i)[0]]=tileMap.getTile(0);
			}
			
		}
	}
	
	public boolean isHallTaken(Rectangle roomOne, Rectangle roomTwo) {
		if(specialRooms.contains(roomTwo))return true;
		return super.isHallTaken(roomOne, roomTwo);
	}
	
	public void addRoomToMap(Rectangle room, boolean hasEnemies, boolean special){
		boolean addedChest = false;
		rooms.add(room);
		if(special){
			specialRooms.add(room);
		}
		int x = (int) room.x;
		int y = (int) room.y;
		int width = (int) room.width;
		int height = (int) room.height;
		for(int i = 0; i<height; i++){
			for(int k = 0; k<width; k++){
				if(special)map[y][x] = tileMap.getTile(4);
				else map[y][x]=tileMap.getTile(0);
				x++;
			}
			y++;
			x-=width;
		}
	}
	

	//populate special rooms
	private void populateSpecialRooms() {
		//find all rooms with just one door and make them special!
		for(Rectangle r: rooms){
			int numDoors = 0;
			checker:
			if(!specialRooms.contains(r)){
				for(int i = 0; i < r.width; i++){
					if(!Tile.isSolid(map[(int) r.y-1][(int) r.x + i].id)){
						numDoors++;
						if(numDoors == 2)break checker;
					}
				}
				for(int i = 0; i < r.width; i++){
					if(!Tile.isSolid(map[(int) (r.y + r.height)][(int) r.x + i].id)){
						numDoors++;
						if(numDoors == 2)break checker;
					}
				}
				for(int i = 0; i < r.height; i++){
					if(!Tile.isSolid(map[(int) r.y + i][(int) r.x - 1].id)){
						numDoors++;
						if(numDoors == 2)break checker;
					}
				}
				for(int i = 0; i < r.height; i++){
					if(!Tile.isSolid(map[(int) r.y + i][(int) (r.x + r.width)].id)){
						numDoors++;
						if(numDoors == 2)break checker;
					}
				}
				specialRooms.add(r);
			}
		}
		normRooms = (ArrayList<Rectangle>) rooms.clone();
		for(int i = 0; i < specialRooms.size(); i++){
			normRooms.remove(specialRooms.get(i));
		}
		
		Rectangle stairRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
		int[][] stairDoorFinder = findDoors(stairRect);
		Room stairRoom = new StairRoom(world, stairRect, stairDoorFinder, tileMap);
		stairRoom.addToMap(map, entities);
		
		Rectangle storeRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
		int[][] storeDoorFinder = findDoors(storeRect);
		Room storeRoom = new GeneralStore(world, storeRect, storeDoorFinder, tileMap);
		storeRoom.addToMap(map, entities);
		
		Rectangle trainingRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
		int[][] trainingDoorFinder = findDoors(trainingRect);
		Room trainingRoom = new TrainingCenter(world, trainingRect, trainingDoorFinder, tileMap);
		trainingRoom.addToMap(map, entities);
		
		while(specialRooms.size()>0){
			
			Rectangle quartersRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
			int[][] quartersDoorFinder = findDoors(quartersRect);
			Room quartersRoom = new Quarters(world, quartersRect, quartersDoorFinder, tileMap);
			quartersRoom.addToMap(map, entities);
		}
	}
	
	//will be used to populate non-special rooms
	private void populateNormRooms(){
		Rectangle normRoomsRect;
		int[][] normRoomsDoorFinder;
		Room normRoom;
		while(normRooms.size()>0){
			normRoomsRect = normRooms.remove((int) (specialRooms.size()*Math.random()));
			normRoomsDoorFinder = findDoors(normRoomsRect);
			if(Math.random() > 0.5f)normRoom = new BasicRoom(world, normRoomsRect, normRoomsDoorFinder, tileMap);
			else normRoom = new Library(world, normRoomsRect, normRoomsDoorFinder, tileMap);
			normRoom.addToMap(map, entities);
		}
		
	}
	
	private void populateHallWays(){
		ArrayList<ArrayList<int[]>> halls = (ArrayList<ArrayList<int[]>>) this.halls.clone();
		Hallway hall;
		while(halls.size()>0){
			hall = new BasicHall(world, halls.remove((int) (halls.size()*Math.random())), tileMap);
			hall.addToMap(map, entities);
		}
	}
	
	//ENTITIES FOR STORE GENERATION
	//bookshelves (diff sides)
	//desk (2 parts)
	//shop keeper
	//table
	//chairs
	//plant pot
	//carpet (tile)
	//other decerative entities (sculptures, etc)
}
