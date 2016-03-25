package com.dungeon.game.spritesheet;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spritesheet {
	public static Texture[] getSprites(String filename, int width, int height) {
		Texture[] textures;
		
		Texture tempSheet = new Texture(filename);
		
		int sheetWidth = tempSheet.getWidth()/width;
		int sheetHeight = tempSheet.getHeight()/height;
		
		TextureRegion[] spritesheet = new TextureRegion[sheetWidth * sheetHeight];
		
		for(int i = 0; i < sheetHeight; i++) {
			for(int k = 0; k < sheetWidth; k++) {
				spritesheet[i*sheetWidth+k] = new TextureRegion(new Texture(filename), k*width,i*height,width,height);
			}
		}
		
		textures  = new Texture[spritesheet.length];
		if (!tempSheet.getTextureData().isPrepared()) {
		    tempSheet.getTextureData().prepare();
		}
		Pixmap wholePixmap = tempSheet.getTextureData().consumePixmap();
		
		for(int i = 0; i < textures.length; i++){
			Pixmap tempMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
			for (int x = 0; x < spritesheet[i].getRegionWidth(); x++) {
			    for (int y = 0; y < spritesheet[i].getRegionHeight(); y++) {
			        int colorInt = wholePixmap.getPixel(spritesheet[i].getRegionX() + x, spritesheet[i].getRegionY() + y);
			        tempMap.drawPixel(x, y, colorInt);
			    }
			}
			textures[i] = new Texture(tempMap);
//			tempMap.dispose();
		}
		wholePixmap.dispose();
		
		return textures;
	}
	
	public static Pixmap rotatePixmap (Pixmap src, int numRotates){
	    final int width = src.getWidth();
	    final int height = src.getHeight();
	    Pixmap rotated = new Pixmap(width, height, src.getFormat());
	    
    	for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	        	int destx = x, desty = y;
	        	for(int i = 0; i < numRotates; i++){
	        		int xOff = destx-width/2;
	        		int yOff = desty-height/2;
	        		desty = height/2+(xOff);
	        		destx = width/2-(yOff);
	        	}
	        	if(numRotates == 1||numRotates == 2)destx--;
	        	if(numRotates == 2||numRotates == 3)desty--;
	        	
	        	rotated.drawPixel(destx, desty, src.getPixel(x, y));
	        }
	    }
	    return rotated;

	}
	
}
