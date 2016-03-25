package com.dungeon.game.entity.hud;

import java.util.ArrayList;

import com.dungeon.game.world.World;

public class HudBackground extends Hud {
	ArrayList<Hud> hudEntities;
	
	public HudBackground(World world) {
		super(world, 0, 0, 640, 360, "hud.png");
	}

	@Override
	public void calc() {
		
	}

	@Override
	public void post() {

	}

}
