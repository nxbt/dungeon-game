package com.dungeon.game.spell;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.SpellProjectile;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.Medium;
import com.dungeon.game.world.World;

public abstract class Spell {
	
	protected World world;
	
	public Texture sprite;

	public float mana;
	
	public float cooldown;
	
	public Spell(World world) {
		this.world = world;
	}

	public abstract void cast(float x, float y, float angle, Medium medium);

	public abstract void hit(Character character, Medium weapon, SpellProjectile projectile);
}
