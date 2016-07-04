package com.dungeon.game.item.weapon.part.sword.guard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.utilities.Spritesheet;

public abstract class SwordGuard extends SwordPart {
	
	public static int NUM = 1;
	public static Texture[] SPIRTES = Spritesheet.getSprites("swordGuardMap.png", 32, 32);

	public SwordGuard(String name, Texture sprite) {
		super(name, sprite);
		part = GUARD;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Guard: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}

}
