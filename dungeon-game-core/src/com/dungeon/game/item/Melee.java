package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.MeleeGraphic;

public abstract class Melee extends Weapon {

	private MeleeGraphic hitbox;
	
	protected float knockstr; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public Melee(int damage, int cooldown,int speed, Texture texture) {
		super(damage, cooldown, speed, texture);
		graphic = new MeleeGraphic(this, new Polygon(new float[]{Item.SIZE*0.6f,Item.SIZE*0.2f,Item.SIZE*0.8f,Item.SIZE*0.4f,0,Item.SIZE*1.1f,Item.SIZE*0.1f,Item.SIZE}), 30, 2);
	}

	public abstract boolean inAttack();
	public abstract void hit(Dynamic e);
}
