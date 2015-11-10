package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.Player;
import com.dungeon.game.world.World;

public class Hat extends Equipable {
	public Hat() {}
	
	@Override
	public void init() {
		type = HELM;
		
		name = "A hat";
		desc = "As opossed to The Hat.";
		
		sprite = new Texture("hat.png");
		
		maxStack = 1;
		
		stack = 1;
	}

	@Override
	public void calc(World world, Entity ent) {
		
	}
}
