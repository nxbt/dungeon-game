package com.dungeon.game.item.weapon;

import com.dungeon.game.spell.Spell;
import com.dungeon.game.world.World;

public abstract class Medium extends Weapon {

	public int numSpells;
	
	public Spell[] spells;
	
	public int spell;
	
	public float dmgMod;
	
	public float cooldown;
	
	public Medium(World world, String filename) {
		super(world, filename);
		
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
