package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.Stair;
import com.dungeon.game.entity.character.friend.Shopkeeper;
import com.dungeon.game.entity.character.friend.StairKeeper;
import com.dungeon.game.entity.character.friend.Villager;
import com.dungeon.game.entity.furniture.Bed;
import com.dungeon.game.entity.furniture.Bookshelf;
import com.dungeon.game.entity.furniture.Carpet;
import com.dungeon.game.entity.furniture.Dresser;
import com.dungeon.game.entity.furniture.Fireplace;
import com.dungeon.game.entity.furniture.Plant;
import com.dungeon.game.entity.furniture.ShopDesk1;
import com.dungeon.game.entity.furniture.ShopDesk2;
import com.dungeon.game.entity.furniture.SmallTable;
import com.dungeon.game.entity.furniture.Torch;
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
	
	public VillageRooms(World world, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY){
		super(world, width, height);
		rooms = new ArrayList<Rectangle>();
		specialRooms = new ArrayList<Rectangle>();
		normRooms = new ArrayList<Rectangle>();
		halls = new ArrayList<ArrayList<int[]>>();
		hallEnds = new ArrayList<ArrayList<Rectangle>>();
		int x = height/2;
		int y = width/2;
		System.out.println(centerX);
		if(world.curDungeon!=null)entities.add(new Stair(world, centerX*Tile.TS-Tile.TS/2, centerY*Tile.TS-Tile.TS/2, false, upTrapX+1, upTrapY+1));

		generateStartRoom(centerX, centerY);
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
				map[hall.get(i)[1]][hall.get(i)[0]]=0;
				if(i == 0){
//					if(hall.get(0)[2] == 0||hall.get(0)[2] == 1)addDoor(hall.get(0)[0],hall.get(0)[1],0);
//					if(hall.get(0)[2] == 2||hall.get(0)[2] == 3)addDoor(hall.get(0)[0],hall.get(0)[1],1);
				}
				if(i==hall.size()-1){
//					if(hall.get(hall.size()-1)[2] == 0||hall.get(hall.size()-1)[2] == 1)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],0);
//					if(hall.get(hall.size()-1)[2] == 2||hall.get(hall.size()-1)[2] == 3)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],1);
				}
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
				map[hall.get(i)[1]][hall.get(i)[0]]=0;
				if(i == 0){
//					if(hall.get(0)[2] == 0||hall.get(0)[2] == 1)addDoor(hall.get(0)[0],hall.get(0)[1],0);
//					if(hall.get(0)[2] == 2||hall.get(0)[2] == 3)addDoor(hall.get(0)[0],hall.get(0)[1],1);
				}
				if(i==hall.size()-1){
//					if(hall.get(hall.size()-1)[2] == 0||hall.get(hall.size()-1)[2] == 1)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],0);
//					if(hall.get(hall.size()-1)[2] == 2||hall.get(hall.size()-1)[2] == 3)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],1);
				}
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
				map[hall.get(i)[1]][hall.get(i)[0]]=0;
				if(i == 0){
//					if(hall.get(0)[2] == 0||hall.get(0)[2] == 1)addDoor(hall.get(0)[0],hall.get(0)[1],0);
//					if(hall.get(0)[2] == 2||hall.get(0)[2] == 3)addDoor(hall.get(0)[0],hall.get(0)[1],1);
				}
				if(i==hall.size()-1){
//					if(hall.get(hall.size()-1)[2] == 0||hall.get(hall.size()-1)[2] == 1)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],0);
//					if(hall.get(hall.size()-1)[2] == 2||hall.get(hall.size()-1)[2] == 3)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],1);
				}
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
				map[hall.get(i)[1]][hall.get(i)[0]]=0;
				if(i == 0){
//					if(hall.get(0)[2] == 0||hall.get(0)[2] == 1)addDoor(hall.get(0)[0],hall.get(0)[1],0);
//					if(hall.get(0)[2] == 2||hall.get(0)[2] == 3)addDoor(hall.get(0)[0],hall.get(0)[1],1);
				}
				if(i==hall.size()-1){
//					if(hall.get(hall.size()-1)[2] == 0||hall.get(hall.size()-1)[2] == 1)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],0);
//					if(hall.get(hall.size()-1)[2] == 2||hall.get(hall.size()-1)[2] == 3)addDoor(hall.get(hall.size()-1)[0],hall.get(hall.size()-1)[1],1);
				}
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
				map[hallCoordinates.get(i)[1]][hallCoordinates.get(i)[0]]=0;
				if(i==0){
//					if(hallCoordinates.get(0)[2] == 0||hallCoordinates.get(0)[2] == 1)addDoor(hallCoordinates.get(0)[0],hallCoordinates.get(0)[1],0);
//					if(hallCoordinates.get(0)[2] == 2||hallCoordinates.get(0)[2] == 3)addDoor(hallCoordinates.get(0)[0],hallCoordinates.get(0)[1],1);
				}
				if(addHallToArrayList&&i==hallCoordinates.size()-1){
//					if(hallCoordinates.get(hallCoordinates.size()-1)[2] == 0||hallCoordinates.get(hallCoordinates.size()-1)[2] == 1)addDoor(hallCoordinates.get(hallCoordinates.size()-1)[0],hallCoordinates.get(hallCoordinates.size()-1)[1],0);
//					if(hallCoordinates.get(hallCoordinates.size()-1)[2] == 2||hallCoordinates.get(hallCoordinates.size()-1)[2] == 3)addDoor(hallCoordinates.get(hallCoordinates.size()-1)[0],hallCoordinates.get(hallCoordinates.size()-1)[1],1);
				}
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
				if(special)map[y][x] = 4;
				else map[y][x]=0;
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
//		for(int i = 0; i < rooms.size(); i++){
//			if(Math.random()<(i+1)/rooms.size()){
//				Rectangle room = rooms.get(i);
//				for(int k = 1; k < room.width-1; k++){
//					for(int j = 1; j < room.height-1; j++){
//						if(Math.random()>((k+1)*(j+1))/((room.width-1)*(room.height-1))){
//							entities.add(new Stair(world,(room.x+k)*Tile.TS , (room.y+j)*Tile.TS, true, 15+(int) (Math.random()*10), 15+((int) Math.random()*10)));
//							return;
//						}
//					}
//				}
//			}
//		}
	}
	

	//populate special rooms
	private void populateSpecialRooms() {
		normRooms = (ArrayList<Rectangle>) rooms.clone();
		for(int i = 0; i < specialRooms.size(); i++){
			normRooms.remove(specialRooms.get(i));
		}
		generateStairRoom(specialRooms.get((int) (specialRooms.size()*Math.random())));
		generateStore(specialRooms.get((int) (specialRooms.size()*Math.random())));
		while(specialRooms.size()>0)generateQuarters(specialRooms.get((int) (specialRooms.size()*Math.random())));
		for(int i = 0; i < specialRooms.size(); i++){
			
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
		int doorside;
		int doorpos;
		int x,y;
		x = (int) room.x-1;
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(map[y][x]==0){
				map[y][x]=5;
				return new int[]{0,x,y};
			}

			y++;
		}
		x = (int) (room.x+room.width);
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(map[y][x]==0){
				map[y][x]=5;
				return new int[]{1,x,y};
			}

			y++;
		}
		x = (int) room.x;
		y = (int) room.y-1;
		for(int i = 0; i < room.width; i++){
			if(map[y][x]==0){
				map[y][x]=5;
				return new int[]{2,x,y};
			}

			x++;
		}
		x = (int) room.x;
		y = (int) (room.y+room.height);
		for(int i = 0; i < room.width; i++){
			if(map[y][x]==0){
				map[y][x]=5;
				return new int[]{3,x,y};
			}

			x++;
		}
		
		return null;
		
	}
	
	private void generateStore(Rectangle room){
		specialRooms.remove(room);
		
		//begin transformation
		

		int[] doorFinder = findDoor(room);
		int[] doorPos = new int[2];
		int[][] roomMap = rotate(room, doorFinder, doorPos);
		int doorX =  doorPos[0];
		int doorY = doorPos[1];
		
		
		//initialize Entity Arrays
		ArrayList<Entity> roomEntities = new ArrayList<Entity>();
		
		boolean keeperBottom = Math.random()>0.5;
		if(doorY == 0)keeperBottom = true;
		else if(doorY == roomMap.length-1)keeperBottom = false;
		
		Shopkeeper tempKeeper = new Shopkeeper(world, (roomMap[0].length-1)*Tile.TS+Tile.TS/2, (keeperBottom? 3f/4f:roomMap.length-1+1f/4f)*Tile.TS);
		tempKeeper.flipX = true;
		
		roomEntities.add(tempKeeper);
		
		ShopDesk1 desk1 = new ShopDesk1(world, (roomMap[0].length-1)*Tile.TS-Tile.TS*1f/4f, (keeperBottom? 3f/4f:roomMap.length-1+1f/4f)*Tile.TS);
		ShopDesk2 desk2 = new ShopDesk2(world, (roomMap[0].length-1)*Tile.TS-Tile.TS*-1f/4f, (keeperBottom? 7f/4f:roomMap.length-1-3f/4f)*Tile.TS);
		
		if(keeperBottom){
			desk1.angle = 180;
			desk2.angle = 180;
		}else{
			desk1.flipX = true;
			desk2.flipX = true;
		}
		
		roomEntities.add(desk1);
		roomEntities.add(desk2);
		
		int[][][] bookMap = new int[1][roomMap[0].length*2][2];
		for(int i = 0; i < roomMap[0].length*2; i++){
			if(i == roomMap[0].length*2-1)bookMap[0][i] = new int[]{3,keeperBottom?2:0};
			else bookMap[0][i] = new int[]{-1,keeperBottom?2:0};
		}
		roomEntities.add(new Bookshelf(world, bookMap[0].length*8,(keeperBottom?roomMap.length-1+3f/4f:1f/4f)*Tile.TS, bookMap));
		

		bookMap = new int[roomMap.length*2-6][1][2];
		for(int i = 0; i < roomMap.length*2-6; i++){
			bookMap[i][0] = new int[]{-1,3};
		}
		roomEntities.add(new Bookshelf(world, (roomMap[0].length-1f/4f)*Tile.TS,(keeperBottom?roomMap.length-1-bookMap.length/4f+1f/2f:((float)bookMap.length)/4f+1f/2f)*Tile.TS, bookMap));
		
		int shelfAreaWidth = (roomMap[0].length-2);
		int shelfAreaHeight = (roomMap.length-4);
		
		if(roomMap.length >= 6){
			Bookshelf shelf = new Bookshelf(world, roomMap[0].length/2f*Tile.TS-Tile.TS/4,(keeperBottom?roomMap.length-2:2)*Tile.TS, roomMap[0].length*2-5,2);
			if(!keeperBottom)shelf.flipY = true;
			roomEntities.add(shelf);
		}
		
		if(keeperBottom){
			if(doorY!=0)roomEntities.add(new Plant(world,Tile.TS/2,Tile.TS/2));
		}else{
			if(doorY!=roomMap.length-1)roomEntities.add(new Plant(world,Tile.TS/2,roomMap.length*Tile.TS-Tile.TS/2));
		}
		
		int x = 1, y = keeperBottom?3:1;
		for(int i = 0; i < shelfAreaHeight; i++){
			for(int k = 0; k < shelfAreaWidth; k++){
				roomMap[y][x] = 3;
				x++;
			}
			y++;
			x = 1;
		}
		
		//carpet
		roomEntities.add(new Carpet(world,roomMap[0].length/2f*Tile.TS-Tile.TS/4,roomMap.length/2f*Tile.TS+(keeperBottom?-Tile.TS/4:Tile.TS/4),2*roomMap[0].length-3,2*roomMap.length-3, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));
		
		//ending transformations

		unrotate(roomMap, room, roomEntities, doorFinder);
	}
	
	private void generateStairRoom(Rectangle room) {
		specialRooms.remove(room);
		
		//begining Tranaformations
		int[] doorFinder = findDoor(room);
		int[] doorPos = new int[2];
		int[][] roomMap = rotate(room, doorFinder, doorPos);
		int doorX =  doorPos[0];
		int doorY = doorPos[1];
		
		
		//initialize Entity Arrays
		ArrayList<Entity> roomEntities = new ArrayList<Entity>();
		
		//spawn entities and change tiles below
		int x, y;
		if(roomMap.length%2 == 0)y = roomMap.length/2 - (int) (Math.random()*2);
		else y = roomMap.length/2;
		
		if(roomMap[0].length%2 == 0)x = roomMap[0].length/2 - (int) (Math.random()*2);
		else x = roomMap[0].length/2;
		
		//spawn stairs
		roomEntities.add(new Stair(world,x*Tile.TS+Tile.TS/2 , y*Tile.TS+Tile.TS/2, true, 15+(int) (Math.random()*10), 15+((int) Math.random()*10)));
		
		//add pilars
		roomMap[1][1] = 1;
		roomMap[1][roomMap[0].length-2] = 1;
		roomMap[roomMap.length-2][1] = 1;
		roomMap[roomMap.length-2][roomMap[0].length-2] = 1;
		
		//add torches
		roomEntities.add(new Torch(world, 2*Tile.TS+Tile.TS/4, 1*Tile.TS+Tile.TS/2,0));
		roomEntities.add(new Torch(world, 1*Tile.TS+Tile.TS/2, 2*Tile.TS+Tile.TS/4,3));
		
		roomEntities.add(new Torch(world, (roomMap[0].length-2)*Tile.TS+Tile.TS/2, 2*Tile.TS+Tile.TS/4,3));
		roomEntities.add(new Torch(world, (roomMap[0].length-3)*Tile.TS+Tile.TS*3/4, 1*Tile.TS+Tile.TS/2,2));
		
		roomEntities.add(new Torch(world, 2*Tile.TS+Tile.TS/4, (roomMap.length-2)*Tile.TS+Tile.TS/2,0));
		roomEntities.add(new Torch(world, 1*Tile.TS+Tile.TS/2, (roomMap.length-3)*Tile.TS+Tile.TS*3/4,1));
		
		roomEntities.add(new Torch(world, (roomMap[0].length-3)*Tile.TS+Tile.TS*3/4, (roomMap.length-2)*Tile.TS+Tile.TS/2,2));
		roomEntities.add(new Torch(world, (roomMap[0].length-2)*Tile.TS+Tile.TS/2, (roomMap.length-3)*Tile.TS+Tile.TS*3/4,1));
		
		//spawn hatchkeeper
		boolean keeperTop = doorY >= roomMap.length/2;
		if(doorY == roomMap.length/2 && roomMap.length%2 == 1)keeperTop = Math.random()> 0.5;
		
		roomEntities.add(new StairKeeper(world, 2*Tile.TS+Tile.TS/2,(keeperTop?roomMap.length - 2:1)*Tile.TS+Tile.TS/2));
		
		
		//ending transformations
		unrotate(roomMap, room, roomEntities, doorFinder);
	}
	
	private void generateQuarters(Rectangle room){
		specialRooms.remove(room);
		
		//begining Tranaformations
		int[] doorFinder = findDoor(room);
		int[] doorPos = new int[2];
		int[][] roomMap = rotate(room, doorFinder, doorPos);
		int doorX =  doorPos[0];
		int doorY = doorPos[1];
		
		//initialize Entity Arrays
		ArrayList<Entity> roomEntities = new ArrayList<Entity>();
		
		//spawn stuff
		int x = 0,y = 0;
		ArrayList<int[]> occupiedTiles = new ArrayList<int[]>();
		
		roomEntities.add(new Villager(world,1*Tile.TS,1*Tile.TS));
		
		occupiedTiles.add(new int[]{doorY,0});
		occupiedTiles.add(new int[]{doorY,1});
		occupiedTiles.add(new int[]{doorY+1,0});
		occupiedTiles.add(new int[]{doorY-1,0});
		 //spawn bed
		int bedOrientation = (int)(Math.random()*4);
		int bedX = 0, bedY = 0;
		if(bedOrientation == 0 && roomMap.length < 3)bedOrientation = (int)(Math.random()*3+1);
		if(bedOrientation == 0){
			x = 0;
			do{
				y = (int) (Math.random()*roomMap.length);
			}while(y == doorY || y - 1 == doorY || y + 1 == doorY);
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			x+=Tile.TS;
			y*=Tile.TS;
			y+=Tile.TS/2;
			occupiedTiles.add(new int[]{bedY,bedX});
			occupiedTiles.add(new int[]{bedY,bedX+1});
			occupiedTiles.add(new int[]{bedY+1,bedX+1});
			occupiedTiles.add(new int[]{bedY-1,bedX+1});
		}else if(bedOrientation == 1){
			x = (int) (Math.random()*roomMap[0].length);
			if(doorY < 2){
				while (x < 2){
					x = (int) (Math.random()*roomMap[0].length);
				}
			}
			y = 0;
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			x+=Tile.TS/2;
			y*=Tile.TS;
			y+=Tile.TS;
			occupiedTiles.add(new int[]{bedY,bedX});
			occupiedTiles.add(new int[]{bedY+1,bedX});
			occupiedTiles.add(new int[]{bedY+1,bedX+1});
			occupiedTiles.add(new int[]{bedY+1,bedX-1});
		}else if(bedOrientation == 2){
			x = roomMap[0].length-1;
			y = (int) (Math.random()*roomMap.length);
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			y*=Tile.TS;
			y+=Tile.TS/2;
			occupiedTiles.add(new int[]{bedY,bedX});
			occupiedTiles.add(new int[]{bedY,bedX-1});
			occupiedTiles.add(new int[]{bedY+1,bedX-1});
			occupiedTiles.add(new int[]{bedY-1,bedX-1});
		}else if(bedOrientation == 3){
			x = (int) (Math.random()*roomMap[0].length);
			if(doorY > roomMap.length - 3){
				while (x < 2){
					x = (int) (Math.random()*roomMap[0].length);
				}
			}
			y = roomMap.length-1;
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			x+=Tile.TS/2;
			y*=Tile.TS;
			occupiedTiles.add(new int[]{bedY,bedX});
			occupiedTiles.add(new int[]{bedY-1,bedX});
			occupiedTiles.add(new int[]{bedY-1,bedX+1});
			occupiedTiles.add(new int[]{bedY-1,bedX-1});
		}
		if(bedOrientation == 0)bedOrientation = 3;
		else if(bedOrientation == 1)bedOrientation = 2;
		else if(bedOrientation == 2)bedOrientation = 1;
		else if(bedOrientation == 3)bedOrientation = 0;
		roomEntities.add(new Bed(world, x, y, bedOrientation, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));
		
		//spawn side table
		ArrayList<int[]> potentialTableSpots = new ArrayList<int[]>();
		if(bedOrientation % 2 == 0){
			potentialTableSpots.add(new int[]{bedY,bedX+1});
			potentialTableSpots.add(new int[]{bedY,bedX-1});
		}else{
			potentialTableSpots.add(new int[]{bedY+1,bedX});
			potentialTableSpots.add(new int[]{bedY-1,bedX});
			
		}
		
		for(int i = 0; i < potentialTableSpots.size(); i++){
			if(potentialTableSpots.get(i)[0] < 0 || potentialTableSpots.get(i)[0] > roomMap.length - 1 || potentialTableSpots.get(i)[1] < 0 || potentialTableSpots.get(i)[1] > roomMap[0].length - 1 || roomMap[potentialTableSpots.get(i)[0]][potentialTableSpots.get(i)[1]] != 0){
				potentialTableSpots.remove(i);
				i--;
			}
		}
		int tableSpot = (int) Math.random()*potentialTableSpots.size();
		
		roomEntities.add(new SmallTable(world,potentialTableSpots.get(tableSpot)[1]*Tile.TS+Tile.TS/2,potentialTableSpots.get(tableSpot)[0]*Tile.TS+Tile.TS/2));
		
		occupiedTiles.add(potentialTableSpots.get(tableSpot));
		
		//spawn dresser
		int attempts = 0;
		boolean placedDresser = false;
		int[] dresserPos = new int[]{0,0};
		int dresserOrientation = 0;
		do{
			attempts++;
			dresserOrientation = (int)(Math.random()*4);
			if(dresserOrientation == 0){
				for(int i = 0; i < roomMap.length-1; i++){
					if(!checkOccupied(occupiedTiles, 0, i)&&!checkOccupied(occupiedTiles, 0, i+1)){
						placedDresser = true;
						dresserPos = new int[]{i,0};
						occupiedTiles.add(dresserPos);
						occupiedTiles.add(new int[]{i+1,0});
						break;
					}
				}
			}else if(dresserOrientation == 1){
				for(int i = 0; i < roomMap[0].length-1; i++){
					if(!checkOccupied(occupiedTiles, i, 0)&&!checkOccupied(occupiedTiles, i+1, 0)){
						placedDresser = true;
						dresserPos = new int[]{0,i};
						occupiedTiles.add(dresserPos);
						occupiedTiles.add(new int[]{0,i+1});
						break;
					}
				}
			}else if(dresserOrientation == 2){
				for(int i = 0; i < roomMap.length-1; i++){
					if(!checkOccupied(occupiedTiles, roomMap[0].length-1, i)&&!checkOccupied(occupiedTiles, roomMap[0].length-1, i+1)){
						placedDresser = true;
						dresserPos = new int[]{i,roomMap[0].length-1};
						occupiedTiles.add(dresserPos);
						occupiedTiles.add(new int[]{i+1,roomMap[0].length-1});
						break;
					}
				}
			}else{
				for(int i = 0; i < roomMap[0].length-1; i++){
					if(!checkOccupied(occupiedTiles, i, roomMap.length-1)&&!checkOccupied(occupiedTiles, i+1, roomMap.length-1)){
						placedDresser = true;
						dresserPos = new int[]{roomMap.length-1,i};
						occupiedTiles.add(dresserPos);
						occupiedTiles.add(new int[]{roomMap.length-1,i+1});
						break;
					}
				}
			}
		}while(!placedDresser&&attempts < 10);
		
		roomEntities.add(new Dresser(world, (dresserPos[1]+(dresserOrientation%2==0?0.5f:1))*Tile.TS, (dresserPos[0]+(dresserOrientation%2==0?1:0.5f))*Tile.TS, dresserOrientation+1));
		roomMap[dresserPos[0]][dresserPos[1]] = 5;
		if(dresserOrientation % 2 == 0)roomMap[dresserPos[0]+1][dresserPos[1]] = 5;
		else roomMap[dresserPos[0]][dresserPos[1]+1] = 5;
		
		//spawn Fireplace
		attempts = 0;
		boolean placedFirepalce = false;
		int[] fireplacePos = new int[]{0,0};
		int fireplaceOrientation = 0;
		do{
			attempts++;
			fireplaceOrientation = (int)(Math.random()*4);
			if(fireplaceOrientation == 0){
				for(int i = 1; i < roomMap.length-1; i++){
					if(!checkOccupied(occupiedTiles, 0, i)){
						placedFirepalce = true;
						fireplacePos = new int[]{i,0};
						occupiedTiles.add(fireplacePos);
						fireplacePos[1]--;
						break;
					}
				}
			}else if(fireplaceOrientation == 1){
				for(int i = 1; i < roomMap[0].length-1; i++){
					if(!checkOccupied(occupiedTiles, i, 0)){
						placedFirepalce = true;
						fireplacePos = new int[]{0,i};
						occupiedTiles.add(fireplacePos);
						fireplacePos[0]--;
						break;
					}
				}
			}else if(fireplaceOrientation == 2){
				for(int i = 1; i < roomMap.length-1; i++){
					if(!checkOccupied(occupiedTiles, roomMap[0].length-1, i)){
						placedFirepalce = true;
						fireplacePos = new int[]{i,roomMap[0].length-1};
						occupiedTiles.add(fireplacePos);
						fireplacePos[1]++;
						break;
					}
				}
			}else{
				for(int i = 1; i < roomMap[0].length-1; i++){
					if(!checkOccupied(occupiedTiles, i, roomMap.length-1)){
						placedFirepalce = true;
						fireplacePos = new int[]{roomMap.length-1,i};
						occupiedTiles.add(fireplacePos);
						fireplacePos[0]++;
						break;
					}
				}
			}
		}while(!placedFirepalce&&attempts < 10);
		
		roomEntities.add(new Fireplace(world, fireplacePos[1]*Tile.TS+Tile.TS/2, fireplacePos[0]*Tile.TS+Tile.TS/2, fireplaceOrientation == 0?3:fireplaceOrientation == 1?2:fireplaceOrientation == 2?1:0));
		
		//spawn Carpet
		roomEntities.add(new Carpet(world,roomMap[0].length/2f*Tile.TS,roomMap.length/2f*Tile.TS,roomMap[0].length*2-2,roomMap.length*2-2, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));
		
		
		
		//ending transformations
		unrotate(roomMap, room, roomEntities, doorFinder);
	}
	
	private int[][] rotate(Rectangle room, int[] doorFinder, int[] doorPos){
		int[][] roomMap;
		if(doorFinder[0]==2||doorFinder[0]==3){
			roomMap = new int[(int) room.width][(int) room.height];
			doorPos[0] = 0;
			doorPos[1] = (int) (doorFinder[1]-room.x);
		}
		else {
			roomMap = new int[(int) room.height][(int) room.width];
			doorPos[0] = 0;
			doorPos[1] = (int) (doorFinder[2]- room.y);
		}
		return roomMap;
	}
	
	private void unrotate(int[][] roomMap, Rectangle room, ArrayList<Entity> roomEntities, int[] doorFinder){
		if(doorFinder[0]==1||doorFinder[0]==3){
			int[][] temp = roomMap.clone();
			
			if(doorFinder[0]==1) roomMap = new int[(int) room.height][(int) room.width];
			else roomMap = new int[(int) room.width][(int) room.height];
			
			for(int i = 0; i < temp.length; i++){
				for(int k = 0; k < temp[i].length; k++){
					System.out.println(i +  "," + k);
					roomMap[i][roomMap[i].length-1-k]=temp[i][k];
				}
			}
			
			for(Entity e: roomEntities) {
				e.x = roomMap[0].length*Tile.TS-e.x;
				e.flipX  = !e.flipX;
			}
		}
		
		if(doorFinder[0]==2||doorFinder[0]==3){
			int[][] temp = roomMap.clone();
			roomMap = new int[(int) room.height][(int) room.width];
			for(int i = 0; i < temp.length; i++){
				for(int k = 0; k < temp[i].length; k++){
					roomMap[k][i]=temp[i][k];
				}
			}
			for(Entity e: roomEntities) {
				float tempX = e.x;
				e.x = e.y;
				e.y = tempX;
				e.angle-=90;
				e.flipX = !e.flipX;
			}
		}
		
		//copy tiles to the map
		
		int x = (int) room.x;
		int y = (int) room.y;
		
		for(int i = 0; i < room.height; i++){
			for(int k = 0; k < room.width; k++){
				map[y][x] = roomMap[i][k];
				x++;
			}
			y++;
			x = (int) room.x;
		}
		
		for(Entity e: roomEntities) {
			e.x += room.x*Tile.TS;
			e.y += room.y*Tile.TS;
			entities.add(e);
		}
	}
	
	private boolean checkOccupied(ArrayList<int[]> occupied, int x, int y){
		for(int i = 0; i < occupied.size(); i++){
			if(x == occupied.get(i)[1] && y == occupied.get(i)[0])return true;
		}
		return false;
		
	}
}
