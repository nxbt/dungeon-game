package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.MeleeGraphic;

public abstract class Melee extends Weapon {

	private MeleeGraphic hitbox;
	
	public int stage;
	
	protected int stageTimer;
	
	protected float knockstr; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public Melee(int damage, int cooldown,int speed, Texture texture) {
		super(damage, cooldown, speed, texture);
	}
	
	public abstract int[] getPos(boolean mousedown, boolean mousepressed);

	public abstract boolean inAttack();
	public abstract void hit(Dynamic e);
}