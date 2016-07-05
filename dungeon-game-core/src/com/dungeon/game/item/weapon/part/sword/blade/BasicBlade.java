package com.dungeon.game.item.weapon.part.sword.blade;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.utilities.Spritesheet;

public class BasicBlade extends SwordBlade {
	;

	public BasicBlade() {
		super("Basic Blade", BasicBlade.SPRITES[0]);
		id = 0;
		dmgMult = 1;
		speedMult = 1;
		knockMult = 1;
		weightMult = 1;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

}
