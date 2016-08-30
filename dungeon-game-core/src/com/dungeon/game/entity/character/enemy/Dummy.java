package com.dungeon.game.entity.character.enemy;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Key;
import com.dungeon.game.world.World;

public class Dummy extends Enemy {

	public Dummy(World world, float x, float y) {
		super(world, x, y, 32, 32, "Slot.png");
		
		sprite = new com.dungeon.game.textures.entity.Goon().texture;
		
		name = "Dummy";
		
		maxLife = 100;
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
	}

	@Override
	public void calc() {
	}

	@Override
	public void post() {
	}

	public void dead() {
		for(Entity e: world.entities) if(e instanceof Dummy && !e.equals(this)) return;
		
		Slot slot = new Slot(world, new int[]{0, 0, 0}, null);
		slot.item = new Key(world);
		Drop drop = new Drop(world, x, y, slot);
		world.entities.add(drop);
	}
	
}
