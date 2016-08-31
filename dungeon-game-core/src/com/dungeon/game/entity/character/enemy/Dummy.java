package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Key;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public class Dummy extends Enemy {

	public Dummy(World world, float x, float y) {
		super(world, x, y, 32, 32, "Slot.png");
		
		sprite = new com.dungeon.game.textures.entity.Goon().texture;
		
		name = "Dummy";
		
		maxLife = 60;
		maxStam = 0;
		maxMana = 0;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 0;
		mvel = 0;
		fric = 0.7f;
		torq = 0;
		
		angle = 180;
		
		vision = 1;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		dOffX = 0;
		dOffY = 0;
		
		bleeds = false;
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
		
		equipSlots = new Slot[]{
				new Slot(world, new int[]{0, 0, 0}, inv),
				new Slot(world, new int[]{0, 0, 0}, inv)
		};
		
		equipItems = new Equipable[2];
	}

	@Override
	public void calc() {
	}

	@Override
	public void post() {
	}

	public void dead() {
		super.dead();
		for(Entity e: world.entities) if(e instanceof Dummy && !e.equals(this)) return;
		
		Slot slot = new Slot(world, new int[]{0, 0, 0}, null);
		slot.item = new Key(world);
		Drop drop = new Drop(world, x, y, slot);
		world.entities.add(drop);
	}
	
}
