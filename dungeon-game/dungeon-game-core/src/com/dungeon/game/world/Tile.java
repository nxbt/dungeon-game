package com.dungeon.game.world;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Tile implements Cloneable{
	public static final int TS = 32;
	public int id;
	public int data;
	public boolean flip;
	public int rotation;
	public Texture[] textures;
	public static final int[] SOLIDS = new int[]{1,10,11,12,13,14,15};
	
	public Tile(Texture[] spritesheet, int id) {
		this.id = id;
		data = (isSolid(id)) ? 1:0;
		rotation = 0;
		
		textures = spritesheet;
	}
	
	public Tile(Texture[] spritesheet, int id, int rotation, boolean flip) {
		this.id = id;
		data = (isSolid(id)) ? 1:0;
		
		this.rotation = rotation;
		this.flip = flip;
		
		textures = spritesheet;
	}
	
	public static boolean isSolid(int id){
		for(int i = 0; i < SOLIDS.length; i++) if(SOLIDS[i] == id) return true;
		return false;
	}
	
	public static boolean isSolid(Tile tile){
		return isSolid(tile.id);
	}
	
	public Tile clone(int rotation, boolean flip){
		Tile newTile;
		try {
			newTile = (Tile) super.clone();
			newTile.textures = new Texture[textures.length];
			for(int i = 0 ; i < textures.length; i++){
				if(!textures[i].getTextureData().isPrepared())textures[i].getTextureData().prepare();
				Pixmap temp = textures[i].getTextureData().consumePixmap(); //cloneing the textures allowes us to customize the tex of each tile, but increases loading time and storage. worth?
				newTile.textures[i] = new Texture(temp);
			}
			newTile.rotation = rotation;
			newTile.flip = flip;
			return newTile;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
