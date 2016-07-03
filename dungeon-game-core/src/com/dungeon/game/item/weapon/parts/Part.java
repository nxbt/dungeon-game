package com.dungeon.game.item.weapon.parts;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Part {

	private World world;
	
	public Texture sprite;
	
	//what type of weapon does this part go on?
	public int type;
	
	//types
	public static final int SWORD = 0;
	public static final int AXE = 1;
	public static final int BOW = 2;
	
	//what part of the weapon does it go on?
	public int part;
	
	//parts
	public static final int BLADE = 0;
	public static final int GUARD = 1;
	public static final int HILT = 2;
	
	//what is the unique Id?
	public int id;
	
	//each part has a unique id
	
	//what is the parts name?
	public String name;
	
	//which swings are allowed by this part?
	public String[] allowedSwings;
	
	//which swings are banned by this part?
	public String[] bannedSwings;
	
	//damage multiplier for this part
	public float dmgMult;
	
	//speed multiplier for this part
	public float speedMult;
	
	//knockback multiplier for this part
	public float knockMult;
	
	//stanima multiplier for this part
	public float stanMult;
	
	public Part(World world, String name, Texture sprite, int type, int part, int id, String[] allowedSwings, String[] bannedSwings, float dmgMult, float speedMult, float knockMult, float stanMult){
		this.world = world;
		this.name = name;
		this.sprite = sprite;
		this.type = type;
		this.part = part;
		this.id = id;
		this.allowedSwings = allowedSwings;
		this.bannedSwings = bannedSwings;
		this.dmgMult = dmgMult;
		this.speedMult = speedMult;
		this.knockMult = knockMult;
		this.stanMult = stanMult;
	}

}
