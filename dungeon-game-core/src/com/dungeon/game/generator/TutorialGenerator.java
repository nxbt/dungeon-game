package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.furniture.LockedDoor;
import com.dungeon.game.generator.rooms.room.EmptyRoom;
import com.dungeon.game.generator.rooms.room.Room;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Key;
import com.dungeon.game.pathing.HierarchicalGraph;
import com.dungeon.game.pathing.Node;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class TutorialGenerator extends Generation {

	public TutorialGenerator(World world, int textureSeed) {
		super(world, 100, 100, textureSeed, new Object[0]);
	}
	
	public void generate(Object[] args){
		super.generate(args);
		Rectangle workingRoom;

		workingRoom = new Rectangle(48, 52, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 48, 52, 0));
		
		workingRoom = new Rectangle(45, 45, 7, 7);
		addToMap(workingRoom);
		Room startRoomRoom = new EmptyRoom(world, workingRoom, findDoors(workingRoom), tileMap);
		startRoomRoom.addToMap(map, entities);
		
		workingRoom = new Rectangle(30, 53, 23, 2);
		addToMap(workingRoom);

		workingRoom = new Rectangle(30, 55, 5, 8);
		addToMap(workingRoom);
		Slot s = new Slot(world, new int[]{0, 0, 0}, null);
		s.item = new Key(world);
		entities.add(new Drop(world, 32*Tile.TS+Tile.TS/2, 59*Tile.TS+Tile.TS/2, s));
		
		workingRoom = new Rectangle(32, 63, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 32, 63, 2));
		

		workingRoom = new Rectangle(30, 64, 20, 2);
		addToMap(workingRoom);
		
		makeWalls(10, 11, 12, 13, 14);
		
		
	}
	

	
	private void addToMap(Rectangle room){
        for(int i = 0; i < room.width; i++){
            for(int k = 0; k < room.height; k++){
                 map[(int) (room.y + k)][(int) (room.x + i)] = tileMap.getTile(0);
             } 
         }
	}

	@Override
	public void generateAreas() {
		// TODO Auto-generated method stub

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
	public HierarchicalGraph getPathGraph() {
		
		return new HierarchicalGraph(new Node[][]{new Node[0]});
	}

}
