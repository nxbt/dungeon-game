package com.dungeon.game.textures.tiles;

import com.badlogic.gdx.graphics.Color;
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
		if(rotation == 0){ // need to add the noise from seed!
			for(int i = 4; i < 28; i++){
				for(int k = 4; k < 6; k++){
					texMap.setColor(new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1));
					texMap.drawPixel(i, k);
				}
				for(int k = 30; k < 32; k++){
					texMap.setColor(new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1));
					texMap.drawPixel(i, k);
				}
				if(i == 4 || i == 5 || i == 26 || i == 27){
					for(int k = 4; k < 32; k++){
						texMap.setColor(new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1));
						texMap.drawPixel(i, k);
					}
				}
			}
		}else if(rotation == 1){
			
		}else if(rotation == 2){
			
		}else if(rotation == 3){
			
		}
			
		brickMap.drawPixmap(texMap, 0, 0);
		
		texture = new Texture(brickMap);

	}

}
