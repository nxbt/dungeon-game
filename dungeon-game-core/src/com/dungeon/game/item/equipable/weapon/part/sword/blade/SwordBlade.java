package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.equipable.weapon.part.sword.SwordPart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class SwordBlade extends SwordPart {
	
	public static final int NUM = 10;

	public static final Texture[] SPRITES = Spritesheet.getSprites("swordBladeMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicBlade.class.getConstructors()[0],
		SlimBlade.class.getConstructors()[0],
		BroadBlade.class.getConstructors()[0],
		Scimitar.class.getConstructors()[0],
		Rapier.class.getConstructors()[0],
		Falchion.class.getConstructors()[0],
		StoutBlade.class.getConstructors()[0],
		Dao.class.getConstructors()[0],
		Seax.class.getConstructors()[0],
		Needle.class.getConstructors()[0],
	};

	public SwordBlade(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		part = BLADE;
		repeatable = false;
		hitbox = new Polygon(new float[]{24,6,26,8,2,32,0,32,0,30}); // default sword blade hitbox
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Blade: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
