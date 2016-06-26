package com.dungeon.game.item.weapon.swing;

import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.character.Character;

public class Swing {
	
	//all durations are in miliseconds
	
	private static final int PAUSE_DURATION = 30;
	
	protected World world;
	
	protected Melee weapon;
	
	private float dmgMult; //the dmg multiplier for this swing
	private float knockMult; //the knockback multiplier for this swing
	private float knockAngleMod; //the knockback angle modifier. 0 = knocked the way the weapon is pointing
	private float knockRatio; //how much knock is the direction of the sword, vs the direction of the hit. 1.0 is all the direction of the sword
	private float stanMult; //the stanima use multiplier for this swing
	
	protected Swing prevSwing; //the previous swing, to access the position of the sword
	
	private boolean cleave; //can the weapon hit multiple enemies?
	
	private float windupDuration;
	
	private float windupDist;
	private float windupAngle;
	private float windupPolarAngle;
	
	protected float duration;
	
	protected float dist;
	protected float angle;
	protected float polarAngle;
	
	protected float counter; //the swing coutner
	
	protected boolean done; //should the SwingSet progress to the next swing?
	
	protected boolean nextSwing; //true go to next swing, false go to rest

	protected boolean isInUse;

	protected boolean isInAttack;
	
	protected boolean hasHit;
	
	public Swing(World world, boolean cleave, int windupDuration, int windupDist, int windupPolarAngle, int windupAngle, int duration, int dist, int polarAngle, int angle, float dmgMult, float knockMult, float knockAngleMod, float knockRatio, float stanMult){
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
		
		this.dmgMult = dmgMult;
		this.knockMult = knockMult;
		this.knockAngleMod = knockAngleMod;
		this.knockRatio = knockRatio;
		this.stanMult = stanMult;
		
		isInUse = false;
		isInAttack = false;
		hasHit = false;
		
	}
	
	//called if the swing is in the windup
	public void progressWindup(float counter){ // have to add stuff for right side
		isInUse = true;
		isInAttack = false;
		System.out.println("Windup");
		weapon.graphic.graphic_angle = (int) (prevSwing.angle-(prevSwing.angle - windupAngle)/windupDuration*counter);
		weapon.graphic.graphic_pAngle = (int) (prevSwing.polarAngle-(prevSwing.polarAngle - windupPolarAngle)/windupDuration*counter);
		weapon.graphic.graphic_dist = (int) (prevSwing.dist-(prevSwing.dist - windupDist)/windupDuration*counter);
	}
	
	//called if the swing is in the swing
	public void progressSwing(float counter){ // have to add stuff for right side
		isInUse = true;
		isInAttack = true;
		System.out.println("Swing");
		weapon.graphic.graphic_angle = (int) (windupAngle-(windupAngle - angle)/duration*counter);
		weapon.graphic.graphic_pAngle = (int) (windupPolarAngle-(windupPolarAngle - polarAngle)/duration*counter);
		weapon.graphic.graphic_dist = (int) (windupDist-(windupDist - dist)/duration*counter);
	}
	
	//called if the swing is in the pause after the swing
	public void progressPause(float counter){
		isInUse = true;
		isInAttack = false;
		System.out.println("Pause");
		//if the mouse button is pressed during the pause after the swing, then nextswing is set to true
		if(weapon.owner.leftEquiped != null && weapon.owner.leftEquiped.equals(weapon) && weapon.owner.leftActivated){
			nextSwing = true; // have to change this for non-players. with owner.attackleft or something
			done = true;
		}
		else if(weapon.owner.rightEquiped != null && weapon.owner.rightEquiped.equals(weapon) && weapon.owner.rightActivated){
			nextSwing = true;
			done = true;
		}
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
	public boolean beginSwing(){
//		System.out.println("BEGIN SWING");
		counter = 0;
		done = false;
		nextSwing = false;
		hasHit = false;
		return (weapon.owner.use_stam(weapon.weight*stanMult));
	}
	
	public void hit(Character c){
		if(!c.knownEntities.contains(weapon.owner))c.knownEntities.add(weapon.owner);
		if(!cleave)hasHit = true;
		float weaponangle = weapon.graphic.angle+135;
		if(c.damage(weapon.damage*dmgMult, weapon.getEffects())>0){
			
			float xSword = (float) (Math.cos((weaponangle+knockAngleMod)/180f*Math.PI)*weapon.knockstr);
			float ySword = (float) (Math.sin((weaponangle+knockAngleMod)/180f*Math.PI)*weapon.knockstr);
			float xOwner = (float) (Math.cos((weaponangle)/180*Math.PI)*weapon.knockstr);
			float yOwner = (float) (Math.sin((weaponangle)/180*Math.PI)*weapon.knockstr);
			Vector2 knockVec = new Vector2();
			knockVec.x = (xSword*(1-knockRatio)+xOwner*(knockRatio))*knockMult;
			knockVec.y = (ySword*(1-knockRatio)+yOwner*(knockRatio))*knockMult;
			c.acel(knockVec, false);
		}
	}
}
