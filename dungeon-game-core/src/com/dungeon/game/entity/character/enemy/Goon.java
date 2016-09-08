package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.ai.CoverFinder;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.weapon.Bow;
import com.dungeon.game.item.equipable.weapon.Melee;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Goon extends Enemy {
	
	boolean ranged;
	
	//temp
	public float[] lineOfCover;

	public Goon(World world, float x, float y) {
		super(world, x, y, 32, 32, "goon.png");
		
		sprite = new com.dungeon.game.textures.entity.Goon().texture;
		
		name = "Goon";
		
		maxLife = 20;
		maxStam = 100;
		maxMana = 0;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		dOffX = 0;
		dOffY = 0;
		
		inv = new Inventory(world, Inventory.DEFAULT_LAYOUT, 10, 100);
		
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31],inv.slot[32],inv.slot[33],inv.slot[34],inv.slot[35],inv.slot[36],inv.slot[37],inv.slot[38],inv.slot[39],inv.slot[40],inv.slot[41]};
		equipItems = new Equipable[equipSlots.length];

		if(Math.random()>0.5){
			equipSlots[0].item = new Bow(world, (float) (0.3 + Math.random()*0.6), 10);
			equipSlots[0].item.dropChance = 0.5f;
			inv.addItem(new Arrow(world), 12, 0.5f);
			ranged = true;
		}
		else {
			equipSlots[0].item = new Sword(world, 1);
			equipSlots[0].item.dropChance = 0.5f;
			ranged = false;
		}
		
		vision = 10;
		hearing = 10;
		
		effects.add(new StamRegen(world, -1, 0.5f));
		
		gold = (int) Math.round(Math.random()*20);
	}

	@Override
	public void calc() {
		if(knownEntities.contains(world.player)){
			if(!fightMode)toggleFightMode();
			if(equipItems[0] instanceof Melee){
				findPath(new float[]{world.player.x,world.player.y});
				moveToTarg();
				moveTo = targetTile;
				if(findDist(world.player) < 90) target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y,world.player.x-x));
				else {
					target_angle = move_angle;
					if(target_angle > 360)target_angle-=360;
				}
			}else{
				lineOfCover = new float[4];
				int[] coverPos = new int[4];
				CoverFinder.findCover(world, this, world.player, coverPos, lineOfCover, true, 10);
				findPath(new float[]{coverPos[0]*Tile.TS + Tile.TS/2,coverPos[1]*Tile.TS + Tile.TS/2});
				moveToTarg();
				moveTo = targetTile;
				target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y,world.player.x-x));
				if(Intersector.distanceSegmentPoint(lineOfCover[0], lineOfCover[1], lineOfCover[2], lineOfCover[3], x, y) < 4){
					if(((Bow)equipItems[0]).stageTimer < 45){
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
		if(!ranged&&knownEntities.contains(world.player)&&Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))<90 && stam > 50){ //stan check needs to change
			if(Math.random() < 0.9)rightActivated = true;
			else rightActivated = false;
			if(Math.random() < 0.9)leftActivated = true;
			else leftActivated = false;
		}else{
			if(equipItems[0] != null){
				boolean down = true;
				if(ranged&&knownEntities.contains(world.player)&&Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))<300){
					if(((Bow)equipItems[0]).stageTimer < 45 || !(Math.sqrt((x - lineOfCover[0])*(x - lineOfCover[0])+(y - lineOfCover[1])*(y - lineOfCover[1])) < 4))down = true;
					else down = false;
				}
				leftActivated = down;
			}
		}
	}

	@Override
	public void post() {}
	
	
}
