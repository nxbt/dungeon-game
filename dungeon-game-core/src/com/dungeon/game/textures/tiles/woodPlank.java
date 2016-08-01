package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class WoodPlank extends ProceduralTile {

	public WoodPlank(int seed, int x, int y) {
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
		seedConstant.nextFloat();
		Color woodColor = new Color(102f / 255f, 51f / 255f, 0f / 255f,1);
		int curX;
		int curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
				Random rand = MathUtils.getRandomFromSeedAndX(seed,(int)(curX/8));
				int yOffset = rand.nextInt(32);
				int barrierZone = (int)((16f+curY+yOffset)/32f);
				int barrier = (int) (barrierZone*32f-8f+16f*MathUtils.getRandomFromSeedAndY(seed*(int)(curX/8),barrierZone).nextFloat());
				if((curX)%8==0 || (curX+1)%8==0 || curY+yOffset == barrier || curY+yOffset - 1 == barrier){
					float num = MathUtils.noise(seed, curX, curY, 2);
					texMap.setColor(new Color(woodColor.r*0.77f+num*0.03f, woodColor.g*0.77f+num*0.03f, woodColor.b*0.77f+num*0.03f, 1));
				}
				else{
					Color activeColor;
					float num;
					if(curY+yOffset > barrier){
						Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), 1+barrierZone);
						float numPlank = rand1.nextFloat();
						activeColor = new Color(woodColor.r*0.9f + numPlank*0.1f, woodColor.g*0.9f + numPlank*0.1f, woodColor.b*0.9f + numPlank*0.1f, 1);
						num = MathUtils.perturbedSinNoise(MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), (1+barrierZone)).nextInt(1000), curX, curY, 4, 3, 20);
					}else{
						Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), barrierZone);
						float numPlank = rand1.nextFloat();
						activeColor = new Color(woodColor.r*0.9f + numPlank*0.1f, woodColor.g*0.9f + numPlank*0.1f, woodColor.b*0.9f + numPlank*0.1f, 1);
						num = MathUtils.perturbedSinNoise(MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), (barrierZone)).nextInt(1000), curX, curY, 4, 3, 20);
					}
					texMap.setColor(new Color(activeColor.r*0.9f+num*0.1f, activeColor.g*0.9f+num*0.1f, activeColor.b*0.9f+num*0.1f, 1));
				}
				texMap.drawPixel(i, 31-k);
			}
		}

		texture = new Texture(texMap);
	}

}
