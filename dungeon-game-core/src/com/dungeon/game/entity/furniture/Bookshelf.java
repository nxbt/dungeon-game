package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public class Bookshelf extends Static{

	public Bookshelf(World world, float x, float y, int width, int height) {
		super(world, x, y, 16, 16, "shelfsheet.png");
		
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
					index = (int) (Math.random()*3);
					rotation = Math.random()>0.5?0:3;
				}else if(i == 0 && k == height - 1){
					index = (int) (Math.random()*3);
					rotation = Math.random()>0.5?2:3;
				}else if(i == width - 1 && k == 0){
					index = (int) (Math.random()*3);
					rotation = Math.random()>0.5?0:1;
				}else if(i == width - 1 && k == height - 1){
					index = (int) (Math.random()*3);
					rotation = Math.random()>0.5?1:2;
				}else if(i == 0){
					index = (int) (Math.random()*3);
					rotation = 3;
				}else if(i == width-1){
					index = (int) (Math.random()*3);
					rotation = 1;
				}else if(k == 0){
					index = (int) (Math.random()*3);
					rotation = 0;
				}else if(k == height-1){
					index = (int) (Math.random()*3);
					rotation = 2;
				}else{
					rotation = 0;
					index = 3;
				}
				Pixmap rotated = Spritesheet.rotatePixmap(textPixmaps[index],rotation);
				tempMap.drawPixmap(rotated, i*16, k*16);
				rotated.dispose();
			}
		}
		
		sprite = new Texture(tempMap);
		
		tempMap.dispose();
		
		d_width = width*16;
		d_height = height*16;
		
		hitbox = new Polygon(new float[] {0,0,width*16,0,width*16,height*16,0,height*16});
		
		origin_x = width*8;
		origin_y = height*8;
		
		solid = true;
	}


	@Override
	public void calc() {
		
	}

	@Override
	public void post() {}
}
