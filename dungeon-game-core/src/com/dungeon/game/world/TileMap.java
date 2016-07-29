package com.dungeon.game.world;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.Spritesheet;

public class TileMap {
	public Tile[] tiles;
	public String fileName;
	
	public TileMap(String fileName){
		this.fileName = fileName;
		createTiles();
	}
	
	public void createTiles(){
		Texture[] texturesTemp = Spritesheet.getSprites(fileName, 32, 32);
		Texture[][] textures = new Texture[texturesTemp.length][8];
		for(int i = 0; i < texturesTemp.length; i++){
			textures[i][0] = texturesTemp[i];
			
			if(!texturesTemp[i].getTextureData().isPrepared()) texturesTemp[i].getTextureData().prepare();
			Pixmap tempMap = texturesTemp[i].getTextureData().consumePixmap();
			for(int k = 1; k<4; k++){
				
				textures[i][k] = new Texture(Spritesheet.rotatePixmap(tempMap, k));
			}
			
			for(int k = 4; k<8; k++){
				
				textures[i][k] = new Texture(Spritesheet.rotatePixmap(Spritesheet.flipPixmap(tempMap), k==4?3:k-5));
			}
			
			
			tempMap.dispose();
		}
		tiles = new Tile[textures.length];
		for(int i = 0; i < textures.length; i++){
			tiles[i] = new Tile(textures[i], i);
		}
		
	}
	
	public Tile getTile(int id){
		return tiles[id].clone(0,false);
	}
	
	public Tile getTile(int id, int rotation, boolean flip){
		return tiles[id].clone(rotation,flip);
	}
}
