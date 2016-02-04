package com.dungeon.game.spell;

import com.dungeon.game.effect.LifeRegen;
import com.dungeon.game.effect.PotionSick;
import com.dungeon.game.entity.SpellProjectile;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.LifePotion;
import com.dungeon.game.item.Medium;
import com.dungeon.game.world.World;

public class Heal extends Spell {

	public Heal(World world) {
		super(world);
		mana = 25;
		cooldown = 600;
	}

	@Override
	public void cast(float x, float y, float angle, Medium medium) {
		medium.owner.addEffect(new LifeRegen(world, 600, 100));

	}

	@Override
	public void hit(Character character, Medium weapon, SpellProjectile projectile) {
		

	}

}
