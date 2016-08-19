package com.dungeon.game.generator.rooms.room;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.friend.Villager;
import com.dungeon.game.entity.furniture.Bed;
import com.dungeon.game.entity.furniture.Carpet;
import com.dungeon.game.entity.furniture.Dresser;
import com.dungeon.game.entity.furniture.Fireplace;
import com.dungeon.game.entity.furniture.SmallTable;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class Quarters extends Room {

	public Quarters(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		int doorX =  doorPos[0][0];
		int doorY = doorPos[0][1];
		
		//spawn stuff
		int x = 0,y = 0;
		
		entities.add(new Villager(world,1*Tile.TS,1*Tile.TS));
		
		addToOccupied(0, doorY);
		addToOccupied(1, doorY);
		addToOccupied(0, doorY+1);
		addToOccupied(0, doorY-1);
		 //spawn bed
		int bedOrientation = (int)(Math.random()*4);
		int bedX = 0, bedY = 0;
		if(bedOrientation == 0 && room.length < 3)bedOrientation = (int)(Math.random()*3+1);
		if(bedOrientation == 0){
			x = 0;
			do{
				y = (int) (Math.random()*room.length);
			}while(y == doorY || y - 1 == doorY || y + 1 == doorY);
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			x+=Tile.TS;
			y*=Tile.TS;
			y+=Tile.TS/2;
			addToOccupied(bedX, bedY);
			addToOccupied(bedX+1, bedY);
			addToOccupied(bedX+1, bedY+1);
			addToOccupied(bedX+1, bedY-1);
		}else if(bedOrientation == 1){
			x = (int) (Math.random()*room[0].length);
			if(doorY < 2){
				while (x < 2){
					x = (int) (Math.random()*room[0].length);
				}
			}
			y = 0;
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			x+=Tile.TS/2;
			y*=Tile.TS;
			y+=Tile.TS;
			addToOccupied(bedX, bedY);
			addToOccupied(bedX, bedY+1);
			addToOccupied(bedX+1, bedY+1);
			addToOccupied(bedX-1, bedY+1);
		}else if(bedOrientation == 2){
			x = room[0].length-1;
			y = (int) (Math.random()*room.length);
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			y*=Tile.TS;
			y+=Tile.TS/2;
			addToOccupied(bedX, bedY);
			addToOccupied(bedX-1, bedY);
			addToOccupied(bedX-1, bedY+1);
			addToOccupied(bedX-1, bedY-1);
		}else if(bedOrientation == 3){
			x = (int) (Math.random()*room[0].length);
			if(doorY > room.length - 3){
				while (x < 2){
					x = (int) (Math.random()*room[0].length);
				}
			}
			y = room.length-1;
			bedX = x;
			bedY = y;
			x*=Tile.TS;
			x+=Tile.TS/2;
			y*=Tile.TS;
			addToOccupied(bedX, bedY);
			addToOccupied(bedX, bedY-1);
			addToOccupied(bedX+1, bedY-1);
			addToOccupied(bedX-1, bedY-1);
		}
		if(bedOrientation == 0)bedOrientation = 3;
		else if(bedOrientation == 1)bedOrientation = 2;
		else if(bedOrientation == 2)bedOrientation = 1;
		else if(bedOrientation == 3)bedOrientation = 0;
		entities.add(new Bed(world, x, y, bedOrientation));
		
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
			if(potentialTableSpots.get(i)[0] < 0 || potentialTableSpots.get(i)[0] > room.length - 1 || potentialTableSpots.get(i)[1] < 0 || potentialTableSpots.get(i)[1] > room[0].length - 1 || (room[potentialTableSpots.get(i)[0]][potentialTableSpots.get(i)[1]] != null && room[potentialTableSpots.get(i)[0]][potentialTableSpots.get(i)[1]].id != 0)){
				potentialTableSpots.remove(i);
				i--;
			}
		}
		int tableSpot = (int) Math.random()*potentialTableSpots.size();
		
		entities.add(new SmallTable(world,potentialTableSpots.get(tableSpot)[1]*Tile.TS+Tile.TS/2,potentialTableSpots.get(tableSpot)[0]*Tile.TS+Tile.TS/2));
		
		addToOccupied(potentialTableSpots.get(tableSpot)[1],potentialTableSpots.get(tableSpot)[0]);
		
		//spawn dresser
		int attempts = 0;
		boolean placedDresser = false;
		int[] dresserPos = new int[]{0,0};
		int dresserOrientation = 0;
		do{
			attempts++;
			dresserOrientation = (int)(Math.random()*4);
			if(dresserOrientation == 0){
				for(int i = 0; i < room.length-1; i++){
					if(!checkOccupied(0, i)&&!checkOccupied(0, i+1)){
						placedDresser = true;
						dresserPos = new int[]{i,0};
						addToOccupied(dresserPos[1], dresserPos[0]);
						addToOccupied(0,i+1);
						break;
					}
				}
			}else if(dresserOrientation == 1){
				for(int i = 0; i < room[0].length-1; i++){
					if(!checkOccupied(i, 0)&&!checkOccupied(i+1, 0)){
						placedDresser = true;
						dresserPos = new int[]{0,i};
						addToOccupied(dresserPos[1], dresserPos[0]);
						addToOccupied(i+1, 0);
						break;
					}
				}
			}else if(dresserOrientation == 2){
				for(int i = 0; i < room.length-1; i++){
					if(!checkOccupied(room[0].length-1, i)&&!checkOccupied(room[0].length-1, i+1)){
						placedDresser = true;
						dresserPos = new int[]{i,room[0].length-1};
						addToOccupied(dresserPos[1], dresserPos[0]);
						addToOccupied(room[0].length-1, i+1);
						break;
					}
				}
			}else{
				for(int i = 0; i < room[0].length-1; i++){
					if(!checkOccupied(i, room.length-1)&&!checkOccupied(i+1, room.length-1)){
						placedDresser = true;
						dresserPos = new int[]{room.length-1,i};
						addToOccupied(dresserPos[1], dresserPos[0]);
						addToOccupied(i+1, room.length-1);
						break;
					}
				}
			}
		}while(!placedDresser&&attempts < 10);
		
		entities.add(new Dresser(world, (dresserPos[1]+(dresserOrientation%2==0?0.5f:1))*Tile.TS, (dresserPos[0]+(dresserOrientation%2==0?1:0.5f))*Tile.TS, dresserOrientation+1));
		room[dresserPos[0]][dresserPos[1]] = tileMap.getTile(5);
		if(dresserOrientation % 2 == 0)room[dresserPos[0]+1][dresserPos[1]] = tileMap.getTile(5);
		else room[dresserPos[0]][dresserPos[1]+1] = tileMap.getTile(5);
		
		//spawn Fireplace
		attempts = 0;
		boolean placedFirepalce = false;
		int[] fireplacePos = new int[]{0,0};
		int fireplaceOrientation = 0;
		do{
			attempts++;
			fireplaceOrientation = (int)(Math.random()*4);
			if(fireplaceOrientation == 0){
				for(int i = 1; i < room.length-1; i++){
					if(!checkOccupied(0, i)){
						placedFirepalce = true;
						fireplacePos = new int[]{i,0};
						addToOccupied(fireplacePos[1], fireplacePos[0]);
						fireplacePos[1]--;
						break;
					}
				}
			}else if(fireplaceOrientation == 1){
				for(int i = 1; i < room[0].length-1; i++){
					if(!checkOccupied(i, 0)){
						placedFirepalce = true;
						fireplacePos = new int[]{0,i};
						addToOccupied(fireplacePos[1], fireplacePos[0]);
						fireplacePos[0]--;
						break;
					}
				}
			}else if(fireplaceOrientation == 2){
				for(int i = 1; i < room.length-1; i++){
					if(!checkOccupied(room[0].length-1, i)){
						placedFirepalce = true;
						fireplacePos = new int[]{i,room[0].length-1};
						addToOccupied(fireplacePos[1], fireplacePos[0]);
						fireplacePos[1]++;
						break;
					}
				}
			}else{
				for(int i = 1; i < room[0].length-1; i++){
					if(!checkOccupied(i, room.length-1)){
						placedFirepalce = true;
						fireplacePos = new int[]{room.length-1,i};
						addToOccupied(fireplacePos[1], fireplacePos[0]);
						fireplacePos[0]++;
						break;
					}
				}
			}
		}while(!placedFirepalce&&attempts < 10);
		
		entities.add(new Fireplace(world, fireplacePos[1]*Tile.TS+Tile.TS/2, fireplacePos[0]*Tile.TS+Tile.TS/2, fireplaceOrientation == 0?3:fireplaceOrientation == 1?2:fireplaceOrientation == 2?1:0));
		
		//spawn Carpet
		entities.add(new Carpet(world,room[0].length/2f*Tile.TS,room.length/2f*Tile.TS,room[0].length*2-2,room.length*2-2, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));

	}

}
