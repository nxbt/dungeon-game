package com.dungeon.game.textures;

import com.badlogic.gdx.graphics.Texture;

public abstract class ProceduralTexture {
	
	public Texture texture;
	
	public int width;
	
	public int height;
	
	public ProceduralTexture(int width, int height, Object[] args){
		long startNanoTime = System.nanoTime();
		this.width = width;
		this.height = height;
		generateTexture(args);
		System.out.println(((float)(System.nanoTime() - startNanoTime))/16000000f + " frames to generate a: " + this.getClass().getSimpleName());
	}
	
	public abstract void generateTexture(Object[] args);

}
