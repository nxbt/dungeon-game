package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.world.World;

public class TutorialGoon extends Enemy {
	public float[] lineOfCover;

	public TutorialGoon(World world, float x, float y) {
		super(world, x, y, 32, 32, "goon.png");
		
		sprite = new com.dungeon.game.textures.entity.Goon().texture;
		
		name = "Goon";
		
		maxLife = 15;
		maxStam = 100;
		maxMana = 0;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		torq = 10;
		
		acel = 3.5f;
		fric = 0.3f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		inv = new Inventory(world, Inventory.DEFAULT_LAYOUT, 10, 100);
		
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31],inv.slot[32],inv.slot[33],inv.slot[34],inv.slot[35],inv.slot[36],inv.slot[37],inv.slot[38],inv.slot[39],inv.slot[40],inv.slot[41]};
		equipItems = new Equipable[equipSlots.length];
		
		equipSlots[0].item = new Sword(world, 1, 0, 0, 0);
		
		vision = 10;
		hearing = 10;
		
		effects.add(new StamRegen(world, -1, 0.1f));
	}
	
	@Override
	public void calc() {
		if(knownEntities.contains(world.player)){
			if(!fightMode)toggleFightMode();
			findPath(new float[]{world.player.x,world.player.y});
			moveToTarg();
			moveTo = targetTile;
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
	
	public void post() {}
}
