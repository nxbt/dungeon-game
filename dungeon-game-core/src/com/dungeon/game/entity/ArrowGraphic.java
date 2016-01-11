package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;

public class ArrowGraphic extends Projectile {

	public ArrowGraphic(int x, int y, float angle, float power) {
		super(x, y, angle, power);
		// TODO Auto-generated constructor stub
		this.sprite = new Texture("Arrow.png");
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		d_originX = sprite.getWidth();
		d_originY = 0;
	}

}
