package com.dungeon.game.item.equipable.weapon.part.claw.main;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.equipable.weapon.part.axe.AxePart;
import com.dungeon.game.item.equipable.weapon.part.claw.ClawPart;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public abstract class ClawMain extends ClawPart {
	
	public static final int NUM = 1;
	
	//axe head named after place?

	public static final Texture[] SPRITES = Spritesheet.getSprites("clawMainMap.png", 32, 32);
	
	public static final Constructor<?>[] parts = new Constructor<?>[]{
		RatClaw.class.getConstructors()[0]
	};

	public ClawMain(World world, String name, Texture sprite, int level) {
		super(world, name, sprite, level);
		part = MAIN;
		repeatable = true;
		hitbox = new Polygon(new float[]{12,12,20,12,20,20,12,20}); // default axe blade hitbox
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Claw: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
