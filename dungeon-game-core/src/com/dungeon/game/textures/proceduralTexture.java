package com.dungeon.game.textures;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtil;

public abstract class proceduralTexture {
	
	public Texture texture;
	
	public int width;
	
	public int height;
	
	public proceduralTexture(int width, int height, int args[]){
		this.width = width;
		this.height = height;
		generateTexture(args);
	}
	
	public abstract void generateTexture(int[] args);

}
