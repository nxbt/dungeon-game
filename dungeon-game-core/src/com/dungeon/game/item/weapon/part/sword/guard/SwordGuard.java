package com.dungeon.game.item.weapon.part.sword.guard;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class SwordGuard extends SwordPart {
	
	public static final int NUM = 3;
	public static final Texture[] SPIRTES = Spritesheet.getSprites("swordGuardMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		BasicGuard.class.getConstructors()[0],
		DefendersGuard.class.getConstructors()[0],
		SpikedGuard.class.getConstructors()[0],
	};

	public SwordGuard(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		part = GUARD;
		repeatable = true;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Guard: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}

}
