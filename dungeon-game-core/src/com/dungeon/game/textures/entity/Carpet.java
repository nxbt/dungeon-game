package com.dungeon.game.textures.entity;

public class Carpet extends ProceduralTexture {
	
	private static final int RANDOM = -1;
	private static final int PLAIN = 0;
	private static final int CHECKERED = 1;
	private static final int ORNIMENTAL = 2;
	private static final int ABSTRACT = 3;
	
	private int type;
	
	
	public Carpet(int width, int height, int type){
		super(16*width, 16*height, new Object[]{type});
		
	}
	
	public void generateTexture(Object[] args) {
		type = (int) args[0];
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		
		texture = new Texture(texMap)
	}

}
