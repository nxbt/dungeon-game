package com.dungeon.game.textures;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class dirt extends proceduralTile {

	public dirt(int seed, int x, int y) {
		super(seed, x, y, new int[]{seed, x, y});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int args[]) {
		seed = args[0];
		x = args[1];
		y = args[2];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		
		int curX;
		int curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				Random ran = new Random(1+seed*curX*curY);
				Random ran1 = new Random(1+seed*(curX-1)*curY);
				Random ran2 = new Random(1+seed*curX*(curY-1));
				Random ran3 = new Random(1+seed*(curX+1)*curY);
				Random ran4 = new Random(1+seed*curX*(curY+1));
				float num1 = (ran.nextFloat()+ran1.nextFloat()+ran2.nextFloat()+ran3.nextFloat()+ran4.nextFloat())/5;
				float num2 = (ran.nextFloat()+ran1.nextFloat()+ran2.nextFloat()+ran3.nextFloat()+ran4.nextFloat())/5;
				float num3 = (ran.nextFloat()+ran1.nextFloat()+ran2.nextFloat()+ran3.nextFloat()+ran4.nextFloat())/5;
				texMap.setColor(new Color((100f + num1*60f) / 255f,(50f + num2*20) / 255f,(10f + num3*20) / 255f, 1));
				//check for dark dirt
				int darkDirtRadius = (int) (Math.random()*5f);
				//well i did SOMETHING good here.... not stones but it'll be usefull haja
				loop:
				for(int j = -darkDirtRadius; j < darkDirtRadius; j++){
					for(int l = -darkDirtRadius; l < darkDirtRadius; l++){
						if(new Random(1+seed*(curX+j)*(curY+l)).nextFloat() < 0.03f){
							texMap.setColor(new Color((90f + num1*30f) / 255f,(40f + num2*10) / 255f,(5f + num3*10) / 255f, 1));
							break loop;
						}
					}
				}
				
				//generate stones... somehow...
				texMap.drawPixel(i, k);
			}
		}
		texture = new Texture(texMap);
	}
	

}
