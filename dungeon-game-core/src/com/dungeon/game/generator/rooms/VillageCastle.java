package com.dungeon.game.generator.rooms;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.generator.rooms.Castle.GenRoom;
import com.dungeon.game.generator.rooms.room.BasicRoom;
import com.dungeon.game.generator.rooms.room.EnemyRoom;
import com.dungeon.game.generator.rooms.room.GeneralStore;
import com.dungeon.game.generator.rooms.room.Library;
import com.dungeon.game.generator.rooms.room.Quarters;
import com.dungeon.game.generator.rooms.room.Room;
import com.dungeon.game.generator.rooms.room.StairRoom;
import com.dungeon.game.generator.rooms.room.TrainingCenter;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class VillageCastle extends Castle {
	
	private ArrayList<GenRoom> specialRooms;
	private ArrayList<GenRoom> normRooms;

	public VillageCastle(World world, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY,
			int textureSeed, Object[] args) {
		super(world, width, height, centerX, centerY, upTrapX, upTrapY, textureSeed, args);
		// TODO Auto-generated constructor stub
	}
	
	protected void generate(Object[] args){
		generateClearDungeon();
		rooms = new ArrayList<GenRoom>();
		specialRooms = new ArrayList<GenRoom>();
		doors = new ArrayList<int[]>();
		
		generateClearDungeon();
		createRooms();
		createDoors();
		if(generateSpecialRooms()){
			populateSpecialRooms();
			populateNormRooms();
			makeWalls(10, 11, 12, 13, 14);
		}else generate(args);
	}
	
	private boolean generateSpecialRooms(){
		int numSpecial = 10;
		int numGen = 0;
		ArrayList<GenRoom> specialCands = (ArrayList<GenRoom>) rooms.clone();
		int attempts = 0;
		do{
			attempts++;
			int index = 1+(int)((specialCands.size()-1)*Math.random());
	        GenRoom room = specialCands.remove(index);
	        boolean isValidSpecialRoom = true;
	        validChecker:
	        {
	            for(int i = 0; i < room.connectedRooms.size(); i++){
	                ArrayList<GenRoom> queue = new ArrayList<GenRoom>();
	                queue.add(room.connectedRooms.get(i));
	                boolean gotToRoom0 = false;
	                for(int k = 0; k < queue.size(); k++){
	                    if(queue.get(k) == rooms.get(0)){
	                        gotToRoom0 = true;
	                        break;
	                    }
	                    //no  "else" is needed because the loop gets broke.
	                    ArrayList<GenRoom> adjacentRooms = queue.get(k).connectedRooms;
	                    for(int e = 0; e < adjacentRooms.size(); e++){
	                        if(queue.indexOf(adjacentRooms.get(e)) == -1 && adjacentRooms.get(e) != room)queue.add(adjacentRooms.get(e));
	                    }
	                }
	                if(!gotToRoom0){
	                    isValidSpecialRoom = false;
	                    break validChecker;
	                }
	            }
	        }
	        
	        if(isValidSpecialRoom){
	            specialRooms.add(room);
	            numGen++;
	            int remainingAdjacentRoom = (int)(room.connectedRooms.size()*Math.random());
	            for(int i = 0; i < room.connectedRooms.size(); i++){
	                if(i != remainingAdjacentRoom){
	                    room.connectedRooms.get(i).connectedRooms.remove(room);
	                }
	            }
	            GenRoom connection = room.connectedRooms.get(remainingAdjacentRoom);
	            room.connectedRooms.clear();
	            room.connectedRooms.add(connection);
	            for(int i = 0; i < doors.size(); i++){
	                int doorX = doors.get(i)[0];
	                int doorY = doors.get(i)[1];
	                ArrayList<GenRoom> adjacentRooms = new ArrayList<GenRoom>();
	                adjacentRooms.add(getAdjacentRooms(doorX, doorY)[0]);
	                adjacentRooms.add(getAdjacentRooms(doorX, doorY)[1]);
	                
	                if(adjacentRooms.contains(room) && !adjacentRooms.contains(connection)){
	                    map[doorY][doorX] = tileMap.getTile(15);
	                    doors.remove(i);
	                    i--;
	                }
	            }
	        }
			if(attempts > 50)return false;
		}while(numGen < numSpecial);
		return true;
	}
	
	private void populateSpecialRooms() {
		//find all rooms with just one door and make them special!
		for(GenRoom r: rooms){
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
		normRooms = (ArrayList<GenRoom>) rooms.clone();
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

}
