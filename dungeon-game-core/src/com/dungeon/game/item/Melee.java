package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.MeleeGraphic;
import com.dungeon.game.world.World;

public abstract class Melee extends Weapon {

	private MeleeGraphic hitbox;
	
	public float damage;
	public float speed;
	
	protected float knockstr; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public boolean hasHit;
	
	public Melee(World world, Texture texture) {
		super(world, texture);
	}

	public abstract boolean inAttack();
}
