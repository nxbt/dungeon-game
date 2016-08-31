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
	
	private int orientaiton;
	
	private boolean flippedSprite;

	public Torch(World world, float x, float y, int orientation) {
		super(world, x*Tile.TS + Tile.TS/2, y*Tile.TS + Tile.TS/2, 16, 4, "torch.png");
		layer = 3;
		Pixmap tempMap = new Pixmap(16, 4, Pixmap.Format.RGB888);
		
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
		
		solid = false;
		
		this.orientaiton = orientation;
		
		dWidth = orientation%2 == 0?16:4;
		dHeight = orientation%2 == 0?4:16;
		
		rotate = true;
		
		if(orientation == 0){
			originX = 16;
			originY = 2;
		}else if(orientation == 1) {
			originX = 2;
			originY = 0;
			
		}else if(orientation == 2) {
			originX = 0;
			originY = 2;
			
		}else if(orientation == 3) {
			originX = 2;
			originY = 16;
			
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
			
		if(flipX && !flippedSprite){
			if(orientaiton == 0 || orientaiton == 2){ //pls work?
				originX = 16 - originX;
			}else originY = 16 - originY;
			flippedSprite = true;
		}
		if(Math.random() < 0.05) {
			world.entities.add(Ember.get(world, x, y, 0, 0));
		}
	}

	@Override
	public void post() {

	}

}
