package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Inconspicuous;
import com.dungeon.game.world.World;

public class Hat extends Equipable {
	public Hat(World world) {
		super(world);
		
		effects.add(new Inconspicuous(world, -1));
	}
	
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
