package com.dungeon.game.effect.damage;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class PoisonDamage extends Effect {
	
	private float amount;

	public PoisonDamage(World world, float amount) {
		super(world, "Poison Damage", 0);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		character.poisonDamage(amount);
		killMe = true;
	}
	
	public String getHoveredText() {
		return "You took " + amount + "poison damage";
	}
	
	public int getNum() {
		return (int) amount;
	}

}
