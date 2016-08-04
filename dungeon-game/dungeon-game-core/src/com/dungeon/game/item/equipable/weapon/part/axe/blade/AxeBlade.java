package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.equipable.weapon.part.axe.AxePart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class AxeBlade extends AxePart {
	
	public static final int NUM = 8;
	
	//axe head named after place?

	public static final Texture[] SPRITES = Spritesheet.getSprites("axeBladeMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicBlade.class.getConstructors()[0],
		SquaredBlade.class.getConstructors()[0],
		Hatchet.class.getConstructors()[0],
		AngularBlade.class.getConstructors()[0],
		Cleaver.class.getConstructors()[0],
		Saxon.class.getConstructors()[0],
		Triangular.class.getConstructors()[0],
		Tomahawk.class.getConstructors()[0],
	};

	public AxeBlade(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		part = BLADE;
		repeatable = false;
		hitbox = new Polygon(new float[]{0,22,10,12,20,22,10,32}); // default axe blade hitbox
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Blade: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
