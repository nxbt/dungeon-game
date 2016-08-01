package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtils;

public class Brick extends ProceduralTile {

	public Brick(int seed, int x, int y, int sides, int corners) {
		super(new int[]{seed, x, y, sides, corners});
	}

	@Override
	public void generateTexture(int[] args) {
		Pixmap texMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		
		//variables

		int sides = args[3];
		int corners = args[4];
		
		Color brickColor = new Color(150f / 255f,25f / 255f,14f / 255f,1);
		
		int curX;
		int curY;
		
		if(sides == 1 && (corners == 10 || corners == 9 || corners == 12 || corners == 4 || corners == 8 || corners == 5 || corners == 7 || corners == 11 || corners == 14 || corners == 13 || corners ==6)){
//			map[i][k] = tileMap.getTile(thre_id, 3, false);
		}
		else if(sides == 2 && (corners == 3||corners == 6||corners == 10||corners == 8||corners == 2||corners == 12||corners == 13||corners == 7 || corners == 11 || corners == 14 || corners == 9)){
//			map[i][k] = tileMap.getTile(thre_id, 2, false);
		}
		else if(sides == 4 && (corners == 5||corners == 9||corners == 3||corners == 2||corners == 1||corners == 10||corners == 14||corners == 13 || corners == 7 || corners == 11 || corners == 6)){
//			map[i][k] = tileMap.getTile(thre_id, 1, false);
		}
		else if(sides == 8 && (corners == 12||corners == 6||corners == 5||corners == 1||corners == 4||corners == 3||corners == 11||corners == 14 || corners == 13 || corners == 7 || corners == 9)){
//			map[i][k] = tileMap.getTile(thre_id, 0, false);
		}
		else if(sides == 0 && corners == 3){
//			map[i][k] = tileMap.getTile(thre_id, 1, false);
		}
		else if(sides == 0 && corners == 5){
//			map[i][k] = tileMap.getTile(thre_id, 0, false);
		}
		else if(sides == 0 && corners == 12){
//			map[i][k] = tileMap.getTile(thre_id, 3, false);
		}
		else if(sides == 0 && corners == 10){
//			map[i][k] = tileMap.getTile(thre_id, 2, false);
		}
		else if(sides == 1 || sides == 4 || sides == 5) {
//			map[i][k] = tileMap.getTile(strt_id, 0, false);
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					verticalBrick(i, k, curX, curY, brickColor, texMap);
				}
			}
		}
		else if(sides == 2 || sides == 8 || sides == 10) {
//			map[i][k] = tileMap.getTile(strt_id, 1, false);
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					horizonBrick(i, k, curX, curY, brickColor, texMap);
				}
			}
		}
		else if(sides == 14) {
//			map[i][k] = tileMap.getTile(revr_id, 1, false);
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(i > 22){
						verticalBrick(i, k, curX, curY, brickColor, texMap);
					}else{
						horizonBrick(i, k, curX, curY, brickColor, texMap);
						
					}
					texMap.drawPixel(i, 31-k);
				}
			}
		}
		else if(sides == 13) {
//			map[i][k] = tileMap.getTile(revr_id, 0, false);
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(k > 22){
						verticalBrick(i, k, curX, curY, brickColor, texMap);
					}else{
						horizonBrick(i, k, curX, curY, brickColor, texMap);
						
					}
					texMap.drawPixel(i, 31-k);
				}
			}
		}
		else if(sides == 11) {
//			map[i][k] = tileMap.getTile(revr_id, 3, false);
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(i < 9){
						horizonBrick(i, k, curX, curY, brickColor, texMap);
					}else{
						verticalBrick(i, k, curX, curY, brickColor, texMap);
						
					}
					texMap.drawPixel(i, 31-k);
				}
			}
		}
		else if(sides == 7) {
//			map[i][k] = tileMap.getTile(revr_id, 2, false);
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					if(k < 9){
						horizonBrick(i, k, curX, curY, brickColor, texMap);
					}else{
						verticalBrick(i, k, curX, curY, brickColor, texMap);
						
					}
					texMap.drawPixel(i, 31-k);
				}
			}
		}
		else if(sides == 3) {
//			map[i][k] = tileMap.getTile(bent_id, 3, true);
		}
		else if(sides == 6) {
//			map[i][k] = tileMap.getTile(bent_id, 2, false);
		}
		else if(sides == 9) {
//			map[i][k] = tileMap.getTile(bent_id, 0, false);
		}
		else if(sides == 12) {
//			map[i][k] = tileMap.getTile(bent_id, 1, true);
		}
		else if(corners == 1) {
//			map[i][k] = tileMap.getTile(bent_id, 1, false);
		}
		else if(corners == 2) {
//			map[i][k] = tileMap.getTile(bent_id, 2, true);
		}
		else if(corners == 4) {
//			map[i][k] = tileMap.getTile(bent_id, 0, true);
		}
		else if(corners == 8) {
//			map[i][k] = tileMap.getTile(bent_id, 3, false);
		}
		else if(sides == 0 && (corners == 6 || corners == 9 || corners == 15 || corners == 14 || corners == 13 || corners == 11 || corners == 7)){
//			map[i][k] = tileMap.getTile(four_id, 0, false);
		}
		
		texture = new Texture(texMap);
	}
	
	private void verticalBrick(int x, int y, int curX, int curY, Color brickColor, Pixmap texMap){
		Random rand = MathUtils.getRandomFromSeedAndX(seed,(int)(curX/8));
		if(curX%8 == 0 || (curX+1)%8 == 0)texMap.setColor(new Color(brickColor.r*0.8f, brickColor.g*0.8f, brickColor.b*0.8f, 1));
		else texMap.setColor(brickColor);
		texMap.drawPixel(x, 31-y);
	}
	
	private void horizonBrick(int x, int y, int curX, int curY, Color brickColor, Pixmap texMap){
		Random rand = MathUtils.getRandomFromSeedAndX(seed,(int)(curY/8));
		if(curY%8 == 0 || (curY+1)%8 == 0)texMap.setColor(new Color(brickColor.r*0.8f, brickColor.g*0.8f, brickColor.b*0.8f, 1));
		else texMap.setColor(brickColor);
		texMap.drawPixel(x, 31-y);
	}

}
