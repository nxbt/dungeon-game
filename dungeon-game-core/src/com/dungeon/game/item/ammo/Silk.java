package com.dungeon.game.item.ammo;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Tangle;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.WeaponProjectile;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Silk extends Ammo {

	public Silk(World world){
		super(world, "vine.png");
		
		damage = 0;
		
		name = "Silk";
		desc = "Ammunition used by spiders.";
		
		maxStack = 12;
	}

	@Override
	public void hit(Character character, Ranged weapon, WeaponProjectile projectile) {
		ArrayList<Effect> effects = new ArrayList<Effect>();
		character.addEffect(new Tangle(world, 300));

		if(!character.knownEntities.contains(projectile.owner))character.knownEntities.add(projectile.owner);
	}
	
	public String getDesc() {
		return "Es silke";
	}

	@Override
	public WeaponProjectile getProjectile(Item item, float x, float y, float angle, float power) {
		WeaponProjectile w = new WeaponProjectile(world, (Ranged) item, new Silk(world), x, y, angle, power, new Polygon(new float[]{1,28,4,31,0,32}), 2, 30, 35);
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
