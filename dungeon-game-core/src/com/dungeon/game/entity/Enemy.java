package com.dungeon.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.pathing.Pathfinder;
import com.dungeon.game.world.Tile;

public abstract class Enemy extends Dynamic {
	Pathfinder pathfinder;
	public Enemy(int x, int y) {
		super(x, y);
		pathfinder = new Pathfinder();
	}
	
	protected void findPath(Tile[][] map, ArrayList<Entity> entities, float[] target){
		int[][] mapData = new int[map.length][map[0].length];
		for(int i = 0;i<map.length;i++){
			for(int k = 0;k<map[0].length;k++){
				mapData[i][k] = map[i][k].data;
				Rectangle tile = new Rectangle(k*Tile.TS,i*Tile.TS,Tile.TS,Tile.TS);
				Rectangle entity;
				for(Entity e: entities){
					if(e.solid){
						entity = new Rectangle(e.x,e.y,e.width,e.height);
						if(tile.overlaps(entity))mapData[i][k]=1;
					}
				}
			}
		}
		pathfinder.setData(mapData,width,height);
		int[] targetTile = pathfinder.findPath(new int[]{(int) ((x+width/2)/Tile.TS), (int) ((y+height/2)/Tile.TS)},new int[]{(int) (target[0]/Tile.TS), (int) (target[1]/Tile.TS)},x,y);
		if(targetTile!=null){
			int targetX = targetTile[0]*Tile.TS;
			int targetY = targetTile[1]*Tile.TS;
			
			boolean inp_rt = false;
			boolean inp_lt = false;
			boolean inp_up = false;
			boolean inp_dn = false;
			
			if(x+d_offx<targetX)inp_rt=true;
			if(x+d_offx>targetX)inp_lt=true;
			if(y+d_offy<targetY)inp_up=true;
			if(y+d_offy>targetY)inp_dn=true;
			
			if(inp_up && inp_rt) move_angle = 45;
			else if(inp_up && inp_lt) move_angle = 135;
			else if(inp_dn && inp_rt) move_angle = -45;
			else if(inp_dn && inp_lt) move_angle = -135;
			else if(inp_up) move_angle = 90;
			else if(inp_dn) move_angle = -90;
			else if(inp_rt) move_angle = 0;
			else if(inp_lt) move_angle = 180;
		}
	}
}
