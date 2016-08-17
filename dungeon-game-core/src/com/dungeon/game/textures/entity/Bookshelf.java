package com.dungeon.game.textures.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.textures.ProceduralTexture;
import com.dungeon.game.utilities.Spritesheet;

public class Bookshelf extends ProceduralTexture {
		private static final int NORM_SHELF = 0;
		private static final int END_SHELF = 1;
		private static final int CORNER_SHELF = 2;
		
		private int[][][] shelfMap;
		
		private Color woodColor;

	public Bookshelf(int width, int height, int[][][] shelfMap) {
		super(16*width, 16*height, new Object[]{shelfMap});
		
	}

	@Override
	public void generateTexture(Object[] args) {
		woodColor = new Color(219f/ 225f, 130f / 255f, 29f / 225f, 1);
		Pixmap texMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		Pixmap shelf = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
		this.shelfMap = (int[][][]) args[0];
		for(int i = 0; i < shelfMap.length; i++){
			for(int k = 0; k < shelfMap[0].length; k++){
				generateShelf(shelf, shelfMap[i][k][0]);
				shelf = Spritesheet.rotatePixmap(shelf, shelfMap[i][k][1]);
				texMap.drawPixmap(shelf, k*16, i*16);
			}
		}
		texture = new Texture(texMap);
	}
	
	private void generateShelf(Pixmap shelf, int type){
		switch(type){
			case 0:
				//draw a normal shelf here!
				shelf.setColor(woodColor);
				shelf.fillRectangle(0,0,16,16);
				shelf.setColor(new Color(woodColor.r*0.7f, woodColor.g*0.7f, woodColor.b*0.7f, 1));
				shelf.drawLine(0, 15, 15, 15);
				shelf.drawLine(0, 0, 0, 15);
				shelf.drawLine(15, 0, 15, 15);
				//we have 14 pixels to draw books,
				//each book needs at least 3 pixles (cover, pages, cover)
				//figure out how to distribute the pixels
				//max 4 books, min 2
				int bookNum = (int)(2+Math.random()*3);
				int totalPages = 14 - 2*bookNum;
				int[] bookWidths = new int[bookNum];
				for(int i = (int)(bookNum*Math.random()); totalPages > 0; i = (i == bookNum -1)?0:i+1){
					if(bookWidths[i] == 0 || Math.random() < 0.3f){
						bookWidths[i]++;
						totalPages--;
					}
				}
				//book lengths max 14 min 10
				int[] bookLengths = new int[bookNum];
				for(int i = 0; i < bookLengths.length; i++){
					bookLengths[i] = (int)(12+Math.random()*4);
				}
				
				//asign the colors of the book covers
				Color[] bookColors = new Color[bookNum];
				for(int i = 0; i < bookColors.length; i++){
					bookColors[i] = new Color((float)(0.2f+Math.random()*0.2f), (float)(0.2f+Math.random()*0.2f), (float) (0.2f+Math.random()*0.2f), 1);
				}
				
				//draw them books!
				int x = 1;
				for(int i = 0; i < bookNum; i++){
					//draw cover
					shelf.setColor(bookColors[i]);
					shelf.drawLine(x,16-bookLengths[i],x+1+bookWidths[i],16-bookLengths[i]);
					shelf.drawLine(x,16-bookLengths[i],x,14);
					shelf.drawLine(x+1+bookWidths[i],16-bookLengths[i],x+1+bookWidths[i],14);
					//draw pages
					for(int curX = x+1; curX < x+1 + bookWidths[i]; curX++){
						for(int curY = 17-bookLengths[i]; curY < 17-bookLengths[i] + bookLengths[i]-2; curY++){
							shelf.setColor(new Color(0.7f+0.3f*((float)(curX-x+1)/(float)bookWidths[i]),0.7f+0.3f*((float)(curX-x+1)/(float)bookWidths[i]),0.7f+0.3f*((float)(curX-x+1)/(float)bookWidths[i]),1));
							shelf.drawPixel(curX, curY);
						}
					}
					x+=bookWidths[i]+2;
				}
			break;
			case 1:
				//draw an end shelf here!
				shelf.setColor(new Color(woodColor.r*0.7f, woodColor.g*0.7f, woodColor.b*0.7f, 1));
				shelf.drawRectangle(0,0,16,16);
				shelf.setColor(woodColor);
				shelf.fillRectangle(1,1,14,14);
			break;
		}
	}

}
