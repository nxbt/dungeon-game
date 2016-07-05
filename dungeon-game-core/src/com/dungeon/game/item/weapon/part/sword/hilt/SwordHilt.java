package com.dungeon.game.item.weapon.part.sword.hilt;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class SwordHilt extends SwordPart {
	
	public static final int NUM = 1;
	public static final Texture[] SPIRTES = Spritesheet.getSprites("swordHiltMap.png", 32, 32); //why is guard spelled ua and not au??
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicHilt.class.getConstructors()[0],
	};

	public SwordHilt(World world,  String name, Texture sprite) {
		super(world, name, sprite);
		part = HILT;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Hilt: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}

}
