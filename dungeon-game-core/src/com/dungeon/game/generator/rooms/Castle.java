package com.dungeon.game.generator.rooms;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.generator.Generation;
import com.dungeon.game.generator.rooms.room.EnemyRoom;
import com.dungeon.game.generator.rooms.room.Room;
import com.dungeon.game.pathing.newpathing.Node;
import com.dungeon.game.pathing.newpathing.Graph;
import com.dungeon.game.pathing.newpathing.GraphLevel;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Castle extends Generation {
	
	protected ArrayList<GenRoom> rooms;
	
	protected ArrayList<int[]> doors;

	public Castle(World world, int width, int height, int centerX, int centerY, int upTrapX, int upTrapY,  int textureSeed, Object[] args) {
		super(world, width, height, textureSeed, args);
	}
	
	protected void generate(Object[] args){
		super.generate(args);
		rooms = new ArrayList<GenRoom>();
		doors = new ArrayList<int[]>();
		
		createRooms();
		createDoors();
		if(args.length == 0 ||  !args[0].equals("test"))populateRooms();
		makeWalls(10);
	}
	
	protected class GenRoom extends Rectangle {
		private ArrayList<String> expands;
		protected ArrayList<GenRoom> connectedRooms;
		
		private GenRoom(int x, int y, int width, int height){
			super(x, y, width, height);
			
			expands = new ArrayList<String>();
			expands.add("W");
			expands.add("H");
			expands.add("X");
			expands.add("Y");
			connectedRooms = new ArrayList<GenRoom>();
		}
		
		private boolean overlaps(GenRoom room){
	        if(this == room)return false;
	        if(x - 1 < room.x + room.width && x + width > room.x - 1 && y - 1 < room.y + room.height && y + height > room.y - 1)return true;
	        return false;
		}
		
		private void addToMap(){
	        for(int i = 0; i < width; i++){
	            for(int k = 0; k < height; k++){
	                 map[(int) (y + k)][(int) (x + i)] = tileMap.getTile(0);
	             } 
	         }
		}
		
		private void expandToFillMap(){
	        int index = (int)(expands.size()*Math.random());
	        String expand = expands.remove(index);
	        if(expand == "W"){
                expandWidthToFillMap();
	        }else if(expand == "H"){
                expandHeightToFillMap();
	        }else if(expand == "X"){
                expandXToFillMap();
	        }else if(expand == "Y"){
                expandYToFillMap();
	        }
		}
		
		private void expandWidthToFillMap(){
	        //width
	        do{
	        	width++;
	        }while(isValidRoom(this));
	        width--;
		}
		
		private void expandHeightToFillMap(){
	        
	        //height
	        do{
	            height++;
	        }while(isValidRoom(this));
	        height--;
		}

		private void expandXToFillMap(){
	        
	        //x
	        do{
	            this.x--;
	            this.width++;
	        }while(isValidRoom(this));
	        x++;
	        width--;
		}
		
		private void expandYToFillMap(){
	        
	        //y
	        do{
	            y--;
	            height++;
	        }while(isValidRoom(this));
	        y++;
	        height--;
		}
		

		private boolean isAdjacent(int x, int y){
	        if((x == this.x - 1 || x == this.x + width) && y > this.y - 1 && y < this.y + height)return true;
	        if((y == this.y - 1 || y == this.y + height) && x > this.x - 1 && x < this.x + width)return true;
	        return false;
	    };
	}
	
	protected void createRooms(){
	    
	    //create the coom seeds
	    for(int i = 0; i < 200; i++){
	        int x, y, w, h;
	        GenRoom room;
	        int attempts = 0;
	        do{
	            attempts++;
	            w = 5;
	            h = 5;
	            x = (int)(Math.random()*(width-w));
	            y = (int)(Math.random()*(height-h));
	            room = new GenRoom(x, y, w, h);
	        }while(!isValidRoom(room) && attempts < 100);
	        if(attempts < 100)rooms.add(room);
	    }
	    //grow the rooms to fill the map
//	    var roomsQuad = rooms.concat(rooms).concat(rooms).concat(rooms);
	    ArrayList<GenRoom> roomsQuad = new ArrayList<GenRoom>();
	    for(int i = 0; i < rooms.size(); i++){
	    	roomsQuad.add(rooms.get(i));
	    	roomsQuad.add(rooms.get(i));
	    	roomsQuad.add(rooms.get(i));
	    	roomsQuad.add(rooms.get(i));
	    }
	    Collections.shuffle(roomsQuad);
	    for(int i = roomsQuad.size() - 1; i >=0; i--)roomsQuad.get(i).expandToFillMap();
	    //add the rooms to the map
	    for(int i = 0; i < rooms.size(); i++)rooms.get(i).addToMap();
	}

	private boolean isValidRoom(GenRoom room){
		if(room.x < 1 || room.y < 1 || room.x + room.width > width - 1 || room.y + room.height > height - 1)return false;
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).overlaps(room))return false;
		}
		return true;
	}
	

	protected void createDoors(){
		ArrayList<int[]> doorCandidates = new ArrayList<int[]>();
		for(int i = 0; i < width; i++){
			for(int k = 0; k < width; k++){
				if(Tile.isSolid(map[k][i])){
					GenRoom[] adjacentRooms = getAdjacentRooms(i, k);
						if(adjacentRooms.length == 2){
							doorCandidates.add(new int[]{i, k});
						}
				}
			}
		}
    
		Collections.shuffle(doorCandidates);
    
		for(int i = 0; i < doorCandidates.size(); i++){
			int x = doorCandidates.get(i)[0];
			int y = doorCandidates.get(i)[1];
			GenRoom[] adjacentRooms  = getAdjacentRooms(x, y);
			GenRoom room1 = adjacentRooms[0];
			GenRoom room2 = adjacentRooms[1];
	        if(room1.connectedRooms.indexOf(room2) == -1){
	            room1.connectedRooms.add(room2);
	            room2.connectedRooms.add(room1);
	            map[y][x] = tileMap.getTile(0);
	            doors.add(new int[]{x, y});
	        }
            
		}
	}
	


	protected GenRoom[] getAdjacentRooms(int x, int y){
		ArrayList<GenRoom> adjacentRooms = new ArrayList<GenRoom>();
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).isAdjacent(x, y))adjacentRooms.add(rooms.get(i));
		}
		GenRoom[] returnArray = new GenRoom[adjacentRooms.size()];
		for(int i = 0; i < returnArray.length; i++){
			returnArray[i] = adjacentRooms.get(i);
		}
		return returnArray;
	}
	
	protected void populateRooms() {
		ArrayList<Rectangle> rooms = (ArrayList<Rectangle>) this.rooms.clone();
		Rectangle normRoomsRect;
		int[][] normRoomsDoorFinder;
		Room normRoom;
		while(rooms.size()>0){
			normRoomsRect = rooms.remove((int) (rooms.size()*Math.random()));
			normRoomsDoorFinder = findDoors(normRoomsRect);
			normRoom = new EnemyRoom(world, normRoomsRect, normRoomsDoorFinder, tileMap);
			normRoom.addToMap(map, entities);
		}
	}
	
	protected int[][] findDoors(Rectangle room){
		ArrayList<int[]> doors = new ArrayList<int[]>();
		int x,y;
		x = (int) room.x-1;
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(!Tile.isSolid(map[y][x])){
//				map[y][x]=tileMap.getTile(5);
//				return new int[]{0,x,y};
				doors.add(new int[]{0,x,y});
			}

			y++;
		}
		x = (int) (room.x+room.width);
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(!Tile.isSolid(map[y][x])){
//				map[y][x]=tileMap.getTile(5);
//				return new int[]{1,x,y};
				doors.add(new int[]{1,x,y});
			}

			y++;
		}
		x = (int) room.x;
		y = (int) room.y-1;
		for(int i = 0; i < room.width; i++){
			if(!Tile.isSolid(map[y][x])){
//				map[y][x]=tileMap.getTile(5);
//				return new int[]{2,x,y};
				doors.add(new int[]{2,x,y});
			}

			x++;
		}
		x = (int) room.x;
		y = (int) (room.y+room.height);
		for(int i = 0; i < room.width; i++){
			if(!Tile.isSolid(map[y][x])){
//				map[y][x]=tileMap.getTile(5);
//				return new int[]{3,x,y};
				doors.add(new int[]{3,x,y});
			}

			x++;
		}
		int[][] ds = doors.toArray(new int[doors.size()][3]);
		return ds;
	}
	


	@Override
	public Graph getPathGraph() {
		GraphLevel gl0 = new GraphLevel();
		GraphLevel gl1 = new GraphLevel();
		
		Node[][] nodeArray = new Node[width][height];
		//generate nodes for each tile
		for(GenRoom room: rooms){
			Node zoneNode = gl1.addNode(room.x + room.width/2, room.y + room.height/2);
			for(int i = (int) room.x; i < room.x + room.width; i++){
				for(int k = (int) room.y; k < room.y + room.height; k++){
					if(!Tile.isSolid(map[k][i])){
						Node node = gl0.addNode(i + 0.5f, k + 0.5f);
						node.upNode = zoneNode;
						zoneNode.downNodes.add(node);
						nodeArray[i][k] = node;
					}
				}
			}
		}
		
		for(int[] d: doors){
			int x = d[0];
			int y = d[1];
			Node zoneNode = null;
			for(int i = 0; i < rooms.size(); i++){
				if(rooms.get(i).isAdjacent(x, y)){
					zoneNode = gl1.nodes.get(i);
					break;
				}
			}
			Node node = gl0.addNode(x + 0.5f, y + 0.5f);
			node.upNode = zoneNode;
			zoneNode.downNodes.add(node);
			nodeArray[x][y] = node;
			
		}
		
		for(int i = 0; i <  nodeArray.length; i++){
			for(int k = 0; k <  nodeArray[0].length; k++){
				Node n = nodeArray[i][k];
				if(n != null){
					if(i > 0 && nodeArray[i - 1][k] != null)n.addConnection(nodeArray[i - 1][k], 1);
					if(i < nodeArray.length - 1 && nodeArray[i + 1][k] != null)n.addConnection(nodeArray[i + 1][k], 1);
					if(k > 0 && nodeArray[i][k - 1] != null)n.addConnection(nodeArray[i][k - 1], 1);
					if(k < nodeArray[0].length - 1 && nodeArray[i][k + 1] != null)n.addConnection(nodeArray[i][k + 1], 1);
				}
			}
		}
		
		for(Node n1: gl1.nodes){
			for(Node n2: gl1.nodes){
				if(!n1.equals(n2) && n1.isAdjacentTo(n2)){
					n1.addConnection(n2, n1.findDistance(n2.x*Tile.TS, n2.y*Tile.TS));
				}
			}
		}
		for(Node n: gl1.nodes){
			n.setDownNode();
		}
		
		return new Graph(new GraphLevel[]{gl0, gl1});
	}
	
	

}
