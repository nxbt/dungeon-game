package com.dungeon.game.entity.character.enemy;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.effect.ManaRegen;
import com.dungeon.game.effect.StamRegen;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.weapon.Bow;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class Goon extends Enemy {
	
	boolean ranged;

	public Goon(World world, float x, float y) {
		super(world, x, y, 32, 32, "goon.png");
		
name = "Goon";
		
		maxLife = 20;
		maxStam = 100;
		maxMana = 0;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 1.5f;
		mvel = 5;
		fric = 0.5f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		
		origin_x = 16;
		origin_y = 16;
		
		d_offx = 0;
		d_offy = 0;
		
		light = new Light(world, x, y, 300, 100, 0, this);
		
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

		if(Math.random()>0.5){
			inv.slot[30].item = new Bow(world, (float) (0.3 + Math.random()*0.6), 10);
			inv.slot[30].item.dropChance = 0.5f;
			inv.slot[20].item = new Arrow(world);
			inv.slot[20].item.stack = 12;
			inv.slot[20].item.dropChance = 0.5f;
			ranged = true;
		}
		else {
			inv.slot[30].item = new Sword(world, (float) (6 + Math.random()*6),10, 10);
			inv.slot[30].item.dropChance = 0.5f;
			ranged = false;
		}
		
		
		vision = 10;
		hearing = 10;
		
		effects.add(new StamRegen(world, -1, 0.1f));
		effects.add(new ManaRegen(world, -1, 0.1f));
		
		gold = (int) Math.round(Math.random()*20);
	}

	@Override
	public void calc() {
//		if(leftEquiped != null && inv.slot[30].item==null) {
//			unequip(leftEquiped);
//			
//			leftEquiped = null;
//		}
//		else if(leftEquiped == null && inv.slot[30].item != null) {
//			leftEquiped = (Weapon) inv.slot[30].item;
//			
//			equip(leftEquiped, true);
//		}
//		else if(leftEquiped != inv.slot[30].item) {
//			unequip(leftEquiped);
//			
//			leftEquiped = (Weapon) inv.slot[30].item;
//			
//			equip(leftEquiped, true);
//		}
//		
//		if(rightEquiped != null && inv.slot[31].item==null) {
//			unequip(rightEquiped);
//			
//			rightEquiped = null;
//		}
//		else if(rightEquiped == null && inv.slot[31].item != null) {
//			rightEquiped = (Weapon) inv.slot[31].item;
//			
//			equip(rightEquiped, false);
//		}
//		else if(rightEquiped != inv.slot[31].item) {
//			unequip(rightEquiped);
//			
//			rightEquiped = (Weapon) inv.slot[31].item;
//			
//			equip(rightEquiped, false);
//		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Entity> entities = (ArrayList<Entity>) world.entities.clone();
		
		entities.remove(world.player);
		entities.remove(this);
		if(knownEntities.contains(world.player)){
			if(!(world.player.inv.slot[35].item != null && world.player.inv.slot[35].item.name.equals("Inconspicuous Hat"))) findPath(entities, new float[]{world.player.x,world.player.y});
//			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y,world.player.x-x));
		}
		attacking = false;
		System.out.println(equipItems[0]);
		if(equipItems[0] != null){
			boolean attack = false;
			boolean down = true;
			boolean click = false;
			if(ranged&&knownEntities.contains(world.player)&&Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))<300){
				click = true;
				down = Math.random()>0.02;
				attack = true;
			}
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
			if(Math.random() < 0.2)rightActivated = true;
			else rightActivated = false;
			if(Math.random() < 0.2)leftActivated = true;
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
	
	//HAVE TO IMPLEMENT FIGHT MODE FOR NON-PLAYERS
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
					equipItems[i].equip(this, false);
				}else{
					if(!equipSlots[i].item.equals(equipItems[i])){
						equipItems[i].unequip();
						equipItems[i] = (Equipable) equipSlots[i].item;
						equipItems[i].equip(this, false);
					}
				}
			}
			
		}
		
	}
}
