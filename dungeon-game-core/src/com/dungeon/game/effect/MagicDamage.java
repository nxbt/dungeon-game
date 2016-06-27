package com.dungeon.game.effect;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class MagicDamage extends Effect {
	
	private float amount;

	public MagicDamage(World world, float amount) {
		super(world, "Magic Damage", 0);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		character.magicDamage(amount);
		killMe = true;
	}
	
	public String getHoveredText() {
		return "You took " + amount + "magic damage";
	}
	
	public int getNum() {
		return (int) amount;
	}

}
