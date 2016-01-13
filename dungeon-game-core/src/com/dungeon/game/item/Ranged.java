package com.dungeon.game.item;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.RangedGraphic;
import com.dungeon.game.entity.Projectile;

public abstract class Ranged extends Weapon {

	protected final int ARROW = 1;
	protected final int BOLT = 2;
	protected final int ROCK = 3;
	
	public float knockstr;
	public float windupTime;
	public int ammoType;
	public float strength;
	
	public ArrayList<Projectile> projectiles;
	
	public Ranged(int damage, int cooldown, int speed, Texture texture) {
		super(damage, cooldown, speed, texture);
		projectiles = new ArrayList<Projectile>();
	}

}