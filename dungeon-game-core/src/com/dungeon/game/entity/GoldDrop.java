package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class GoldDrop extends Static {
	public int amount;
	
	public GoldDrop(World world, float x, float y, int amount) {
		super(world, x, y);
		
		hitbox = new Polygon(new float[]{4,4,28,4,28,28,4,28});
		
		sprite = new Texture("coin.png");
		
		this.amount = amount;
		
		angle = (float) (180f-Math.random()*360);
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		origin_x = 16;
		origin_y = 16;
	}

	@Override
	public void init() {}

	public void hovered() {
		if(world.mouse.lb_pressed) {
			world.player.gold += amount;
			killMe = true;
		}
	}
	
	@Override
	public void calc() {}

	@Override
	public void post() {}

}
