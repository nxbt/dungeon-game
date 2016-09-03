package com.dungeon.game.generator;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Skeleton;
import com.dungeon.game.entity.character.enemy.Dummy;
import com.dungeon.game.entity.character.enemy.TutorialGoon;
import com.dungeon.game.entity.character.friend.Guide;
import com.dungeon.game.entity.furniture.Bar;
import com.dungeon.game.entity.furniture.Chest;
import com.dungeon.game.entity.furniture.Door;
import com.dungeon.game.entity.furniture.Lamp;
import com.dungeon.game.entity.furniture.LockedDoor;
import com.dungeon.game.entity.furniture.Stair;
import com.dungeon.game.entity.furniture.Torch;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Gold;
import com.dungeon.game.item.Key;
import com.dungeon.game.item.equipable.armor.WoolPants;
import com.dungeon.game.item.equipable.armor.WoolShirt;
import com.dungeon.game.pathing.Graph;
import com.dungeon.game.pathing.GraphLevel;
import com.dungeon.game.pathing.Node;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class TutorialGenerator extends Generation {
	
	private Node[][] nodeArray;

	private GraphLevel gl0;
	
	private GraphLevel gl1;

	public TutorialGenerator(World world, int textureSeed) {
		super(world, 100, 100, textureSeed, new Object[0]);
	}
	
	public void generate(Object[] args){

		gl0 = new GraphLevel();
		gl1 = new GraphLevel();
		super.generate(args);
		nodeArray = new Node[width][height];
		Rectangle workingRoom;
		
		workingRoom = new Rectangle(48, 52, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 48, 52, 0));
		
		
		//start Room
		workingRoom = new Rectangle(45, 45, 7, 7);
		addToMap(workingRoom);

		entities.add(new Guide(world, 45*Tile.TS+Tile.TS/2, 51*Tile.TS+Tile.TS/2));
		entities.add(new Torch(world, 45, 46, 0));
		entities.add(new Torch(world, 45, 50, 0));
		entities.add(new Torch(world, 50, 45, 3));
		entities.add(new Torch(world, 51, 48, 2));
		entities.add(new Torch(world, 47, 51, 1));
		entities.add(new Torch(world, 49, 51, 1));
		
		//first hallway
		workingRoom = new Rectangle(30, 53, 23, 2);
		addToMap(workingRoom);
		
		entities.add(new Torch(world, 51, 54, 1));
		entities.add(new Torch(world, 46, 53, 3));
		entities.add(new Torch(world, 43, 54, 1));
		entities.add(new Torch(world, 40, 54, 1));
		entities.add(new Torch(world, 38, 53, 3));
		entities.add(new Torch(world, 34, 53, 3));
		entities.add(new Torch(world, 30, 54, 0));
		
		//room with key on floor
		workingRoom = new Rectangle(30, 55, 5, 8);
		addToMap(workingRoom);
		Slot s = new Slot(world, new int[]{0, 0, 0}, null);
		s.item = new Key(world);
		entities.add(new Drop(world, 32*Tile.TS+Tile.TS/2, 59*Tile.TS+Tile.TS/2, s));
		
		workingRoom = new Rectangle(32, 63, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 32, 63, 0));
		
		entities.add(new Torch(world, 34, 56, 2));
		entities.add(new Torch(world, 34, 59, 2));
		entities.add(new Torch(world, 30, 58, 0));
		entities.add(new Torch(world, 31, 62, 1));
		entities.add(new Torch(world, 33, 62, 1));
		
		//second hallway
		workingRoom = new Rectangle(30, 64, 27, 2);
		addToMap(workingRoom);

		entities.add(new Torch(world, 30, 65, 0));
		entities.add(new Torch(world, 34, 65, 1));
		entities.add(new Torch(world, 38, 64, 3));
		entities.add(new Torch(world, 42, 65, 1));
		entities.add(new Torch(world, 46, 64, 3));
		entities.add(new Torch(world, 51, 65, 1));
		entities.add(new Torch(world, 56, 65, 2));
		
		
		//loot rooms bottom
		
		workingRoom = new Rectangle(39, 59, 5, 4);
		addToMap(workingRoom);
		
		entities.add(new Lamp(world, 41*Tile.TS + Tile.TS/2, 60*Tile.TS + Tile.TS/2));

		workingRoom = new Rectangle(49, 59, 5, 4);
		addToMap(workingRoom);

		entities.add(new Lamp(world, 52*Tile.TS + Tile.TS/2, 61*Tile.TS + Tile.TS/2));

		workingRoom = new Rectangle(41, 63, 1, 1);
		addToMap(workingRoom);
		entities.add(new Door(world, 41, 63, 0));
		
		workingRoom = new Rectangle(51, 63, 1, 1);
		addToMap(workingRoom);
		entities.add(new Door(world, 51, 63, 2));
		
		Chest chest = new Chest(world, 39*Tile.TS+Tile.TS/2, 59*Tile.TS+Tile.TS/2);
		chest.inv.addItemRand(new Gold(world),5);
		
		entities.add(chest);
		
		chest = new Chest(world, 50*Tile.TS+Tile.TS/2, 61*Tile.TS+Tile.TS/2);
		chest.inv.addItemRand(new WoolPants(world, new Color(0.2f, 0.2f,0.7f,0.5f)));
		
		entities.add(chest);
		
		//loot rooms top
		
		workingRoom = new Rectangle(34, 67, 5, 4);
		addToMap(workingRoom);
		

		
		entities.add(new Lamp(world, 38*Tile.TS + Tile.TS/2, 68*Tile.TS + Tile.TS/2));

		workingRoom = new Rectangle(44, 67, 5, 4);
		addToMap(workingRoom);
		
		entities.add(new Lamp(world, 44*Tile.TS + Tile.TS/2, 70*Tile.TS + Tile.TS/2));

		workingRoom = new Rectangle(36, 66, 1, 1);
		addToMap(workingRoom);
		entities.add(new Door(world, 36, 66, 2));
		
		workingRoom = new Rectangle(46, 66, 1, 1);
		addToMap(workingRoom);
		entities.add(new Door(world, 46, 66, 2));
		
		chest = new Chest(world, 36*Tile.TS+Tile.TS/2, 70*Tile.TS+Tile.TS/2);
		chest.inv.addItemRand(new WoolShirt(world, new Color(0.7f, 0.2f,0.2f,0.5f)));
		
		entities.add(chest);
		
		chest = new Chest(world, 47*Tile.TS+Tile.TS/2, 69*Tile.TS+Tile.TS/2);
		chest.inv.addItemRand(new Key(world));
		
		entities.add(chest);
		
		//DummyRoom
		workingRoom = new Rectangle(58, 60, 11, 11);
		addToMap(workingRoom);
		
		map[62][60] = tileMap.getTile(1);
		entities.add(new Torch(world, 59, 62, 2));
		entities.add(new Torch(world, 61, 62, 0));
		entities.add(new Torch(world, 60, 61, 1));
		entities.add(new Torch(world, 60, 63, 3));
		removeNode(60, 62);

		map[68][66] = tileMap.getTile(1);
		entities.add(new Torch(world, 65, 68, 2));
		entities.add(new Torch(world, 67, 68, 0));
		entities.add(new Torch(world, 66, 67, 1));
		entities.add(new Torch(world, 66, 69, 3));
		removeNode(66, 68);
		

		map[68][60] = tileMap.getTile(1);
		entities.add(new Torch(world, 59, 68, 2));
		entities.add(new Torch(world, 61, 68, 0));
		entities.add(new Torch(world, 60, 67, 1));
		entities.add(new Torch(world, 60, 69, 3));
		removeNode(60, 68);
		
		map[62][66] = tileMap.getTile(1);
		entities.add(new Torch(world, 65, 62, 2));
		entities.add(new Torch(world, 67, 62, 0));
		entities.add(new Torch(world, 66, 61, 1));
		entities.add(new Torch(world, 66, 63, 3));
		removeNode(66, 62);
		
		entities.add(new Dummy(world, 63*Tile.TS+Tile.TS/2, 65*Tile.TS+Tile.TS/2));
		entities.add(new Dummy(world, 66*Tile.TS+Tile.TS/2, 66*Tile.TS+Tile.TS/2));
		entities.add(new Dummy(world, 65*Tile.TS+Tile.TS/2, 63*Tile.TS+Tile.TS/2));
		

		entities.add(new Torch(world, 62, 60, 3));
		entities.add(new Torch(world, 64, 60, 3));
		
		//dark room
		
		workingRoom = new Rectangle(57, 64, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 57, 64, 1));
		
		workingRoom = new Rectangle(63, 59,  1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 63, 59, 0));
		
		workingRoom = new Rectangle(59, 40, 12, 19);
		addToMap(workingRoom);
		

		entities.add(new Torch(world, 65, 58, 1));
		int x, y;
		y = 55;
		for(x = 61; x < 69 ; x++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}
		
		y = 52;
		for(x = 59; x < 63 ; x++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}
		
		for(x = 68; x < 71 ; x++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}
		x = 65;
		for(y = 50; y < 55  ; y++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}

		x = 62;
		for(y = 46; y < 52  ; y++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}

		y = 46;
		for(x = 63; x < 69 ; x++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}
		
		y = 43;
		for(x = 61; x < 69 ; x++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}
		
		x = 60;
		for(y = 43; y < 50  ; y++){
			removeNode(x, y);
			map[y][x] = tileMap.getTile(1);
		}
		

		
		workingRoom = new Rectangle(65, 39, 1, 1);
		addToMap(workingRoom);
		entities.add(new Door(world, 65, 39, 2));
		
		//hallway 3
		workingRoom = new Rectangle(64, 20, 3, 19);
		addToMap(workingRoom);

		entities.add(new Torch(world, 64, 38, 1));
		entities.add(new Torch(world, 66, 38, 1));

		entities.add(new Torch(world, 64, 32, 0));
		entities.add(new Torch(world, 66, 29, 2));
		entities.add(new Torch(world, 64, 25, 0));
		entities.add(new Torch(world, 65, 20, 3));
		
		//cell 1
		workingRoom = new Rectangle(67, 32, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 67, 32, 1));
		
		workingRoom = new Rectangle(68, 32, 4, 4);
		addToMap(workingRoom);
		entities.add(new Torch(world, 71, 32, 2));
		Chest c = new Chest(world, 71*Tile.TS + Tile.TS/2, 33*Tile.TS + Tile.TS/2);
		c.inv.addItemRand(new Gold(world, 100));
		entities.add(c);
		TutorialGoon g = new TutorialGoon(world, 71*Tile.TS + Tile.TS/2, 35*Tile.TS + Tile.TS/2);
		g.equipSlots[0].item = null;
		entities.add(g);
		

		workingRoom = new Rectangle(67, 34, 1, 1);
		addToMap(workingRoom);
		entities.add(new Bar(world, 67, 34, 1));
		

		//cell 2
		workingRoom = new Rectangle(63, 29, 1, 1);
		addToMap(workingRoom);
		
		workingRoom = new Rectangle(59, 27, 4, 4);
		addToMap(workingRoom);
		entities.add(new Torch(world, 59, 29, 0));
		c = new Chest(world, 59*Tile.TS + Tile.TS/2, 27*Tile.TS + Tile.TS/2);
		c.inv.addItemRand(new Key(world));
		c.inv.addItemRand(new Gold(world, 10));
		entities.add(c);
		
		entities.add(new TutorialGoon(world, 59*Tile.TS + Tile.TS/2, 29*Tile.TS + Tile.TS/2));
		

		//cell 3
		workingRoom = new Rectangle(67, 25, 1, 1);
		addToMap(workingRoom);
		entities.add(new LockedDoor(world, 67, 25, 1));
		
		workingRoom = new Rectangle(68, 24, 4, 4);
		addToMap(workingRoom);
		entities.add(new Torch(world, 71, 25, 2));
		
		s = new Slot(world, new int[]{0, 0, 0}, null);
		
		s.item = new Gold(world, 30);

		entities.add(new Drop(world, 70*Tile.TS + Tile.TS/3, 25*Tile.TS + Tile.TS/4, s));
		entities.add(new Skeleton(world, 70*Tile.TS + Tile.TS/2, 25*Tile.TS + Tile.TS/2));
		
		//hallway 4
		

		workingRoom = new Rectangle(59, 20, 5, 3);
		addToMap(workingRoom);
		
		entities.add(new Stair(world, 60*Tile.TS + Tile.TS/2, 21*Tile.TS + Tile.TS/2, true, 25, 25));
		
		entities.add(new Torch(world, 59, 21, 0));
		
		makeWalls(10);
	}
	
	private void removeNode(int x, int y){
		nodeArray[x][y].upNode.downNodes.remove(nodeArray[x][y]);
		nodeArray[x][y] = null;
	}
	

	
	private void addToMap(Rectangle room){
		Node zoneNode = gl1.addNode(room.x+room.width/2, room.y+room.height/2);
        for(int i = 0; i < room.width; i++){
            for(int k = 0; k < room.height; k++){
            	map[(int) (room.y + k)][(int) (room.x + i)] = tileMap.getTile(0);
				Node node = gl0.addNode(room.x + i + 0.5f, room.y + k + 0.5f);
				node.upNode = zoneNode;
				zoneNode.downNodes.add(node);
				nodeArray[(int) (room.x + i)][(int) (room.y + k)] = node;
             } 
         }
	}
	
	protected int[][] findDoors(Rectangle room){
		ArrayList<int[]> doors = new ArrayList<int[]>();
		int x,y;
		x = (int) room.x-1;
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(!Tile.isSolid(map[y][x])){
				doors.add(new int[]{0,x,y});
			}

			y++;
		}
		x = (int) (room.x+room.width);
		y = (int) room.y;
		for(int i = 0; i < room.height; i++){
			if(!Tile.isSolid(map[y][x])){
				doors.add(new int[]{1,x,y});
			}

			y++;
		}
		x = (int) room.x;
		y = (int) room.y-1;
		for(int i = 0; i < room.width; i++){
			if(!Tile.isSolid(map[y][x])){
				doors.add(new int[]{2,x,y});
			}

			x++;
		}
		x = (int) room.x;
		y = (int) (room.y+room.height);
		for(int i = 0; i < room.width; i++){
			if(!Tile.isSolid(map[y][x])){
				doors.add(new int[]{3,x,y});
			}

			x++;
		}
		int[][] ds = doors.toArray(new int[doors.size()][3]);
		return ds;
	}

	@Override
	public Graph getPathGraph() {
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
			for(Node n2:  gl1.nodes){
				if(!n1.equals(n2) && n1.isAdjacentTo(n2)){
					n1.addConnection(n2, n1.findDistance(n2.x, n2.y));
				}
			}
		}
		for(Node n: gl1.nodes){
			n.setDownNode();
		}

		
		return new Graph(new GraphLevel[]{gl0, gl1});
	}

}
