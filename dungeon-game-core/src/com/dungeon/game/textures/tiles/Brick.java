package com.dungeon.game.textures.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Brick extends ProceduralTile {

	public Brick(int seed, int x, int y, int leftTile, int rightTile, int botTile, int topTile) {
		super(new int[]{seed, x, y, leftTile, rightTile, botTile, topTile});
	}

	@Override
	public void generateTexture(int[] args) {
		Pixmap texMap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		
		//variables

		int leftTile = args[3];
		int rightTile = args[4];
		int botTile = args[5];
		int topTile = args[6];
		
		int sides = (leftTile == 1?1:0) + (rightTile == 1?2:0) + (botTile == 1?4:0) + (topTile == 1?8:0);
		
		Color brickColor = new Color(150f / 255f,25f / 255f,14f / 255f,1);
		
		if(sides == 3){
			System.out.println("triggered");
			int curX;
			int curY;
			for(int i = 0; i < 32; i++){
				for(int k = 0; k < 32; k++){
					curX = x*32+i;
					curY = y*32+k;
					texMap.setColor(brickColor);
					texMap.drawPixel(i, 31-k);
				}
			}
		}
		
		texture = new Texture(texMap);
	}

}
