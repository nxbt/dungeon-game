package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class HiltJab extends SwordSwing {

	public HiltJab(World world) {
		super(world, "Hilt Jab");
	}

	
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		if(prevSwing.endingZone == LEFT){
			setStats(false, 10, 15, 70, 150, 10, 26, 0, 90, 2f, 0.6f, 180, 0, 2);
			endingZone = LEFT;
		}else{
			setStats(false, 10, 15, -70, -150, 10, 26, 0, -90, 2f, 0.6f, 180, 0, 2);
			endingZone = RIGHT;
		}
	}

}
