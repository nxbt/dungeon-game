package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.world.World;

public class Rest extends Swing{
	
	public Rest(World world, int duration, int dist, int polarAngle, int angle){
		super(world, false, 0, 0, 0, 0, duration, dist, polarAngle, angle, 0, 0, 0, 0, 0); //rests have no windup and never hit anything, so we pass in 0 for those values
	}
	
	public void progressSwing(float counter){
		isInUse = true;
		isInAttack = false;
		weapon.graphic.graphic_angle = (int) (prevSwing.angle-(prevSwing.angle - angle)/duration*counter);
		weapon.graphic.graphic_pAngle = (int) (prevSwing.polarAngle-(prevSwing.polarAngle - polarAngle)/duration*counter);
		weapon.graphic.graphic_dist = (int) (prevSwing.dist-(prevSwing.dist - dist)/duration*counter);
	}
	
	public void progressPause(float counter){
		isInUse = false;
		isInAttack = false;
		//if the user tries to attack during rest, done and nextSwing are set to true, so the swingSet knows to proceed.
		if(weapon.owner.leftEquiped != null && weapon.owner.leftEquiped.equals(weapon) && weapon.owner.leftActivated){
			nextSwing = true;
			done = true;
		}
		else if(weapon.owner.rightEquiped != null && weapon.owner.rightEquiped.equals(weapon) && weapon.owner.rightActivated){
			nextSwing = true;
			done = true;
		}
	}
	
	public void progress(){
		counter+=weapon.speed/10; //progress the counter
		if(counter - weapon.speed/10f < duration)progressSwing(counter); //we are in the winddown phase
		else progressPause(counter); //we are at rest
	}
	
	public boolean beginSwing(){
		counter = 0;
		done = false;
		nextSwing = false;
		hasHit = false;
		return true; //rest consumes no stanima so it always returns true
	}
	
	public void hit(Character c){
		System.out.println("ERROR: rest.hit should NEVER be called!");
	}
}