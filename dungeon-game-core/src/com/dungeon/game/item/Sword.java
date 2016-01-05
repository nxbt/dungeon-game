package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Sword extends Weapon {

	public Sword(int damage, int cooldown) {
		super(damage, cooldown, new Texture("Sword.png"));
		desc = "Real sword I swear! \n\n Damage: "+damage+"\n Cooldown: "+cooldown;
	}

	@Override
	public void init() {
		name = "Sword";
		
	}

}
