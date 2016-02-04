package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Medium;
import com.dungeon.game.item.Wand;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.spell.Spell;
import com.dungeon.game.world.World;

public class MediumGraphic extends WeaponGraphic {

	public MediumGraphic(World world, Weapon weapon, Polygon hitbox, float originX, float originY){
		super(world, weapon);
		this.origin_x = originX;
		this.origin_y = originY;

		this.hitbox = hitbox;
	}

	@Override
	public void calc() {

	}

	@Override
	public void post() {

	}

	public void cast(Spell spell, Medium medium) {
		spell.cast(x,y,angle, medium);
	}

}
