package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.weapon.Claw;
import com.dungeon.game.world.World;

public class Rat extends Enemy{
	
	public Rat(World world, float x, float y) {
		super(world, x, y, 16, 12, "rat.png");
		
		hitbox = new Polygon(new float[] {0,0,16,0,16,12,0,12});
		genVisBox();
		
		originX = 8;
		originY = 6;
		
		bodyRad = 1f;
		
		name = "Rat";
		
		maxLife = 20;
		maxStam = 100;
		maxMana = 0;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 1.4f;
		fric = 0.3f;
		
		inv = new Inventory(world, Inventory.DEFAULT_LAYOUT, 10, 100);
		
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31]};
		equipItems = new Equipable[equipSlots.length];
		
		vision = 5;
		hearing = 15;
		
		effects.add(new StamRegen(world, -1, 0.5f));

		equipSlots[0].item = new Claw(world, 1);
		equipSlots[1].item = new Claw(world, 1);
	}

	@Override
	public void calc() {
		if(knownEntities.contains(world.player)){
			if(!fightMode)toggleFightMode();
			if(findDist(world.player) > 26 || world.player.move_angle != 361){
				findPath(new float[]{world.player.x,world.player.y});
				moveToTarg();
				moveTo = targetTile;
			}
			if(findDist(world.player) < 90) target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y,world.player.x-x));
			else {
				target_angle = move_angle;
				if(target_angle > 360)target_angle-=360;
			}
		}
	}
	
	@Override
	protected void activations() {
		if(knownEntities.contains(world.player)&&Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))<90 && stam > 20){
			if(Math.random() < 0.9)rightActivated = true;
			else rightActivated = false;
			if(Math.random() < 0.9)leftActivated = true;
			else leftActivated = false;
		}else{
			rightActivated = false;
			leftActivated = false;
		}
	}
	
	@Override
	public void post() {}
}
