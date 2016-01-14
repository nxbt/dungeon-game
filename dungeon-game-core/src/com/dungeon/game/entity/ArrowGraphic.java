package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class ArrowGraphic extends Projectile {

	public ArrowGraphic(int x, int y, float angle, float power, Polygon hitbox, float originX, float originY) {
		super(x, y, angle, power, hitbox, originX, originY);
		
		this.sprite = new Texture("Arrow.png");
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		fric = 0.2;
		solid = false;
		name = "arrow";
	}

}
