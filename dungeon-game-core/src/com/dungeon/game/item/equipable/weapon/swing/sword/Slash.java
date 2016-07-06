package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class Slash extends SwordSwing {

	public Slash(World world) {
		super(world, "Slash");
	}

	
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		System.out.println(prevSwing.endingZone);
		if(prevSwing.endingZone == LEFT){
			setStats(false, 10, 24, 70, 35, 8, 14, -55, -50, 0.7f, 1, -90, 0.4f, 1);
			endingZone = RIGHT;
		}else{
			setStats(false, 10, 24, -70, -35, 8, 14, 55, 50, 0.7f, 1, 90, 0.4f, 1);
			endingZone = LEFT;
		}
	}

}
