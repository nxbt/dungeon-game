package com.dungeon.game.effect;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class ElectricDamage extends Effect {
	
	private float amount;

	public ElectricDamage(World world, float amount) {
		super(world, "Electric Damage", 0);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		character.electricDamage(amount);
		killMe = true;
	}
	
	public String getHoveredText() {
		return "You took " + amount + "electric damage";
	}
	
	public int getNum() {
		return (int) amount;
	}

}
