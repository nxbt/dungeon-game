package com.dungeon.game.entity;

import com.dungeon.game.world.Tile;

public abstract class Enemy extends Dynamic {
	Pathfinder pathfinder;
	public Enemy(int x, int y) {
		super(x, y);
		pathfinder = new Pathfinder();
	}
	
	protected void findPath(Tile[][] map, float[] target){
		int[][] mapData = new int[map.length][map[0].length];
		for(int i = 0;i<map.length;i++){
			for(int k = 0;k<map[0].length;k++){
				mapData[i][k] = map[i][k].data;
			}
		}
		pathfinder.setMap(mapData);
		int[] targetTile = pathfinder.findPath(new int[]{(int) ((x+width/2)/Tile.TS), (int) ((y+height/2)/Tile.TS)},new int[]{(int) (target[0]/Tile.TS), (int) (target[1]/Tile.TS)});
		int targetX = targetTile[0]*Tile.TS;
		int targetY = targetTile[1]*Tile.TS;
		System.out.println(x+" vXs "+targetX);
		System.out.println(y+" vYs "+targetY);
		if(x+d_offx<targetX)inp_rt=true;
		if(x+d_offx>targetX)inp_lt=true;
		if(y+d_offy<targetY)inp_up=true;
		if(y+d_offy>targetY)inp_dn=true;
	}
}
