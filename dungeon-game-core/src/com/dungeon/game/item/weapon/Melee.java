package com.dungeon.game.item.weapon;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.weapon.swing.Swing;
import com.dungeon.game.item.weapon.swing.SwingSet;
import com.dungeon.game.world.World;

public abstract class Melee extends Weapon {	
	public float damage;
	public float speed;
	
	public float knockback; //str of the knockback of this weapon
	protected float knockratio; //1 = all away from player, 0 = all by weapon movement;
	
	public boolean hasHit;
	
	protected SwingSet swings;
	
	public Melee(World world, String filename) {
		super(world, filename);
	}

	public abstract boolean inAttack();
	
	public abstract void hit(Character e);
	
	public abstract String getDamageText();
	
	public abstract String getSpeedText();
	
	public abstract String getKnockText();
	
	public abstract String getWeightText();
	
	public abstract String[] getAllowedSwings();
	
	public abstract SwingSet getStartSwings();
}
