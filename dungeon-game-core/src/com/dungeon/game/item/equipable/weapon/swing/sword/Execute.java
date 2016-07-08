package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class Execute extends SwordSwing {

	public Execute(World world) {
		super(world, "Execute");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		if(prevSwing.endingZone == LEFT){
			setStats(false, 20, 20, 30, 90, 5, 25, -35, 90, 2f, 0, 0, 1, 2f);
			endingZone = RIGHT;
			weapon.graphic.toFlip = true;
		}else{
			setStats(false, 20, 20, -30, -90, 5, 25, 35, -90, 2f, 0, 0, 1, 2f);
			endingZone = LEFT;
			weapon.graphic.toFlip = false;
		}

	}

}
