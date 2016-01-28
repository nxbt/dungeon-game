package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class HudBackground extends Hud {

	public HudBackground(World world) {
		super(world, 0, 0);
		sprite = new Texture("hud.png");
		d_width = world.cam.WIDTH;
		d_height = world.cam.HEIGHT;
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
