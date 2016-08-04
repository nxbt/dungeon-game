package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class Brick extends ProceduralTile {
	
	private static final Color[] brickColors = new Color[]{
			new Color(133f / 255f, 5f / 255f, 12f / 255f,1),
			new Color(66f / 255f, 66f / 255f, 66f / 255f,1),
			new Color(227f / 255f, 152f / 255f, 51f / 255f,1)

			
	};

	public Brick(int seed, int x, int y, int sides, int corners) {
		super(new int[]{seed, x, y, sides, corners});
	}

	@Override
	public void generateTexture(int[] args) { //have to find a fix for the 1 pixel mortar
		seed = args[0];
		x = args[1];
		y = args[2];
		Pixmap texMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		
		//variables
		int sides = args[3];
		int corners = args[4];
		
		int curX;
		int curY;
		
		if((sides == 1 && (corners == 10 || corners == 9 || corners == 12 || corners == 4 || corners == 8 || corners == 5 || corners == 7 || corners == 11 || corners == 14 || corners == 13 || corners ==6)) || (sides == 0 && corners == 12)){
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(i < 17){
						verticalBrick(i, k, curX, curY, texMap);
					}else if(i < 25 && (k < 7 || k > 24)){
						verticalBrick(i, k, curX, curY, texMap);
					}else{
						horizonBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if((sides == 2 && (corners == 3||corners == 6||corners == 10||corners == 8||corners == 2||corners == 12||corners == 13||corners == 7 || corners == 11 || corners == 14 || corners == 9)) || (sides == 0 && corners == 10)){
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(k < 17){
						horizonBrick(i, k, curX, curY, texMap);
					}else if(k < 25 && (i < 7 || i > 24)){
						horizonBrick(i, k, curX, curY, texMap);
					}else{
						verticalBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if((sides == 4 && (corners == 5||corners == 9||corners == 3||corners == 2||corners == 1||corners == 10||corners == 14||corners == 13 || corners == 7 || corners == 11 || corners == 6)) || (sides == 0 && corners == 3)){
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(i > 14){
						verticalBrick(i, k, curX, curY, texMap);
					}else if(i > 6 && (k < 7 || k > 24)){
						verticalBrick(i, k, curX, curY, texMap);
					}else{
						horizonBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if(sides == 8 && ((corners == 12||corners == 6||corners == 5||corners == 1||corners == 4||corners == 3||corners == 11||corners == 14 || corners == 13 || corners == 7 || corners == 9)) || ((sides == 0 && corners == 5))){
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(k > 14){
						horizonBrick(i, k, curX, curY, texMap);
					}else if(k > 6 && (i < 7 || i > 24)){
						horizonBrick(i, k, curX, curY, texMap);
					}else{
						verticalBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if(sides == 1 || sides == 4 || sides == 5) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					verticalBrick(i, k, curX, curY, texMap);
				}
			}
		}
		else if(sides == 2 || sides == 8 || sides == 10) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					horizonBrick(i, k, curX, curY, texMap);
				}
			}
		}
		else if(sides == 14) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(i > 22 && k == 0 || k == 31){
						mortar(i, k, curX, curY, texMap);
					}else if(i > 22){
						verticalBrick(i, k, curX, curY, texMap);
					}else{
						horizonBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if(sides == 13) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(k > 22 && i == 0 || i == 31){
						mortar(i, k, curX, curY, texMap);
					}else if(k > 22){
						horizonBrick(i, k, curX, curY, texMap);
					}else{
						verticalBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if(sides == 11) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(i < 9 && k == 0 || k == 31){
						mortar(i, k, curX, curY, texMap);
					}else if(i < 9){
						verticalBrick(i, k, curX, curY, texMap);
					}else{
						horizonBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if(sides == 7) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(k < 9 && i == 0 || i == 31){
						mortar(i, k, curX, curY, texMap);
					}else if(k < 9){
						horizonBrick(i, k, curX, curY, texMap);
					}else{
						verticalBrick(i, k, curX, curY, texMap);
					}
				}
			}
		}
		else if(sides == 3 || corners == 8) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(MathUtils.getRandomFromSeedAndCords(seed, x, y).nextFloat() > 0.5f){
						if((i < 9 && k == 0) || (k < 9 && i == 0)){
							mortar(i, k, curX, curY, texMap);
						}else if(i < 9){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 9){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 17){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 17){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 25){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 25){
							horizonBrick(i, k, curX, curY, texMap);
						}else{
							verticalBrick(i, k, curX, curY, texMap);
						}
					}else{
						if((i < 9 && k == 0) || (k < 9 && i == 0)){
							mortar(i, k, curX, curY, texMap);
						}else if(k < 9){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 9){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 17){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 17){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 25){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 25){
							verticalBrick(i, k, curX, curY, texMap);
						}else{
							horizonBrick(i, k, curX, curY, texMap);
						}
					}
				}
			}
		}
		else if(sides == 6 || corners == 2) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(MathUtils.getRandomFromSeedAndCords(seed, x, y).nextFloat() > 0.5f){
						if((i > 22 && k == 0) || (k < 9 && i == 31)){
							mortar(i, k, curX, curY, texMap);
						}else if(i > 22){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 9){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 14){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 17){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i  > 6){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 25){
							horizonBrick(i, k, curX, curY, texMap);
						}else{
							verticalBrick(i, k, curX, curY, texMap);
						}
					}else{
						if((i > 22 && k == 0) || (k < 9 && i == 31)){
							mortar(i, k, curX, curY, texMap);
						}else if(k < 9){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 22){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 17){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 14){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k < 25){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 6){
							verticalBrick(i, k, curX, curY, texMap);
						}else{
							horizonBrick(i, k, curX, curY, texMap);
						}
					}
				}
			}
		}
		else if(sides == 9 || corners == 4) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(MathUtils.getRandomFromSeedAndCords(seed, x, y).nextFloat() > 0.5f){
						if((i < 9 && k == 31) || (k > 22 && i == 0)){
							mortar(i, k, curX, curY, texMap);
						}else if(i < 9){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 22){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 17){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 14){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 25){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 6){
							horizonBrick(i, k, curX, curY, texMap);
						}else{
							verticalBrick(i, k, curX, curY, texMap);
						}
					}else{
						if((i < 9 && k == 31) || (k > 22 && i == 0)){
							mortar(i, k, curX, curY, texMap);
						}else if(k > 22){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 9){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 14){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 17){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 6){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i < 25){
							verticalBrick(i, k, curX, curY, texMap);
						}else{
							horizonBrick(i, k, curX, curY, texMap);
						}
					}
				}
			}
		}
		else if(sides == 12 || corners == 1) {
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(MathUtils.getRandomFromSeedAndCords(seed, x, y).nextFloat() > 0.5f){
						if((i > 22 && k == 31) || (k > 22 && i == 31)){
							mortar(i, k, curX, curY, texMap);
						}else if(i > 22){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 22){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 14){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 14){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 6){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 6){
							horizonBrick(i, k, curX, curY, texMap);
						}else{
							verticalBrick(i, k, curX, curY, texMap);
						}
					}else{
						if((i > 22 && k == 31) || (k > 22 && i == 31)){
							mortar(i, k, curX, curY, texMap);
						}else if(k > 22){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 22){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 14){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 14){
							verticalBrick(i, k, curX, curY, texMap);
						}else if(k > 6){
							horizonBrick(i, k, curX, curY, texMap);
						}else if(i > 6){
							verticalBrick(i, k, curX, curY, texMap);
						}else{
							horizonBrick(i, k, curX, curY, texMap);
						}
					}
				}
			}
		}
		else if(sides == 0 && (corners == 6 || corners == 9 || corners == 15 || corners == 14 || corners == 13 || corners == 11 || corners == 7)){
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					Random rand = MathUtils.getRandomFromSeedAndCords(seed, x, y);
					if(i < 16 && k < 16){ //bottom left corner
						if(rand.nextFloat() > 0.5f){
							if(i > 6){
								verticalBrick(i, k, curX, curY, texMap);
							}else if(k > 6){
								horizonBrick(i, k, curX, curY, texMap);
							}else{
								verticalBrick(i, k, curX, curY, texMap);
							}
						}else{
							if(k > 6){
								horizonBrick(i, k, curX, curY, texMap);
							}else if(i > 6){
								verticalBrick(i, k, curX, curY, texMap);
							}else{
								horizonBrick(i, k, curX, curY, texMap);
							}
						}
						
					}
					else if(i < 16 && k >= 16){ //top left corner
						if(rand.nextFloat() > 0.5f){
							if(i > 6){
								verticalBrick(i, k, curX, curY, texMap);
							}else if(k < 25){
								horizonBrick(i, k, curX, curY, texMap);
							}else{
								verticalBrick(i, k, curX, curY, texMap);
							}
						}else{
							if(k < 25){
								horizonBrick(i, k, curX, curY, texMap);
							}else if(i > 6){
								verticalBrick(i, k, curX, curY, texMap);
							}else{
								horizonBrick(i, k, curX, curY, texMap);
							}
						}
						
					}
					else if(i >= 16 && k < 16){ //bottom right corner
						if(rand.nextFloat() > 0.5f){
							if(i < 25){
								verticalBrick(i, k, curX, curY, texMap);
							}else if(k > 6){
								horizonBrick(i, k, curX, curY, texMap);
							}else{
								verticalBrick(i, k, curX, curY, texMap);
							}
						}else{
							if(k > 6){
								horizonBrick(i, k, curX, curY, texMap);
							}else if(i < 25){
								verticalBrick(i, k, curX, curY, texMap);
							}else{
								horizonBrick(i, k, curX, curY, texMap);
							}
						}
						
					}
					else if(i >= 16 && k >= 16){ //top right corner
						if(rand.nextFloat() > 0.5f){
							if(i < 25){
								verticalBrick(i, k, curX, curY, texMap);
							}else if(k < 25){
								horizonBrick(i, k, curX, curY, texMap);
							}else{
								verticalBrick(i, k, curX, curY, texMap);
							}
						}else{
							if(k < 25){
								horizonBrick(i, k, curX, curY, texMap);
							}else if(i < 25){
								verticalBrick(i, k, curX, curY, texMap);
							}else{
								horizonBrick(i, k, curX, curY, texMap);
							}
						}
						
					}
				}
			}
		}
		
		texture = new Texture(texMap);
	}
	
	private void verticalBrick(int x, int y, int curX, int curY, Pixmap texMap){
		Color activeColor;
		Random rand = MathUtils.getRandomFromSeedAndX(seed,(int)(curX/8));
		int yOffset = rand.nextInt(16);
		int barrierZone = (int)((8f+curY+yOffset)/16f);
		int barrier = (int) (barrierZone*16f-4f+8f*MathUtils.getRandomFromSeedAndY(seed*(int)(curX/8),barrierZone).nextFloat());
		if(curX%8 == 0 || (curX+1)%8 == 0 || curY+yOffset == barrier || curY+yOffset - 1 == barrier)activeColor = new Color(getColor(seed).r*0.8f, getColor(seed).g*0.8f, getColor(seed).b*0.8f, 1);
		else if(curY+yOffset > barrier){
			Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), 1+barrierZone);
			float num = rand1.nextFloat();
			activeColor = new Color(getColor(seed).r*0.8f + num*0.2f, getColor(seed).g*0.8f + num*0.2f, getColor(seed).b*0.8f + num*0.2f, 1);
		}else{
			Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curX/8), barrierZone);
			float num = rand1.nextFloat();
			activeColor = new Color(getColor(seed).r*0.8f + num*0.2f, getColor(seed).g*0.8f + num*0.2f, getColor(seed).b*0.8f + num*0.2f, 1);
		}
		texMap.setColor(addNoise(activeColor, curX, curY));
		texMap.drawPixel(x, 31-y);
	}
	
	private void horizonBrick(int x, int y, int curX, int curY, Pixmap texMap){
		Color activeColor;
		Random rand = MathUtils.getRandomFromSeedAndY(seed,(int)(curY/8));
		int xOffset = rand.nextInt(16);
		int barrierZone = (int)((8f+curX+xOffset)/16f);
		int barrier = (int) (barrierZone*16f-4f+8f*MathUtils.getRandomFromSeedAndY(seed*(int)(curY/8),barrierZone).nextFloat());
		if(curY%8 == 0 || (curY+1)%8 == 0 || curX+xOffset == barrier || curX+xOffset - 1 == barrier)activeColor = new Color(getColor(seed).r*0.8f, getColor(seed).g*0.8f, getColor(seed).b*0.8f, 1);
		else if(curX+xOffset > barrier){
			Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curY/8), 1+barrierZone);
			float num = rand1.nextFloat();
			activeColor = new Color(getColor(seed).r*0.8f + num*0.2f, getColor(seed).g*0.8f + num*0.2f, getColor(seed).b*0.8f + num*0.2f, 1);
		}else{
			Random rand1 = MathUtils.getRandomFromSeedAndCords(seed, (int)(curY/8), barrierZone);
			float num = rand1.nextFloat();
			activeColor = new Color(getColor(seed).r*0.8f + num*0.2f, getColor(seed).g*0.8f + num*0.2f, getColor(seed).b*0.8f + num*0.2f, 1);
		}
		texMap.setColor(addNoise(activeColor, curX, curY));
		texMap.drawPixel(x, 31-y);
	}
	
	private void mortar(int x, int y, int curX, int curY, Pixmap texMap){
		Color activeColor;
		activeColor = new Color(getColor(seed).r*0.8f, getColor(seed).g*0.8f, getColor(seed).b*0.8f, 1);
		texMap.setColor(addNoise(activeColor, curX, curY));
		texMap.drawPixel(x, 31-y);
	}
	
	
	private Color addNoise(Color c, int x, int y) {
		float num = MathUtils.reductiveNoise2d(seed, x, y, new float[]{4,2,1}, new float[]{3,2,1});
		return new Color(c.r * (0.8f + num*0.4f), c.g * (0.8f + num*0.4f), c.b * (0.8f + num*0.4f), 1);
	}
	
	public static final Color getColor(int seed){
		Random rand = new Random(seed);
		rand.nextFloat();
		float numR = 0.8f+0.4f*rand.nextFloat();
		float numG = 0.8f+0.4f*rand.nextFloat();
		float numB = 0.8f+0.4f*rand.nextFloat();
		Color baseColor = brickColors[(int) (rand.nextFloat()*(brickColors.length))];
		return new Color(baseColor.r*numR, baseColor.g*numG, baseColor.b*numB, 1);
	}
}
