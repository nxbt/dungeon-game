package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.Crap;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class FireballGraphic extends Projectile {

	public FireballGraphic(World world, int x, int y, float angle, float power, Polygon hitbox, float originX, float originY,
			Weapon weapon) {
		super(world, (int) (x+Math.cos((angle+135)/180*Math.PI)*40), (int) (y+Math.sin((angle+135)/180*Math.PI)*40), angle, power, hitbox, originX, originY, weapon);
		// TODO Auto-generated constructor stub
		this.sprite = new Texture("fireball.png");
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		fric = 0.1f;
		solid = false;
		name = "fireball";
		this.power = power;
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
