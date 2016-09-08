package com.dungeon.game.item.ammo;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.effect.Webbed;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.WeaponProjectile;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Silk extends Ammo {

	public Silk(World world){
		super(world, "webShot.png");
		
		damage = 0;
		
		name = "Silk";
		desc = "Ammunition used by spiders.";
		
		maxStack = 12;
	}

	@Override
	public void hit(Character character, Ranged weapon, WeaponProjectile projectile) {
		ArrayList<Effect> effects = new ArrayList<Effect>();
		character.addEffect(new Webbed(world, 300));

		if(!character.knownEntities.contains(projectile.owner))character.knownEntities.add(projectile.owner);
	}
	
	public String getDesc() {
		return "Es silke";
	}

	@Override
	public WeaponProjectile getProjectile(Item item, float x, float y, float angle, float power) {
		Silk s = new Silk(world);
		s.dropChance = 0;
		WeaponProjectile w = new WeaponProjectile(world, (Ranged) item, s, x, y, angle, power, new Polygon(new float[]{1,28,4,31,0,32}), 2, 30, 100);
		w.fric = 0.01f;
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
