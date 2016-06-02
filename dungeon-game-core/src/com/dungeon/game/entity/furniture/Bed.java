package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public class Bed extends Static {

	public Bed(World world, float x, float y, int orientation, Color color) {
		super(world, x, y, 32, 64, "bed.png");
		Pixmap tempMap = new Pixmap(32, 64, Pixmap.Format.RGB888);
		
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		tempMap.setColor(color);
		tempMap.fillRectangle(0, 0, 32, 64);
		sprite = new Texture(tempMap);
		tempMap.dispose();
		solid = true;
		
		rotate = true;
		angle = orientation*90;
		
		origin_x = 16;
		origin_y = 32;
		

		hitbox = new Polygon(new float[]{0,0,32,0,32,64,0,64});
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
