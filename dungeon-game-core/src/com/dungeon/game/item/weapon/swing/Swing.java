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
	
	public Swing(World world){
		this.world = world;
		
	}
	
	public void progressTwordWindup(){
		
	}
	
	public void progressTwordSwing(){
		
	}
}
