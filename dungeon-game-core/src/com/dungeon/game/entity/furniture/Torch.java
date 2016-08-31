package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.entity.particle.Ember;
import com.dungeon.game.light.Light;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Torch extends Static {
	
	private int orientation;

	public Torch(World world, float x, float y, int orientation) {
		super(world, x*Tile.TS + Tile.TS/2, y*Tile.TS + Tile.TS/2, 16, 4, "torch.png");
		layer = ROOF;
		Pixmap tempMap = new Pixmap(16, 4, Pixmap.Format.RGB888);
		
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
		
		solid = false;
		
		dWidth = orientation%2 == 0?16:4;
		dHeight = orientation%2 == 0?4:16;
		
		this.orientation = orientation;
		
		rotate = true;
		
		if(orientation == 0){
			this.x-=Tile.TS/4;
			originX = 8;
			originY = 2;
		}else if(orientation == 1) {
			this.y+=Tile.TS/4;
			originX = 2;
			originY = 8;
			
		}else if(orientation == 2) {
			this.x+=Tile.TS/4;
			originX = 8;
			originY = 2;
			
		}else if(orientation == 3) {
			this.y-=Tile.TS/4;
			originX = 2;
			originY = 8;
			
		}
		

		if(orientation%2==0){
			hitbox = new Polygon(new float[]{0,0,16,0,16,4,0,4});
		}
		else{
			hitbox = new Polygon(new float[]{0,0,4,0,4,16,0,16});
		}
		genVisBox();
		
		light = new Light(world, x, y, 150, 100, Light.ORANGE, 40, this);
		
		clickable = false;
	}

	@Override
	public void calc() {
		if(Math.random() < 0.05) {
			float xOff = 0;
			float yOff = 0;
			if(orientation == 0){
				if(flipX)xOff-=Tile.TS/4;
				else xOff+=Tile.TS/4;
			}else if(orientation == 1) {
				yOff-=Tile.TS/4;
				
			}else if(orientation == 2) {
				if(flipX)xOff+=Tile.TS/4;
				else xOff-=Tile.TS/4;
				
			}else if(orientation == 3) {
				yOff+=Tile.TS/4;
				
			}

//			x' = x*cos q - y*sin q
//			y' = x*sin q + y*cos q 
			

			world.entities.add(Ember.get(world, (float)(x + xOff*Math.cos(angle/180f*Math.PI) - yOff*Math.sin(angle/180f*Math.PI)), (float) (y + xOff*Math.sin(angle/180f*Math.PI) + yOff*Math.cos(angle/180f*Math.PI)), 0, 0));
		}
	}

	@Override
	public void post() {

	}

}
