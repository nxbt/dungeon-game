package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class WeaponHitbox extends Static {
	
	private int width;
	private int height;
	
	public WeaponHitbox(int width, int height, Texture texture){
		super(height, height);
		this.width = width;
		this.height = height;
		this.sprite = texture;
	}

	@Override
	public void init() {
		
		
	}

	@Override
	public void calc(World world) {
		
		
	}
}
