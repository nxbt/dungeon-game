package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.world.World;

public class HandheldGraphic extends Dynamic {
	
	public Hand item;
	
	public int graphic_angle;
	public int graphc_pAngle;
	public int graphic_dist;
	
	public HandheldGraphic(World world, Hand item, Polygon hitbox, float originX, float originY) {
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png"); // x and y don't matter, they are set every frame
		sprite = item.sprite;
		
		name = "Graphic";
		this.item = item;
		rotate = true;
		this.hitbox = hitbox;
		
		this.origin_x = originX;
		this.origin_y = originY;
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
		
	}

}
