package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public class Bed extends Static {

	public Bed(World world, float x, float y, int orientation) {
		super(world, x, y, 32, 64, "bed.png");
		Pixmap tempMap = new Pixmap(32, 64, Pixmap.Format.RGB888);

		textures[0] = new com.dungeon.game.textures.entity.Bed().texture;
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
//		rotated.dispose();
		solid = true;
		
		
		dWidth = orientation%2 == 0?32:64;
		dHeight = orientation%2 == 0?64:32;
		
		rotate = true;
		
		originX = 16;
		originY = 32;
		

		if(orientation%2==0){
			hitbox = new Polygon(new float[]{0,0,32,0,32,64,0,64});
			originX = 16;
			originY = 32;
		}
		else{
			hitbox = new Polygon(new float[]{0,0,64,0,64,32,0,32});
			originX = 32;
			originY = 16;
		}
		genVisBox();
	}

	@Override
	public void calc() {
	}

	@Override
	public void post() {
	}

}
