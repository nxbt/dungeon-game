package com.dungeon.game.item;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.world.World;

public abstract class Ranged extends Weapon {

	protected final int ARROW = 1;
	protected final int BOLT = 2;
	protected final int ROCK = 3;
	
	public float dmgMod;
	public float speed;
	public float knockstr;
	public float windupTime;
	public int ammoType;
	public float strength;
	
	public ArrayList<Projectile> projectiles;
	
	public Ranged(World world, Texture texture) {
		super(world, texture);
		projectiles = new ArrayList<Projectile>();
	}

}