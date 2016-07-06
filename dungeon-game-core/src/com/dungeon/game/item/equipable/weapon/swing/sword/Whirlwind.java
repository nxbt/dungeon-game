package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class Whirlwind extends SwordSwing {

	public Whirlwind(World world) {
		super(world, "Whirlwind");
	}

	
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		System.out.println(prevSwing.endingZone);
		if(prevSwing.endingZone == LEFT){
			setStats(false, 10, 10, 90, 90, 30, 10, -270, -270, 0.5f, 1.3f, -90, 0, 2);
			endingZone = LEFT;
		}else{
			setStats(false, 10, 10, 90, 90, 30, 10, -270, -270, 0.5f, 1.3f, -90, 0, 2);
			endingZone = RIGHT;
		}
	}

}
