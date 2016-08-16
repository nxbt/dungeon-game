package com.dungeon.game.textures.entity;

import com.dungeon.game.textures.ProceduralTexture;

public class Bookshelf extends ProceduralTexture {
		private static final int NORM_SHELF = 0;
		private static final int END_SHELF = 1;
		private static final int CORNER_SHELF = 2;
		
		private int width;
		private int height;
		private int[][][] shelfMap;

	public Bookshelf(int width, int height, int[][][] shelfMap) {
		super(16*width, 16*height, setShelfMap(width, height, shelfMap));
		
	}

	@Override
	public void generateTexture(int[] args) {
		Pixmap texMap = new Pixmap(width*16, height*16, Pixmap.Format.RGBA8888);
		Pixmap shelf = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
		for(int i = 0; i < shelfMap.length; i++){
			for(int k = 0; k < shelfMap[0].length; k++){
				generateShelf(shelf, shelfMap[i][k][0]);
				SpriteSheet.RotatePixmap(shelf, shelfMap[i][k][1]);
				texMap.drawPixmap(shelf, i*16, k*16);
			}
		}
		texture = new Texture(texMap);
	}
	
	private void generateShelf(Pixmap shelf, int type){
		switch(type){
			case 0:
				//draw a normal shelf here!
			break;
			case 1:
				//draw an end shelf here!
			break;
		}
	}
	
	private int[] getShelfMap1DArray(int width, int height, int[][][] shelfMap){
		this.width = width;
		this.height = height;
		this.shelfMap = shelfMap;
		// int[] args = new int[shelfMap.length*shelfMap[0].length*2];
		// for(int i = 0; i < shelfMap.length; i++){
		// 	for(int k = 0; k < shelfMap[0].length; k++){
		// 		args[i*shelfMap[0].length*2+k*2+0] = shelfMap[i][k][0];
		// 		args[i*shelfMap[0].length*2+k*2+1] = shelfMap[i][k][1];
		// 	}
		// }
		return new int[0];
	}

}
