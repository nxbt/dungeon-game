package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.AnimatedTexture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.MathUtils;

public class Mist extends ProceduralTexture implements AnimatedTexture {
	
	private float timer; //timer for animations
	
	private int timerDir;
	
	private int seed; //seed unique to this mist
	
	private int fadeEdges; //weather this mist should fade around the edges. (yes if the mist should fade in, no if it should not fade in OR cover the whole map)
	
	private int x;
	
	private int y;
	
	private Color color;
	
	public Mist(int seed, int x, int y, int width, int height) { //x and y are pixels NOT tiles
		super(width, height, new Object[]{seed, x, y});
	}
	
	public void generateTexture(Object[] args) {
		timer = 0;
		timerDir = 1;
		seed = (Integer) args[0];
		x = (Integer) args[1];
		y = (Integer) args[2];
		color = new Color(1, 1, 1, 1);
		texture = genTexture();
	}
	
	
	public void update(){
		timer++;
	}
	
	public Texture genTexture(){
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		float curX, curY;
		for(int i = 0; i < width; i++){
			for(int k = 0; k < height; k++){
				curX = x+i;
				curY = y+k;
				float num = MathUtils.reductiveNoise3d(seed, curX, curY, timer, new float[]{32,16,8,4,2,1}, new float[]{6,5,4,3,2,1});
				texMap.setColor(new Color(color.r, color.g, color.b, num));
				texMap.drawPixel(i, height - 1 - k);
			}
		}
		return new Texture(texMap);
	}
}
