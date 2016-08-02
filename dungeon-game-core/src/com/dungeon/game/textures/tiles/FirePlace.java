package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;
import com.dungeon.game.utilities.Spritesheet;

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
		Pixmap texMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		Color activeColor;
		for(int i = 4; i < 28; i++){
			for(int k = 4; k < 6; k++){
				activeColor = new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1);
				float num = MathUtils.noise(seed, x*32+i, y*32+i, 1);
				texMap.setColor(new Color(activeColor.r*0.95f + num*0.05f, activeColor.g, activeColor.b, 1));
				texMap.setColor(activeColor);
				texMap.drawPixel(i, k);
			}
			for(int k = 30; k < 32; k++){
				activeColor = new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1);
				float num = MathUtils.noise(seed, x*32+i, y*32+i, 1);
				texMap.setColor(new Color(activeColor.r*0.95f + num*0.05f, activeColor.g, activeColor.b, 1));
				texMap.setColor(activeColor);
				texMap.drawPixel(i, k);
			}
			if(i == 4 || i == 5 || i == 26 || i == 27){
				for(int k = 4; k < 32; k++){
					activeColor = new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1);
					float num = MathUtils.noise(seed, x*32+i, y*32+i, 1);
					texMap.setColor(new Color(activeColor.r*0.95f + num*0.05f, activeColor.g, activeColor.b, 1));
					texMap.setColor(activeColor);
					texMap.drawPixel(i, k);
				}
			}
			if(i >= 6 && i <= 25){
				for(int k = 6; k < 30; k++){
					texMap.setColor(new Color(0,0,0,0.5f));
					texMap.drawPixel(i, k);
				}
			}
		}
		drawFire(texMap, rotation);
		texMap = Spritesheet.rotatePixmap(texMap, rotation);
		texture = new Texture(texMap);

	}
	
	public void drawFire(Pixmap texMap, int rotation){
		Pixmap fireMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		Random rand = MathUtils.getRandomFromSeedAndCords(seed, x, y);
		int curX;
		int curY;
		for(int i = 6; i < 26; i++){
			for(int k = 2; k < 26; k++){
				curX = x*32 + i;
				curY = y*32 + k;
				float num = 0.8f+0.2f*MathUtils.perturbedSinNoise(seed, curX, curY, 4, 3, 20);
				fireMap.setColor(new Color(1, 0, 0, 0.3f+0.7f*num*(32-k)/32));
				fireMap.drawPixel(i, 31 - k);
			}
		}
		texMap.drawPixmap(fireMap, 0, 0);
	}

}
