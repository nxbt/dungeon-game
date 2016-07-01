package com.dungeon.game.entity.furniture;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;


public class Carpet extends Static {

	public Carpet(World world, float x, float y, int width, int height, Color color) {
		super(world, x, y, 16, 16, "carpet.png");
		Pixmap tempMap = new Pixmap(width*16, height*16, Pixmap.Format.RGB888);
		
		Pixmap[] textPixmaps = new Pixmap[textures.length];
		
		for(int i = 0; i < textures.length; i++) {
			if(!textures[i].getTextureData().isPrepared()) textures[i].getTextureData().prepare();
			textPixmaps[i] = textures[i].getTextureData().consumePixmap();
		}
		
		for(int i = 0; i < width; i++) {
			for(int k = 0; k < height; k++) {
				int index = 0;
				int rotation = 0;
				if(i == 0 && k == 0){
					index = 0;
					rotation = 3;
				}else if(i == 0 && k == height - 1){
					index = 0;
					rotation = 2;
				}else if(i == width - 1 && k == 0){
					index = 0;
					rotation = 0;
				}else if(i == width - 1 && k == height - 1){
					index = 0;
					rotation = 1;
				}else if(i == 0){
					index = 1;
					rotation = 3;
				}else if(i == width-1){
					index = 1;
					rotation = 1;
				}else if(k == 0){
					index = 1;
					rotation = 0;
				}else if(k == height-1){
					index = 1;
					rotation = 2;
				}else{
					rotation = 0;
					index = 5;
				}
				Pixmap rotated = Spritesheet.rotatePixmap(textPixmaps[index],rotation);
				tempMap.drawPixmap(rotated, i*16, k*16);
				tempMap.setColor(color);
				tempMap.fillRectangle(i*16, k*16, 16, 16);
//				rotated.dispose();
			}
		}

		
		sprite = new Texture(tempMap);
		
		tempMap.dispose();
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		hitbox = new Polygon(new float[] {0,0,width*16,0,width*16,height*16,0,height*16});
		
		origin_x = d_width/2;
		origin_y = d_height/2;
		
		solid = false;
		rotate = true;
	}

	@Override
	public void calc() {
		
		
	}

	@Override
	public void post() {
		
		
	}

}
