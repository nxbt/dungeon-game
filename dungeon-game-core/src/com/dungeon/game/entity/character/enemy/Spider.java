package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.ai.CoverFinder;
import com.dungeon.game.effect.Webbed;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.ammo.Silk;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.weapon.Claw;
import com.dungeon.game.item.equipable.weapon.WebShooter;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Spider extends Enemy{
	
	public float[] lineOfCover;
	
	private boolean shootWeb;
	
	private Webbed web;
	
	private Silk silk;
	
	public Spider(World world, float x, float y) {
		super(world, x, y, 32, 32, "spider.png");
		
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

		equipSlots[0].item = new Claw(world, 1); // this will be webs and bite
		equipSlots[1].item = new WebShooter(world); // this will be webs and bite
		inv.addItem(new Silk(world), 12, 0.5f);
		
		web = new Webbed(world, 0);
		
		silk = new Silk(world);
	}

	@Override
	public void calc() {
		if(world.player.hasEffect(web) || findDist(world.player) < 90 || inv.contains(silk) == null)shootWeb = false;
		else shootWeb = true;
		if(knownEntities.contains(world.player)){
			if(!fightMode)toggleFightMode();
			if(!shootWeb){
				//meele shit
				if(findDist(world.player) > 32 || world.player.move_angle != 361){
					findPath(new float[]{world.player.x,world.player.y});
					moveToTarg();
					moveTo = targetTile;
				}
				if(findDist(world.player) < 90) target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y,world.player.x-x));
				else {
					target_angle = move_angle;
					if(target_angle > 360)target_angle-=360;
				}
			}else{
				//ranged shit
				lineOfCover = new float[4];
				int[] coverPos = new int[4];
				CoverFinder.findCover(world, this, world.player, coverPos, lineOfCover, true, 10);
				findPath(new float[]{coverPos[0]*Tile.TS + Tile.TS/2,coverPos[1]*Tile.TS + Tile.TS/2});
				moveToTarg();
				moveTo = targetTile;
				target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y,world.player.x-x));
				if(Intersector.distanceSegmentPoint(lineOfCover[0], lineOfCover[1], lineOfCover[2], lineOfCover[3], x, y) < 4){
					if(((WebShooter)equipItems[1]).stageTimer < 45){
						//move into cover to hide from player
						move_angle = (float) (Math.atan2(lineOfCover[3] - y, lineOfCover[2] - x)/Math.PI*180f);
					}else{
						//move out of cover to get LOS on player
						move_angle = (float) (Math.atan2(lineOfCover[1] - y, lineOfCover[0] - x)/Math.PI*180f);
					}
						
				}
			}
		}
	}
	
	@Override
	protected void activations() {
		rightActivated = false;
		leftActivated = false;
		if(!shootWeb&&knownEntities.contains(world.player)&&findDist(world.player)<90 && stam > 10){ //stan check needs to change
			System.out.println("claw");
			if(Math.random() < 0.9)leftActivated = true;
			else leftActivated = false;
		}else{
			if(shootWeb&&knownEntities.contains(world.player)&&Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))<300 && stam > 10){
				boolean down = true;
				if(((WebShooter)equipItems[1]).stageTimer < 45 || !(Math.sqrt((x - lineOfCover[0])*(x - lineOfCover[0])+(y - lineOfCover[1])*(y - lineOfCover[1])) < 4))down = true;
				else down = false;
				rightActivated = down;
			}
		}
	}
	
	@Override
	public void post() {}
}
