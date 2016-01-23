package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class ArrowGraphic extends Projectile {

	public ArrowGraphic(World world, int x, int y, float angle, float power, Polygon hitbox, float originX, float originY, Weapon weapon) {
		super(world, x, y, angle, power, hitbox, originX, originY, weapon);
		
		this.sprite = new Texture("Arrow.png");
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		fric = 0.1f;
		solid = false;
		name = "arrow";
		this.power = power;
		slot.item = new Arrow(world);
		
	}

	public void post() {}
}
