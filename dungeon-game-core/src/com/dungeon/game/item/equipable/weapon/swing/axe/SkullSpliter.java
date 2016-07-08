package com.dungeon.game.item.equipable.weapon.swing.axe;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class SkullSpliter extends AxeSwing {

	public SkullSpliter(World world) {
		super(world, "Skull Spliter");
		
	}

	@Override
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
			setStats(false, 50, -10, 0, 0, 5, 20, 0, 0, 2f, 0, 0, 1, 1.8f);
			endingZone = CENTER;
	}

}
