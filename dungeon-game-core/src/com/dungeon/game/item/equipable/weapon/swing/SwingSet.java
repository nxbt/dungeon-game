package com.dungeon.game.item.equipable.weapon.swing;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.equipable.weapon.Melee;
import com.dungeon.game.world.World;

public class SwingSet{

	public Swing[] swings;
	
	private int curSwing;
	
	private World world;
	private Melee weapon;
	
	public boolean isInUse;
	
	public boolean isInAttack;
	
	public boolean repeatable;
	
	
	public SwingSet(World world, Melee weapon, Swing[] swings, boolean repeatable){
		this.world = world;
		this.weapon = weapon;
		this.swings = swings;
		this.repeatable = repeatable;
		
		this.swings[0].weapon = this.weapon;
		
		//set the weapon for all the swings
		for(int i = 1; i < this.swings.length; i++){
			this.swings[i].weapon = this.weapon;
			this.swings[i].setPrevSwing(this.swings[i-1]);
		}
		reset();
		
		isInUse = false;

		isInAttack = false;
		
		weapon.hasHit = false;
		
	}
	
	public void progressWeapon(){
		swings[curSwing].progress(); //progress the current swing
		
		//update some variables
		isInUse = swings[curSwing].isInUse;
		isInAttack = swings[curSwing].isInAttack;
		weapon.hasHit = swings[curSwing].hasHit;
		
		//if the swing is done, we need to figure out what to do!
		if(swings[curSwing].done && ((curSwing != swings.length-1) || (swings[curSwing].counter > swings[curSwing].windupDuration + swings[curSwing].duration + Swing.PAUSE_DURATION*weapon.speed/10f) || repeatable)) { //if not repeatable and on the last swing, you can't force a return to the rest position (don't change it works trust me)
			if(swings[curSwing].nextSwing){ //if nextSwing is true, then we progress to the next swing
				curSwing++; //increase curSwing by 1
				if(curSwing < swings.length)swings[curSwing].setPrevSwing(swings[curSwing-1]);
				if(curSwing == swings.length){ //if it was the last swing we need to go back to the resting position
					if(!repeatable){
						swings[0].setPrevSwing(swings[curSwing-1]); //set rest's prevSwing to the swing that just finished
						curSwing = 0; //set curSwing to 0, the resting position
						swings[curSwing].beginSwing();
					}else{
						if(!swings[1].beginSwing()){
							swings[0].setPrevSwing(swings[curSwing-1]); //if the owner does not have enough stanima to swing, we go to the resting position, gotta update prevSwing!
							curSwing = 0; // set the curSwing to rest
							swings[curSwing].beginSwing(); //begin the resting position
						}else{
							swings[1].setPrevSwing(swings[curSwing-1]); //if its repeatable then we go to the first swing
							curSwing = 1; //set curSwing to 1, the first swing position
						}
					}
					
				}else if(!swings[curSwing].beginSwing()){ //reset variables for the new swing, and check if the owner has stanima to do so
					swings[0].setPrevSwing(swings[curSwing-1]); //if the owner does not have enough stanima to swing, we go to the resting position, gotta update prevSwing!
					curSwing = 0; // set the curSwing to rest
					swings[curSwing].beginSwing(); //begin the resting position
				}
			}else { //if nextSwing is false, we go back to the resting position
				swings[0].setPrevSwing(swings[curSwing]); //set the prevSwing to the swing we just finished
				curSwing = 0; // set the curSwing to rest, aka 0
				swings[curSwing].beginSwing(); // begin the rest swing.
			}
		}
		
	}

	public void reset() { //reset function is called when weapons are equiped or unequiped
		swings[0].setPrevSwing(swings[0]);
		curSwing = 0;
		swings[curSwing].beginSwing();
		swings[curSwing].counter = swings[curSwing].duration*weapon.speed/10*2;
	}
	
	public void hit(Character c){ //called when the weapon hits a character
		swings[curSwing].hit(c); //call the swing's hit function
		weapon.hasHit = swings[curSwing].hasHit; //update hasHit
	}
	
	public void knockback(Character c){ //called when the weapon hits a weapon
		swings[curSwing].knockback(c); //call the swing's hit function
		weapon.hasHit = swings[curSwing].hasHit; //update hasHit
	}
	
	public void setWorld(World w){ //set the world, not sure if this function is neccicary, but using it for now due to use of static variable
		world = w; //update the world
		for(int i = 0; i < swings.length; i++)swings[i].world = w; //update all the swings worlds
	}

	public void addSwing(Swing swing) {
		Swing[] oldSwings = swings;
		swings = new Swing[swings.length+1];
		for(int i = 0; i < oldSwings.length; i++)swings[i] = oldSwings[i];
		swing.weapon = weapon;
		swing.setPrevSwing(oldSwings[oldSwings.length-1]);
		swings[swings.length-1] = swing;
		
	}

	public void setSwings(Swing[] newSwings) {

		swings = newSwings;
		
		this.swings[0].weapon = this.weapon;
		//set the weapon for all the swings
		for(int i = 1; i < swings.length; i++){
			swings[i].weapon = weapon;
			swings[i].setPrevSwing(swings[i-1]);
		}
		reset();
				
		isInUse = false;
			
		isInAttack = false;
			
		weapon.hasHit = false;
		
	}
}
