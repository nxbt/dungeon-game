package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public class Hat extends Equipable {
	public Hat() {}
	
	@Override
	public void init() {
		type = HELM;
		
		name = "Inconspicuous Hat";
		desc = "Not conspicuos.";
		
		sprite = new Texture("hat.png");
		
		maxStack = 1;
		
		stack = 1;
	}
}
