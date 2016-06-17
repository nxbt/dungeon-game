package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.weapon.Medium;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.spell.Spell;
import com.dungeon.game.world.World;

public class MediumGraphic extends HandheldGraphic {

	public MediumGraphic(World world, Weapon weapon, Polygon hitbox, float originX, float originY){
		super(world, weapon, hitbox);
		this.origin_x = originX;
		this.origin_y = originY;
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
