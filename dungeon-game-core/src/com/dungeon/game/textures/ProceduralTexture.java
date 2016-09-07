package com.dungeon.game.textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public abstract class ProceduralTexture {
	
	public static final Color ACTIVE_COLOR = new Color();
	
	public Texture texture;
	
	public int width;
	
	public int height;
	
	public ProceduralTexture(int width, int height, Object[] args){
		long startNanoTime = System.nanoTime();
		this.width = width;
		this.height = height;
		generateTexture(args);
		System.out.println(((float)(System.nanoTime() - startNanoTime))/1000000f + " ms to generate a: " + this.getClass().getSimpleName());
	}
	
	public abstract void generateTexture(Object[] args);

}
