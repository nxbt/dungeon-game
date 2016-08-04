package com.dungeon.game.item.equipable.armor;

import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public class Hat extends Equipable {

	public Hat(World world, String tex) {
		super(world, tex);

		name = "Hat";
		
		type = HELM;
		
		desc = "This small animal squeaks in your arms. /s It's a fucking hat you idiot.";

	}

}
