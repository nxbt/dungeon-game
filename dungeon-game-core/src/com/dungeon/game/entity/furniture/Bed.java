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
		
		
		d_width = orientation%2 == 0?32:64;
		d_height = orientation%2 == 0?64:32;
		
		rotate = true;
		
		origin_x = 16;
		origin_y = 32;
		

		if(orientation%2==0){
			hitbox = new Polygon(new float[]{0,0,32,0,32,64,0,64});
			origin_x = 16;
			origin_y = 32;
		}
		else{
			hitbox = new Polygon(new float[]{0,0,64,0,64,32,0,32});
			origin_x = 32;
			origin_y = 16;
		}
	}

	@Override
	public void calc() {
	}

	@Override
	public void post() {
	}

}
