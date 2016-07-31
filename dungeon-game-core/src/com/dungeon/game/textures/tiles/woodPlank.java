package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class woodPlank extends proceduralTile {

	public woodPlank(int seed, int x, int y) {
		super(new int[]{seed, x, y});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int[] args) {
		seed = args[0];
		x = args[1];
		y = args[2];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		
		//variables
		Random seedConstant = new Random(seed);
		Color woodColor = seedConstant.nextFloat() > 0.5? new Color(102f / 255f, 51f / 255f, 0f / 255f,1): new Color(94f / 255f, 15f / 255f, 18f / 255f,1);
		int curX;
		int curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				Random rand = getRandomFromSeedAndX(seed,(int)(curX/8));
				if((curX+1)%8==0 || (curX)%8==0 || (curY + rand.nextInt(32)) % 32 <= 1)texMap.setColor(new Color(woodColor.r*0.7f, woodColor.g*0.7f, woodColor.b*0.7f,1));
				else texMap.setColor(woodColor);
				texMap.drawPixel(i, 31-k);
			}
		}

		texture = new Texture(texMap);
	}

}
