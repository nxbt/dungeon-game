package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.spell.Spell;
import com.dungeon.game.world.World;

public abstract class Medium extends Weapon {

	public int numSpells;
	
	public Spell[] spells;
	
	public Medium(World world, Texture texture) {
		super(world, texture);
		
	}
}
