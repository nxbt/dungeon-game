package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.light.Light;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public class Fireplace extends Static {

	public Fireplace(World world, float x, float y, int orientation) {
		super(world, x, y, 32, 32, "fireplace.png");
		
		Pixmap tempMap = new Pixmap(32, 32, Pixmap.Format.RGB888);
		if(!textures[0].getTextureData().isPrepared()) textures[0].getTextureData().prepare();
		Pixmap textPixmap = textures[0].getTextureData().consumePixmap();
		tempMap.drawPixmap(textPixmap, 0, 0);
		Pixmap rotated = Spritesheet.rotatePixmap(tempMap,orientation);
		sprite = new Texture(rotated);
		tempMap.dispose();
		
		solid = true;
		hitbox = new Polygon(new float[]{0,0,32,0,32,32,0,32});
		light = new Light(world, x, y, 300, 100, Light.ORANGE, 40, this);
		if(orientation == 0)light.setOffset(0, -18);
		else if(orientation == 1)light.setOffset(-18, 0);
		else if(orientation == 2)light.setOffset(0, 18);
		else if(orientation == 3)light.setOffset(18, 0);

		rotate = true;
		origin_x = 16;
		origin_y = 16;
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
