package com.dungeon.game.textures.tiles;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class FirePlace extends ProceduralTile {

	public FirePlace(int seed, int x, int y, int rotation) {
		super(new int[]{seed, x, y, rotation});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int[] args) {
		seed = args[0];
		x = args[1];
		y = args[2];
		int rotation = args[3];
		Pixmap brickMap = new Brick(seed, x, y, (rotation == 0 || rotation == 2)?2:1, 0).texture.getTextureData().consumePixmap();
		
		Pixmap texMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		if(rotation == 0){
			
		}else if(rotation == 1){
			
		}else if(rotation == 2){
			
		}else if(rotation == 3){
			
		}
			
		brickMap.drawPixmap(texMap, 0, 0);
		
		texture = new Texture(texMap);

	}

}
