package com.dungeon.game.item;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.entity.WeaponGraphic;
import com.dungeon.game.world.World;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.Character;

public abstract class Weapon extends Equipable{
	public WeaponGraphic graphic;
	
	public int stage;
	protected int stageTimer;
	
	public Character owner;
	
	public Weapon(World world, Texture texture){
		super(world);
		maxStack = 1;
		type = WEAPON;
		sprite = texture;
	}
	
	protected void changeSprite(Texture texture){
		sprite = texture;
		graphic.sprite = sprite;
	}
	
	public void equip(World world, Character e){
		this.owner = e;
		world.entities.add(this.graphic);
	}
	
	public void unequip(World world, Character e){
		this.owner = null;
		world.entities.remove(this.graphic);
	}
	
	public ArrayList<Effect> hitEffects(){
		ArrayList<Effect> effects = new ArrayList<Effect>();
		
		return effects;
		
	}
	
	public abstract float[] getPos(boolean mousedown, boolean mousepressed);
	
	public abstract boolean isInUse();
}
