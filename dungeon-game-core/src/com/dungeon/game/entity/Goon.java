package com.dungeon.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.Hat;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class Goon extends Enemy {

	public Goon(int x, int y) {
		super(x, y);

	}

	@Override
	public void init() {
		name = "Goon";
		
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
		
		light = new Light(this, 0.5f);
	}

	@Override
	public void calc(World world) {
		ArrayList<Entity> entities = (ArrayList<Entity>) world.entities.clone();
		entities.remove(world.player);
		entities.remove(this);
		if(!(world.player.inv.slot[35].item != null && world.player.inv.slot[35].item.name.equals("Inconspicuous Hat"))) findPath(world.curFloor.tm,entities, new float[]{world.player.x+world.player.width/2,world.player.y+world.player.height/2});
	
	}

}
