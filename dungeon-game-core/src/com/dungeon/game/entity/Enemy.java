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
		pathfinder.findPath(new int[]{(int) (x/Tile.TS), (int) (y/Tile.TS)},new int[]{(int) (target[0]/Tile.TS), (int) (target[1]/Tile.TS)});
	}
}
