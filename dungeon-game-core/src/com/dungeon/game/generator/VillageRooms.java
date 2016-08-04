package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.friend.Trainer;
import com.dungeon.game.entity.furniture.Stair;
import com.dungeon.game.generator.room.GeneralStore;
import com.dungeon.game.generator.room.Quarters;
import com.dungeon.game.generator.room.Room;
import com.dungeon.game.generator.room.StairRoom;
import com.dungeon.game.generator.room.TrainingCenter;
import com.dungeon.game.pathing.Area;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class VillageRooms extends Generation {
	private ArrayList<Rectangle> rooms;
	private ArrayList<Rectangle> specialRooms;
	private ArrayList<Rectangle> normRooms;
	private ArrayList<ArrayList<int[]>> halls;
	private ArrayList<ArrayList<Rectangle>> hallEnds;
	
	//make special room choosing code better?
	
	public VillageRooms(World world, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY, int textureSeed){
		super(world, width, height, textureSeed);
		int x = height/2;
		int y = width/2;
		if(world.curDungeon!=null)entities.add(new Stair(world, centerX*Tile.TS-Tile.TS/2, centerY*Tile.TS-Tile.TS/2, false, upTrapX+1, upTrapY+1));

		do{
			generateClearDungeon();
			rooms = new ArrayList<Rectangle>();
			specialRooms = new ArrayList<Rectangle>();
			normRooms = new ArrayList<Rectangle>();
			halls = new ArrayList<ArrayList<int[]>>();
			hallEnds = new ArrayList<ArrayList<Rectangle>>();
			generateStartRoom(centerX, centerY);
		}while(specialRooms.size() < 3);
		populateSpecialRooms();
		
		makeWalls(10, 11, 12, 13, 14);
	}

	public boolean generateStartRoom(int x, int y){
		int originX = x;
		int originY = y;
		int height = (int) (5+Math.random()*map.length/20);
		y-=(int) (Math.random()*height)+1;
		int width = (int) (5+Math.random()*map[0].length/20);
		x-=(int) (Math.random()*width)+1;
		Rectangle room = new Rectangle(x,y,width,height);
		int nextX;
		int nextY;
		if(isValidRoom(room)){
			addRoomToMap(room, false,false);
			for(int i = 0; i<100;i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateHallWay(nextX, nextY-1, dir, room);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir, room);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir, room);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir, room);
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
			addRoomToMap(room, true,special);
			for(int i = 0; i<(special?0:100);i++){
				int dir = (int) (Math.random()*4);
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateHallWay(nextX, nextY-1, dir, room);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir, room);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir, room);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir, room);
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
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateHallWay(nextX, nextY-1, dir, room);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir, room);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir, room);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir, room);
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
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateHallWay(nextX, nextY-1, dir, room);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir, room);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir, room);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir, room);
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
				if(dir == 0){
					nextX = (int) (x+width*Math.random());
					nextY = y;
					generateHallWay(nextX, nextY-1, dir, room);
				}else if(dir == 1){
					nextX = (int) (x+width*Math.random());
					nextY = y+height;
					generateHallWay(nextX, nextY, dir, room);
				}else if(dir == 2){
					nextX = x;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX-1, nextY, dir, room);
				}else if(dir == 3){
					nextX = x+width;
					nextY = (int) (y+height*Math.random());
					generateHallWay(nextX, nextY, dir, room);
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

	public boolean isValidRoom(Rectangle room){
		boolean result = true;
		for(Rectangle i: rooms){
			if(Intersector.intersectRectangles(new Rectangle(room.x-1,room.y-1,room.width+1,room.height+1), new Rectangle(i.x-1,i.y-1,i.width+1,i.height+1), new Rectangle()))result = false;
		}
		for(ArrayList<int[]> i: halls){
			for(int[] k: i){
				if(!(i==halls.get(halls.size()-1)&&k==i.get(i.size()-1))){
				boolean xInter = false;
				boolean yInter = false;
				if(k[0]>=room.getX()-1&&k[0]<room.getX()+room.getWidth()+1)xInter = true;
				if(k[1]>=room.getY()-1&&k[1]<room.getY()+room.getHeight()+1)yInter = true;
				if(xInter&&yInter)result = false;
				}
			}
		}
		if(room.x<1)result = false;
		if(room.y<1)result = false;
		if(room.y+room.height>map.length-2)result = false;
		if(room.x+room.width>map[0].length-2)result = false;
		return result;
	}
	
	public boolean isValidHallTile(int x, int y, ArrayList<int[]> otherTiles){
		boolean result = true;
		for(Rectangle i: rooms){
			boolean xInter = false;
			boolean yInter = false;
			if(x>=i.getX()-1&&x<i.getX()+i.getWidth()+1)xInter = true;
			if(y>=i.getY()-1&&y<i.getY()+i.getHeight()+1)yInter = true;
			if(xInter&&yInter)result = false;
		}
		if(x<1||y<1||y>map.length-2||x>map[0].length-2)result = false;
		for(ArrayList<int[]> i: halls){
			for(int[] k: i){
				if(x == k[0]&&y==k[1])result = false;
				if(x+1 == k[0]&&y==k[1])result = false;
				if(x-1 == k[0]&&y==k[1])result = false;
				if(x == k[0]&&y+1==k[1])result = false;
				if(x == k[0]&&y-1==k[1])result = false;
			}
		}
		for(int[] i: otherTiles){
			if(i[0]==x&&i[1]==y)result = false;
		}
		return result;
	}
	
	public Rectangle isTileInRoom(int x, int y, Rectangle room){
		for(Rectangle i: rooms){
			boolean xInter = false;
			boolean yInter = false;
			if(i!=room){
				if(x>=i.getX()&&x<i.getX()+i.getWidth())xInter = true;
				if(y>=i.getY()&&y<i.getY()+i.getHeight())yInter = true;
				if(xInter&&yInter)return i;
			}
		}
		return null;
	}

	private boolean isTileInHall(int x, int y, Rectangle room, ArrayList<int[]> hallTiles) {
		//loops for all halls
		for(int i = 0; i< halls.size(); i++){
			//loops for all tiles
			for(int k = 0; k<halls.get(i).size();k++){
				//checks if tile matches
				if(x == halls.get(i).get(k)[0]&& y == halls.get(i).get(k)[1]){
					//loops for all roomEnds of the hall
					for(int e = 0; e <hallEnds.get(i).size(); e++){
						if(room==hallEnds.get(i).get(e)||isHallTaken(room,hallEnds.get(i).get(e))){
							return false;
						}
					}
					hallEnds.get(i).add(room);
					for(int[] o: hallTiles){
						halls.get(i).add(o);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isHallTaken(Rectangle roomOne, Rectangle roomTwo) {
		if(specialRooms.contains(roomTwo))return true;
		for(ArrayList<Rectangle> i: hallEnds){
			boolean roomOneFound = false;
			boolean roomTwoFound = false;
			for(Rectangle k: i){
				if(k == roomOne)roomOneFound = true;
				if(k == roomTwo)roomTwoFound = true;
				if(roomOneFound&&roomTwoFound)return true;
			}
		}
		return false;
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
	
	public void generateAreas(){
		for(Rectangle room: rooms){
			Area area = new Area();
			area.addRectangleToArea((int)room.x, (int)room.y, (int)room.width, (int)room.height);
			areas.add(area);
		}
		for(ArrayList<int[]> hall: halls){
			Area area = new Area();
			for(int[] point: hall){
				area.addPointToArea(point);
			}
			areas.add(area);
		}
	}
	
	public void generateStairDown(){
		Rectangle room = rooms.get(1+(int) (Math.random()*(rooms.size()-1)));
		int width = 1+(int) (Math.random()*(room.width-1));
		int height = 1+(int) (Math.random()*(room.width-1));
		entities.add(new Stair(world,(room.x+width)*Tile.TS+Tile.TS/2 , (room.y+height)*Tile.TS+Tile.TS/2, true, 15+(int) (Math.random()*10), 15+((int) Math.random()*10)));
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
		int[] stairDoorFinder = findDoor(stairRect);
		Room stairRoom = new StairRoom(world, stairRect, stairDoorFinder, tileMap);
		stairRoom.addToMap(map, entities);
		
		Rectangle storeRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
		int[] storeDoorFinder = findDoor(storeRect);
		Room storeRoom = new GeneralStore(world, storeRect, storeDoorFinder, tileMap);
		storeRoom.addToMap(map, entities);
		
		Rectangle trainingRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
		int[] trainingDoorFinder = findDoor(trainingRect);
		Room trainingRoom = new TrainingCenter(world, trainingRect, trainingDoorFinder, tileMap);
		trainingRoom.addToMap(map, entities);
		
		while(specialRooms.size()>0){
			
			Rectangle quartersRect = specialRooms.remove((int) (specialRooms.size()*Math.random()));
			int[] quartersDoorFinder = findDoor(quartersRect);
			Room quartersRoom = new Quarters(world, quartersRect, quartersDoorFinder, tileMap);
			quartersRoom.addToMap(map, entities);
		}
	}
	
	//will be used to populate non-special rooms
	private void populateNormRooms(){
		
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
	
	private int[] findDoor(Rectangle room){
		int x,y;
		x = (int) room.x-1;
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(map[y][x].id==0){
				map[y][x]=tileMap.getTile(5);
				return new int[]{0,x,y};
			}

			y++;
		}
		x = (int) (room.x+room.width);
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(map[y][x].id==0){
				map[y][x]=tileMap.getTile(5);
				return new int[]{1,x,y};
			}

			y++;
		}
		x = (int) room.x;
		y = (int) room.y-1;
		for(int i = 0; i < room.width; i++){
			if(map[y][x].id==0){
				map[y][x]=tileMap.getTile(5);
				return new int[]{2,x,y};
			}

			x++;
		}
		x = (int) room.x;
		y = (int) (room.y+room.height);
		for(int i = 0; i < room.width; i++){
			if(map[y][x].id==0){
				map[y][x]=tileMap.getTile(5);
				return new int[]{3,x,y};
			}

			x++;
		}
		
		return null;
	}
}
