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
			setStats(true, 10, 10, 90, 90, 30, 10, -270, -270, 0.7f, 1f, -90, 0.4f, 2);
			endingZone = LEFT;
		}else{
			setStats(true, 10, 10, 90, 90, 30, 10, -270, -270, 0.7f, 1f, -90, 0.4f, 2);
			endingZone = RIGHT;
		}
	}
	
	public void progressPause(float counter){
		isInUse = true;
		isInAttack = false;
		//if the mouse button is pressed during the pause after the swing, then nextswing is set to true
		if(weapon.owner.equipItems[0] != null && weapon.owner.equipItems[0].equals(weapon) && weapon.owner.leftActivated){
			nextSwing = true; // have to change this for non-players. with owner.attackleft or something
			done = true;
			angle = 90;
			polarAngle = 90;
		}
		else if(weapon.owner.equipItems[1] != null && weapon.owner.equipItems[1].equals(weapon) && weapon.owner.rightActivated){
			nextSwing = true;
			done = true;
			angle = 90;
			polarAngle = 90;
		}
	}
	
	//ticks the swing
	public void progress(){
		counter+=weapon.speed/10f; //progress the counter, the unaccuracy is caused by overshooting durations!
		if(counter - weapon.speed/10f< windupDuration)progressWindup(counter); //we are in the windup phase
		else if(counter - weapon.speed/10f< windupDuration + duration)progressSwing(counter - windupDuration); //we are in the swing phase
		else if(counter - weapon.speed/10f< windupDuration + duration + PAUSE_DURATION*(weapon.speed/10))progressPause((int) (counter/(weapon.speed/10))); // we are in the pause after the swing, in which you can initiate another swing
		else {
			done = true; //this swing is done!
			angle = 90;
			polarAngle = 90;
		}
		
	}

}
