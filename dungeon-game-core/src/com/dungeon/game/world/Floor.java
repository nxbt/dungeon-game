package com.dungeon.game.world;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dungeon.game.entity.Entity;

public class Floor {
	private static final String DEFAULT = "Tilemap.png";
	
	private TextureRegion[] spritesheet;
	
	public Tile[][] tm;
	
	public ArrayList<Entity> entities;
	
	private int width;
	private int height;
	
	public Floor(int width, int height) {
		this.width = width;
		this.height = height;
		
		entities = new ArrayList<Entity>();
		
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
		
		fixBleeding(spritesheet);
		
		//temp: remove once random generator has been created
		for(int i = 0;i<tm.length;i++){
			for(int k = 0;k<tm[i].length;k++){
				if(i==0||k==0||i==tm.length-1||k==tm[i].length-1)tm[i][k] = new Tile(spritesheet,2);
				else tm[i][k] = new Tile(spritesheet,(int)(Math.random()*3));
			}
		}
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		int startHeight = (int) (y-Gdx.graphics.getHeight()/2)/Tile.TS;
		int endHeight = (int)(y+Gdx.graphics.getHeight()/2)/Tile.TS+1;
		int startWidth = (int) (x-Gdx.graphics.getWidth()/2)/Tile.TS-1;
		int endWidth = (int)(x+Gdx.graphics.getWidth()/2)/Tile.TS+1;
		
		startHeight = Math.max(startHeight,0);
		endHeight = Math.min(endHeight,tm.length);
		startWidth = Math.max(startWidth,0);
		endWidth = Math.min(endWidth,tm[0].length);
		
		for(int i = startHeight; i < endHeight; i++){
			for(int k = startWidth; k < endWidth; k++){
				batch.draw(tm[i][k].texture, k*Tile.TS, i*Tile.TS);
			}
		}
	}

	public static void fixBleeding(TextureRegion[] region) {
	        for (TextureRegion texture : region) {
	            fixBleeding(texture);
	        }
	}

	public static void fixBleeding(TextureRegion region) {
	    float fix = 0.01f;

	    float x = region.getRegionX();
	    float y = region.getRegionY();
	    float width = region.getRegionWidth();
	    float height = region.getRegionHeight();
	    float invTexWidth = 1f / region.getTexture().getWidth();
	    float invTexHeight = 1f / region.getTexture().getHeight();
	    region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims
	                                                                                                                                                // region
	}
}
