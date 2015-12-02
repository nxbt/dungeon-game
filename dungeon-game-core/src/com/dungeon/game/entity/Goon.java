package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Goon extends Enemy {

	public Goon(int x, int y) {
		super(x, y);

	}

	@Override
	public void init() {
		name = "Player";
	
	acel = 1.5f;
	mvel = 5;
	fric = 1;
	
	width = 32;
	height = 32;
	
	d_width = 32;
	d_height = 32;
	
	d_offx = 0;
	d_offy = 0;
	
	sprite = new Texture("goon.png");
	
	solid = true;
		
	}

	@Override
	public void calc(World world) {
		findPath(world.curFloor.tm, new float[]{world.player.x+world.player.width/2,world.player.y+world.player.height/2});
	
	}

}
