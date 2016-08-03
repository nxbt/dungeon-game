package com.dungeon.game.textures.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class FloorTile extends ProceduralTile {

	public FloorTile(int seed, int x, int y) {
		super(new int[]{seed, x, y});
	}

	@Override
	public void generateTexture(int[] args) { //randomize color and maybe make tiles smaller?
		seed = args[0];
		x = args[1];
		y = args[2];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		Color mortarColor = new Color(150f / 255f, 150f / 255f, 150f / 255f, 1);
		Color tileColor = new Color(218f / 255f, 214f / 255f, 207f / 255f, 1);
		float angle = (float) (2f*MathUtils.getRandomFromSeedAndCords(seed, x, y).nextFloat()*Math.PI);
//		float angle = (float) 0.7f;
		float curX, curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x*32+i;
				curY = y*32+k;
		        float newCurX = 100000f+(float) (curX*Math.cos(angle) - curY*Math.sin(angle));//suppper confused as to why i must add 1000000???
		        float newCurY = 100000f+(float) (curX*Math.sin(angle) + curY*Math.cos(angle));
		        if(i == 31 || k == 31){
		        	float num = MathUtils.noise2d(seed, newCurX, newCurY, 1);
					texMap.setColor(new Color(mortarColor.r*(0.9f+num*0.1f), mortarColor.g*(0.9f+num*0.1f), mortarColor.b*(0.9f+num*0.1f), 1));
				}else if(i == 0 || k == 0){
		        	float num = MathUtils.noise2d(seed, newCurX, newCurY, 1);
				texMap.setColor(new Color(mortarColor.r*0.8f*(0.9f+num*0.1f), mortarColor.g*0.8f*(0.9f+num*0.1f), mortarColor.b*0.8f*(0.9f+num*0.1f), 1));
				}else{
					float num = (0.7f+ MathUtils.noise2d(seed, newCurX, newCurY, 2)*0.3f)*MathUtils.perturbedSinNoise2d(MathUtils.getRandomFromSeedAndCords(seed, x, y).nextInt(1000), newCurX, newCurY, 10, 6, 100);
					texMap.setColor(new Color(tileColor.r*(0.5f+0.5f*num), tileColor.g*(0.5f+0.5f*num), tileColor.b*(0.5f+0.5f*num), 1));
				}
				
				texMap.drawPixel(i, 31-k);
			}
		}
		
		texture = new Texture(texMap);

	}

}
