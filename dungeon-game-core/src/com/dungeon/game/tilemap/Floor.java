package com.dungeon.game.tilemap;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Texture;

public class Floor {
	private static final String DEFAULT = "Tilemap.png";
	
	private Texture texMap;
	public Tile[][] tm;
	
	private int width;
	private int height;
	
	public Floor(int width, int height) {
		texMap = new Texture(DEFAULT);
		this.width = width;
		this.height = height;
		
		tm = new Tile[height][width];
		
		//temp: remove once random generator has been created
		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++){
				tm[i][k] = new Tile(texMap,0);
			}
		}
	}
	public void update(){
		
	}

}
