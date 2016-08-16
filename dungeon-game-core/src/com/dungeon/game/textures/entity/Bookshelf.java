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
				shelf.setColor(new Color(0,0,0,1));
				shelf.drawRectangle(0,0,16,16);
				shelf.setColor(new Color(99f/ 255f, 37f / 255f, 11f / 255f, 1));
				shelf.drawLine(0, 0, 16, 0);
				shelf.drawLine(0, 0, 0, 16);
				shelf.drawLine(16, 0, 16, 16);
				//we have 14 pixels to draw books,
				//each book needs at least 3 pixles (cover, pages, cover)
				//figure out how to distribute the pixels
				//max 4 books, min 2
				int bookNum = (int)(2+Math.random()*2);
				int totalPages = 14 - 2*bookNum;
				int[] bookWidths = new int[bookNum];
				while(totalPages > 0){
					
				}
			break;
			case 1:
				//draw an end shelf here!
				shelf.setColor(new Color(0,0,0,1));
				shelf.drawRectangle(0,0,16,16);
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
