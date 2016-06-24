package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;

public class Swing {
	
	private World world;
	
	protected Melee weapon;
	
	protected Swing PrevSwing;
	
	private boolean cleave;
	
	private int windupDuration;
	
	private int windupDist;
	private int windupAngle;
	private int windupPolarAngle;
	
	private int duration;
	
	private int dist;
	private int angle;
	private int polarAngle;
	
	private int counter;
	
	protected boolean done;
	
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
	
	public void progressWindup(){
		
	}
	
	public void progressSwing(){
		
	}
	
	public void progress(){
		counter+=weapon.speed/10;
		if(counter < windupDuration)progressWindup();
		else progressSwing();
		if(counter >= windupDuration + duration)done = true;
	}
	
	public void beginSwing(){
		counter = 0;
		done = false;
	}
}
