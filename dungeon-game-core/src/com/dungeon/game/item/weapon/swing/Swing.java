package com.dungeon.game.item.weapon.swing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.item.weapon.part.Part;
import com.dungeon.game.item.weapon.swing.sword.Slash;
import com.dungeon.game.item.weapon.swing.sword.Stab;
import com.dungeon.game.world.World;

public abstract class Swing implements Cloneable{
	
	//all durations are in miliseconds
	
	protected static final int PAUSE_DURATION = 30;
	
	protected World world;
	
	protected Melee weapon;
	
	private float dmgMult; //the dmg multiplier for this swing
	private float knockMult; //the knockback multiplier for this swing
	private float knockAngleMod; //the knockback angle modifier. 0 = knocked the way the weapon is pointing
	private float knockRatio; //how much knock is the direction of the sword, vs the direction of the hit. 1.0 is all the direction of the sword
	private float stanMult; //the stanima use multiplier for this swing
	
	public Swing prevSwing; //the previous swing, to access the position of the sword
	
	private boolean cleave; //can the weapon hit multiple enemies?
	
	protected float windupDuration;
	
	private float windupDist;
	private float windupAngle;
	private float windupPolarAngle;
	
	protected float duration;
	
	public float dist;
	public float angle;
	public float polarAngle;
	
	protected float counter; //the swing coutner
	
	protected boolean done; //should the SwingSet progress to the next swing?
	
	protected boolean nextSwing; //true go to next swing, false go to rest

	protected boolean isInUse;

	protected boolean isInAttack;
	
	protected boolean hasHit;
	
	protected ArrayList<Character> hitChars;
	
	public String name;
	
	public int endingZone;
	
	public static final int LEFT = 0;
	
	public static final int CENTER = 1;
	
	public static final int RIGHT = 2;
	
	public Swing(World world, String name){
		this.world = world;
		
		this.name = name;
		
		isInUse = false;
		isInAttack = false;
		hasHit = false;
		
		hitChars = new ArrayList<Character>();
	}
	
	public void setStats(boolean cleave, int windupDuration, int windupDist, int windupPolarAngle, int windupAngle, int duration, int dist, int polarAngle, int angle, float dmgMult, float knockMult, float knockAngleMod, float knockRatio, float stanMult){
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
	}
	
	//called if the swing is in the windup
	public void progressWindup(float counter){ // have to add stuff for right side
		if(counter >= windupDuration&&((weapon.owner.equipItems[0] != null && weapon.owner.equipItems[0].equals(weapon) && weapon.owner.leftActivated) || (weapon.owner.equipItems[1] != null && weapon.owner.equipItems[1].equals(weapon) && weapon.owner.rightActivated))){
			this.counter-=weapon.speed/10f;
		}
		isInUse = true;
		isInAttack = false;
		weapon.graphic.graphic_angle = (int) (prevSwing.angle-(prevSwing.angle - windupAngle)/windupDuration*counter);
		weapon.graphic.graphic_pAngle = (int) (prevSwing.polarAngle-(prevSwing.polarAngle - windupPolarAngle)/windupDuration*counter);
		weapon.graphic.graphic_dist = (int) (prevSwing.dist-(prevSwing.dist - windupDist)/windupDuration*counter);
	}
	
	//called if the swing is in the swing
	public void progressSwing(float counter){ // have to add stuff for right side
		isInUse = true;
		isInAttack = true;
		weapon.graphic.graphic_angle = (int) (windupAngle-(windupAngle - angle)/duration*counter);
		weapon.graphic.graphic_pAngle = (int) (windupPolarAngle-(windupPolarAngle - polarAngle)/duration*counter);
		weapon.graphic.graphic_dist = (int) (windupDist-(windupDist - dist)/duration*counter);
	}
	
	//called if the swing is in the pause after the swing
	public void progressPause(float counter){
		isInUse = true;
		isInAttack = false;
		//if the mouse button is pressed during the pause after the swing, then nextswing is set to true
		if(weapon.owner.equipItems[0] != null && weapon.owner.equipItems[0].equals(weapon) && weapon.owner.leftActivated){
			nextSwing = true; // have to change this for non-players. with owner.attackleft or something
			done = true;
		}
		else if(weapon.owner.equipItems[1] != null && weapon.owner.equipItems[1].equals(weapon) && weapon.owner.rightActivated){
			nextSwing = true;
			done = true;
		}
	}
	
	//ticks the swing
	public void progress(){
		counter+=weapon.speed/10f; //progress the counter, the unaccuracy is caused by overshooting durations!
		if(counter - weapon.speed/10f< windupDuration)progressWindup(counter); //we are in the windup phase
		else if(counter - weapon.speed/10f< windupDuration + duration)progressSwing(counter - windupDuration); //we are in the swing phase
		else if(counter - weapon.speed/10f< windupDuration + duration + PAUSE_DURATION*(weapon.speed/10))progressPause((int) (counter/(weapon.speed/10))); // we are in the pause after the swing, in which you can initiate another swing
		else done = true; //this swing is done!
		
	}
	
	//clears all variables for the swing;
	public boolean beginSwing(){
		hitChars.clear();
		counter = 0;
		done = false;
		nextSwing = false;
		hasHit = false;
		return (weapon.owner.use_stam(weapon.weight*stanMult)); //return true if the user has enough stanima to proceed with the swing
	}
	
	//called if the weapon hits a character during this swing.
	public void hit(Character c){
		if(hitChars.contains(c)) return;
		
		hitChars.add(c);
		
		if(!c.knownEntities.contains(weapon.owner))c.knownEntities.add(weapon.owner); //the target now knows where the attacker is!
		if(!cleave)hasHit = true; //if the weapon is not a cleave weapon, then we update hashit, so it cant hit another enemy this swing.
		float weaponangle = weapon.graphic.angle+135; // get the angle the tip of the sword is pointing so we can calc knockback
		if(c.damage(weapon.damage*dmgMult, weapon.getEffects())>0){ //if the target takes at least 1 dmg...
			
			float xSword = (float) (Math.cos((weaponangle+(weapon.leftSide?knockAngleMod:-knockAngleMod))/180f*Math.PI)*weapon.knockback); //x knockback caused by sword swing
			float ySword = (float) (Math.sin((weaponangle+(weapon.leftSide?knockAngleMod:-knockAngleMod))/180f*Math.PI)*weapon.knockback); //y knockback caused by sword swing
			float xOwner = (float) (Math.cos((weaponangle)/180*Math.PI)*weapon.knockback); //x knockback away from the attacker
			float yOwner = (float) (Math.sin((weaponangle)/180*Math.PI)*weapon.knockback); //y knockback away from the attacker
			Vector2 knockVec = new Vector2(); //create a knockback vector
			knockVec.x = (xSword*(1-knockRatio)+xOwner*(knockRatio))*knockMult; //set the x of the knockVec based on the knockRatio and the knockMult
			knockVec.y = (ySword*(1-knockRatio)+yOwner*(knockRatio))*knockMult; //set the y of the knockVec based on the knockRatio and the knockMult
			c.acel(knockVec, false); //knock that ***** about!
		}
	}
	
	public Swing clone() {
		try {
			return (Swing) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static  Class<?>[] SWINGS;
	
	public static Swing getSwingByName(World world, String name){
		return null;
		
	};
	
	public abstract void setPrevSwing(Swing prevSwing);
	
}
