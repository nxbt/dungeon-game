package com.dungeon.game.generator.rooms.room;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.character.enemy.Goon;
import com.dungeon.game.entity.furniture.Torch;
import com.dungeon.game.generator.LootGenerator;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class EnemyRoom extends Room {

	public EnemyRoom(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		for(int i = 0; i < doorPos.length; i++){
			addToOccupied(doorPos[i][0]+1, doorPos[i][1]);
			addToOccupied(doorPos[i][0]-1, doorPos[i][1]);
			addToOccupied(doorPos[i][0], doorPos[i][1]+1);
			addToOccupied(doorPos[i][0], doorPos[i][1]-1);
		}
		int x, y;
		x = 0;
		for(int i = 0; i < room.length; i++){
			y = i;
			if(!checkOccupied(x, y) && Math.random() < 0.2){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS/4, y*Tile.TS+Tile.TS/2,0));
				addToOccupied(x, y);
			}
		}
		x = room[0].length-1;
		for(int i = 0; i < room.length; i++){
			y = i;
			if(!checkOccupied(x, y) && Math.random() < 0.2){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS*3/4, y*Tile.TS+Tile.TS/2,2));
				addToOccupied(x, y);
			}
		}
		y = 0;
		for(int i = 0; i < room[0].length; i++){
			x = i;
			if(checkOccupied(x, y) && Math.random() < 0.2){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS/2, y*Tile.TS+Tile.TS/4,3));
				addToOccupied(x, y);
			}
		}
		y = room.length-1;
		for(int i = 0; i < room[0].length; i++){
			x = i;
			if(checkOccupied(x, y) && Math.random() < 0.2){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS/2, y*Tile.TS+Tile.TS*3/4,1));
				addToOccupied(x, y);
			}
		}
		
		int numEnemies = (int) ((room.length * room[0].length)/20f*Math.random()*2f);
		
		while(numEnemies > 0){
			numEnemies--;
			do{
				x = (int)(room[0].length*Math.random());
				y = (int)(room.length*Math.random());
			}while(checkOccupied(x, y));
			
			entities.add(new Goon(world, x*Tile.TS + Tile.TS/2, y*Tile.TS + Tile.TS/2));
			
			addToOccupied(x, y);
		}
		
		int numChests = (int) ((room.length * room[0].length)/40f*Math.random()*2f);
		while(numChests > 0){
			numChests--;
			do{
				x = (int)(room[0].length*Math.random());
				y = (int)(room.length*Math.random());
			}while(checkOccupied(x, y));
			
			entities.add(LootGenerator.getChest(world, 1, x, y));
			
			addToOccupied(x, y);
		}
	}

}
