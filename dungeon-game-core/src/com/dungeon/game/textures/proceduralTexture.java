package com.dungeon.game.textures;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.MathUtil;

public abstract class ProceduralTexture {
	
	public Texture texture;
	
	public int width;
	
	public int height;
	
	public ProceduralTexture(int width, int height, int args[]){
		this.width = width;
		this.height = height;
		generateTexture(args);
	}
	
	public abstract void generateTexture(int[] args);

}
