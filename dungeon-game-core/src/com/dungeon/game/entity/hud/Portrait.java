package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Portrait extends Hud {

	public Portrait(World world, int x, int y) {
		super(world, x, y);

		d_width = 68;
		d_height = 68;
		sprite = Math.random()>0.5?new Texture("face2.png"):new Texture("face.png");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

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
