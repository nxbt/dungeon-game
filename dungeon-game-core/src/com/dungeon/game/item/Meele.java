package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.WeaponGraphic;

public abstract class Meele extends Weapon {

	private WeaponGraphic hitbox;
	
	public int stage;
	
	protected int stageTimer;
	
	protected float[] cur_knockback; //how much the knockback is this frame [x, y];
	
	protected float knockstr; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public Meele(int damage, int cooldown,int speed, Texture texture) {
		super(damage, cooldown, speed, texture);
		cur_knockback = new float[]{0,0};
	}
	
	public abstract int[] getPos(boolean mousedown, boolean mousepressed);

	public abstract boolean inAttack();
	public abstract void hit(Dynamic e);
}
