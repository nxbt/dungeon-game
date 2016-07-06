package com.dungeon.game.item.weapon.swing.sword;

import com.dungeon.game.item.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class GuardJab extends SwordSwing {

	public GuardJab(World world) {
		super(world, "Guard Jab");
	}

	
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		System.out.println(prevSwing.endingZone);
		if(prevSwing.endingZone == LEFT){
			setStats(false, 10, 10, -20, 90, 10, 26, -15, 90, 0.5f, 1.3f, -90, 0, 2);
			endingZone = LEFT;
		}else{
			setStats(false, 10, 10, 20, -90, 10, 26, 15, -90, 0.5f, 1.3f, 90, 0, 2);
			endingZone = RIGHT;
		}
	}

}
