package com.dungeon.game.item.weapon.part.sword.hilt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.weapon.part.Part;
import com.dungeon.game.item.weapon.part.Part.PartGetter;
import com.dungeon.game.item.weapon.part.sword.SwordPart;
import com.dungeon.game.item.weapon.part.sword.blade.BasicBlade;
import com.dungeon.game.utilities.Spritesheet;

public abstract class SwordHilt extends SwordPart {
	
	public static int NUM = 1;
	public static Texture[] SPIRTES = Spritesheet.getSprites("swordHiltMap.png", 32, 32); //why is guard spelled ua and not au??
	
	public static PartGetter[] parts = new PartGetter[]{
			
	};

	public SwordHilt(String name, Texture sprite) {
		super(name, sprite);
		part = HILT;
	}
	
	public void draw(SpriteBatch batch, float x, float y){
		font.draw(batch, "Hilt: ", x - 70, y + 22);
		batch.draw(slot, x, y, 32, 32);
		batch.draw(sprite, x, y, 32, 32);
	}

}
