package com.dungeon.game.tilemap;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Floor {
	private static final String DEFAULT = "Tilemap.png";
	
	private TextureRegion[] spritesheet;
	
	public Tile[][] tm;
	
	private int width;
	private int height;
	
	public Floor(int width, int height) {
		this.width = width;
		this.height = height;
		
		tm = new Tile[height][width];
		
		Texture tempsheet = new Texture(DEFAULT);
		
		int sheetWidth = tempsheet.getWidth()/Tile.TS;
		int sheetHeight = tempsheet.getHeight()/Tile.TS;
		
		spritesheet = new TextureRegion[sheetWidth * sheetHeight];
		
		for(int i = 0; i < sheetHeight; i++) {
			for(int k = 0; k < sheetWidth; k++) {
				spritesheet[i*sheetWidth+k] = new TextureRegion(new Texture(DEFAULT),k*Tile.TS,i*Tile.TS,Tile.TS,Tile.TS);
			}
		}
		

		//temp: remove once random generator has been created
		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++){
				tm[i][k] = new Tile(spritesheet,(int)(Math.random()*2));
				
			}
		}
		
		
	}
	public void update(){
		
	}

}
