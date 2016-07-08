package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.equipable.weapon.part.axe.AxePart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class AxeBlade extends AxePart {
	
	public static final int NUM = 4;
	
	//axe head named after place?

	public static final Texture[] SPRITES = Spritesheet.getSprites("axeBladeMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicBlade.class.getConstructors()[0],
		SquaredBlade.class.getConstructors()[0],
		Hatchet.class.getConstructors()[0],
		AngularBlade.class.getConstructors()[0],
	};

	public AxeBlade(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		part = BLADE;
		repeatable = false;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Blade: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
