package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.MeleeGraphic;
import com.dungeon.game.world.World;

public abstract class Melee extends Weapon {

	private MeleeGraphic hitbox;
	
	protected float knockstr; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public boolean hasHit;
	
	public Melee(World world, int damage, int cooldown,int speed, Texture texture) {
		super(world, damage, cooldown, speed, texture);
	}

	public abstract boolean inAttack();
}
