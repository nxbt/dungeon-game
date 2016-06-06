package com.dungeon.game.item.equipable;

import java.util.ArrayList;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public abstract class Equipable extends Item {
	public int bonus_hp;
	public int bonus_sp;
	public int bonus_mp;
	
	public float physc_resist;
	public float arcan_resist;
	public float flame_resist;
	public float ligtn_resist;
	public float poisn_resist;
	
	public ArrayList<Effect> effects;
	
	public Equipable(World world, String filename) {
		super(world, filename);
		
		maxStack = 1;
		
		effects = new ArrayList<Effect>();
	}
	
	public void update(World world, Entity ent) {}
	
}
