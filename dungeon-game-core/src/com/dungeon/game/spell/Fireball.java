package com.dungeon.game.spell;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.SpellProjectile;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.Medium;
import com.dungeon.game.world.World;

public class Fireball extends Spell {
	
	private float damage;

	public Fireball(World world) {
		super(world);
		this.sprite = new Texture("fireball.png");
		this.damage = 10;
		mana = 10;
		cooldown = 40;
	}

	@Override
	public void cast(float x, float y, float angle, Medium medium) {
		float xOff = (float) (Math.cos((angle+135)/180*Math.PI)*30);

		float yOff = (float) (Math.sin((angle+135)/180*Math.PI)*30);
		world.entities.add(new SpellProjectile(world, medium, this, (x+xOff), (y+yOff), angle, 6, new Polygon(new float[]{0,28,4,28,4,32,0,32}), 0, 32, 70));
		
	}

	@Override
	public void hit(Character character, Medium weapon, SpellProjectile projectile) {
		float damage = this.damage*weapon.dmgMod*projectile.getVel()/10;
		
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Stun(world, 10));
		
		if(character.damage(damage, effects) > 0) {
			Vector2 knockback = new Vector2();
			
			knockback.x = projectile.moveVec.x/2;
			knockback.y = projectile.moveVec.y/2;
			
			character.acel(knockback, false);
		}
		
		if(!character.knownEntities.contains(projectile.owner))character.knownEntities.add(projectile.owner);
		
	}

}