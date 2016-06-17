package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.light.Light;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

import box2dLight.PointLight;

public class Torch extends Static {

	public Torch(World world, float x, float y, int orientation) {
		super(world, x, y, 16, 4, "torch.png");
Pixmap tempMap = new Pixmap(16, 4, Pixmap.Format.RGB888);
		
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
//		rotated.dispose();
		solid = false;
		
		d_width = orientation%2 == 0?16:4;
		d_height = orientation%2 == 0?4:16;
		
		rotate = true;
		
		origin_x = 16;
		origin_y = 16;
		

		if(orientation%2==0){
			hitbox = new Polygon(new float[]{0,0,16,0,16,4,0,4});
			origin_x = 8;
			origin_y = 2;
		}
		else{
			hitbox = new Polygon(new float[]{0,0,4,0,4,16,0,16});
			origin_x = 2;
			origin_y = 8;
		}
		
		light = new Light(world, x, y, 300, 100, this);
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
