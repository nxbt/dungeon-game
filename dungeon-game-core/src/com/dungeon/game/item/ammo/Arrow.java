package com.dungeon.game.item.ammo;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Stun;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.WeaponProjectile;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Arrow extends Ammo {

	public Arrow(World world){
		super(world, "arrow.png");
		
		damage = 10;
		
		name = "Arrow";
		desc = "Common ammunition used for bows.\n\nDamage: " + damage;
		
		maxStack = 12;
	}

	@Override
	public void hit(Character character, Ranged weapon, WeaponProjectile projectile) {
		float damage = this.damage*weapon.dmgMod*projectile.body.getLinearVelocity().len()/5*Tile.PPM;
		
		ArrayList<Effect> effects = new ArrayList<Effect>();
		effects.add(new Stun(world, 30));
		
		if(character.damage(damage, weapon.getEffects())>0){
			
			Vector2 knockback = new Vector2();
			
			knockback.x = projectile.body.getLinearVelocity().x*6;
			knockback.y = projectile.body.getLinearVelocity().y*6;
			
			character.acel(knockback, false);
		}
		if(!character.knownEntities.contains(projectile.owner))character.knownEntities.add(projectile.owner);
	}
	
	public String getDesc() {
		return "Arrow\n  A common to find and easy to create projectile, the arrow is what makes the bow the most used projectile weapon. The arrow consists of three major parts; the shaft, "
				+ "which is a simple wooden stick; the fletching, which is the tuff of feathers near the end, and the head, which is a sharpened black stone. The head is the most difficult "
				+ "piece to craft, to the point where people will spend their whole lives practicing the art of sharpening heads to perfection. These people can be found in some villages "
				+ "and towns, where the head of an arrow and be bought cheaply. Once someone has all three pieces to create an arrow, the process of putting them together is simple enough "
				+ "to be completed at any basic workbench by even an amature.\n  Firing an arrow is a simple matter of latching it to the string of a bow and pulling back on the bow, then "
				+ "releasing. Arrows are sturdy enough that they will sometimes survive an impact intact, where they can then be retrieved and used again. If an arrow comes in contact with "
				+ "its target, the sharp head will dig into flesh with ease, inflicting damage porportional to the speed it was launched at.\n  While enchanting arrows is technically possible, "
				+ "doing so inefficient, and so the art has been long forgotten. Instead, arrows are indirectly imbued by enchanting a bow, which can transfer magical properties to the arrow "
				+ "while it is notched. While this method of magically enhancing arrows is superior to the alternative in most regards, certin enchantments are impossible with this method. "
				+ "for example, some enchanters and mages speculate that an enchantment directly on an arrow may be able to produce an arrow that magically returns to the owner after impact. "
				+ "while many have sought after such an enchantment, little sucsess has been found, and many still doubt that such an enchantment would be possible, especially since the art of "
				+ "arrow enchantment has been long lost.\n\n\"White fluff on one end, and black pain on the other.\" -Duryin, bow crafter";
	}

	@Override
	public WeaponProjectile getProjectile(Item item, float x, float y, float angle, float power) {
		Arrow a = new Arrow(world);
		a.dropChance = 0.5f;
		WeaponProjectile w = new WeaponProjectile(world, (Ranged) item, a, x, y, angle, power, new Polygon(new float[]{1,28,4,31,0,32}), 2, 30, 35);
		w.fric = 0.03f;
		w.getBody(world.curFloor.box2dWorld);
		w.bodyMade = true;
		Vector2 acelVec = new Vector2();
		acelVec.x = (float) Math.cos((angle+135)/180*Math.PI)*power/Tile.PPM;
		acelVec.y = (float) Math.sin((angle+135)/180*Math.PI)*power/Tile.PPM;
		w.body.setLinearVelocity(acelVec);
		w.prevAngle = acelVec.angle();
		return w;
	}
}
