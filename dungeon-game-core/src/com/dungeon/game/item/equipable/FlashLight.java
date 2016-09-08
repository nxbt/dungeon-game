package com.dungeon.game.item.equipable;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.HandheldGraphic;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class FlashLight extends Hand {

	public FlashLight(World world) {
		super(world, "flashLight.png");
		name = "Flash Light";
		desc = "A small, hand held Flash Light.";
		graphic = new HandheldGraphic(world,this, new Polygon(new float[]{0,0,32,0,32,32,0,32}), 26,6);
		graphic.light = new Light(world, graphic.x, graphic.y, 50, 100, (int) graphic.angle, 20, 135, 0, graphic);
	}

	@Override
	public void reset() {
		
	}

	@Override
	public float[] getPos(boolean mousedown, boolean mousepressed) {
		if(!leftSide) return new float[]{14,-82,10};
		return new float[]{14,82,-10};
	}

	public boolean isInUse() {
		return false;
	}
	
	public void equip(Character owner, boolean leftSide){
		super.equip(owner, leftSide);
		this.graphic.light.load();
	}
	
	public void unequip(){
		super.unequip();
		this.graphic.light.unload();
	}

}
