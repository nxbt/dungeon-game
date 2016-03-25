package com.dungeon.game.item;

import com.dungeon.game.effect.Inconspicuous;
import com.dungeon.game.world.World;

public class Hat extends Equipable {
	public Hat(World world) {
		super(world, "hat.png");
		
		type = HELM;
		
		name = "Inconspicuous Hat";
		desc = "Not conspicuos.";
		
		maxStack = 1;
		
		stack = 1;
		
		effects.add(new Inconspicuous(world, -1));
	}
	
	public String getDesc() {
		return " ";
	}
}
