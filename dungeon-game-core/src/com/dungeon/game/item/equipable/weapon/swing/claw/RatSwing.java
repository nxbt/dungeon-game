package com.dungeon.game.item.equipable.weapon.swing.claw;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class RatSwing extends ClawSwing {

	public RatSwing(World world) {
		super(world, "Rat Swing");
		
	}

	@Override
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		if(prevSwing.endingZone == LEFT){
			setStats(true, 10, 12, 80, 45, 8, 10, -65, -55, 0.7f, 1, -90, 0.2f, 1);
			endingZone = RIGHT;
			weapon.graphic.toFlip = true;
		}else{
			setStats(true, 10, 12, -80, -45, 8, 10, 65, 55, 0.7f, 1, 90, 0.2f, 1);
			endingZone = LEFT;
			weapon.graphic.toFlip = false;
		}
	}

}
