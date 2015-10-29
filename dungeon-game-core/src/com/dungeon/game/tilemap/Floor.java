package com.dungeon.game.tilemap;

import java.util.Arrays;

public class Floor {
	protected Tile[][] tm;
	
	private int width;
	private int height;
	
	public Floor(int width, int height) {
		this.width = width;
		this.height = height;
		
		tm = new Tile[height][width];
		
		//temp: remove once random generator has been created
		for(Tile[] y: tm) {
			for(Tile x: y) {
				x = new Tile();
			}
		}
	}

}
