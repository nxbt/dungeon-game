package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;

public class Swing {
	
	//all durations are in miliseconds
	
	private static final int PAUSE_DURATION = 30;
	
	protected World world;
	
	protected Melee weapon;
	
	protected Swing prevSwing; //the previous swing, to access the position of the sword
	
	private boolean cleave; //can the weapon hit multiple enemies?
	
	private int windupDuration;
	
	private int windupDist;
	private int windupAngle;
	private int windupPolarAngle;
	
	protected int duration;
	
	protected int dist;
	protected int angle;
	protected int polarAngle;
	
	protected float counter; //the swing coutner
	
	protected boolean done; //should the SwingSet progress to the next swing?
	
	protected boolean nextSwing; //true go to next swing, false go to rest
	
	public Swing(World world, boolean cleave, int windupDuration, int windupDist, int windupPolarAngle, int windupAngle, int duration, int dist, int polarAngle, int angle){
		this.world = world;
		
		this.cleave = cleave;
		
		this.windupDuration = windupDuration;
		
		this.windupDist = windupDist;
		this.windupAngle = windupAngle;
		this.windupPolarAngle = windupPolarAngle;
		
		this.duration = duration;
		
		this.dist = dist;
		this.angle = angle;
		this.polarAngle = polarAngle;
		
	}
	
	//called if the swing is in the windup
	public void progressWindup(float counter){ // have to add stuff for right side
		System.out.println("Windup");
		weapon.graphic.graphic_angle = (int) (prevSwing.angle-(prevSwing.angle - windupAngle)/windupDuration*counter);
		weapon.graphic.graphic_pAngle = (int) (prevSwing.polarAngle-(prevSwing.polarAngle - windupPolarAngle)/windupDuration*counter);
		weapon.graphic.graphic_dist = (int) (prevSwing.dist-(prevSwing.dist - windupDist)/windupDuration*counter);
	}
	
	//called if the swing is in the swing
	public void progressSwing(float counter){ // have to add stuff for right side
		System.out.println("Swing");
		weapon.graphic.graphic_angle = (int) (windupAngle-(windupAngle - angle)/duration*counter);
		weapon.graphic.graphic_pAngle = (int) (windupPolarAngle-(windupPolarAngle - polarAngle)/duration*counter);
		weapon.graphic.graphic_dist = (int) (windupDist-(windupDist - dist)/duration*counter);
	}
	
	//called if the swing is in the pause after the swing
	public void progressPause(float counter){
		System.out.println("Pause");
		//if the mouse button is pressed during the pause after the swing, then nextswing is set to true
		if(weapon.owner.leftEquiped != null && weapon.owner.leftEquiped.equals(weapon) && world.mouse.lb_pressed)nextSwing = true; // have to change this for non-players. with owner.attackleft or something
		else if(weapon.owner.rightEquiped != null && weapon.owner.rightEquiped.equals(weapon) && world.mouse.rb_pressed)nextSwing = true;
	}
	
	//ticks the swing
	public void progress(){
		counter+=weapon.speed/10f; //progress the counter, the unaccuracy is caused by overshooting durations!
//		System.out.println("Counter: " + counter);
		if(counter - weapon.speed/10f< windupDuration)progressWindup(counter);
		else if(counter - weapon.speed/10f< windupDuration + duration)progressSwing(counter - windupDuration);
		else if(counter - weapon.speed/10f< windupDuration + duration + PAUSE_DURATION*(weapon.speed/10))progressPause((int) (counter/(weapon.speed/10)));
		else done = true;
		
	}
	
	//clears all variables for the swing;
	public void beginSwing(){
//		System.out.println("BEGIN SWING");
		counter = 0;
		done = false;
		nextSwing = false;
	}
}
