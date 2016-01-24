package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.spell.Spell;
import com.dungeon.game.world.World;

public abstract class Medium extends Weapon {

	public int numSpells;
	
	public Spell[] spells;
	
	public int spell;
	
	public Medium(World world, Texture texture) {
		super(world, texture);
		
	}
	
	public void nextSpell(){
		spell++;
		if(spell==spells.length)spell = 0;
	}
	
	public void preSpell(){
		spell--;
		if(spell==-1)spell = spells.length-1;
	}
}
