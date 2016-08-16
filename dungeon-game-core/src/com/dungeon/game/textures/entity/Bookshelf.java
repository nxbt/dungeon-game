package com.dungeon.game.textures.entity;

import com.dungeon.game.textures.ProceduralTexture;

public class Bookshelf extends ProceduralTexture {
		private static final int NORM_SHELF = 0;
		private static final int END_SHELF = 1;
		private static final int CORNER_SHELF = 2;
		
		private int width;
		private int height;

	public Bookshelf(int width, int height, int[][][] shelfMap) {
		super(16*width, 16*height, getShelfMap1DArray(width, height, shelfMap));
		
	}

	@Override
	public void generateTexture(int[] args) {

	}
	
	private void getShelfMap1DArray(int width, int height, int[][][] shelfMap){
		this.width = width;
		this.height = height;
	}

}
