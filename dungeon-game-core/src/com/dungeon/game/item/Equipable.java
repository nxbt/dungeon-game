package com.dungeon.game.item;

import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public abstract class Equipable extends Item {
	public int bonus_hp;
	public int bonus_sp;
	public int bonus_mp;
	
	public Equipable(World world) {
		super(world);
	}
	
	public void update(World world, Entity ent) {}
	
}
