package com.dungeon.game.item.equipable;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.HandheldGraphic;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class FlashLight extends Hand {

	public FlashLight(World world) {
		super(world, "stick.png");
		name = "Flash Light";
		desc = "A small, hand held Flash Light";
		graphic = new HandheldGraphic(world,this, new Polygon(new float[]{0,0,32,0,32,32,0,32}));
		graphic.light = new Light(world, graphic.x, graphic.y, 300, 100, (int) graphic.angle, 20, 45, 20, graphic);
	}

	@Override
	public void reset() {
		
	}

	@Override
	public float[] getPos(boolean mousedown, boolean mousepressed) {
		if(!leftSide) return new float[]{10,-82,80};
		return new float[]{10,82,80};
	}

	public boolean isInUse() {
		return false;
	}
	
	public void equip(Character owner, boolean leftSide){
		reset();
		
		this.graphic.light.load();
		
		this.owner = owner;
		
		this.leftSide = leftSide;
		
		world.entities.add(world.entities.indexOf(owner)+1,this.graphic);
	}
	
	public void unequip(){
		reset();
		
		this.graphic.light.unload();
		
		this.owner = null;
		
		world.entities.remove(this.graphic);
	}

}
