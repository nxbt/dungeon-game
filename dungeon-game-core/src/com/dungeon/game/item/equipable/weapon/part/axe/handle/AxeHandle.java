package com.dungeon.game.item.equipable.weapon.part.axe.handle;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.equipable.weapon.part.axe.AxePart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class AxeHandle extends AxePart {
	
	public static final int NUM = 1;

	public static final Texture[] SPRITES = Spritesheet.getSprites("axeHiltMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicHandle.class.getConstructors()[0],
	};

	public AxeHandle(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		part = HANDLE;
		repeatable = true;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Handle: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
