package com.dungeon.game.entity.hud;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class HudBackground extends Hud {
	ArrayList<Hud> hudEntities;
	
	public HudBackground(World world) {
		super(world, 0, 0);
		
		sprite = new Texture("hud.png");
		
		d_width = world.cam.WIDTH;
		d_height = world.cam.HEIGHT;
	}

	@Override
	public void calc() {
		
	}

	@Override
	public void post() {

	}

}
