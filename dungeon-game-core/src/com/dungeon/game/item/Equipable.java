package com.dungeon.game.item;

import java.util.ArrayList;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public abstract class Equipable extends Item {
	public int bonus_hp;
	public int bonus_sp;
	public int bonus_mp;
	
	public ArrayList<Effect> effects;
	
	public Equipable(World world) {
		super(world);
		
		effects = new ArrayList<Effect>();
	}
	
	public void update(World world, Entity ent) {}
	
}
