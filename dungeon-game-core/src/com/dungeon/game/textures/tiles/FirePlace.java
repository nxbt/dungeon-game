package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;
import com.dungeon.game.utilities.Spritesheet;

public class FirePlace extends ProceduralTile {
	
	private Pixmap outLineMap;
	private int rotation;
	private int timer;

	public FirePlace(int seed, int x, int y, int rotation) {
		super(new int[]{seed, x, y, rotation});
	}

	@Override
	public void generateTexture(int[] args) {
		timer = 0;
		seed = args[0];
		x = args[1];
		y = args[2];
		rotation = args[3];
		outLineMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		Color activeColor;
		for(int i = 4; i < 28; i++){
			for(int k = 4; k < 6; k++){
				activeColor = new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1);
				float num = MathUtils.noise2d(seed, x*32+i, y*32+i, 1);
				outLineMap.setColor(new Color(activeColor.r*0.95f + num*0.05f, activeColor.g, activeColor.b, 1));
				outLineMap.setColor(activeColor);
				outLineMap.drawPixel(i, k);
			}
			for(int k = 30; k < 32; k++){
				activeColor = new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1);
				float num = MathUtils.noise2d(seed, x*32+i, y*32+i, 1);
				outLineMap.setColor(new Color(activeColor.r*0.95f + num*0.05f, activeColor.g, activeColor.b, 1));
				outLineMap.setColor(activeColor);
				outLineMap.drawPixel(i, k);
			}
			if(i == 4 || i == 5 || i == 26 || i == 27){
				for(int k = 4; k < 32; k++){
					activeColor = new Color(Brick.brickColor.r*0.8f, Brick.brickColor.g*0.8f, Brick.brickColor.b*0.8f, 1);
					float num = MathUtils.noise2d(seed, x*32+i, y*32+i, 1);
					outLineMap.setColor(new Color(activeColor.r*0.95f + num*0.05f, activeColor.g, activeColor.b, 1));
					outLineMap.setColor(activeColor);
					outLineMap.drawPixel(i, k);
				}
			}
			if(i >= 6 && i <= 25){
				for(int k = 6; k < 30; k++){
					outLineMap.setColor(new Color(0,0,0,0.5f));
					outLineMap.drawPixel(i, k);
				}
			}
		}
		drawFire();

	}
	
	public void drawFire(){
		Pixmap fireMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		int curX;
		int curY;
		for(int i = 6; i < 26; i++){
			for(int k = 2; k < 26; k++){
				curX = x*32 + i;
				curY = y*32 + k;
				float num = (0.8f+0.2f*MathUtils.perturbedSinNoise3d(seed, curX, curY, timer, 5, 5, 40))*(32-k)/32;
//				if(num > 0.5f){
					fireMap.setColor(new Color(num, 0, 0, num));
					fireMap.drawPixel(i, 31 - k);
//				}
//				float num = MathUtils.perturbedSinNoise3d(seed, curX, curY, timer, 4, 3, 20);
//				fireMap.setColor(new Color(1*num,1*num,1*num,1));
			}
		}
		Pixmap texMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		texMap.drawPixmap(outLineMap, 0, 0);
		texMap.drawPixmap(fireMap, 0, 0);
		texMap = Spritesheet.rotatePixmap(texMap, rotation);
		texture = new Texture(texMap);
		texMap.dispose();
		timer+=2;
	}
	
	public void update(){
		drawFire();
	}

}
