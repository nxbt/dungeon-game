package com.dungeon.game.item.weapon.part.sword.blade;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class SwordBlade extends SwordPart {
	
	public static final int NUM = 2;

	public static final Texture[] SPRITES = Spritesheet.getSprites("swordBladeMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicBlade.class.getConstructors()[0],
		SlimBlade.class.getConstructors()[0],
	};

	public SwordBlade(World world, String name, Texture sprite) {
		super(world, name, sprite);
		part = BLADE;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Blade: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
