package com.dungeon.game.item.equipable.weapon.swing.sword;

import com.dungeon.game.item.equipable.weapon.swing.Swing;
import com.dungeon.game.world.World;

public class Stab extends SwordSwing {

	public Stab(World world) {
		super(world, "Stab");
	}

	
	public void setPrevSwing(Swing prevSwing) {
		this.prevSwing = prevSwing;
		if(prevSwing instanceof Stab){
			if((prevSwing.endingZone == RIGHT && prevSwing.prevSwing != null && prevSwing.prevSwing.endingZone == CENTER)){
				setStats(false, 15, 12, 0, 0, 4, 28, 0, 0, 1.5f, 0.7f, 0, 0.4f, 1f);
				endingZone = CENTER;
			}else if((prevSwing.endingZone == CENTER && prevSwing.prevSwing != null && prevSwing.prevSwing.endingZone == RIGHT)){
				setStats(false, 15, 12, 30, -7, 4, 28, 6, -3, 1.5f, 0.7f, 0, 0.4f, 1f);
				endingZone = LEFT;
			}
			else if(prevSwing.endingZone == LEFT){
				setStats(false, 15, 12, 0, 0, 4, 28, 0, 0, 1.5f, 0.7f, 0, 0.4f, 1f);
				endingZone = CENTER;
			}else if(prevSwing.endingZone == CENTER){
				setStats(false, 15, 12, -40, 7, 4, 28, -12, 3, 0.75f, 0.7f, 0, 0.4f, 1f);
				endingZone = RIGHT;
			}else if(prevSwing.endingZone == RIGHT){
				setStats(false, 15, 12, 0, 0, 4, 28, 0, 0, 1.5f, 0.7f, 0, 0.4f, 1f);
				endingZone = CENTER;
			}
		}else{
			if(prevSwing.endingZone == LEFT){
				setStats(false, 15, 12, 30, -7, 4, 28, 6, -3, 1.5f, 0.7f, 0, 0.4f, 1f);
				endingZone = LEFT;
			}else if(prevSwing.endingZone == CENTER){
				setStats(false, 15, 12, 0, 0, 4, 28, 0, 0, 1.5f, 0.7f, 0, 0.4f, 1f);
				endingZone = CENTER;
			}else if(prevSwing.endingZone == RIGHT){
				setStats(false, 15, 12, -40, 7, 4, 28, -12, 3, 0.75f, 0.7f, 0, 0.4f, 1f);
				endingZone = RIGHT;
			}
		}
	}

}
