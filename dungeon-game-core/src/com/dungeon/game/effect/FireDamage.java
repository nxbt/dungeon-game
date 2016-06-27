package com.dungeon.game.effect;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class FireDamage extends Effect {
	
	private float amount;

	public FireDamage(World world, float amount) {
		super(world, "Fire Damage", 0);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		character.fireDamage(amount);
		killMe = true;
	}
	
	public String getHoveredText() {
		return "You took " + amount + "fire damage";
	}
	
	public int getNum() {
		return (int) amount;
	}

}
