package com.dungeon.game.generator.room;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.friend.Shopkeeper;
import com.dungeon.game.entity.furniture.Bookshelf;
import com.dungeon.game.entity.furniture.Carpet;
import com.dungeon.game.entity.furniture.Plant;
import com.dungeon.game.entity.furniture.ShopDesk1;
import com.dungeon.game.entity.furniture.ShopDesk2;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class GeneralStore extends Room {

	public GeneralStore(World world, Rectangle roomBase, int[] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
	}

	@Override
	public void generate() {//begin transformation
		int doorX =  doorPos[0];
		int doorY = doorPos[1];
		
		boolean keeperBottom = Math.random()>0.5;
		if(doorY == 0)keeperBottom = true;
		else if(doorY == room.length-1)keeperBottom = false;
		
		for(int i = 0; i < room.length; i++){
			for(int k = 0; k < room[i].length; k++){
				room[i][k] = tileMap.getTile(0);
			}
		}
		
		Shopkeeper tempKeeper = new Shopkeeper(world, (room[0].length-1)*Tile.TS+Tile.TS/2, (keeperBottom? 3f/4f:room.length-1+1f/4f)*Tile.TS);
		tempKeeper.flipX = true;
		
		entities.add(tempKeeper);
		
		ShopDesk1 desk1 = new ShopDesk1(world, (room[0].length-1)*Tile.TS-Tile.TS*1f/4f, (keeperBottom? 3f/4f:room.length-1+1f/4f)*Tile.TS);
		ShopDesk2 desk2 = new ShopDesk2(world, (room[0].length-1)*Tile.TS-Tile.TS*-1f/4f, (keeperBottom? 7f/4f:room.length-1-3f/4f)*Tile.TS);
		
		if(keeperBottom){
			desk1.angle = 180;
			desk2.angle = 180;
		}else{
			desk1.flipX = true;
			desk2.flipX = true;
		}
		
		entities.add(desk1);
		entities.add(desk2);
		
		int[][][] bookMap = new int[1][room[0].length*2][2];
		for(int i = 0; i < room[0].length*2; i++){
			if(i == room[0].length*2-1)bookMap[0][i] = new int[]{3,keeperBottom?2:0};
			else bookMap[0][i] = new int[]{-1,keeperBottom?2:0};
		}
		entities.add(new Bookshelf(world, bookMap[0].length*8,(keeperBottom?room.length-1+3f/4f:1f/4f)*Tile.TS, bookMap));
		

		bookMap = new int[room.length*2-6][1][2];
		for(int i = 0; i < room.length*2-6; i++){
			bookMap[i][0] = new int[]{-1,3};
		}
		entities.add(new Bookshelf(world, (room[0].length-1f/4f)*Tile.TS,(keeperBottom?room.length-1-bookMap.length/4f+1f/2f:((float)bookMap.length)/4f+1f/2f)*Tile.TS, bookMap));
		
		int shelfAreaWidth = (room[0].length-2);
		int shelfAreaHeight = (room.length-4);
		
		if(room.length >= 6){
			Bookshelf shelf = new Bookshelf(world, room[0].length/2f*Tile.TS-Tile.TS/4,(keeperBottom?room.length-2:2)*Tile.TS, room[0].length*2-5,2);
			if(!keeperBottom)shelf.flipY = true;
			entities.add(shelf);
		}
		
		if(keeperBottom){
			if(doorY!=0)entities.add(new Plant(world,Tile.TS/2,Tile.TS/2));
		}else{
			if(doorY!=room.length-1)entities.add(new Plant(world,Tile.TS/2,room.length*Tile.TS-Tile.TS/2));
		}
		
		int x = 1, y = keeperBottom?3:1;
		for(int i = 0; i < shelfAreaHeight; i++){
			for(int k = 0; k < shelfAreaWidth; k++){
				room[y][x] = tileMap.getTile(3);
				x++;
			}
			y++;
			x = 1;
		}
		
		//carpet
		entities.add(new Carpet(world,room[0].length/2f*Tile.TS-Tile.TS/4,room.length/2f*Tile.TS+(keeperBottom?-Tile.TS/4:Tile.TS/4),2*room[0].length-3,2*room.length-3, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));
		
		//ending transformations
		
	}

}
