package com.dungeon.game.item.weapon.part.sword;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.weapon.part.Part;

public abstract class SwordPart extends Part {
	
	//parts
	public static final int BLADE = 0;
	public static final int GUARD = 1;
	public static final int HILT = 2;

	public SwordPart(String name, Texture sprite) {
		super(name, sprite);
		type = SWORD;
	}

}
