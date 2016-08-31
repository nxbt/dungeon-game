package com.dungeon.game.entity.character.enemy;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.regen.StamRegen;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.item.equipable.weapon.Weapon;
import com.dungeon.game.world.World;

public class TutorialGoon extends Enemy {
	
	boolean ranged;
	
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
		
		acel = 1f;
		mvel = 3.5f;
		fric = 0.5f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		dOffX = 0;
		dOffY = 0;
		
		int[][] invLayout = new int[][] {
			//consumables
			new int[] {1, 8, 8},
			new int[] {1, 48, 8},
			new int[] {1, 88, 8},
			new int[] {1, 128, 8},
			new int[] {1, 168, 8},
			//inventory
			new int[] {0, 8, 48},
			new int[] {0, 48, 48},
			new int[] {0, 88, 48},
			new int[] {0, 128, 48},
			new int[] {0, 168, 48},
			new int[] {0, 8, 88},
			new int[] {0, 48, 88},
			new int[] {0, 88, 88},
			new int[] {0, 128, 88},
			new int[] {0, 168, 88},
			new int[] {0, 8, 128},
			new int[] {0, 48, 128},
			new int[] {0, 88, 128},
			new int[] {0, 128, 128},
			new int[] {0, 168, 128},
			new int[] {0, 8, 168},
			new int[] {0, 48, 168},
			new int[] {0, 88, 168},
			new int[] {0, 128, 168},
			new int[] {0, 168, 168},
			new int[] {0, 8, 208},
			new int[] {0, 48, 208},
			new int[] {0, 88, 208},
			new int[] {0, 128, 208},
			new int[] {0, 168, 208},
			//weapons
			new int[] {2, 208, 8},
			new int[] {2, 248, 8},
			//Armor
			new int[] {7, 208, 48},
			new int[] {6, 208, 88},
			new int[] {5, 208, 128},
			new int[] {4, 208, 168},
			new int[] {3, 208, 208},
			//Rings and Amulet
			new int[] {9, 248, 48},
			new int[] {9, 248, 88},
			new int[] {9, 248, 128},
			new int[] {9, 248, 168},
			new int[] {8, 248, 208},
		};
		
		inv = new Inventory(world, invLayout, 10, 100);
		
		equipSlots = new Slot[]{inv.slot[30],inv.slot[31],inv.slot[32],inv.slot[33],inv.slot[34],inv.slot[35],inv.slot[36],inv.slot[37],inv.slot[38],inv.slot[39],inv.slot[40],inv.slot[41]};
		equipItems = new Equipable[equipSlots.length];

		inv.slot[30].item = new Sword(world, 1, 1, 1, 1);
		inv.slot[30].item.dropChance = 0.5f;
		ranged = false;

		
		
		vision = 10;
		hearing = 10;
		
		effects.add(new StamRegen(world, -1, 0.1f));
	}

	@Override
	public void calc() {
		
		@SuppressWarnings("unchecked")
		ArrayList<Entity> entities = (ArrayList<Entity>) world.entities.clone();
		
		entities.remove(world.player);
		entities.remove(this);
		if(knownEntities.contains(world.player)){
				if(!(world.player.inv.slot[35].item != null && world.player.inv.slot[35].item.name.equals("Inconspicuous Hat"))) findPath(entities, new float[]{world.player.x,world.player.y});
				target_angle = move_angle;
				if(flipX)target_angle+=180;
				if(target_angle > 360)target_angle-=360;
		}
		attacking = false;
		if(equipItems[0] != null){
			boolean attack = false;
			boolean down = true;
			boolean click = false;
			if(world.player.inv.slot[35].item != null && world.player.inv.slot[35].item.name.equals("Inconspicuous Hat")){
				attack = false;
			}
			if(((Weapon) inv.slot[30].item).isInUse())attacking = true;
			((Weapon) inv.slot[30].item).getPos(down&&attack, click&&attack);
			((Weapon)inv.slot[30].item).graphic.calc();
			
		}
		if(attacking){
			mvel = 2.5f;
			torq = 3;
		}
		else{
			mvel = 5;
			torq = 10;
		}
	}
	
	@Override
	protected void activations() {
		if(!ranged&&knownEntities.contains(world.player)&&Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))<90){
			if(Math.random() < 0.9)rightActivated = true;
			else rightActivated = false;
			if(Math.random() < 0.9)leftActivated = true;
			else leftActivated = false;
		}else{
			rightActivated = false;
			leftActivated = false;
		}
	}

	public void post() {
		if(equipItems[0] != null){
			((Hand)equipItems[0]).graphic.updatePos(true);
		}
	}
	
	public void handleEquips() {
		for(int i = 0; i < equipSlots.length; i++){
			if(equipSlots[i].item == null){
				if(equipItems[i] != null){
					equipItems[i].unequip();
					equipItems[i] = null;
				}
			}else{
				if(equipItems[i] == null){
					equipItems[i] = (Equipable) equipSlots[i].item;
					equipItems[i].equip(this, true);
				}else{
					if(!equipSlots[i].item.equals(equipItems[i])){
						equipItems[i].unequip();
						equipItems[i] = (Equipable) equipSlots[i].item;
						equipItems[i].equip(this, true);
					}
				}
			}
			
		}
		
	}
}
