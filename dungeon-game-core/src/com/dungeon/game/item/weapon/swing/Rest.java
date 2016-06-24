package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.world.World;

public class Rest extends Swing{
	
	public Rest(World world, int duration, int dist, int polarAngle, int angle){
		super(world, false, 0, 0, 0, 0, duration, dist, polarAngle, angle, 0, 0, 0, 0);
	}
	
	public void progressSwing(float counter){
		isInUse = true;
		isInAttack = false;
		System.out.println("Winddown");
		weapon.graphic.graphic_angle = (int) (prevSwing.angle-(prevSwing.angle - angle)/duration*counter);
		weapon.graphic.graphic_pAngle = (int) (prevSwing.polarAngle-(prevSwing.polarAngle - polarAngle)/duration*counter);
		weapon.graphic.graphic_dist = (int) (prevSwing.dist-(prevSwing.dist - dist)/duration*counter);
	}
	
	public void progressPause(float counter){
		isInUse = false;
		isInAttack = false;
		System.out.println("At rest");
		if(weapon.owner.leftEquiped != null && weapon.owner.leftEquiped.equals(weapon) && world.mouse.lb_pressed){
			nextSwing = true;
			done = true;
		}
		else if(weapon.owner.rightEquiped != null && weapon.owner.rightEquiped.equals(weapon) && world.mouse.rb_pressed){
			nextSwing = true;
			done = true;
		}
	}
	
	public void progress(){
		counter+=weapon.speed/10; //progress the counter
//		System.out.println("Counter: " + counter);
		if(counter - weapon.speed/10f < duration)progressSwing(counter);
		else progressPause(counter);
	}
	
	public void hit(Character c){
		System.out.println("ERROR: rest.hit should NEVER be called!");
	}
}