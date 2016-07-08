package com.dungeon.game.item.equipable.weapon.swing.axe;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class Chop extends AxeSwing {

	public Chop(World world) {
		super(world, "Chop");
		
	}

	@Override
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		if(prevSwing.endingZone == LEFT){
			setStats(false, 10, 10, 65, 35, 10, 10, 0, 0, 0.6f, 0.7f, -90, 0.4f, 0.7f);
			endingZone = LEFT;
			weapon.graphic.toFlip = true;
		}else{
			setStats(false, 10, 10, -65, -35, 10, 10, 0, 0, 0.6f, 0.7f, 90, 0.4f, 0.7f);
			endingZone = RIGHT;
			weapon.graphic.toFlip = false;
		}
	}

}
