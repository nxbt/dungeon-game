package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.MeleeGraphic;

public abstract class Melee extends Weapon {

	private MeleeGraphic hitbox;
	
	protected float knockstr; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public boolean hasHit;
	
	public Melee(int damage, int cooldown,int speed, Texture texture) {
		super(damage, cooldown, speed, texture);
	}

	public abstract boolean inAttack();
}
