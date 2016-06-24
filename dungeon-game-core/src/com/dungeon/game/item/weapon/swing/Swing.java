package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;

public class Swing {
	
	//all durations are in miliseconds
	
	private static final int PAUSE_DURATION = 30;
	
	private World world;
	
	protected Melee weapon;
	
	protected Swing prevSwing; //the previous swing, to access the position of the sword
	
	private boolean cleave; //can the weapon hit multiple enemies?
	
	private int windupDuration;
	
	private int windupDist;
	private int windupAngle;
	private int windupPolarAngle;
	
	private int duration;
	
	private int dist;
	private int angle;
	private int polarAngle;
	
	private int counter; //the swing coutner
	
	protected boolean done; //should the SwingSet progress to the next swing?
	
	protected boolean nextSwing; //true go to next swing, false go to rest
	
	public Swing(World world, boolean cleave, int windupDuration, int windupDist, int windupAngle, int windupPolarAngle, int duration, int dist, int angle, int polarAngle){
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
	public void progressWindup(int counter){
		weapon.graphic.graphic_angle = prevSwing.angle+(prevSwing.angle - windupAngle)/windupDuration*counter;
		weapon.graphic.graphic_pAngle = prevSwing.polarAngle+(prevSwing.polarAngle - windupPolarAngle)/windupDuration*counter;
		weapon.graphic.graphic_dist = prevSwing.dist+(prevSwing.dist - windupDist)/windupDuration*counter;
	}
	
	//called if the swing is in the swing
	public void progressSwing(int counter){
		weapon.graphic.graphic_angle = windupAngle+(windupAngle - angle)/windupDuration*counter;
		weapon.graphic.graphic_pAngle = windupPolarAngle+(windupPolarAngle - polarAngle)/windupDuration*counter;
		weapon.graphic.graphic_dist = windupDist+(windupDist - dist)/windupDuration*counter;
	}
	
	//called if the swing is in the pause after the swing
	public void progressPause(int counter){
		//if the mouse button is pressed during the pause after the swing, then nextswing is set to true
		if(weapon.owner.leftEquiped.equals(weapon) && world.mouse.lb_pressed)nextSwing = true;
		else if(world.mouse.rb_pressed)nextSwing = true;
	}
	
	//ticks the swing
	public void progress(){
		counter+=weapon.speed/10; //progress the counter
		if(counter < windupDuration)progressWindup(counter);
		else if(counter < windupDuration + duration)progressSwing(counter - windupDuration);
		else if(counter < windupDuration + duration + PAUSE_DURATION*(weapon.speed/10))progressPause((int) (counter/(weapon.speed/10)));
		else done = true;
		
	}
	
	//clears all variables for the swing;
	public void beginSwing(){
		counter = 0;
		done = false;
		nextSwing = false;
	}
}
