package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.weapon.WebShooter;
import com.dungeon.game.world.World;

public class Spider extends Enemy{
	
	public Spider(World world, float x, float y) {
		super(world, x, y, 16, 12, "spider.png");
		
		hitbox = new Polygon(new float[] {4,4,28,4,28,28,4,28});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		bodyRad = 1.6f;
		
		name = "Spider";
		
		maxLife = 30;
		maxStam = 100;
		maxMana = 0;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 2f;
		fric = 0.3f;
		
		inv = new Inventory(world, Inventory.DEFAULT_LAYOUT, 10, 100);
		
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31]};
		equipItems = new Equipable[equipSlots.length];
		
		vision = 10;
		hearing = 10;
		
		effects.add(new StamRegen(world, -1, 0.5f));

		equipSlots[0].item = new WebShooter(world); // this will be webs and bite
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
