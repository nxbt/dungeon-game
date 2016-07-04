package com.dungeon.game.item.weapon.part.sword.blade;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.utilities.Spritesheet;

public abstract class SwordBlade extends SwordPart {
	
	public static int NUM = 1;
	public static Texture[] SPIRTES = Spritesheet.getSprites("swordBladeMap.png", 32, 32);


	public SwordBlade(String name, Texture sprite) {
		super(name, sprite);
		part = BLADE;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Blade: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}
}
