package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Mouse extends Hud {

	public Mouse(int x, int y) {
		super(x, y);
	}

	@Override
	public void init() {
		Gdx.input.setCursorCatched(true);
		
		sprite = new Texture("Crosshair.png");
		
		d_width = 16;
		d_height = 16;
		
		d_offx = -8;
		d_offy = -8;
	}

	@Override
	public void calc(World world) {
		
		x += Gdx.input.getDeltaX();
		y -= Gdx.input.getDeltaY();
		
		int screenWidth = (int) world.cam.WIDTH;
		int screenHeight = (int) world.cam.HEIGHT;
		
		if(x < 0) x = 0;
		else if(x > screenWidth) x = screenWidth;
		if(y < 0) y = 0;
		else if(y > screenHeight) y = screenHeight;
	}

}
