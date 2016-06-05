package com.dungeon.game.item.ammo;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.WeaponProjectile;
import com.dungeon.game.item.weapon.Ranged;
import com.dungeon.game.world.World;

public class Arrow extends Ammo {

	public Arrow(World world){
		super(world, "arrow.png");
		
		damage = 10;
		
		name = "Arrow";
		desc = "Usually fired from a bow.\n\nDamage: " + damage;
		
		maxStack = 12;
	}

	@Override
	public void hit(Character character, Ranged weapon, WeaponProjectile projectile) {;
		float damage = this.damage*weapon.dmgMod*projectile.getVel()/10;
		
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Stun(world, 30));
		
		if(character.damage(damage, effects)>0){
			
			Vector2 knockback = new Vector2();
			
			knockback.x = projectile.moveVec.x;
			knockback.y = projectile.moveVec.y;
			
			character.acel(knockback, false);
		}
		if(!character.knownEntities.contains(projectile.owner))character.knownEntities.add(projectile.owner);
	}
	
	public String getDesc() {
		return "A common ammo class item generally consumed by using a bow class weapon. Once fired from a bow class weapon, an arrow item will become an arrow projectile, traveling in a straight line and damaging any target in comes into contact with.\n\n\"White fluff on one end, and black pain on the other.\" -Duryin, bow crafter\n\n Damage: " + damage;
	}
}
