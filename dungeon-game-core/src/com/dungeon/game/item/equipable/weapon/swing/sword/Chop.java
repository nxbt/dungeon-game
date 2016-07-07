package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class Chop extends SwordSwing {

	public Chop(World world) {
		super(world, "Chop");
	}

	
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		if(prevSwing.endingZone == LEFT){
			setStats(false, 20, 10, 100, 100, 10, 15, 0, 0, 1.3f, 1f, -90, 0, 1.3f);
			endingZone = LEFT;
		}else{
			setStats(false, 20, 10, -100, -100, 10, 15, 0, 0, 1.3f, 1f, 90, 0, 1.3f);
			endingZone = RIGHT;
		}
	}

}
